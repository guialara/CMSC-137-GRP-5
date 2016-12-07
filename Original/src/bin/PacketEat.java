package bin;

public class PacketEat extends Packet{
	
	public float x, y;
	public String username;

	public PacketEat(byte[] data) {
		super(07);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.x = Float.parseFloat(dataArray[1]);
		this.y = Float.parseFloat(dataArray[2]);
	}

	public PacketEat(String username, float x, float y){
		super(07);
		this.username = username;
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
		return ("07"+this.username+","+this.x+","+this.y+",extra").getBytes();
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
	
		return username;
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
