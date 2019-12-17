import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
	
public class Missile extends Object implements Entity {
	
	private int damage;
	private double explosionX;
	private double explosionY;
	private int explosionDamage;
	private int maxDiameter;
	private int currentDiameter = 20;
	private double velocityX;
	private double velocityY;
	private boolean friendly;
	private Textures texture;
	private HellFireGame game;
	private boolean explode = false;
	private int counter = 0;
	private BufferedImage image;
	private boolean isTargetable = true;
	private boolean canMove = true;
	private ArrayList<Integer> hitEnemies = new ArrayList<Integer>();
	
	public Missile(int damage, int explodeDamage, int maxDiameter, double x, double y, BufferedImage image, Textures texture, int width, int height, double velX, double velY, boolean friendly, HellFireGame game) {
		super(x, y, width, height);
		this.texture = texture;
		this.damage = damage;
		explosionDamage = explodeDamage;
		this.maxDiameter = maxDiameter;
		this.velocityX = velX;
		this.velocityY = velY;
		this.image = image;
		this.friendly = friendly;
		this.game = game;
	}
	
	public void tick() {
		
		if(!game.getPlayer().getPause()) {
			if(explode) {
				if(counter >= 60 || currentDiameter == maxDiameter) {
					game.getSpawner().removeBullet(this);
				}
				counter++;
			} else {
				if(canMove) {
					x += velocityX;
					y += velocityY;
					if(counter % 7 == 0) {
						velocityY--;
					}
					counter++;
				}
				if(friendly) {
					if(game.getSpawner().getBoss().isEmpty()) {
						int index = Collision.checkCollision(this, game.getSpawner().getEnemy());
						if(index > -1) {
							game.getSpawner().getEnemy().get(index).takeDamage(damage);
							explode();
						}
					} else {
						if(Collision.checkBossCollision(this, game.getSpawner().getBoss())) {
							game.getSpawner().getBoss().get(0).takeDamage(damage);
							explode();
						}
					}
				} else {
					if(Collision.checkCollision(this, game.getPlayer())) {
						canMove = false;
						explode();
						game.getPlayer().takeDamage(this.damage);
					}
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(explode) {
			for(int i = 0; i < game.getSpawner().getEnemy().size(); i++) {
				if(!hitEnemies.contains(i) && game.getSpawner().getEnemy().get(i).isTargetable() && checkExplosionCollision(game.getSpawner().getEnemy().get(i).getBounds(), g)) {
					game.getSpawner().getEnemy().get(i).takeDamage(explosionDamage);
					hitEnemies.add(i);
				}
			}
			currentDiameter = (maxDiameter / 32) * counter;
			if(currentDiameter > maxDiameter) {
				currentDiameter = maxDiameter;
			}
			explosionX = x - (currentDiameter / 2);
			explosionY = y - (currentDiameter / 2);
			g.drawImage(image, (int)explosionX, (int)explosionY, currentDiameter, currentDiameter, null);
		} else {
			g.drawImage(image, (int)x, (int)y, null);
		}
		
		//DRAW HITBOX
		//g.setColor(Color.RED);
		//g.drawOval(this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height); 
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage bullet) {
		this.image = bullet;
	}
	
	public boolean getFriendly() {
		return friendly;
	}
	
	public void explode() {
		counter = 0;
		this.explode = true;
		x += 10;
		explosionX = x - (maxDiameter / 30 * counter) / 2;
		explosionY = y - (maxDiameter / 30 * counter) / 2;
		this.image = texture.missileExplosion;
	}
	
	public Rectangle getBounds() {
		if(explode) {
			return new Rectangle((int)explosionX + counter / 3, (int)explosionY + counter / 3, currentDiameter - counter / 2, currentDiameter - counter * 13 / 16);
		}
		return new Rectangle((int)x + 2, (int)y, 16, 20);
	}

	public boolean isTargetable() {
		return isTargetable;
	}

	public void takeDamage(double damage) {}
	
	public boolean checkExplosionCollision(Rectangle rect, Graphics g) {
		
		double width = rect.getWidth();
		double height = rect.getHeight();
		double initialX = rect.getX();
		double initialY = rect.getY();
		double pointCheckingX = initialX;
		double pointCheckingY = initialY;
		double circleRadius = currentDiameter / 2;
		double circleCenterX = explosionX + circleRadius;
		double circleCenterY = explosionY + circleRadius;
		for(int i = 0; i < width; i++) { //Checks Top Side
			if(getDistance(pointCheckingX, pointCheckingY, circleCenterX, circleCenterY) <= circleRadius) {
				return true;
			}
			pointCheckingX++;
		}
		pointCheckingX = initialX;
		pointCheckingY = initialY;
		for(int i = 0; i < height; i++) { //Checks Left Side
			if(getDistance(pointCheckingX, pointCheckingY, circleCenterX, circleCenterY) <= circleRadius) {
				return true;
			}
			pointCheckingY++;
		}
		pointCheckingX = initialX;
		pointCheckingY = initialY + height;
		for(int i = 0; i < width; i++) { //Checks Bottom Side
			if(getDistance(pointCheckingX, pointCheckingY, circleCenterX, circleCenterY) <= circleRadius) {
				return true;
			}
			pointCheckingX++;
		}
		pointCheckingX = initialX + width;
		pointCheckingY = initialY;
		for(int i = 0; i < height; i++) { //Checks Right Side
			if(getDistance(pointCheckingX, pointCheckingY, circleCenterX, circleCenterY) <= circleRadius) {
				return true;
			}
			pointCheckingY++;
		}
		return false;
	}
	
	public double getDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}
	
}