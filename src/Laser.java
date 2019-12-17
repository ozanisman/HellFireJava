import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class Laser extends Object implements Entity {
	
	private double damage;
	private Textures texture;
	private HellFireGame game;
	private int counter = 0;
	private int currentImage = 0;
	private BufferedImage[] images;
	private boolean isTargetable = true;
	private boolean friendly;
	private int laserDuration;
	private int changeImageDuration;
	private int waitBetweenShots;
	
	public Laser(int damage, int x, int y, BufferedImage[] images, Textures texture, int width, int height, boolean friendly, HellFireGame game) {
		super(x, y, width, height);
		this.game = game;
		this.texture = texture;
		this.damage = damage;
		this.images = images;
		this.friendly = friendly;
		if(friendly) {
			laserDuration = 30;
			changeImageDuration = 2;
			waitBetweenShots = 20;
		} else {
			laserDuration = 60;
			changeImageDuration = 3;
			waitBetweenShots = 90;
		}
	}
	
	public void tick() {
		if(!game.getPlayer().getPause()) {
			if(currentImage == 0) {
				if(friendly) {
					int[] index = Collision.laserCollision(this, game.getSpawner().getEnemy());
					if(index != null) {
						for(int i = 0; i < index.length; i++) {
							game.getSpawner().getEnemy().get(index[i]).takeDamage(damage / 20);
						}
					}
				} else {
					if(Collision.checkCollision(this, game.getPlayer())) {
						game.getPlayer().takeDamage(damage / 20);
					}
				}
			}
			if(currentImage == 6) {
				if(counter >= waitBetweenShots) {
					currentImage = 0;
					counter = 0;
				} else {
					counter++;
				}
			} else {
				if(currentImage == 0 && counter < laserDuration) {
					counter++;
				} else if(currentImage == 0 && counter >= laserDuration) {
					currentImage++;
					counter = 0;
				} else if(currentImage != 0 && counter < changeImageDuration) {
					counter++;
				} else {
					currentImage++;
					counter = 0;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(friendly) {
			g.drawImage(images[currentImage], (int)x, (int)y, width, 640, null);
		} else {
			g.drawImage(images[currentImage], (int)x, (int)y, width, 660 - (int)y, null);
		}
		
		//DRAW HITBOX
		//g.setColor(Color.RED);
		//g.drawRect(this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height);
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public Rectangle getBounds() {
		if(friendly) {
			return new Rectangle((int)game.getPlayer().getX() + 24, 0, 14, (int)game.getPlayer().getY() + 5);
		}
		return new Rectangle((int)x + 10, (int)y, width - 20, 660 - (int)y);
	}

	public BufferedImage[] getImage() {
		return images;
	}

	public void setImage(BufferedImage[] images) {
		this.images = images;
	}
	
	public void setCurrentImage(int x) {
		currentImage = x;
	}

	public boolean isTargetable() {
		return isTargetable;
	}

	public void takeDamage(double damage) {}

	public void explode() {}
	
}