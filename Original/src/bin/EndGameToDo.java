package bin;

import java.awt.Dimension;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EndGameToDo {
	private final Game game;
	public String ranking;
	//game.endGame.ranking = (string) -> sinend ni server
	public EndGameToDo(Game game){
		this.game = game;
	}
	public void gameEnded(){
		//what to do
		System.out.println("GAME ENDED!");
		PacketEndGame packet = new PacketEndGame(this.game.car.getUsername());
		packet.writeData(this.game.gameClient);
		this.game.frame.dispose();
		JFrame frame = new JFrame("End Game: "+this.game.car.getUsername());
		JPanel panel = new JPanel();
		JLabel label = new JLabel();
		label.setText("END GAME!!!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel.add(label);
		
		Iterator<Entry<String, Integer>> it = this.game.ranking.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>)it.next();
			JLabel labelTemp = new JLabel();
			labelTemp.setText(pair.getKey()+","+pair.getValue());
			panel.add(labelTemp);
			it.remove();
		}
		frame.getContentPane().add(panel);
		frame.setPreferredSize(new Dimension(400, 300));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
