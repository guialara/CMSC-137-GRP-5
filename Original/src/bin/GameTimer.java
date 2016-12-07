package bin;

import java.util.TimerTask;

public class GameTimer extends TimerTask{

	private EndGameToDo endGame;
	
	//private Game game;
	
	public GameTimer(EndGameToDo endGame){
		this.endGame = endGame;
	}
	
	public void run() {
		endGame.gameEnded();
	}

}
