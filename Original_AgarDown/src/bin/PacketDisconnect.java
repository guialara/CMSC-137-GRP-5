package bin;
import bin.GameClient;
import bin.GameServer;
public class PacketDisconnect extends Packet{

	private String username;

	public PacketDisconnect(byte[] data) {
		super(01);
		this.username = readData(data);
	}
	
	public PacketDisconnect(String username){
		super(01);
		this.username = username;
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
		return ("01" + this.username).getBytes();
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public int getX() {
		return 0;
	}

	@Override
	public int getY() {
		return 0;
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
	public ObjectId getId() {
		return null;
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
