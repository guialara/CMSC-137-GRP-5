package bin;

import java.util.HashMap;

public class PacketEndGame extends Packet{

	private String username;
	public HashMap<String, Integer> ranking;
	
	
	//ung data na pinapasa nya ay ung ranking
	public PacketEndGame(byte[] data) {
		super(05);
		this.username = readData(data);
	}

	public PacketEndGame(String username){
		super(05);
		this.username = username;
	}
	
	public void writeData(GameClient client) {
		client.sendData(getData());
	}

	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
	}

	public byte[] getData() {
		return ("05" + this.username).getBytes();
	}

	public int getX() {
		return 0;
	}

	public int getY() {
		return 0;
	}

	public int getH() {
		return 0;
	}

	public int getW() {
		return 0;
	}

	public String getUsername() {
		return this.username;
	}

	public ObjectId getId() {
		return null;
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}
}
