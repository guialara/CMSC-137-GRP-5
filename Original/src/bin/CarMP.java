package bin;

//import java.awt.Graphics;
import java.util.LinkedList;
//import java.awt.Color;
//import java.awt.Rectangle;
//import java.awt.Shape;
//import java.awt.geom.RoundRectangle2D;
//import java.awt.geom.RoundRectangle2D.Float;
import java.net.InetAddress;
//import java.awt.Graphics2D;

public class CarMP extends Car{

	public InetAddress ipAddress;
	public int port;
	
	public CarMP(float x, float y, int width, int height, Handler handler, String pName, ObjectId id, InetAddress ipAddress, int port, int score){
		super(x,y,width,height,handler,pName,id, score);
		this.ipAddress = ipAddress;
		this.port = port;
	}

	public CarMP(float x, float y, int width, int height, String pName, ObjectId id, InetAddress ipAddress, int port, int score){
		super(x,y,width,height,null,pName,id, score);
		this.ipAddress = ipAddress;
		this.port = port;
	}

	@Override
	public void tick(LinkedList<GameObject> object){
		super.tick(object);
	}
}
