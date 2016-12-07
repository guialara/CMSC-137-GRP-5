package bin;

public class PacketEat extends Packet{
	
	public float x, y;

	public PacketEat(byte[] data) {
		super(07);
		String[] dataArray = readData(data).split(",");
		this.x = Float.parseFloat(dataArray[0]);
		this.y = Float.parseFloat(dataArray[1]);
	}

	public PacketEat(float x, float y){
		super(07);
		this.x = x;
		this.y = y;
	}

	public void writeData(GameClient client) {
		 client.sendData(getData());
		
	}

	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
		
	}
	
	public byte[] getData() {
		return ("07"+this.x+","+this.y+",extra").getBytes();
	}

	public float getXPos() {
		return x;
	}

	
	public float getYPos() {
		return y;
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

}
