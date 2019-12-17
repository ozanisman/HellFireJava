import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Boss extends Object implements Entity, Cloneable {
	
	private int money;
	private int maxHealth;
	private int health;
	private double velocityY = 5;
	private BufferedImage image;
	private BufferedImage bulletImage;
	private Laser mainLaser;
	private Laser leftLaser1;
	private Laser leftLaser2;
	private Laser rightLaser1;
	private Laser rightLaser2;
	private int leftLaserActivated = 1;
	private int rightLaserActivated = 2;
	private HellFireGame game;
	private Textures texture;
	private int counter = 0;
	private boolean explode = false;
	private int explodeCounter = 0;
	private boolean isTargetable = true;
	private double excessDamage = 0;
	private boolean inPosition = false;
	private int movingSide = 0; //0 = RIGHT, 1 = LEFT

	
	public Boss(double x, double y, int width, int height, int money, int health, BufferedImage image, BufferedImage bulletImage, HellFireGame game, Textures texture) {
		super(x, y, width, height);
		this.money = money;
		this.maxHealth = health;
		this.health = 50000;
		this.image = image;
		this.game = game;
		this.texture = texture; //145
		this.bulletImage = texture.bossBlast;
		mainLaser = new Laser(13, (int)x + 149, 180, texture.laser5, texture, 50, 145, false, game);
		leftLaser1 = new Laser(10, (int)x + 90, 280, texture.laser4, texture, 30, 145, false, game);
		leftLaser2 = new Laser(10, (int)x + 45, 245, texture.laser4, texture, 30, 145, false, game);
		rightLaser1 = new Laser(10, (int)x + 225, 280, texture.laser4, texture, 30, 145, false, game);
		rightLaser2 = new Laser(10, (int)x + 275, 245, texture.laser4, texture, 30, 145, false, game);
		
		leftLaser1.setCurrentImage(6);
		rightLaser1.setCurrentImage(6);
	}
	
	public void tick() {
		if(!game.getPlayer().getPause()) {
			if(explode) {
				if(explodeCounter >= texture.shipExplosion.length) {
					game.getSpawner().restart();
				} else if(counter == 4) {
					image = texture.shipExplosion[explodeCounter];
					explodeCounter++;
					counter = 0;
				} else {
					counter++;
				}
			} else if(inPosition) {
				if(movingSide == 0) {
					if(x + 350 >= 640) {
						movingSide = 1;
						x -= 0.5;
					} else {
						x += 0.5;
					}
				} else {
					if(x <= 5) {
						movingSide = 0;
						x += 0.5;
					} else {
						x-= 0.5;
					}
				}
				mainLaser.tick();
				leftLaser1.tick();
				leftLaser2.tick();
				rightLaser1.tick();
				rightLaser2.tick();
				
				mainLaser.setX((int)getX() + 149);
				leftLaser1.setX((int)getX() + 90);
				leftLaser2.setX((int)getX() + 45);
				rightLaser1.setX((int)getX() + 225);
				rightLaser2.setX((int)getX() + 275);
				
				if(counter % 38 == 0) {
					game.getSpawner().addBullet(new Blast(35, getX() + 7, getY() + 130, bulletImage, texture, 20, 25, 0, 5, false, game));
					game.getSpawner().addBullet(new Blast(35, getX() + 315, getY() + 130, bulletImage, texture, 20, 25, 0, 5, false, game));
				}
				if(counter % 85 == 0) {
					game.getSpawner().addBullet(new Blast(35, getX() + 130, getY() + 190, bulletImage, texture, 20, 25, 0, 3, false, game));
					game.getSpawner().addBullet(new Blast(35, getX() + 190, getY() + 190, bulletImage, texture, 20, 25, 0, 3, false, game));
				}
				counter++;
			} else {
				if(y < 0) {
					y += velocityY;
				} else {
					inPosition = true;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(inPosition) {
			if(explode) {
				g.drawImage(image, (int)x + 80, (int)y + 80, 200, 200, null);
			} else {
				mainLaser.render(g);
				leftLaser1.render(g);
				leftLaser2.render(g);
				rightLaser1.render(g);
				rightLaser2.render(g);
				g.drawImage(image, (int)x, (int)y, 350, 300, null);
				g.setColor(Color.RED);
				g.fillRect(70, 5, (int)(health/(0.0 + maxHealth) * 500), 20);
				g.setColor(Color.WHITE);
				g.drawRect(70, 5, 500, 20);
				Font f1 = new Font("SansSerif", Font.BOLD, 17);
				g.setFont(f1);
				g.drawString("Health: " + health, game.getMainMenu().getCenterX("Health: " + health, g, g.getFont()), 22);
				
			}
		} else {
			g.drawImage(image, (int)x, (int)y, 350, 300, null);
		}
		
		//DRAW HITBOX
		/*g.setColor(Color.RED);
		for(int i = 0; i < getBossBounds().length; i++) {
			if(getBossBounds()[i] != null) {
				g.drawRect((int) this.getBossBounds()[i].getX(), (int) this.getBossBounds()[i].getY(), (int) this.getBossBounds()[i].getWidth(), (int) this.getBossBounds()[i].getHeight());
			}
		} */
	}
	
	public Rectangle[] getBossBounds() {
		Rectangle[] array = new Rectangle[7];
		array[0] = new Rectangle((int)x + 115, (int)y + 50, 120, 150);
		array[1] = new Rectangle((int)x + 220, (int)y + 160, 80, 130);
		array[2] = new Rectangle((int)x + 50, (int)y + 160, 80, 130);
		array[3] = new Rectangle((int)x + 50, (int)y + 40, 70, 90);
		array[4] = new Rectangle((int)x + 220, (int)y + 40, 70, 90);
		array[5] = new Rectangle((int)x, (int)y + 110, 70, 50);
		array[6] = new Rectangle((int)x + 280, (int)y + 110, 70, 50);
		return array;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
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
		if(!inPosition) {
			return;
		}
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

	public Enemy clone() {
	    try {
			return (Enemy)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	    return null;
	}
}
