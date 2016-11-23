package bin;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Rectangle;

public class Food extends GameObject{

	public Food(float x, float y, int width, int height, ObjectId id){
		super(x, y, width, height, id);
	}

	public void tick(LinkedList<GameObject> object){

	}

	public void render(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.blue);
		g2d.fillOval((int)x, (int)y, width, height);
	}

	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, width, height);
	}
}