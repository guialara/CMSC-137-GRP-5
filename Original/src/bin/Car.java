package bin;

import java.awt.Graphics;
import java.util.LinkedList;
import java.awt.Color;
//import java.awt.Rectangle;
//import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RoundRectangle2D.Float;
import java.awt.Graphics2D;

public class Car extends GameObject{

	private KeyInput input;
	//private Rectangle botBound, topBound, rightBound, leftBound;
	//private ObjectId id;
	String pName;
	public boolean isMoving=false;
	public Handler handler;
	//GameClient gameClient;
	
	public Car(float x, float y, int width, int height, Handler handler, String pName, ObjectId id, int score){
		super(x,y,width,height,id);
		//this.input = input;
		this.x += width/2;
		this.y += height/2;
		this.pName = pName;
		this.handler = handler;
	}

	public void tick(LinkedList<GameObject> object){
		this.x+=this.velX;
		this.y+=this.velY;
		
		if(this.velX != 0 || this.velY != 0){
			isMoving = true;
			PacketMove packet = new PacketMove(this.getUsername(),(int)this.x, (int)this.y);
            packet.writeData(Game.game.gameClient);
		}else{
			isMoving=false;
		}

		Collision(object);
	}
	

	public void Collision(LinkedList<GameObject> object){
		for(int i=0;i<object.size();i++){
			if(object.get(i).getId()==ObjectId.Block){
				Block block = (Block)object.get(i);
				if(getBounds().intersects(block.getBoundsTop())){
					y = block.getY()-height-2;
					velY = 0;
				}
				if(getBounds().intersects(block.getBoundsBottom())){
					y = block.getY()+22;
					velY = 0;
				}
				if(getBounds().intersects(block.getBoundsRight())){
					x = block.getX()+22;
					velX = 0;
				}
				if(getBounds().intersects(block.getBoundsLeft())){
					x = block.getX()-width-2;
					velX = 0;
				}
			}
			if(object.get(i).getId()==ObjectId.Food){
				Food food = (Food)object.get(i);
					if(getBounds().intersects(food.getBounds())){
						PacketEat packet = new PacketEat(food.getX(), food.getY());
						packet.writeData(Game.game.gameClient);
					}
			}
			if(object.get(i).getId()==ObjectId.Car){
				Car car = (Car)object.get(i);
				if(!getUsername().equals(car.getUsername())){	
					if(getBounds().intersects((double)car.getX(), (double)car.getY(), (double)car.getWidth(), (double)car.getHeight())){
						if(getWidth()==car.getWidth()){
							if(getX()<car.getX()) car.setX(car.getX()+1);
							if(getX()>car.getX()) car.setX(car.getX()-1);
							if(getY()<car.getY()) car.setY(car.getY()+1);
							if(getY()>car.getY()) car.setY(car.getY()-1);
							PacketMove packet = new PacketMove(car.getUsername(),(int)car.getX(), (int)car.getY());
							packet.writeData(Game.game.gameClient);
						}
					}
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

	@Override
	public String getUsername() {
		return pName;
	}
	
	public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }
	public boolean isMoving() {
        return isMoving;
    }
}
