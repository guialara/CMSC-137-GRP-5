package bin;

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
					tempObject.setVelY(-3);
				} 
				if(key==KeyEvent.VK_S){
					tempObject.setVelY(3);
				} 
				if(key==KeyEvent.VK_D){
					tempObject.setVelX(3);
				}
				if(key==KeyEvent.VK_A){
					tempObject.setVelX(-3);
				}
			}
		}
	}


	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		for(int i=0;i<handler.object.size();i++){
			GameObject tempObject = handler.object.get(i);

			if(tempObject.getId()==ObjectId.Car){
				if(key==KeyEvent.VK_W || key==KeyEvent.VK_S)
					tempObject.setVelY(0);
				if(key==KeyEvent.VK_A || key==KeyEvent.VK_D)
					tempObject.setVelX(0);
			}
		}
	}
}