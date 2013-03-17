package glide;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class HTPKeyInput extends KeyAdapter{
	
	HTPMenu menu;
	
	public HTPKeyInput (HTPMenu game){
		this.menu = game;
	}
	
	public void keyPressed(KeyEvent e){
		menu.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e){
		menu.keyReleased(e);
	}

}
