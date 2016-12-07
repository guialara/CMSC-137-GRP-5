//package bumpCar;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
 
public class WelcomeMenu extends BasicGameState {
	private Image bg;
	private Image car;
	
	private int x,y;
	private int sizeW, sizeH;
	public WelcomeMenu() {
 
	}
 
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		bg = new Image("res/BumpCarLogo.png");
		car = new Image("res/redCar.png");
		
		x=y=0;
		sizeW = 100;
		sizeH = sizeW/2; 
 
	}	
 
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		int bgX = (gc.getScreenWidth()/2) - (bg.getWidth()/2);
		int bgY = (gc.getScreenHeight()/2) - (bg.getHeight()/2);
		bg.draw(bgX,bgY);
		
		car.setCenterOfRotation(sizeW/2, sizeH/2);
		car.draw(x,y,sizeW,sizeH);
		//car.draw(x,y,100,50);
 
	}
 
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = gc.getInput();
		x = input.getMouseX()-(sizeW/2);
		y = input.getMouseY()-(sizeH/2);
		if(input.isKeyDown(Input.KEY_LEFT)){
			
		}
		
	}
 
	public int getID() {
		return 0;
	}
}
