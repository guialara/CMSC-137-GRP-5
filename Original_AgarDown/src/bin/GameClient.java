package bin;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
//import java.net.SocketException;
//import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import bin.Packet.PacketTypes;

public class GameClient extends Thread{
	private InetAddress ipAddress;
	private DatagramSocket socket;
	
	private Game game;

	public GameClient(Game game, String ipAddress){
		this.game = game;

		try{
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(ipAddress);
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
			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}
	private void parsePacket(byte[] data, InetAddress address, int port) {
		String msg =new String(data).trim();
		PacketTypes type = Packet.lookupPacket(msg.substring(0, 2));
		Packet packet;
		switch(type){
			default:
			case INVALID: break;
			case LOGIN: 
				packet = new PacketLogin(data);
				handleLogin((PacketLogin) packet, address, port);
				break;
			case DISCONNECT: 
				packet = new PacketDisconnect(data);				
	            System.out.println(packet.getUsername()+" disconnected...");
	            game.handler.removeObjectFromList(((PacketDisconnect) packet).getUsername());
				break;
			case MOVE:
				 packet = new PacketMove(data);
		         handleMove((PacketMove) packet);
		         break;
			case NUM:
				packet = new PacketPlayerNum(data);
				setPlayerNum((PacketPlayerNum) packet);
				break;
			case ENDGAME:
				packet = new PacketEndGame(data);
				game.handler.removeObjectFromList(((PacketEndGame) packet).getUsername());
				break;
			case RANK: 
				packet = new PacketRank(data);
				HandleEndGame((PacketRank) packet);
		}
		
	}
	private void HandleEndGame(PacketRank packet){
		Iterator<Entry<String, Integer>> it = packet.ranking.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>)it.next();
			System.out.println(pair.getKey()+","+pair.getValue());
			this.game.ranking.put(pair.getKey(), pair.getValue());
			it.remove();
		}
	}
	private void handleMove(PacketMove packet) {
		 this.game.handler.movePlayer(packet.getUsername(), packet.getX(), packet.getY());
	}

	private void handleLogin(PacketLogin packet, InetAddress address, int port) {
		System.out.println(packet.getUsername()+" joined...");
		CarMP car = new CarMP((float)packet.getX(),(float)packet.getY(), packet.getW(),packet.getH(),packet.getUsername(),packet.getId(),address, port, 0);
		game.handler.addObject(car);
	}
	
	private void setPlayerNum (PacketPlayerNum packet){
		this.game.playerNum = packet.playerNum;
		this.game.currentPlayer = packet.currentPlayer;
	}

	public void sendData(byte[] data){
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);

		try{
			socket.send(packet);
		}catch(Exception e){
			
		}
	}
}