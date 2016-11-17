package bin;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Rectangle;

public class Block extends GameObject{
	
	private int width = 10, height = 10;

	public Block(float x, float y, int width, int height, ObjectId id){
		super(x, y, width, height, id);
	}

	public void tick(LinkedList<GameObject> object){

	}

	public void render(Graphics g){
		g.setColor(Color.white);
		g.drawRect((int)x, (int)y, width, height);
		// Graphics2D g2d = (Graphics2D) g;
		// g2d.setColor(Color.red);
		// g2d.draw(getBoundsBottom());
		// g2d.setColor(Color.blue);
		// g2d.draw(getBoundsTop());
		// g2d.setColor(Color.green);
		// g2d.draw(getBoundsLeft());
		// g2d.setColor(Color.yellow);
		// g2d.draw(getBoundsRight());
	}

	public Rectangle getBoundsBottom(){
		return new Rectangle((int)x+3, (int)y+height-3, width-6, 3);
	}
	public Rectangle getBoundsTop(){
		return new Rectangle((int)x+3, (int)y, width-6, 3);
	}
	public Rectangle getBoundsLeft(){
		return new Rectangle((int)x, (int)y+(height/2)-(height/4), width/2, height/2);
	}
	public Rectangle getBoundsRight(){
		return new Rectangle((int)x+(width/2), (int)y+(height/2)-(height/4), width/2, height/2);		
	}
}