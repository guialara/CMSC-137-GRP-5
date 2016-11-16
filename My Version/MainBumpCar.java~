//package bumpCar;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
 
public class MainBumpCar extends StateBasedGame{
	
   public static final String gamename = "MyGameName";
   public static final int play = 0;
   public static final int xSize = 1000;
   public static final int ySize = 600;
   
   public MainBumpCar (String gamename){
      super(gamename);
      //this.addState(new WelcomeMenu());
   }
   
   public void initStatesList(GameContainer gc) throws SlickException{
      this.getState(play).init(gc, this);
      this.enterState(play);
   }
   
   public static void main(String[] args) {
	  //System.setProperty("org.lwjgl.librarypath", "/lib/natives");
      AppGameContainer appgc;
      try{
         appgc = new AppGameContainer(new MainBumpCar(gamename));
         appgc.setDisplayMode(appgc.getScreenWidth(), appgc.getScreenHeight(), false);
         //appgc.setTargetFrameRate(60);
         appgc.start();
      }catch(SlickException e){
         e.printStackTrace();
      }
   }
}
