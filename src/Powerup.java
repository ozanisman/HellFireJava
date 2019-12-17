import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Powerup extends Object implements Entity, Cloneable  {
	
	private HellFireGame game;
	private Textures texture;
	private int type;
	private BufferedImage image;
	private boolean canMove = true;
	private boolean exploded = false;
	private int countdown = 600;

	public Powerup(double x, double y, int width, int height, HellFireGame game, Textures texture, int type) {
		super(x, y, width, height);
		this.game = game;
		this.texture = texture;
		this.type = type;
		switch(type) {
			case 0: image = texture.powerup1; break;
			case 1: image = texture.powerup3; break;
			case 2: image = texture.powerup4; break;
			case 3: image = texture.powerup5; break;
			default: image = texture.powerup6; break;
		}
	}
	
	public void tick() {
		if(exploded && countdown > 0) {
			countdown--;
		} else if(exploded) {
			switch(type) {
				case 0: break; //red, restore health
				case 1: break; //light green, gain $100 + $10 for every upgrade bought
				case 2: game.getPlayer().increaseDamage(-0.5); break; //yellow, increases damage
				case 3: freezeEnemies(5); break; //light blue, slows enemies on screen
				default: decreaseEnemyDamage(1); break; //brown, black hole?
			}
			game.getSpawner().removePowerup(this);
		} else if(Collision.checkCollision(this, game.getPlayer())) {
			explode();
		} else if(canMove) {
			y += 1;
		}
	}
	
	public void render(Graphics g) {
		if(!exploded) {
			g.drawImage(image, (int)x, (int)y, null);
		}
	}

	public void explode() {
		exploded = true;
		switch(type) {
			case 0: game.getPlayer().increaseHealth(75); countdown = 0; break; //red, restore health
			case 1: game.getPlayer().money(100 + (10 * game.getPlayer().getUpgrades().getNumUpgradesBought())); countdown = 0; break; //light green, gain $200 + $10 for every upgrade bought
			case 2: game.getPlayer().increaseDamage(0.5); break; //yellow, increases damage by 0.5
			case 3: freezeEnemies(-5); break; //light blue, slows enemies on screen
			default: decreaseEnemyDamage(2); break; //brown, reduces enemy damage by half
		}
		
	}

	public boolean isTargetable() {
		return false;
	}

	public void takeDamage(double damage) {}
	
	public void freezeEnemies(int movementDecrease) {
		for(int i = 0; i < game.getSpawner().getEnemy().size(); i++) {
			Enemy e = (Enemy)game.getSpawner().getEnemy().get(i);
			e.changeVelocityY(movementDecrease);
		}
	}
	
	public void decreaseEnemyDamage(int damageDecrease) {
		for(int i = 0; i < game.getSpawner().getEnemy().size(); i++) {
			Enemy e = (Enemy)game.getSpawner().getEnemy().get(i);
			e.setDamageDecrease(damageDecrease);
		}
	}

}
