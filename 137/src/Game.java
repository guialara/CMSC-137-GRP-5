package bin;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.util.Random;

public class Game extends Canvas implements Runnable{

	private boolean running = false;
	private Thread thread;
	Handler handler;
	public static double delta;
	private String fps;
	public static int WIDTH, HEIGHT;
	private GameClient gameClient;
	String pName;

	public Game(int w, int h, String title, String pName){
		this.setPreferredSize(new Dimension(w, h));
		this.setMaximumSize(new Dimension(w, h));
		this.setMinimumSize(new Dimension(w, h));
		this.pName = pName;
		
		JFrame frame = new JFrame(title);
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		this.start();
	}

	private void init(){
		WIDTH = getWidth();
		HEIGHT = getHeight();
		int randX = new Random().nextInt(WIDTH-20)+5;
		int randY = new Random().nextInt(HEIGHT-20)+5;

		handler = new Handler();
		gameClient = new GameClient(this, "localhost");
		gameClient.start();
		gameClient.sendData("ping".getBytes());

		handler.createLevel();
		handler.addObject(new Car(randX,randY,50,50,handler,pName,gameClient,ObjectId.Car));
		for(int i=0;i<10;i++)
			handler.createFood();
		
		this.addKeyListener(new KeyInput(handler, pName));
	}

	public synchronized void start(){
		if(running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void run(){
		init();
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			fps = "FPS: " + frames + " TICKS: " + updates;
			render();
			frames++;

			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				
				frames = 0;
				updates = 0;
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
		if(args.length != 2){
			System.out.println("Usage: java Game <server> <player name>");
			System.exit(1);
		}

		new Game(800,700,"BumpCar.io",args[1]);
	}
}