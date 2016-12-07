package bin;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferStrategy;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.util.Random;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	private boolean running = false;
	private Thread thread;
	Handler handler;
	public static double delta;
	private String fps;
	public static int WIDTH, HEIGHT;
	public static Game game;
	public GameClient gameClient;
	public WindowControl windowCtrl;
	private GameServer gameServer;
	public JFrame frame;
	public String pName;
	public Car car;
	public boolean debug = true;
	public KeyInput input;
	public int playerNum;
	public int currentPlayer;
	 
	public Game(int w, int h, String title, String pName){
		this.setPreferredSize(new Dimension(w-120, h-400));
		this.setMaximumSize(new Dimension(w-120, h-400));
		this.setMinimumSize(new Dimension(w-120, h-400));
		this.pName = pName;
		this.game = this;
		
		frame = new JFrame("AgarDown");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
		
		/////////////gamepanel
		JPanel gamePanel = new JPanel();
		gamePanel.add(game);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 1;
		c.ipady = 500;
		c.ipadx = 200;
		mainPanel.add(game, c); //integrates the game into the JFrame with the chat
		
		//////////////// chatpanel
		JPanel chatPanel = new JPanel();
		chatPanel.setLayout(new BorderLayout());
		chatPanel.setBackground(new Color(200,180,230));

		JTextArea messageBox = new JTextArea(30,20);
		messageBox.setEditable(false);		
		messageBox.setLineWrap(true);
		JScrollPane scroll = new JScrollPane(messageBox);
		JButton send = new JButton("SEND");

		JTextField messageField = new JTextField();
		messageField.setPreferredSize(new Dimension(30,20));

		chatPanel.add(scroll, BorderLayout.NORTH);
		chatPanel.add(messageField, BorderLayout.CENTER);
		chatPanel.add(send, BorderLayout.EAST);
			
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 500;
		c.ipadx = 300;
		mainPanel.add(chatPanel, c);
		
		//////////////////////////////////////////////
		frame.add(mainPanel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		this.start();
	}

	private void init(){
		game=this;
		currentPlayer = 0;
		WIDTH = getWidth();
		HEIGHT = getHeight();
		int randX = new Random().nextInt(WIDTH-20)+10;
		int randY = new Random().nextInt(HEIGHT-20)+10;

		handler = new Handler();
		windowCtrl = new WindowControl(this);
		input = new KeyInput(handler,pName);

		handler.createLevel();
		car = new CarMP(randX,randY,50,50,handler,pName,ObjectId.Car,null, -1);
		handler.addObject(car);
		PacketLogin loginPacket = new PacketLogin(car.pName,(int) car.getX(),(int) car.getY(), car.getWidth(), car.getHeight());
		if(gameServer!=null){
			gameServer.addConnection((CarMP) car, loginPacket);
		}
		loginPacket.writeData(gameClient);
		for(int i=0;i<10;i++)
			handler.createFood();
		
		this.addKeyListener(input);
	}

	public synchronized void start(){
		if(running) return;
		running = true;
		
		if (JOptionPane.showConfirmDialog(this, "Do you want to run the server") == 0) {
			String tempNum = JOptionPane.showInputDialog("Please enter number of players");
			int tempplayerNum = Integer.parseInt(tempNum);
			gameServer = new GameServer(this, tempplayerNum);
            gameServer.start();
            this.playerNum = gameServer.playerNum;
        }
		String serverAddress = JOptionPane.showInputDialog("Enter Server Address: ");
		gameClient = new GameClient(this, serverAddress);
		gameClient.start();
		thread = new Thread(this);
		thread.start();
	}
	 public synchronized void stop() {
	        running = false;

	        try {
	            thread.join();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	public void run(){
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		init();
		
		while (running) {
			System.out.println("LIMIT: "+playerNum);
			System.out.println("CURRENT: "+currentPlayer);
			
			if(playerNum == currentPlayer){
	            long now = System.nanoTime();
	            delta += (now - lastTime) / ns;
	            lastTime = now;
	            boolean shouldRender = true;
	
	            while (delta >= 1) {
	                tick();
	                updates++;
	                delta -= 1;
	                shouldRender = true;
	            }
	
	            try {
	                Thread.sleep(2);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	
	            if (shouldRender) {
	                frames++;
	                render();
	            }
	
	            if (System.currentTimeMillis() - timer >= 1000) {
	                timer += 1000;
	                frames = 0;
	                updates = 0;
	            }
	        }
		}

	}
	
	public static double getDelta(){
		return delta;
	}

	public void tick(){
		handler.tick();
	}

	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null){
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0,0,getWidth(),getHeight());
		handler.render(g);

		g.dispose();
		bs.show();
	}

	public static void main(String[] args){
		String name = JOptionPane.showInputDialog("Please enter a username");
		new Game(900,600,"BumpCar.io",name);
	}
}