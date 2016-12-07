package bin;

import java.util.LinkedList;

public class PacketFood extends Packet{
	
	public float[][] foodCoords;

	public PacketFood(byte[] data) {
		super(03);
		String[] dataArray = readData(data).split(",");
		foodCoords = new float[dataArray.length][2];
		for(int i=0;i<dataArray.length-1;i+=2){
			foodCoords[i][0] = Float.parseFloat(dataArray[i]);
			foodCoords[i][1] = Float.parseFloat(dataArray[i+1]);
		}

	}

	public PacketFood(LinkedList<Food> foods){
		super(03);
		foodCoords = new float[foods.size()][2];
		int i=0;
		for(Food f:foods){
			foodCoords[i][0] = f.getX();
			foodCoords[i][1] = f.getY();
			i++;
		}
	}

	public void writeData(GameClient client) {
		 client.sendData(getData());
		
	}

	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
		
	}
	
	public byte[] getData() {
		String str = "03";
		for(int i=0;i<foodCoords.length;i++)
			str+=foodCoords[i][0]+","+foodCoords[i][1]+",";
		str+="extra";
		return str.getBytes();
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
	
		return null;
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
