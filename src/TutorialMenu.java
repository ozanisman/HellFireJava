import java.awt.*;
import java.awt.geom.Rectangle2D;

public class TutorialMenu {
	
	private HellFireGame game;
	private Textures texture;
	private Enemy enemy;
	private boolean addedEnemy = false;
	private int stage = 0;
	private int counter;
	
	public TutorialMenu (HellFireGame game, Textures texture) {
		this.game = game;
		this.texture = texture;
		enemy = new Enemy(287, 160, 64, 64, 10, 10, 60, 5, 0, texture.enemy1, texture.enemyBlast1, game, texture);
	}
	
	public void render(Graphics g) {
		
		g.setColor(Color.RED);
		g.setFont(new Font("SansSerif", Font.BOLD, 30));
		if(stage == 0) {
			g.drawImage(texture.player, 287, 160, null);
			
			g.drawString("Welcome to HellFire!", game.getMainMenu().getCenterX("Welcome to HellFire!", g, g.getFont()), 100);
			
			g.setFont(new Font("SansSerif", Font.BOLD, 20));
			g.drawString("This is your ship.", game.getMainMenu().getCenterX("This is your ship.", g, g.getFont()), 250);
			g.drawString("Movement is controlled with the mouse by default.", game.getMainMenu().getCenterX("Movement is controlled with the mouse by default.", g, g.getFont()), 280);
			g.drawString("You can switch to WASD and Arrows in the settings menu.", game.getMainMenu().getCenterX("You can switch to WASD and Arrows in the settings menu.", g, g.getFont()), 310);
		} else if(stage == 1) {
			g.drawImage(texture.player, (int)game.getPlayer().getX(), (int)game.getPlayer().getY(), null);
			g.setFont(new Font("SansSerif", Font.BOLD, 20));
			g.drawString("Use your controls to move the ship around.", game.getMainMenu().getCenterX("Use your controls to move the ship around.", g, g.getFont()), 250);
		} else if(stage == 2) {
			g.drawImage(texture.enemy1, 287, 160, null);
			g.drawImage(texture.player, (int)game.getPlayer().getX(), (int)game.getPlayer().getY(), null);
			g.setFont(new Font("SansSerif", Font.BOLD, 20));
			g.drawString("This is a basic enemy.", game.getMainMenu().getCenterX("This is a basic enemy.", g, g.getFont()), 250);
			g.drawString("It travels downwards and shoots bullets at set intervals.", game.getMainMenu().getCenterX("It travels downwards and shoots bullets at set intervals.", g, g.getFont()), 280);
			g.drawString("The bullets damage your ship if they makes contact.", game.getMainMenu().getCenterX("The bullets damage your ship if they makes contact.", g, g.getFont()), 310);
			g.drawString("Money is earned every 5 waves and from defeating enemies.", game.getMainMenu().getCenterX("Money is earned every 5 waves and from defeating enemies.", g, g.getFont()), 370);
			g.drawString("Enemies that reach the bottom deal damage = 5 * total escaped.", game.getMainMenu().getCenterX("Enemies that reach the bottom deal damage = 5 * total escaped.", g, g.getFont()), 340);
			g.drawString("Money is earned every 5 waves and from defeating enemies.", game.getMainMenu().getCenterX("Money is earned every 5 waves and from defeating enemies.", g, g.getFont()), 370);
			g.drawString("This money can be used to upgrade your ship after defeat.", game.getMainMenu().getCenterX("This money can be used to upgrade your ship after defeat.", g, g.getFont()), 400);
		} else if(stage == 3) {
			g.setFont(new Font("SansSerif", Font.BOLD, 20));
			g.drawString("Try dodging the enemy's blasts.", game.getMainMenu().getCenterX("Try dodging the enemy's blasts.", g, g.getFont()), 250);
			g.drawImage(texture.player, (int)game.getPlayer().getX(), (int)game.getPlayer().getY(), null);
			game.getPlayer().setBlastShooting(false);
			game.getPlayer().setAutoShoot(false);
			game.getPlayer().tick();
			enemy.tick();
			enemy.render(g);
			game.getSpawner().tick();
			game.getSpawner().render(g);
		} else if(stage == 4) {
			g.setFont(new Font("SansSerif", Font.BOLD, 20));
			g.drawString("Practice dodging and shooting at the same time.", game.getMainMenu().getCenterX("Practice dodging and shooting at the same time.", g, g.getFont()), 250);
			g.drawString("Press M to toggle between manual and auto shooting.", game.getMainMenu().getCenterX("Press M to toggle between manual and auto shooting.", g, g.getFont()), 280);
			g.drawString("When in manual mode, J shoots Blasts", game.getMainMenu().getCenterX("When in manual mode, J shoots Blasts", g, g.getFont()), 310);
			g.drawString("and K shoots Missiles (when unlocked).", game.getMainMenu().getCenterX("and K shoots Missiles (when unlocked).", g, g.getFont()), 340);
			g.drawString("L activates the shield in either mode (when unlocked)", game.getMainMenu().getCenterX("L  activates the shield in either mode (when unlocked)", g, g.getFont()), 370);
			g.drawImage(texture.player, (int)game.getPlayer().getX(), (int)game.getPlayer().getY(), null);
			if(game.getSpawner().getEnemy().isEmpty()) {
				game.getSpawner().addEnemy(enemy.clone());
			}
			game.getPlayer().tick();
			game.getSpawner().tick();
			game.getSpawner().render(g);
		} else if(stage == 5) {
			game.getSpawner().restart();
			g.setFont(new Font("SansSerif", Font.BOLD, 20));
			g.drawImage(texture.player, (int)game.getPlayer().getX(), (int)game.getPlayer().getY(), null);
			g.drawImage(texture.playerShield1, (int)game.getPlayer().getX() - 8, (int)game.getPlayer().getY() - 5, null);
			game.getPlayer().tick();
			g.drawString("The ship looks like this when the shield is enabled.", game.getMainMenu().getCenterX("The ship looks like this when the shield is enabled.", g, g.getFont()), 250);
			g.drawString("While activated, the shield negates all damage.", game.getMainMenu().getCenterX("While activated, the shield negates all damage.", g, g.getFont()), 280);
			g.drawString("The shield then requires a cooldown period before reuse.", game.getMainMenu().getCenterX("The shield then requires a cooldown period before reuse.", g, g.getFont()), 310);
			g.drawString("The cooldown period is quite long, so use each charge wisely.", game.getMainMenu().getCenterX("The cooldown period is quite long, so use each charge wisely.", g, g.getFont()), 340);
		} else if(stage == 6) {
			g.setFont(new Font("SansSerif", Font.BOLD, 20));
			g.drawString("An upgrade gives enemies a chance to drop powerups, which are:", game.getMainMenu().getCenterX("An upgrade gives enemies a chance to drop powerups, which are:", g, g.getFont()), 150);
			g.drawImage(texture.powerup1, 80, 200, null);
			g.drawString("Restores 75 Armor", 150, 217);
			g.drawImage(texture.powerup3, 80, 250, null);
			g.drawString("Gives $100 + $10 * total # upgrades bought", 150, 267);
			g.drawImage(texture.powerup4, 80, 300, null);
			g.drawString("Increases Player Damage By 50%", 150, 317);
			g.drawImage(texture.powerup5, 80, 350, null);
			g.drawString("Slows All Enemies On Screen", 150, 367);
			g.drawImage(texture.powerup6, 80, 400, null);
			g.drawString("Reduces Enemy Damage By 50% (does not stack)", 150, 417);
			g.drawString("The last 3 upgrades have a duration of 10 seconds.", game.getMainMenu().getCenterX("The last 3 upgrades have a duration of 10 seconds.", g, g.getFont()), 480);

		} else if(stage == 7) {
			g.setFont(new Font("SansSerif", Font.BOLD, 20));
			g.drawString("You've reached the end of the tutorial!", game.getMainMenu().getCenterX("You've reached the end of the tutorial!", g, g.getFont()), 200);
			g.drawString("Press Launch to play the game or Back to review the tutorial.", game.getMainMenu().getCenterX("Press Lauch to play the game or Back to review the tutorial.", g, g.getFont()), 250);
		}
		
		g.setFont(new Font("SansSerif", Font.BOLD, 30));
		g.setColor(Color.RED);
		g.fillRect(10, 670, 110, 40);
		g.setColor(Color.BLACK);
		if(stage == 0) {
			g.drawString("MENU", 21, 701);
		} else {
			g.drawString("BACK", 21, 701);
		}
		if(stage < 7) {
			g.setColor(Color.RED);
			g.fillRect(510, 670, 110, 40);
			g.setColor(Color.BLACK);
			g.setFont(new Font("SansSerif", Font.BOLD, 20));
			g.drawString("CONTINUE", 514, 698);
		}
		if(stage == 7) {
			g.setColor(Color.RED);
			g.fillRect(480, 670, 150, 40);
			g.setColor(Color.BLACK);
			g.drawString("LAUNCH!", 488, 700);

		}
	}
	
	public void setStage(int stage) {
		this.stage = stage;
	}
	
	public int getStage() {
		return stage;
	}
	
}
