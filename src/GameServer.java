package bin;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class GameServer extends Thread{
	
	private DatagramSocket socket;

	public GameServer(){
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
			String msg = new String(packet.getData());
			if(msg.trim().equalsIgnoreCase("ping")){
				System.out.println("CLIENT > "+ msg);
				sendData("pong".getBytes(), packet.getAddress(), packet.getPort());	
			}
			
		}
	}

	public void sendData(byte[] data, InetAddress ipAddress, int port){
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);

		try{
			socket.send(packet);
		}catch(Exception e){
			
		}
	}

	public static void main(String[] args){
		if(args.length != 1){
			System.out.println("Usage: java GameServer <number of players>");
			System.exit(1);
		}
		GameServer server = new GameServer();
		server.start();
	}
}