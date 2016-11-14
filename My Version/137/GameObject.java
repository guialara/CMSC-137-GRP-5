
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public abstract class GameObject{
	
	protected float x, y, x1, y1;
	protected float velX = 0, velY = 0;
	protected ObjectId id;

	public GameObject(float x, float y, ObjectId id){
		this.x = x;
		this.y = y;
		this.id = id;
		this.x1 = x+90;
		this.y1 = y+50;
	}

	public abstract void tick(LinkedList<GameObject> object);
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();

	public float getX(){
		return x;
	}

	public float getY(){
		return y;
	}

	public float getX1(){
		return x1;
	}

	public float getY1(){
		return y1;
	}

	public void setX(float x){
		this.x = x;
	}

	public void setY(float y){
		this.y = y;
	}

	public float getVelX(){
		return velX;
	}

	public float getVelY(){
		return velY;
	}

	public void setVelX(float velX){
		this.velX = velX;
	}

	public void setVelY(float velY){
		this.velY = velY;
	}

	public ObjectId getId(){
		return id;
	}
}