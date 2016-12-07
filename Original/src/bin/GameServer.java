package bin;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
//import java.net.SocketException;
//import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JLabel;

import bin.Packet.PacketTypes;

public class GameServer extends Thread{

	//public static LinkedList<Food> food = new LinkedList<Food>();
	LinkedList<CarMP> cars = new LinkedList<CarMP>();
	LinkedList<Food> foods = new LinkedList<Food>();
	Map<String, Integer> ranks = new HashMap<String, Integer>();
	private DatagramSocket socket;
	private Game game;
	public int playerNum;
	Handler handler;
	

	public GameServer(Game game, int playerNum){
		this.game = game;
		this.playerNum = playerNum;
		this.handler = game.handler;
		try{
			this.socket = new DatagramSocket(1331);
		}catch(Exception ioe){

		}
		Random rand = new Random();
		for(int i=0; i<50;i+=1){
			int randx = rand.nextInt(Game.WIDTH-40)+21;
			int randy = rand.nextInt(Game.HEIGHT-40)+21;
			Food newFood = new Food(randx,randy,7,7,ObjectId.Food);
			handler.addObject(newFood);
			foods.add(newFood);
		}
	}

	public void run(){
		while(true){
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try{
				socket.receive(packet);
			}catch(Exception e){} 
			parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}

	private void parsePacket(byte[] data, InetAddress address, int port) {
		String msg =new String(data).trim();
		PacketTypes type = Packet.lookupPacket(msg.substring(0, 2));
		Packet packet;
		switch(type){
			default:
			case INVALID: 
				break;
			case LOGIN: 
				packet = new PacketLogin(data);
				System.out.println(packet.getUsername()+" connected...");
				CarMP car = new CarMP((float)packet.getX(),(float)packet.getY(), packet.getW(),packet.getH(),packet.getUsername(),packet.getId(),address, port);
				PacketPlayerNum limitPacket = new PacketPlayerNum(this.playerNum, game.currentPlayer+1);
				String foodCoords = getAllFoodCoords();
				PacketFood foodPacket = new PacketFood(foods);
				this.addConnection(car, (PacketLogin)packet);
				this.sendDataToAllClients(limitPacket.getData());
				this.sendDataToAllClients(foodPacket.getData());
				break;
			case DISCONNECT:
				packet = new PacketDisconnect(data);
				System.out.println(packet.getUsername()+" disconnected from Server...");
				this.removeConnection((PacketDisconnect)packet);
				break;
			case MOVE:
				packet = new PacketMove(data);
				// System.out.println("MOVE: "+packet.getUsername());
	            this.handleMove((PacketMove) packet);
	            break;
	        case NUM: 
	            packet = new PacketPlayerNum(data);
	            this.sendPlayerLimit((PacketPlayerNum)packet);
	        	break;
	        case ENDGAME:
	        	packet = new PacketEndGame(data);
	        	this.removeConnectionEndGame((PacketEndGame)packet, this.getRanking());
	        	break;
	        case EAT:
	        	packet = new PacketEat(data);
	        	this.handleEat((PacketEat)packet);
	        	break;
		}
	}

	private String getRanking() {
		String rank = "06";
		int ctrl=1;
		Iterator<Entry<String, Integer>> it = ranks.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>)it.next();
			if(ctrl==1){
				rank = rank+pair.getKey()+","+pair.getValue();
				ctrl+=1;
			}else{
				rank = rank+","+pair.getKey()+","+pair.getValue();
			}
			it.remove();
		}
		return rank; 
	}

	private CarMP getCarMP(String username) {
		 for (CarMP player : this.cars) {
	            if (player.getUsername().equals(username)) {
	                return player;
	            }
	        }
	        return null;
	}

	private void removeConnection(PacketDisconnect packet) {
		this.cars.remove(getCarMPIndex(packet.getUsername()));
		packet.writeData(this);
	}

	private void removeConnectionEndGame(PacketEndGame packet, String rank) {
		int index = getCarMPIndex(packet.getUsername());
		sendData(rank.getBytes(), this.cars.get(index).ipAddress, this.cars.get(index).port);
		this.cars.remove(index);
		packet.writeData(this);
	}

	private int getCarMPIndex(String username) {
		int index=0;
		for(CarMP car: this.cars){
			if(car.pName.equals(username)){
				break;
			}
			index++;
		}
		return index;
	}

	public void sendData(byte[] data, InetAddress ipAddress, int port){
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try{
			socket.send(packet);
		}catch(Exception e){
			
		}
	}

	public void sendDataToAllClients(byte[] data) {
		for(CarMP c: cars){
			sendData(data, c.ipAddress, c.port);
		}
	}

	public void addConnection(CarMP car, PacketLogin loginPacket) {
		boolean alreadyConnected = false;
		PacketLogin oldCar;
		for(CarMP c: this.cars){
			if(car.getUsername().equalsIgnoreCase(c.pName)){
				if(c.ipAddress == null){
					c.ipAddress = car.ipAddress;
				}
				if(c.port==-1){
					c.port = car.port;
				}
				alreadyConnected = true;
			} else{
				sendData(loginPacket.getData(),c.ipAddress,c.port);
				oldCar = new PacketLogin(c.getUsername(),(int) c.x,(int) c.y, c.width, c.height);
				sendData(oldCar.getData(), car.ipAddress, car.port);
			}
		}
		if(!alreadyConnected){
			this.cars.add(car);
		}
	}
	
	private void handleMove(PacketMove packet) {
		 if (getCarMP(packet.getUsername()) != null) {
	            int index = getCarMPIndex(packet.getUsername());
	            CarMP player = this.cars.get(index);
	            player.x = packet.getX();
	            player.y = packet.getY();
	            packet.writeData(this);
	        }
	}
	
	private void sendPlayerLimit(PacketPlayerNum packet){
		packet.writeData(this);
	}

	private String getAllFoodCoords(){
		String str="";
		for(Food f : foods)
			str += (f.getX() + "," + f.getY() + ",");
		return str;
	}

	private Food searchFood(float x, float y){
		for(GameObject f : this.handler.object){
			if(f.getId()==ObjectId.Food){
				if(f.getX()==x && f.getY()==y) return (Food)f;
			}
		}
		return null;
	}

	// private void modifyPlayer(String username){
	// 	for(GameObject c: this.handler.object){
	// 		if(c.getId()==ObjectId.Car){
	// 			if(((Car)c).getUsername().equals(username)){
	// 				c.setWidth(c.getWidth()+2);
	// 				c.setHeight(c.getHeight()+2);
	// 				((Car)c).setScore(((Car)c).getScore()+1);
	// 			}
	// 		}
	// 	}
	// }
	
	private void handleEat(PacketEat packet){
		this.handler.removeObject(searchFood(packet.getXPos(), packet.getYPos()));
		// modifyPlayer(packet.getUsername());
		packet.writeData(this);
	}
}