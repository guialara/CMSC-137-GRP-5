
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.Math;
import java.awt.Graphics2D;

public class KeyInput extends KeyAdapter{
	
	Handler handler;

	public KeyInput(Handler handler){
		this.handler = handler;
	}

	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key==KeyEvent.VK_ESCAPE) System.exit(1);

		for(int i=0;i<handler.object.size();i++){
			GameObject tempObject = handler.object.get(i);

			if(tempObject.getId()==ObjectId.Car){
				if(key==KeyEvent.VK_W){
					tempObject.setVelX((float)(3*Math.cos(tempObject.getRot())));
					tempObject.setVelY((float)(3*Math.sin(tempObject.getRot())));
					// tempObject.setX(tempObject.getX()+(float)(0.1*Math.sin(setRot)));
					// tempObject.setY(tempObject.getY()+(float)(0.1*Math.cos(setRot)));
					// tempObject.setX(tempObject.getX() - (float)(0.1*Game.getDelta()*(float)(Math.cos(Math.toRadians(setRot)))));
					// if(setRot != 0) tempObject.setY(tempObject.getY() - (float)(0.1*Game.getDelta()*(float)Math.sin(Math.toRadians(setRot))));
				} 
				// tempObject.setVelX(3);
				if(key==KeyEvent.VK_S){
					tempObject.setVelX((float)(-3*Math.cos(tempObject.getRot())));
					tempObject.setVelY((float)(-3*Math.sin(tempObject.getRot())));
				} 
				// tempObject.setVelX(-3);
				if(key==KeyEvent.VK_D){
					tempObject.setRot((float)(tempObject.getRot()-6*Game.getDelta()));
					// tempObject.setX(tempObject.getX() - (int)((double)50*Math.cos(6)));
					// tempObject.setY(tempObject.getY() + (int)((double)90*Math.sin(6)));
				}
				if(key==KeyEvent.VK_A){
					tempObject.setRot((float)(tempObject.getRot()+6*Game.getDelta()));
					// tempObject.setX(tempObject.getX() + (int)((double)50*Math.cos(6)));
					// tempObject.setY(tempObject.getY() - (int)((double)90*Math.sin(6)));
				}
			}
		}
	}


	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		for(int i=0;i<handler.object.size();i++){
			GameObject tempObject = handler.object.get(i);

			if(tempObject.getId()==ObjectId.Car){
				if(key==KeyEvent.VK_W){
					tempObject.setVelX(0);
					tempObject.setVelY(0);
				}
				if(key==KeyEvent.VK_S){
					tempObject.setVelX(0);
					tempObject.setVelY(0);
				}

			}
		}
	}
}