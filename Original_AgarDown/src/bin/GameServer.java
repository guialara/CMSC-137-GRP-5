package bin;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
//import java.net.SocketException;
//import java.net.UnknownHostException;
import java.util.LinkedList;
//import java.util.Random;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;

import bin.Packet.PacketTypes;

public class GameServer extends Thread{

	LinkedList<CarMP> cars = new LinkedList<CarMP>();
	Map<String, Integer> ranks = new HashMap<String, Integer>();
	private DatagramSocket socket;
	private Game game;
	public int playerNum;
	Handler handler;
	

	public GameServer(Game game, int playerNum){
		this.game = game;
		this.playerNum = playerNum;
		try{
			this.socket = new DatagramSocket(1331);
		}catch(Exception ioe){

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
				CarMP car = new CarMP((float)packet.getX(),(float)packet.getY(), packet.getW(),packet.getH(),packet.getUsername(),packet.getId(),address, port, 0);
				PacketPlayerNum limitPacket = new PacketPlayerNum(this.playerNum, this.game.currentPlayer+1);
				this.ranks.put(car.getUsername(), car.getScore());
				this.addConnection(car, (PacketLogin)packet);
				this.sendDataToAllClients(limitPacket.getData());
				break;
			case DISCONNECT:
				packet = new PacketDisconnect(data);
				System.out.println(packet.getUsername()+" disconnected from Server...");
				this.removeConnection((PacketDisconnect)packet);
				break;
			case MOVE:
				packet = new PacketMove(data);
				System.out.println("MOVE: "+packet.getUsername());
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
				oldCar = new PacketLogin(c.getUsername(),(int) c.x,(int) c.y, c.width, c.height, c.getScore());
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
}