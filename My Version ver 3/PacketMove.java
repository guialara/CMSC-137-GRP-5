//package bin;

public class PacketMove extends Packet{

	private String username;
	private int x,y;
	
	private int numSteps = 0;
	private boolean isMoving;

	public PacketMove(byte[] data) {
		super(02);
		String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
	}

	public PacketMove(String username, int x, int y) {
        super(02);
        this.username = username;
        this.x = x;
        this.y = y;
    }
	@Override
	public void writeData(GameClient client) {
		 client.sendData(getData());
		
	}

	@Override
	public void writeData(GameServer server) {
		 server.sendDataToAllClients(getData());	
	}
	@Override
	public byte[] getData() {
		return ("02" + this.username + "," + this.x + "," + this.y).getBytes();
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public int getH() {
		return 0;
	}

	@Override
	public int getW() {
		return 0;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public ObjectId getId() {
		return ObjectId.Car;
	}
}
