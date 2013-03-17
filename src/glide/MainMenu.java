package glide;

import glide.spritehandles.BufferedImageLoader;
import glide.spritehandles.Textures;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;




public class MainMenu extends Canvas implements Runnable{

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = -4093553489357496142L;
	
	
	/* Dimensions for the game */
	public static final int WIDTH = 320;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 2;
	
	/* Game Properties */
	public static final String TITLE = "Glide v0.1a";
	private boolean running = false;
	private Thread thread;
	
	private BufferedImage background = null;
	private BufferedImage logo = null;
	
	
	private Textures textures;
	
	private int score = 0;
	
	private int selected = 1;
	
	/*
	private int level = 1;
	*/
	
	
	

	public void init(){
		requestFocus();
		BufferedImageLoader loader = new BufferedImageLoader();
		try{
			background = loader.loadImage("/images/mm_b.png");
			logo = loader.loadImage("/images/logo.png");
		}catch(IOException e){
			e.printStackTrace();
		}
		
		addKeyListener(new MenuKeyInput(this));
		
	}
	
	/* Thread Control */
	public synchronized void start(){
		if(running){
			return;
		}
		
		running = true;
		thread = new Thread(this);
		thread.start();
	
	}
	
	public synchronized void stop() throws Exception{
		if(!running){
			return;
		}
		
		running = false;
	}
	/* End Thread Control */
	
	//Main GAME
	@Override
	public void run() {
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				//System.out.println("TPS: " + updates + " , FPS:" + frames);
				updates = 0;
				frames = 0;
			}
		}
		try {
			stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	int adde = 0;
	private void tick(){
		
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		////////////////////////////////////////////////////////

		g.drawImage(background, 0, 0, null);
		Font f = new Font("Ariel", Font.BOLD, 24);
		g.setFont(f);
		
		/* Logo */
		int logow = logo.getWidth();
		int logoh = logo.getHeight();
		g.drawImage(logo, ((WIDTH * SCALE) / 2) - (logow / 2), 120, null);
		
		
		/* Menu Items */
		
		//Play
		/////
		if(selected == 1){
			g.setColor(Color.GREEN);
		}else{
			g.setColor(Color.DARK_GRAY);
		}
		String sco = "Play";
		int w = g.getFontMetrics().stringWidth(sco);
		g.drawChars(sco.toCharArray(), 0, sco.toCharArray().length, ((WIDTH * SCALE) / 2) - (w / 2), ((HEIGHT * SCALE) / 2) + (g.getFontMetrics().getDescent() - 49 + logoh));
		////
		
		
		//How to play
		///
		if(selected == 2){
			g.setColor(Color.GREEN);
		}else{
			g.setColor(Color.DARK_GRAY);
		}
		String sco2 = "How To Play";
		int w2 = g.getFontMetrics().stringWidth(sco2);
		g.drawChars(sco2.toCharArray(), 0, sco2.toCharArray().length, ((WIDTH * SCALE) / 2) - (w2 / 2), ((HEIGHT * SCALE) / 2) + (g.getFontMetrics().getDescent() - 25 + logoh));
		///
		
		
		//Exit
		///
		if(selected == 3){
			g.setColor(Color.GREEN);
		}else{
			g.setColor(Color.DARK_GRAY);
		}
		String sco3 = "Exit";
		int w3 = g.getFontMetrics().stringWidth(sco3);
		g.drawChars(sco3.toCharArray(), 0, sco3.toCharArray().length, ((WIDTH * SCALE) / 2) - (w3 / 2), ((HEIGHT * SCALE) / 2) + (g.getFontMetrics().getDescent() + logoh));
		///
		
		////////////////////////////////////////////////////////
		g.dispose();
		bs.show();
	}
	
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	public void keyPressed(KeyEvent e){

		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP){
			if(selected == 1){
				selected = 3;
			}else if(selected == 2){
				selected = 1;
			}else if(selected == 3){
				selected = 2;
			}
		}else if (key == KeyEvent.VK_DOWN){
			if(selected == 1){
				selected = 2;
			}else if(selected == 2){
				selected = 3;
			}else if(selected == 3){
				selected = 1;
			}
		}else if(key == KeyEvent.VK_ENTER){
			if(selected == 1){
				Game game = new Game();
				game.setPreferredSize(new Dimension(MainMenu.WIDTH * MainMenu.SCALE, MainMenu.HEIGHT * MainMenu.SCALE));
				game.setMaximumSize(new Dimension(MainMenu.WIDTH * MainMenu.SCALE, MainMenu.HEIGHT * MainMenu.SCALE));
				game.setMinimumSize(new Dimension(MainMenu.WIDTH * MainMenu.SCALE, MainMenu.HEIGHT * MainMenu.SCALE));
				try {
					stop();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Glide.game = game;
				Glide.frame.remove(Glide.mm);
				Glide.frame.add(Glide.game);
				Glide.frame.pack();
				Glide.game.start();
			}else if(selected == 2){
				HTPMenu htp = new HTPMenu();
				htp.setPreferredSize(new Dimension(MainMenu.WIDTH * MainMenu.SCALE, MainMenu.HEIGHT * MainMenu.SCALE));
				htp.setMaximumSize(new Dimension(MainMenu.WIDTH * MainMenu.SCALE, MainMenu.HEIGHT * MainMenu.SCALE));
				htp.setMinimumSize(new Dimension(MainMenu.WIDTH * MainMenu.SCALE, MainMenu.HEIGHT * MainMenu.SCALE));
				
				try{
					stop();
				} catch (Exception e1){
					e1.printStackTrace();
				}
				
				Glide.htp = htp;
				Glide.frame.remove(Glide.mm);
				Glide.frame.add(Glide.htp);
				Glide.frame.pack();
				Glide.htp.start();
				
			}else if(selected == 3){
				System.exit(0);
			}
		}
		

	}
	public void keyReleased(KeyEvent e){
		/*
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP){
			this.getPlayer().setVelocityY(0);
		}else if(key == KeyEvent.VK_LEFT){
			this.getPlayer().setVelocityX(0);
		}else if(key == KeyEvent.VK_DOWN){
			this.getPlayer().setVelocityY(0);
		}else if(key == KeyEvent.VK_RIGHT){
			this.getPlayer().setVelocityX(0);
		}else if(key == KeyEvent.VK_SPACE){
			if(!getPlayer().isRapidFire()){
				getPlayer().setShooting(false);
			}
		}
		*/
	}

	public static int getScale() {
		return SCALE;
	}
	
	public Textures getTextures() {
		return textures;
	}

	public void setTextures(Textures textures) {
		this.textures = textures;
	}
	

}
