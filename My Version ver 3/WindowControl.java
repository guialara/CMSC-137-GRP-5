//package bin;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowControl implements WindowListener{
	
	private final Game game;

	public WindowControl(Game game){
		this.game = game;
        this.game.frame.addWindowListener(this);
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		PacketDisconnect packet = new PacketDisconnect(this.game.car.getUsername());
		packet.writeData(this.game.gameClient);
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}

}
