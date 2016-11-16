
import java.awt.Graphics;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.image.ImageObserver;
import java.awt.Graphics2D;

public class Car extends GameObject{

	private int width = 90, height = 50;
	
	public Car(float x, float y, ObjectId id){
		super(x,y,id);
	}

	public void tick(LinkedList<GameObject> object){
		x+=velX;
		y+=velY;
	}

	public void render(Graphics g){
		// g.setColor(Color.black);
		// g.drawRect((int) x, (int) y, 90, 50);
		ImageIcon img = new ImageIcon("cars/blue.png");
		Image image = img.getImage().getScaledInstance(90, 50, Image.SCALE_SMOOTH);
		img = new ImageIcon(image);
		g.drawImage(img.getImage(), (int)x, (int)y, null);
		// g2d.
	}

	public Rectangle getBounds(){
		return new Rectangle((int) x, (int) y, width, height);
	}
}