package bin;

public class PacketLogin extends Packet {

    private String username;
    private int x, y,w,h;
    private ObjectId id;

    public PacketLogin(byte[] data) {
        super(00);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
        this.w = Integer.parseInt(dataArray[3]);
        this.h = Integer.parseInt(dataArray[4]);
        this.id = ObjectId.Car;
    }

    public PacketLogin(String username, int x, int y, int h, int w) {
        super(00);
        this.username = username;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.id = ObjectId.Car; 
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
        return ("00"+this.username+","+getX()+","+getY()+","+getW()+","+getH()+","+getId()).getBytes();
    }

    public String getUsername() {
        return username;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getW() {
        return w;
    }
    
    public int getH() {
        return h;
    }

	public ObjectId getId() {
		return id;
	}
    

}
