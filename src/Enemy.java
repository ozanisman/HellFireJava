import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Enemy extends Object implements Entity, Cloneable {
	
	private int money;
	private int health;
	private int shootingSpeed;
	private int bulletDamage;
	private double defaultVelocityY;
	private double velocityY;
	private int damageDecrease = 1;
	private BufferedImage image;
	private BufferedImage bulletImage;
	private HellFireGame game;
	private Textures texture;
	private int counter = 0;
	private boolean explode = false;
	private int explodeCounter = 0;
	private boolean isTargetable = true;
	private double excessDamage = 0;

	
	public Enemy(double x, double y, int width, int height, int money, int health, int shootingSpeed, int bulletDamage, double velocityY, BufferedImage image, BufferedImage bulletImage, HellFireGame game, Textures texture) {
		super(x, y, width, height);
		this.money = money;
		this.health = health;
		this.shootingSpeed = shootingSpeed;
		this.bulletDamage = bulletDamage;
		this.velocityY = velocityY;
		this.defaultVelocityY = velocityY;
		this.image = image;
		this.bulletImage = bulletImage;
		this.game = game;
		this.texture = texture;
	}
	
	public void tick() {
		if(!game.getPlayer().getPause()) {
			if(explode) {
				if(explodeCounter >= texture.shipExplosion.length) {
					game.getSpawner().removeEnemy(this);
					double random = Math.random();
					if(random <= game.getPlayer().getUpgrades().getPowerupDrop().getUpgradeValue()) {
						game.getSpawner().addPowerup(new Powerup(getX() + 32, getY() + 32, 20, 20, game, texture, (int)(Math.random() * 5)));
					}
				} else if(counter == 4) {
					image = texture.shipExplosion[explodeCounter];
					explodeCounter++;
					counter = 0;
				} else {
					counter++;
				}
			} else {
				if(velocityY > defaultVelocityY) {
					velocityY = defaultVelocityY;
				}
				if(velocityY > 0) {
					y += velocityY;
				}
				if(counter == shootingSpeed) {
					
					game.getSpawner().addBullet(new Blast(bulletDamage / damageDecrease, getX() + 24, getY() + 40, bulletImage, texture, 20, 25, 0, 5, false, game));

					counter = 0;
				} else {
					counter++;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(image, (int)x, (int)y, null);
		
		//DRAW HITBOX
		//g.setColor(Color.RED);
		//g.drawRect(this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height); 
	}
	
	public Rectangle getBounds() {
		if(image == texture.enemy1) {
			return new Rectangle((int)getX() + 8, (int)getY() + 10, 47, 40);
		} else if(image == texture.enemy2) {
			return new Rectangle((int)getX() + 19, (int)getY() + 5, 26, 52);
		} else if(image == texture.enemy3) {
			return new Rectangle((int)getX() + 5, (int)getY() + 5, 54, 50);
		} else if(image == texture.enemy4) {
			return new Rectangle((int)getX() + 7, (int)getY() + 5, 52, 52);
		} else if(image == texture.enemy5) {
			return new Rectangle((int)getX() + 7, (int)getY() + 5, 48, 50);
		} else if(image == texture.enemy6) {
			return new Rectangle((int)getX() + 7, (int)getY() + 5, 48, 52);
		} else if(image == texture.enemy7){
			return new Rectangle((int)getX() + 5, (int)getY() + 5, 52, 50);
		} else {
			return new Rectangle((int)getX() + 5, (int)getY() + 5, 52, 50);
		}
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void changeVelocityY(double velocityY) {
		this.velocityY += velocityY;
	}

	public double getVelocityY() {
		return velocityY;
	}
	
	public void explode() {
		game.getPlayer().money(money);
		counter = 0;
		this.explode = true;
		isTargetable = false;
	}

	public boolean isTargetable() {
		return isTargetable;
	}
	
	public void takeDamage(double damage) {
		int whole = (int)damage % 1;
		double fraction = damage - whole;
		
		if(damage % 1 == 0) {
			health -= damage;
		} else {
			health -= whole;
			excessDamage += fraction;
		}
		while(excessDamage > 1) {
			health -= 1;
			excessDamage -= 1;
		}
		if(health <= 0) {
			explode();
		}
	}
	
	public void setDamageDecrease(int d) {
		damageDecrease = d;
	}

	public Enemy clone() {
	    try {
			return (Enemy)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	    return null;
	}
}
