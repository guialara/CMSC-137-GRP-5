
import java.awt.Graphics;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.image.ImageObserver;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Car extends GameObject{

	private int width = 100, height = 50;
	private Handler handler;
	
	public Car(float x, float y, Handler handler, ObjectId id){
		super(x,y,id);
		this.handler = handler;
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

		AffineTransform identity = new AffineTransform();
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform trans = new AffineTransform();
		trans.setTransform(identity);

		trans.rotate(Math.toRadians(setRot), (double)x+width/2, (double)y+height/2);
		trans.translate((double)x, (double)y);
		g2d.rotate(Math.toRadians(setRot), (double)x+width/2, (double)y+height/2);
		// g2d.drawImage(image, trans, null);
		// g2d.drawRect();
		// g2d.setColor(Color.red);
		g2d.drawImage(image, (int)x, (int)y, null);
		g2d.draw(getBoundsBottom());
		g2d.draw(getBoundsTop());
		g2d.draw(getBoundsRight());
		g2d.draw(getBoundsLeft());
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
					x = tempObject.getX()-width;
					velX = 0;
				}
				if(getBoundsTop().intersects(tempObject.getBoundsLeft())){
					System.out.println("TL");
					x = tempObject.getX()+width+10;
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
					x = tempObject.getX()+10+height;
					velX = 0;
				}
				if(getBoundsLeft().intersects(tempObject.getBoundsLeft())){
					x = tempObject.getX()-height;
					velX = 0;
				}
				if(getBoundsLeft().intersects(tempObject.getBoundsTop())){
					y = tempObject.getY()-10;
					velY = 0;
				}
				if(getBoundsLeft().intersects(tempObject.getBoundsBottom())){
					y = tempObject.getY()+10;
					velY = 0;
				}
				//Car Right Collision
				if(getBoundsRight().intersects(tempObject.getBoundsRight())){
					x = tempObject.getX()+10+height;
					velX = 0;
				}
				if(getBoundsRight().intersects(tempObject.getBoundsLeft())){
					x = tempObject.getX()-height;
					velX = 0;
				}
				if(getBoundsRight().intersects(tempObject.getBoundsTop())){
					y = tempObject.getY()-height;
					velY = 0;
				}
				if(getBoundsRight().intersects(tempObject.getBoundsBottom())){
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
}