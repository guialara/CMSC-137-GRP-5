package bin;

import java.awt.Graphics;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RoundRectangle2D.Float;
import java.awt.Graphics2D;

public class Car extends GameObject{

	private Handler handler;
	private Rectangle botBound, topBound, rightBound, leftBound;
	String pName;
	GameClient gameClient;
	
	public Car(float x, float y, int width, int height, Handler handler, String pName, GameClient gameClient, ObjectId id){
		super(x,y,width,height,id);
		this.handler = handler;
		this.x += width/2;
		this.y += height/2;
		this.pName = pName;
		this.gameClient = gameClient;
	}

	public void tick(LinkedList<GameObject> object){
		x+=velX;
		y+=velY;
		String str = "PLAYER: " + pName + " "+ x+" "+ y;
		// gameClient.sendData(str.getBytes());

		Collision(object);
	}

	public void Collision(LinkedList<GameObject> object){
		for(int i=0;i<handler.object.size();i++){
			if(handler.object.get(i).getId()==ObjectId.Block){
				Block block = (Block)handler.object.get(i);
				if(getBounds().intersects(block.getBoundsTop())){
					y = block.getY()-width;
					velY = 0;
				}
				if(getBounds().intersects(block.getBoundsBottom())){
					y = block.getY()+10;
					velY = 0;
				}
				if(getBounds().intersects(block.getBoundsRight())){
					x = block.getX()+10;
					velX = 0;
				}
				if(getBounds().intersects(block.getBoundsLeft())){
					x = block.getX()-width;
					velX = 0;
				}
			}
			if(handler.object.get(i).getId()==ObjectId.Food){
				Food food = (Food)handler.object.get(i);
				if(getBounds().intersects(food.getBounds())){
					handler.removeObject(food);
					handler.createFood();
				}
			}
		}
	}

	public void render(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.fillOval((int)x, (int)y, (int)width,(int)height);
		g2d.drawString(pName, (int)x+10, (int)y+60);
		g2d.setColor(Color.red);
		g2d.draw(getBounds());
	}

	public RoundRectangle2D.Float getBounds(){
		return new RoundRectangle2D.Float((float)x, (float)y, (float)width, (float)height, 360, 360);
	}
}