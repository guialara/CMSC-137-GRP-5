package bin;

import java.awt.Graphics;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Car extends GameObject{

	private Handler handler;
	private Rectangle botBound, topBound, rightBound, leftBound;

	
	public Car(float x, float y, int width, int height, Handler handler, ObjectId id){
		super(x,y,width,height,id);
		this.handler = handler;
		botBound = new Rectangle((int)x, (int)y+(height/2)-(height/4), width/2, height/2);
		topBound = new Rectangle((int)x+(width/2), (int)y+(height/2)-(height/4), width/2, height/2);
		rightBound = new Rectangle((int)x+5, (int)y, width-10, 3);
		leftBound = new Rectangle((int)x+5, (int)y+height-3, width-10, 3);
	}

	public void tick(LinkedList<GameObject> object){
		x+=velX;
		y+=velY;


		Collision(object);
	}

	public void render(Graphics g){
		// g.setColor(Color.black);
		// g.drawRect((int) x, (int) y, 90, 50);
		ImageIcon img = new ImageIcon("cars/blue.png");
		Image image = img.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);
		img = new ImageIcon(image);

		Graphics2D g2d = (Graphics2D) g;
		// AffineTransform identity = new AffineTransform();
		// AffineTransform trans = new AffineTransform();
		// trans.setTransform(identity);
		g2d.setColor(Color.red);
		
		g2d.draw(getBoundsBottom());
		g2d.draw(getBoundsTop());
		g2d.draw(getBoundsRight());
		g2d.draw(getBoundsLeft());
		// trans.rotate(Math.toRadians(setRot), (double)x+width/2, (double)y+height/2);
		// trans.translate((double)x, (double)y);
		g2d.rotate(Math.toRadians(setRot), (double)x+width/2, (double)y+height/2);
		// System.out.println(trans.getRotateInstance(Math.toRadians(setRot), (double)x+width/2, (double)y+height/2));

		// g2d.drawImage(image, trans, null);
		g2d.drawImage(image, (int)x, (int)y, null);
		// System.out.println(g2d.getClipRect());
		// System.out.println(g2d.getClipBounds());
		g2d.setColor(Color.white);	
		g2d.draw(getBoundsBottom());
		g2d.draw(getBoundsTop());
		g2d.draw(getBoundsRight());
		g2d.draw(getBoundsLeft());
		// System.out.println(getBoundsBottom().getBounds2D());
	}

	private void Collision(LinkedList<GameObject> object){
		for(int i=0;i<handler.object.size();i++){
			GameObject tempObject = handler.object.get(i);

			if(tempObject.getId()==ObjectId.Block){
				//Car Bottom Collision
				if(getBoundsBottom().intersects(tempObject.getBoundsRight())){
					System.out.println("BR");
					x = tempObject.getX()+10;
					velX = 0;
				}
				if(getBoundsBottom().intersects(tempObject.getBoundsLeft())){
					System.out.println("BL");
					x = tempObject.getX()-width;
					velX = 0;
				}
				if(getBoundsBottom().intersects(tempObject.getBoundsTop())){
					System.out.println("BT");
					y = tempObject.getY()-10;
					velY = 0;
				}
				if(getBoundsBottom().intersects(tempObject.getBoundsBottom())){
					System.out.println("BB");
					y = tempObject.getY()+10;
					velY = 0;
				}	
				//Car Top Collision
				if(getBoundsTop().intersects(tempObject.getBoundsRight())){
					System.out.println("TR");
					x = tempObject.getX()+width+10;
					velX = 0;
				}
				if(getBoundsTop().intersects(tempObject.getBoundsLeft())){
					System.out.println("TL");
					x = tempObject.getX()-width-10;
					velX = 0;
				}
				if(getBoundsTop().intersects(tempObject.getBoundsTop())){
					System.out.println("TT");
					y = tempObject.getY()-width-10;
					velY = 0;
				}
				if(getBoundsTop().intersects(tempObject.getBoundsBottom())){
					System.out.println("TB");
					y = tempObject.getY()+10+width;
					velY = 0;
				}
				//Car Left Collision
				if(getBoundsLeft().intersects(tempObject.getBoundsRight())){
					System.out.println("LR");
					x = tempObject.getX()+10+height;
					velX = 0;
				}
				if(getBoundsLeft().intersects(tempObject.getBoundsLeft())){
					System.out.println("LL");
					x = tempObject.getX()-height;
					velX = 0;
				}
				if(getBoundsLeft().intersects(tempObject.getBoundsTop())){
					System.out.println("LT");
					y = tempObject.getY()-10;
					velY = 0;
				}
				if(getBoundsLeft().intersects(tempObject.getBoundsBottom())){
					System.out.println("LB");
					y = tempObject.getY()+10;
					velY = 0;
				}
				//Car Right Collision
				if(getBoundsRight().intersects(tempObject.getBoundsRight())){
					System.out.println("RR");
					x = tempObject.getX()+10+height;
					velX = 0;
				}
				if(getBoundsRight().intersects(tempObject.getBoundsLeft())){
					System.out.println("RL");
					x = tempObject.getX()-height;
					velX = 0;
				}
				if(getBoundsRight().intersects(tempObject.getBoundsTop())){
					System.out.println("RT");
					y = tempObject.getY()-height;
					velY = 0;
				}
				if(getBoundsRight().intersects(tempObject.getBoundsBottom())){
					System.out.println("RB");
					y = tempObject.getY()+10+height;
					velY = 0;
				}
			}
		}
	}
	public Rectangle getBoundsBottom(){
		return new Rectangle((int)x, (int)y+(height/2)-(height/4), width/2, height/2);
	}
	public Rectangle getBoundsTop(){
		return new Rectangle((int)x+(width/2), (int)y+(height/2)-(height/4), width/2, height/2);
	}
	public Rectangle getBoundsLeft(){
		return new Rectangle((int)x+5, (int)y, width-10, 3);
	}
	public Rectangle getBoundsRight(){
		return new Rectangle((int)x+5, (int)y+height-3, width-10, 3);
	}
	public void setBoundsBottom(Rectangle r){
		botBound = r;
	}
}