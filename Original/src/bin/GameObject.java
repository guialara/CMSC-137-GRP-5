package bin;

import java.awt.Graphics;
//import java.awt.Rectangle;
import java.util.LinkedList;
//import java.awt.geom.RoundRectangle2D;
//import java.awt.geom.RoundRectangle2D.Float;

public abstract class GameObject{
	
	protected float x, y, x1, y1;
	protected float velX = 0, velY = 0;
	protected ObjectId id;
	protected float setRot;
	protected int width, height;
	protected int numSteps;
	private boolean isMoving;

	public GameObject(float x, float y, int width, int height, ObjectId id){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		this.setRot = 0;
	}

	public abstract void tick(LinkedList<GameObject> object);
	public abstract void render(Graphics g);
	// public abstract Rectangle getBoundsBottom();
	// public abstract Rectangle getBoundsTop();
	// public abstract Rectangle getBoundsLeft();
	// public abstract Rectangle getBoundsRight();
	public abstract String getUsername();

	public float getX(){
		return x;
	}

	public float getY(){
		return y;
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

	public float getRot(){
		return setRot;
	}

	public void setRot(float setRot){
		this.setRot = setRot;
	}

	public ObjectId getId(){
		return id;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public void setWidth(int width){
		this.width = width;
	}
	public void setHeight(int height){
		this.height = height;
	}
	public boolean getIsMoving(){
		return isMoving;
	}
	public void setMoving(boolean isMoving){
		this.isMoving = isMoving;
	}
	public void setNumSteps(int numSteps) {
		this.numSteps = numSteps;
	}
}