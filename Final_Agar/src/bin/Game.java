package bin;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.Random;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	private boolean running = false;
	private Thread thread;
	Handler handler;
	public static double delta;
	private String fps;
	public static int WIDTH=800, HEIGHT=700;
	public static Game game;
	public GameClient gameClient;
	public WindowControl windowCtrl;
	public EndGameToDo endGame;
	private GameServer gameServer;
	public JFrame frame;
	public String pName;
	public Car car;
	public boolean debug = true;
	public KeyInput input;
	public int playerNum;
	public int currentPlayer;
	public Map<String, Integer> ranking;
	public static String name;
	public JButton send;
	public JTextArea messageBox;

	public static ServerSocket hostServer = null;
	public static Socket socket = null;
	public static PrintWriter out = null;
	public static BufferedReader in = null;
	public static StringBuffer toAppend = new StringBuffer("");
	public static StringBuffer toSend = new StringBuffer("");
	public static int port = 1331;

	public Game(int w, int h, String title, String pName){
		this.setPreferredSize(new Dimension(w, h));
		this.setMaximumSize(new Dimension(w, h));
		this.setMinimumSize(new Dimension(w, h));
		this.pName = pName;
		this.game = this;
		handler = new Handler();
		
		frame = new JFrame("AgarDown");

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

		JPanel gamePanel = new JPanel();
		gamePanel.add(game);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 1;
		c.ipady = 500;
		c.ipadx = 200;
		mainPanel.add(game, c); //integrates the game into the JFrame with the chat

		JPanel chatPanel = new JPanel();
		chatPanel.setLayout(new BorderLayout());
		chatPanel.setBackground(new Color(200,180,230));

		messageBox = new JTextArea(30,20);
		messageBox.setEditable(false);		
		messageBox.setLineWrap(true);
		JScrollPane scroll = new JScrollPane(messageBox);
		send = new JButton("SEND");

		JTextField messageField = new JTextField();
		messageField.setPreferredSize(new Dimension(30,20));
		
		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String s = messageField.getText();
	               if (!s.equals("")) {
	                  appendToChatBox("ME: " + s + "\n");
	                  messageField.selectAll();

	                  // Send the string
	                  sendString(s);
	               }
			}
         });

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
		int randX = new Random().nextInt(WIDTH-40)+21;
		int randY = new Random().nextInt(HEIGHT-40)+21;

		windowCtrl = new WindowControl(this);
		input = new KeyInput(handler,pName);
		endGame = new EndGameToDo(this);
		ranking = new HashMap<String, Integer>();

		handler.createLevel();
		car = new CarMP(randX,randY,50,50,handler,pName,ObjectId.Car,null, -1);
		handler.addObject(car);
		PacketLogin loginPacket = new PacketLogin(car.pName,(int) car.getX(),(int) car.getY(), car.getWidth(), car.getHeight());
		if(gameServer!=null){
			gameServer.addConnection((CarMP) car, loginPacket);
		}
		loginPacket.writeData(gameClient);
		// for(int i=0;i<10;i++)
		// 	handler.createFood();
		
		this.addKeyListener(input);
	}

	private static void appendToChatBox(String s) {
    	synchronized (toAppend) {
    		toAppend.append(s);
    	}
   	}

 	// send-buffer
 	private static void sendString(String s) {
 		synchronized (toSend) {
 	    toSend.append(s + "\n");
 		}
   	}

	public synchronized void start(){
		if(running) return;
		running = true;
		int flag = 0;
		
		if (JOptionPane.showConfirmDialog(this, "Do you want to run the server") == 0) {
			String tempNum = JOptionPane.showInputDialog("Please enter number of players");
			int tempplayerNum = Integer.parseInt(tempNum);
			gameServer = new GameServer(this, tempplayerNum);
            gameServer.start();
            this.playerNum = gameServer.playerNum;
            
            flag=1;
        }
		String serverAddress = JOptionPane.showInputDialog("Enter Server Address: ");
		gameClient = new GameClient(this, serverAddress);
		gameClient.start();
		thread = new Thread(this);
		thread.start();

		try{
			if(flag == 1){ // set-up TCP Connection if server
				hostServer = new ServerSocket(port);
				socket = hostServer.accept();	
			}
			else{ // connect to server if client
				socket = new Socket(serverAddress, port);
			}
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		int ctrl = 1;
		init();
		
		while (running) {
			System.out.println("LIMIT: "+playerNum);
			System.out.println("CURRENT: "+currentPlayer);
			try{
				Thread.sleep(100);
			}catch(InterruptedException e1){
            	e1.printStackTrace();
            }
			while(playerNum == currentPlayer){
	            if(ctrl==1){
	            	Timer timerGame = new Timer();
	            	timerGame.schedule(new GameTimer(endGame), 120000);
	            	ctrl+=1;
	            } 
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
	            messageBox.append(toAppend.toString());
	            toAppend.setLength(0);
	        }
		}

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
		if(name.isEmpty()){
			name = "AnonyMonkey";
		}
		new Game(900,600,"AgarDown.io",name);

		String s;
	    while(true){	    	
	    	 try {
	                // Send data
	                if (toSend.length() != 0) {
	                   out.print(toSend); 
	                   out.flush();
	                   toSend.setLength(0);
	                }

	                // Receive data
	                if (in.ready()) {
	                   s = in.readLine();
	                   if ((s != null) &&  (s.length() != 0)) {
	                         appendToChatBox(name + ": " + s + "\n");
	                   }
	                }
	             }
	             catch (IOException e) {
	     			e.printStackTrace();
	             }
	    }

	}
}