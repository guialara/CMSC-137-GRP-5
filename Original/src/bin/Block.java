package bin;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Rectangle;

public class Block extends GameObject{
	
	private int width, height;

	public Block(float x, float y, int width, int height, ObjectId id){
		super(x, y, width, height, id);
		this.width = width;
		this.height = height;
	}

	public void tick(LinkedList<GameObject> object){

	}

	public void render(Graphics g){
		g.setColor(Color.white);
		g.drawRect((int)x, (int)y, width, height);
	}

	public Rectangle getBoundsBottom(){
		return new Rectangle((int)x+5, (int)y+height-5, width-10, 5);
	}
	public Rectangle getBoundsTop(){
		return new Rectangle((int)x+5, (int)y, width-10, 5);
	}
	public Rectangle getBoundsLeft(){
		return new Rectangle((int)x, (int)y+5, width/2, height/2);
	}
	public Rectangle getBoundsRight(){
		return new Rectangle((int)x+(width/2), (int)y+5, width/2, height/2);		
	}

	@Override
	public String getUsername() {
		return null;
	}
}