import java.awt.*;
import java.awt.image.BufferedImage;

public class Blast extends Object implements Entity {
	
	private int damage;
	private double velocityX;
	private double velocityY;
	private boolean friendly;
	private Textures texture;
	private HellFireGame game;
	private boolean explode = false;
	private int explodeCounter = 0;
	private int counter = 0;
	private BufferedImage image;
	private boolean isTargetable = true;
	private BufferedImage[] explosion;
	private boolean canMove = true;
	
	public Blast(int damage, double x, double y, BufferedImage image, Textures texture, int width, int height, double velX, double velY, boolean friendly, HellFireGame game) {
		super(x, y, width, height);
		this.texture = texture;
		this.damage = damage;
		this.velocityX = velX;
		this.velocityY = velY;
		this.image = image;
		this.friendly = friendly;
		this.game = game;
		explosion = texture.bulletExplosion;
	}
	
	public void tick() {
		if(!game.getPlayer().getPause()) {
			if(explode) {
				if(explodeCounter >= explosion.length) {
					game.getSpawner().removeBullet(this);
				} else if(counter == 4) {
					image = explosion[explodeCounter];
					explodeCounter++;
					counter = 0;
				} else {
					counter++;
				}
			} else {
				if(canMove) {
					x += velocityX;
					y += velocityY;
				}
				if(friendly) {
					if(game.getSpawner().getBoss().isEmpty()) {
						int index = Collision.checkCollision(this, game.getSpawner().getEnemy());
						if(index > -1) {
							game.getSpawner().getEnemy().get(index).takeDamage(damage);
							game.getSpawner().removeBullet(this);
						}
					} else {
						if(Collision.checkBossCollision(this, game.getSpawner().getBoss())) {
							game.getSpawner().getBoss().get(0).takeDamage(damage);
							game.getSpawner().removeBullet(this);
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
		g.drawImage(image, (int)x, (int)y, null);
		
		//DRAW HITBOX
		//g.drawRect(this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height);
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
		this.explode = true;
		this.image = explosion[0];
		explodeCounter++;
		this.x -= 27;
	}
	
	public Rectangle getBounds() {
		if(image == texture.blast1 || image == texture.blast2 || image == texture.blast3 || image == texture.blast4 || image == texture.blast5) {
			return new Rectangle((int)x + 4, (int)y + 2, width - 7, height - 2);
		}
		if(image == texture.enemyBlast1 || image == texture.enemyBlast2 || image == texture.enemyBlast3 || image == texture.enemyBlast4 || image == texture.enemyBlast5 || image == texture.enemyBlast6 || image == texture.enemyBlast7 || image == texture.enemyBlast8) {
			return new Rectangle((int)x + 4, (int)y + 3, width - 12, height);
		}
		return new Rectangle((int)x, (int)y, width, height);
	}

	public boolean isTargetable() {
		return isTargetable;
	}

	public void takeDamage(double damage) {}
	
}