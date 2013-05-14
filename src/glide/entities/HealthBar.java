package glide.entities;

import glide.Game;

public class HealthBar extends Entity{
	private int health = 5;
	private Game game;
	public HealthBar(double x, double y, Game game) {
		super(x, y, game);
		this.game = game;
		this.setType(Entity.Type.HEALTHBAR);
		this.setEntityImage(game.getTextures().healthbar1);
	}
	@Override
	public void tick(){
		if(health == 5){
			this.setEntityImage(game.getTextures().healthbar1);
		}else if(health == 4){
			this.setEntityImage(game.getTextures().healthbar2);
		}else if(health == 3){
			this.setEntityImage(game.getTextures().healthbar3);
		}else if(health == 2){
			this.setEntityImage(game.getTextures().healthbar4);
		}else if(health == 1){
			this.setEntityImage(game.getTextures().healthbar5);
		}else if(health > 5){
			health = 5;
		}else if(health < 1){
			health = 1;
		}else{
			health = 1;
		}
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
}