package bin;

public class PacketPlayerNum extends Packet{
	
	public int playerNum;
	public int currentPlayer;
	
	public PacketPlayerNum(byte[] data) {
		super(04);
		String[] dataArray = readData(data).split(",");
		this.playerNum = Integer.parseInt(dataArray[0]);
		this.currentPlayer = Integer.parseInt(dataArray[1]);
	}
	
	public PacketPlayerNum(int playerNum, int currentPlayer){
		super(04);
		this.playerNum = playerNum;
		this.currentPlayer = currentPlayer;
	}

	public void writeData(GameClient client) {
		 client.sendData(getData());
		
	}

	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
		
	}
	
	public byte[] getData() {
		return ("04"+this.playerNum+","+this.currentPlayer+",extra").getBytes();
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
