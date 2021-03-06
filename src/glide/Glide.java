package glide;





import glide.sounds.Music;
import glide.sounds.Sound;
import glide.spritehandles.BufferedImageLoader;
import glide.versioning.Version;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import kuusisto.tinysound.TinySound;


public class Glide {
	/* Dimensions for the game */
	public static final int WIDTH = 400; 
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 2;
	
	/* Game Properties */
	public static final String version = Version.getAppVersion() + " [Build: " + Version.getAppBuild() + "]";
	public static String TITLE = "Glide - " + version;
	
	public static JFrame frame = new JFrame(TITLE);
	public static MainMenu mm;
	public static HTPMenu htp;
	public static OptionsMenu op;
	public static ControlsMenu cm;
	public static SinglePlayerGame game;
	public static boolean sounds = true;
	public static boolean music = true;
	public static boolean fullscreen = true;
	
	
	/* sounds */
	public static Sound s_explosion;// = new Sound("/sounds/explode.wav");
	public static Sound s_hurt;// = new Sound("/sounds/hurt.wav");
	public static Sound s_pickup;// = new Sound("/sounds/pickup.wav");
	public static Sound s_shoot;// = new Sound("/sounds/shoot.wav");
	public static Sound s_select;// = new Sound("/sounds/select.wav");
	public static Sound s_enter;// = new Sound("/sounds/enter.wav");
	public static Sound s_gameover;// = new Sound("/sounds/gameover.wav");
	public static Sound s_dropdeath;// = new Sound("/sounds/dropexplode.wav");
	
	public static Music s_backgroundmusic;// = new Sound2("/sounds/cr.wav");
	
	/* Updater */
	public static String update = "You are running the latest version!";
	public static String update2 = "";
	public static Color update_color = Color.GREEN;
	public static boolean checkedForUpdate = false;
	
	/* Loading Status */
	public static JLabel loading = new JLabel("Loading.. ");
	public static LayoutManager correctLayout = null;
	
	/* Scrolling Background */
	public static BufferedImage background = null;
	public static BufferedImage background2 = null;
	public static float b1y = 0;
	public static float b2y = 0;
	public static int backgroundSpeed = 1;
	
	/* Cheats */
	public static boolean mdb_cheat = false;
	public static boolean beam_cheat = false;
	public static boolean shield_cheat = false;
	public static boolean health_cheat = false;
	public static boolean cod_cheat = false;
	
	/* Difficulty */
	public static enum Difficulty {
		Easy, Normal, Hard, Expert;
	} public static Difficulty difficulty = Difficulty.Expert;
	public static Color getDifficultyColor(Difficulty difficulty){
		switch(difficulty){
			case Easy:
				return Color.GREEN;
			case Expert:
				return Color.RED;
			case Hard:
				return Color.ORANGE;
			case Normal:
				return Color.BLUE;
			default:
				return Color.WHITE;
		}
	}
	

	
	
	public static void main(String[] args){
		if(args.length > 0){
			if(args[0].equalsIgnoreCase("-windowed")){
				fullscreen = false;
			}
		}	
		if(fullscreen){
			final Choice c = new Choice();
			Runnable r = new Runnable(){
				public void run(){
					c.init();
				}
			};
			Thread ct = new Thread(r);
			ct.start();
			while(!c.cont){
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		BufferedImageLoader loader = new BufferedImageLoader();
		try{
			background = loader.loadImage("/images/mm_b.png");
			background2 = background;
			b2y = -background.getHeight();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		mm = new MainMenu();
		mm.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		mm.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		mm.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		frame = new JFrame(TITLE + " - Loading..");
		frame.setBackground(Color.BLACK);
		frame.add(mm);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setIconImage(new ImageIcon(Glide.class.getClass().getResource("/images/icon.png")).getImage());
		frame.setVisible(true);
		
		mm.start();
		
		if (fullscreen) {
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice vc = env.getDefaultScreenDevice();
			DisplayMode dm = new DisplayMode(800, 600, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
			vc.setFullScreenWindow(frame);
			if(dm != null){
				try{
					vc.setDisplayMode(dm);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
		
		/** Tiny Sound by finnkuusisto - https://github.com/finnkuusisto/TinySound */
		TinySound.init();
		
		s_explosion = new Sound("/sounds/explode.wav");
		s_hurt = new Sound("/sounds/hurt.wav");
		s_pickup = new Sound("/sounds/pickup.wav");
		s_shoot = new Sound("/sounds/shoot.wav");
		s_select = new Sound("/sounds/select.wav");
		s_enter = new Sound("/sounds/enter.wav");
		s_gameover = new Sound("/sounds/gameover.wav");
		s_dropdeath = new Sound("/sounds/explode.wav");
		
		s_backgroundmusic = new Music("/sounds/cr.wav");
		s_backgroundmusic.play();
		
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		frame.getContentPane().setCursor(blankCursor);
		
	}
	
	public static void muteMusic(){
		Glide.music = false;
		s_backgroundmusic.pause();
	}
	public static void unmuteMusic(){
		Glide.music = true;
		s_backgroundmusic.play();
	}
	
	public static void muteSounds(){
		Glide.sounds = false;
	}
	public static void unmuteSounds(){
		Glide.sounds = true;
	}

}
