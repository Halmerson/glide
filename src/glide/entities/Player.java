package glide.entities;

import glide.Game;
import glide.Glide;

public class Player extends Entity{
	private double velX = 0;
	private double velY = 0;
	private boolean shooting = false;
	private boolean beaming = false;
	private boolean plasma = false;
	private boolean hurting = false;
	private boolean t = true;
	private boolean t2 = true;
	
	public static final int normalSpeed = 8;
	public static final int beemSpeed = 15;
	public static final int boostSpeed = 15;
	
	public int speed = Player.normalSpeed;

	public Player(double x, double y, Game game){
		super(x, y, game);
		this.setType(Entity.Type.PLAYER);
		this.setEntityImage(game.getTextures().player);
	}
	
	public int beamtick = 0;
	int hurttick = 0;
	public int plasmatick = 0;
	@Override
	public void tick(){
		this.setY(this.getY()+velY);
		this.setX(this.getX()+velX);
		
		
		
		if(isBeaming()){
			if(!Glide.game.isPaused()  && !Glide.game.lost() && !Glide.game.won()){
				speed = Player.beemSpeed;
				Glide.game.getController().addBullet(new Bullet(getX(), getY() - 32, Glide.game));
				beamtick ++;
				if(beamtick == 60){
					Glide.game.beam = 4;
				}else if(beamtick == 120){
					Glide.game.beam = 3;
				}else if (beamtick == 180){
					Glide.game.beam = 2;
				}else if (beamtick == 240){
					Glide.game.beam = 1;
				}else if(beamtick == 300){
					speed = Player.normalSpeed;
					setBeaming(false);
					beamtick = 0;
					Glide.game.beam = 0;
				}
			}
			setEntityImage(game.getTextures().player2);
			t = false;
		}else if(!t){
			setEntityImage(game.getTextures().player);
			t = true;
		}
		
		if(isPlasma()){
			if(!Glide.game.isPaused() && !Glide.game.lost() && !Glide.game.won()){
				plasmatick++;
				
				if(plasmatick == 60){
					Glide.game.shield = 4;
					game.plasma = true;
				}else if(plasmatick == 120){
					Glide.game.shield = 3;
					game.plasma = true;
				}else if (plasmatick == 180){
					Glide.game.shield = 2;
					game.plasma = true;
				}else if (plasmatick == 240){
					Glide.game.shield = 1;
					game.plasma = true;
				}else if(plasmatick == 300){
					setPlasma(false);
					game.plasma = false;
					plasmatick = 0;
					Glide.game.shield = 0;
				}
			}
		}
	
		
		if(hurttick < 10 && isHurting()){
			setEntityImage(game.getTextures().playerhurt);
			t2 = false;
			hurttick ++;
		}else if(!t2){
			hurttick = 0;
			if(!isBeaming()){
				setEntityImage(game.getTextures().player);
			}else{
				setEntityImage(game.getTextures().player2);
			}
			t2 = true;
			setHurting(false);
		}
		
		if(this.getX() < 0)
			this.setX(0);
		if(this.getX() >= (Glide.WIDTH * Glide.SCALE) - 22)
			this.setX((Glide.WIDTH * Glide.SCALE) - 22);
		if(this.getY() <= 0)
			this.setY(0);
		if(this.getY() >= (Glide.HEIGHT * Glide.SCALE) - 32)
			this.setY((Glide.HEIGHT * Glide.SCALE) - 32);
	}
	
	public void hurt(){
		setHurting(true);
	}
	
	
	public void setVelocityY(double y){
		this.velY = y;
	}
	
	public void setVelocityX(double x){
		this.velX = x;
	}
	
	public boolean isShooting() {
		return shooting;
	}

	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}


	public boolean isBeaming() {
		return beaming;
	}


	public void setBeaming(boolean beam) {
		this.beaming = beam;
		Glide.game.beam = (beam) ? 5 : 0;
		if(beam){
			this.beamtick = 0;
		}
	}

	public boolean isHurting() {
		return hurting;
	}

	public void setHurting(boolean hurting) {
		this.hurting = hurting;
	}

	public boolean isPlasma() {
		return plasma;
	}

	public void setPlasma(boolean plasma) {
		this.plasma = plasma;
		Glide.game.shield = (plasma) ? 5 : 0;
		if(plasma){
			this.plasmatick = 0;
		}
	}
}
