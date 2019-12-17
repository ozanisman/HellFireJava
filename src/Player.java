import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class Player extends Object implements Entity {
	
	//Upgrade Variables
	private Upgrades upgrades;
	private int maxHealth;
	private int health;
	private int shieldDuration;
	private int currentShieldTimer;
	private int shieldCooldown;
	private boolean shieldActive;
	private boolean shieldReady;
	private int blastDamage;
	private int missileDamage;
	private int missileExplosionDamage;
	private int missileExplosionRadius;
	private double damageBonus = 1;
	private double moneyBonus = 1;
	private int blastShootingSpeed;
	private int missileShootingSpeed;
	
	//Other
	private double velocityX;
	private double velocityY;
	private int money = 0;
	private int speed = 10;
	private BufferedImage playerImage;
	private BufferedImage blastImage;
	private BufferedImage missileImage;
	private Laser laser;
	private HellFireGame game;
	private Textures texture;
	private boolean left = false;
	private boolean up = false;
	private boolean right = false;
	private boolean down = false;
	private int counter = 0;
	private boolean explode = false;
	private int explodeCounter;
	private boolean isTargetable = true;
	private int alpha = 255;
	private int gotHit = -1;
	private boolean mouseControl = true;
	public boolean canMove = true;
	public boolean startWave = true;
	private boolean gameOver = false;
	private boolean pause = false;
	private int escapeDamage = 5;
	private double excessDamage = 0;
	private boolean blastShooting = false;
	private boolean missileShooting = false;
	private boolean autoShoot = true;
	private int blastShootingCounter = 0;
	private int missileShootingCounter = 0;

	
	public Player(double x, double y, HellFireGame game, Textures texture, int width, int height) {
		super(x, y, width, height);
		this.game = game;
		this.texture = texture;
		upgrades = new Upgrades(game, texture);
		laser = new Laser(0, (int)getX() + 18, (int)getY() - 600, upgrades.getLaserType(), texture, 25, 145, true, game);
		playerImage = texture.player;
		upgrade();
	}
	
	public void tick() {
		if(!pause) {
			if(explode) {
				canMove = false;
				if(explodeCounter >= texture.shipExplosion.length) {
					game.restart();
					game.setStateUpgrade();
				} else if(counter == 4) {
					playerImage = texture.shipExplosion[explodeCounter];
					explodeCounter++;
					counter = 0;
				} else {
					counter++;
				}
			} else {
				laser.setX((int)getX() + 18);
				laser.setY((int)getY() - 600);
				if(!mouseControl) {
					x += velocityX;
					y += velocityY;
					if(x <= 0) {
						x = 0;
					}
					if(x >= 576) {
						x = 576;
					}
					if(y <= 0) {
						y = 0;
					}
					if(y >= 600) {
						y = 600;
					}
				}
				if(blastShootingCounter % blastShootingSpeed == 0) {
					shootBlast();
				}
				if(missileShootingSpeed != 0 && missileShootingCounter % missileShootingSpeed == 0) {
					shootMissile();
				}
				if(counter % 60 == 0) {
					currentShieldTimer++;
				}
				if(counter == Integer.MAX_VALUE) {
					counter = 0;
				}
				counter++;
				if(!(blastShootingCounter % blastShootingSpeed == 0 && (blastShooting == false && !autoShoot))) {
					blastShootingCounter++;
				}
				if(missileShootingSpeed != 0 && !(missileShootingCounter % missileShootingSpeed == 0 && (missileShooting == false && !autoShoot))) {
					missileShootingCounter++;
				}
			}
		}
	}
	
	public void render(Graphics g) {
			if(upgrades.getLaserDamage().getUpgradeValue() != 0) {
				laser.tick();
				laser.render(g);
			}
			Font f = g.getFont();
			Font f1 = new Font("SansSerif", Font.BOLD, 30);
			g.setFont(f1);
			if(pause) {
				game.setVisibleCursor();
				g.setColor(Color.DARK_GRAY);
				g.fillRect(220, 210, 200, 300);
				g.setColor(Color.RED);
				g.fillRect(230, 235, 180, 40); //resume
				g.fillRect(230, 305, 180, 40); //upgrades
				g.fillRect(230, 375, 180, 40); //settings
				g.fillRect(230, 445, 180, 40); //menu
				g.setColor(Color.BLACK);
				g.drawString("RESUME", game.getMainMenu().getCenterX("RESUME", g, g.getFont()), 266);
				g.drawString("UPGRADES", game.getMainMenu().getCenterX("UPGRADES", g, g.getFont()), 336);
				g.drawString("SETTINGS", game.getMainMenu().getCenterX("SETTINGS", g, g.getFont()), 406);
				g.drawString("MENU", game.getMainMenu().getCenterX("MENU", g, g.getFont()), 476);
			}
			if(gameOver) {
				g.setColor(Color.RED);
				g.drawString("YOU WIN!", MainMenu.getCenterX("YOU WIN!", g, f1), 280);
				g.drawString("Thanks for playing!", MainMenu.getCenterX("Thanks for playing!", g, f1), 330);
			}
			
			g.drawImage(playerImage, (int)x, (int)y, null);
			if(shieldActive) {
				g.drawImage(texture.playerShield1, (int)x - 8, (int)y - 5, null);
			}
			
			g.setColor(Color.RED);
			
			if(gotHit != -1) {
				if(alpha >= 0 && alpha <= 255) {
					if(gotHit == 0) {
						g.setColor(new Color(255, 0, 0, alpha));
					} else {
						g.setColor(new Color(0, 225, 255, alpha));
					}
					g.fillRect(0, 0, 640, 10);
					g.fillRect(0, 10, 10, 650);
					g.fillRect(10, 650, 630, 10);
					g.fillRect(630, 10, 10, 640);
					alpha -= 5;
				}
			}
			g.setFont(new Font("SansSerif", Font.BOLD, 15));
			g.setColor(Color.RED);
			g.fillRect(130, 680, (int)(health/(0.0 + maxHealth) * 200), 20);
			g.drawString("Health: " + health, 195, 715);
			if(shieldActive && shieldDuration != 0) {
				if(shieldDuration - currentShieldTimer == 0) {
					shieldActive = false;
					currentShieldTimer = 0;
				} else {
					g.setColor(Color.GREEN);
					g.fillRect(335, 680, 200 - ((int)(currentShieldTimer / (0.0 + shieldDuration) * 200)), 20);
					g.drawString("Shield Active For: " + (shieldDuration - currentShieldTimer), 368, 715);
				}
				shieldReady = false;
			} else if(shieldCooldown != 0 && currentShieldTimer < shieldCooldown) {
				g.setColor(Color.CYAN);
				g.fillRect(335, 680, (int)(currentShieldTimer/(0.0 + shieldCooldown) * 200), 20);
				g.drawString("Shield Charged In: " + (shieldCooldown - currentShieldTimer), 368, 715);
			} else if(shieldCooldown != 0 && currentShieldTimer >= shieldCooldown) {
				shieldReady = true;
				g.setColor(Color.GREEN);
				g.fillRect(335, 680, 200, 20);
				g.drawString("Shield Charged!", 380, 715);
			}
			g.setColor(Color.WHITE);
			g.drawRect(130, 680, 200, 20);
			g.drawRect(335, 680, 200, 20);
			g.drawLine(0, 660, 640, 660);
			g.drawLine(640, 0, 640, 740);
			Font f2 = new Font("SansSerif", Font.BOLD, 30);
			g.setFont(f2);
			g.setColor(Color.RED);
			
			g.fillRect(10, 670, 110, 40);
			g.setColor(Color.BLACK);
			g.setFont(new Font("SansSerif", Font.BOLD, 25));
			g.drawString("PAUSE", 21, 699);
			
			
			g.setColor(Color.RED);
			g.drawString("$" + money, 545, 699);
			g.setFont(new Font("SansSerif", Font.BOLD, 15));
			g.drawString("Next escape deals " + escapeDamage, game.getMainMenu().getCenterX("Next escape deals " + escapeDamage, g, g.getFont()), 675);
			
			 //DRAW HITBOX
			/*g.setColor(Color.RED);
			for(int i = 0; i < getPlayerBounds().length; i++) { 
				g.drawRect(getPlayerBounds()[i].x, getPlayerBounds()[i].y, getPlayerBounds()[i].width, getPlayerBounds()[i].height);
			} */
			
	}
	
	public void upgrade() {
		maxHealth = (int) upgrades.getShipHealth().getUpgradeValue();
		health = maxHealth;
		damageBonus = (int) (upgrades.getDamageBonus().getUpgradeValue());
		blastDamage = (int)(upgrades.getBlastDamage().getUpgradeValue() * damageBonus);
		blastShootingSpeed = (int) upgrades.getBlastFirerate().getUpgradeValue();
		blastImage = upgrades.getBlastType();
		missileDamage = (int)(upgrades.getMissileDamage().getUpgradeValue() * damageBonus);
		missileShootingSpeed = (int) upgrades.getMissileFirerate().getUpgradeValue();
		missileExplosionRadius = (int) upgrades.getMissileExplosionRadius().getUpgradeValue();
		missileExplosionDamage = (int)(upgrades.getMissileExplosionDamage().getUpgradeValue() * damageBonus);
		missileImage = upgrades.getMissileType();
		laser.setDamage((int)(upgrades.getLaserDamage().getUpgradeValue() * damageBonus));
		laser.setImage(upgrades.getLaserType());
		shieldCooldown = (int) upgrades.getShieldCooldown().getUpgradeValue();
		currentShieldTimer = 0;
		shieldDuration = (int) upgrades.getShieldDuration().getUpgradeValue();
		shieldActive = false;
		moneyBonus = upgrades.getMoneyBonus().getUpgradeValue();
		damageBonus = upgrades.getDamageBonus().getUpgradeValue();
	}
		
	public void restart() {
		blastShootingCounter = 0;
		missileShootingCounter = 0;
		health = maxHealth;
		currentShieldTimer = 0;
		shieldActive = false;
		canMove = true;
		explode = false;
		isTargetable = true;
		damageBonus = 1;
		counter = 0;
		explodeCounter = 0;
		playerImage= texture.player;
		gotHit = -1;
		escapeDamage = 5;
		pause = false;
		gameOver = false;
	}
	
	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}

	public double getVelocityX() {
		return velocityX;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	public double getVelocityY() {
		return velocityY;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}

	public void moveUp() {
		if (!up) {
			if (down) {
				setVelocityY(0);
			} else {
				setVelocityY(-(speed));
			}
			up = true;
		}
	}
	public void moveLeft() {
		if (!left) {
			if (right) {
				setVelocityX(0);
			} else {
				setVelocityX(-(speed));
			}
			left = true;
		}
	}
	public void moveRight() {
		if (!right) {
			if (left) {
				setVelocityX(0);
			} else {
				setVelocityX(speed);
			}
			right = true;
		}
	}
	public void moveDown() {
		if (!down) {
			if (up) {
				setVelocityY(0);
			} else {
				setVelocityY(speed);
			}
			down = true;
		}
	}
	public void stopUp() {
		if (up) {
			if (down) {
				setVelocityY(speed);
			} else {
				setVelocityY(0);
			}
			up = false;
		}
	}
	public void stopLeft() {
		if (left) {
			if (right) {
				setVelocityX(speed);
			} else {
				setVelocityX(0);
			}
			left = false;
		}
	}
	public void stopRight() {
		if (right) {
			if (left) {
				setVelocityX(-(speed));
			} else {
				setVelocityX(0);
			}
			right = false;
		}
	}
	public void stopDown() {
		if (down) {
			if (up) {
				setVelocityY(-(speed));
			} else {
				setVelocityY(0);
			}
			down = false;
		}
	}
	
	public void explode() {
		counter = 0;
		this.explode = true;
		isTargetable = false;
	}

	public boolean isTargetable() {
		return isTargetable;
	}
	
	public void setImage(BufferedImage image) {
		this.playerImage= image;
	}
	
	public Rectangle[] getPlayerBounds() {
		Rectangle[] rectangles = new Rectangle[] {new Rectangle((int)x + 4, (int)y + 33, width - 9, height - 40), new Rectangle((int)x + 23, (int)y + 5, width - 46, height - 35)};
		return rectangles;
	}
	public void takeDamage(double damage) {
		
		this.alpha = 255;
		if(game.getHacksOn()) {
			return;
		}
		damage = damage * (1 - upgrades.getDamageReduction().getUpgradeValue());

		if(shieldActive) {
			gotHit = 1;
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
		gotHit = 0;
		if(health <= 0) {
			health = 0;
			explode();
		}
	}
	
	public void hacks(boolean b) {
		if(b) {
			game.setHacksOn(true);
			money(10000000);
			return;
		}
		game.setHacksOn(false);
		money(-(money));
	}
	
	public void money(int value) {
		if(value > 0) {
			value *= moneyBonus;
		}
		this.money += value;
	}
	
	public int getMoney() {
		return money;
	}
	
	public void gameOver(boolean boo) {
		gameOver = boo;
	}
	
	public Upgrades getUpgrades() {
		return upgrades;
	}
	
	public boolean getMouseControl() {
		return mouseControl;
	}
	
	public void setMouseControl(boolean b) {
		mouseControl = b;
	}
	
	public void takeEscapeDamage() {
		this.takeDamage(escapeDamage);
		escapeDamage += 5;
	}
	
	public boolean getPause() {
		return pause;
	}
	
	public void setPause(boolean pause) {
		this.pause = pause;	
	}
	
	public void shootBlast() {
		if(blastShooting || autoShoot) {
			if(upgrades.getDualShot().getUpgradeValue() > 0) {
				game.getSpawner().addBullet(new Blast(blastDamage, getX() + 32, getY() + 5, blastImage, texture, 20, 20, 0, -5, true, game));
				game.getSpawner().addBullet(new Blast(blastDamage, getX() + 12, getY() + 5, blastImage, texture, 20, 20, 0, -5, true, game));
			} else {
				game.getSpawner().addBullet(new Blast(blastDamage, getX() + 22, getY() - 8, blastImage, texture, 20, 20, 0, -5, true, game));
			}
		}
	}
	
	public void shootMissile() {
		if(missileShooting || autoShoot) {
			if(upgrades.getDualShot().getUpgradeValue() > 2) {
				game.getSpawner().addBullet(new Missile(missileDamage, missileExplosionDamage, missileExplosionRadius, getX() + 3, getY() + 20, missileImage, texture, 20, 20, 0, -3, true, game));
				game.getSpawner().addBullet(new Missile(missileDamage, missileExplosionDamage, missileExplosionRadius, getX() + 41, getY() + 20, missileImage, texture, 20, 20, 0, -3, true, game));
			} else {
				game.getSpawner().addBullet(new Missile(missileDamage, missileExplosionDamage, missileExplosionRadius, getX() + 22, getY() - 8, missileImage, texture, 20, 20, 0, -3, true, game));

			}
		}
	}
	
	public boolean isBlastShooting() {
		return blastShooting;
	}
	
	public void setBlastShooting(boolean isShooting) {
		this.blastShooting = isShooting;
	}
	
	public boolean isMissileShooting() {
		return missileShooting;
	}
	
	public void setMissileShooting(boolean isShooting) {
		missileShooting = isShooting;
	}
	
	public boolean getAutoShoot() {
		return autoShoot;
	}
	
	public void setAutoShoot(boolean autoShoot) {
		this.autoShoot = autoShoot;
	}
	
	public void increaseHealth(int increase) {
		health += increase;
		if(health > maxHealth) {
			health = maxHealth;
		}
	}
	
	public void increaseDamage(double percent) {
		damageBonus += percent;
	}
	
	public boolean getShieldReady() {
		return shieldReady;
	}
	
	public void setShieldActive() {
		shieldActive = true;
		shieldReady = false;
		currentShieldTimer = 0;
	}
	
}