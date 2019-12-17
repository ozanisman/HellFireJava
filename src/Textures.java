import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class Textures {
	
	private SpriteSheet shipSheet; // 64x64
	private SpriteSheet missileSheet; // 20x20
	private SpriteSheet laserSheet; // 25x145
	private SpriteSheet blastSheet; // 20x25
	private SpriteSheet explosionSheet; // 30x30
	private SpriteSheet shipExplosionSheet; // 70x70
	private SpriteSheet powerupSheet; // 20x20
	private SpriteSheet shieldSheet; // 80x73
	private SpriteSheet enemyBlastSheet; // 15x32
	private SpriteSheet bossSheet; //	300x350
	
	public BufferedImage player, playerShield1, playerShield2, blast1, blast2, blast3, blast4, blast5, missile1, missile2, missile3, missile4, missileExplosion, enemy1, enemy2, enemy3, enemy4, enemy5, enemy6, enemy7, boss1, enemyBlast1, enemyBlast2, enemyBlast3, enemyBlast4, enemyBlast5, enemyBlast6, enemyBlast7, enemyBlast8, bossBlast, enemyMissile1, enemyMissile2, enemyMissile3, enemyMissile4, powerup1, powerup2, powerup3, powerup4, powerup5, powerup6, powerup7, powerup8;
	public BufferedImage[] shipExplosion = new BufferedImage[8];
	public BufferedImage[] bulletExplosion = new BufferedImage[6];
	public BufferedImage[] laser1 = new BufferedImage[7], laser2 = new BufferedImage[7], laser3 = new BufferedImage[7], laser4 = new BufferedImage[7], laser5 = new BufferedImage[7];
	
	public Textures(HellFireGame game) {
		shipSheet = new SpriteSheet(game.getShipSheet());
		missileSheet = new SpriteSheet(game.getMissileSheet());
		laserSheet = new SpriteSheet(game.getLaserSheet());
		blastSheet = new SpriteSheet(game.getBlastSheet());
		explosionSheet = new SpriteSheet(game.getExplosionSheet());
		shipExplosionSheet = new SpriteSheet(game.getShipExplosionSheet());
		powerupSheet = new SpriteSheet(game.getPowerupSheet());
		shieldSheet = new SpriteSheet(game.getShieldSheet());
		enemyBlastSheet = new SpriteSheet(game.getEnemyBlastSheet());
		bossSheet = new SpriteSheet(game.getBossSheet());
		
		//Player sprites
		player = shipSheet.grabImage(1, 2, 64, 64);
		playerShield1 = shieldSheet.grabImage(1, 1, 80, 80);
		playerShield2 = shieldSheet.grabImage(1, 2, 80, 80);
		
		blast1 = blastSheet.grabImage(5, 1, 20, 25);
		blast2 = blastSheet.grabImage(2, 1, 20, 25);
		blast3 = blastSheet.grabImage(1, 1, 20, 25);
		blast4 = blastSheet.grabImage(3, 1, 20, 25);
		blast5 = blastSheet.grabImage(4, 1, 20, 25);
		
		missile1 = missileSheet.grabImage(2, 4, 20, 20);
		missile2 = missileSheet.grabImage(5, 4, 20, 20);
		missile3 = missileSheet.grabImage(2, 1, 20, 20);
		missile4 = missileSheet.grabImage(5, 1, 20, 20);
		
		for(int i = 0; i < 6; i++) {
			laser1[i] = laserSheet.grabImage(i + 1, 1, 25, 145); //Green
		}
		for(int i = 0; i < 6; i++) {
			laser2[i] = laserSheet.grabImage(i + 1, 3, 25, 145); //Yellow
		}
		for(int i = 0; i < 6; i++) {
			laser3[i] = laserSheet.grabImage(i + 7, 3, 25, 145); //White
		}
		for(int i = 0; i < 6; i++) {
			laser4[i] = laserSheet.grabImage(i + 7, 1, 25, 145); //Purple
		}
		for(int i = 0; i < 6; i++) {
			laser5[i] = laserSheet.grabImage(i + 1, 2, 25, 145); //Red
		}
		
		powerup1 = powerupSheet.grabImage(1, 1, 20, 20); // red, restore health
		powerup2 = powerupSheet.grabImage(4, 1, 20, 20); // dark blue, increases shield cap
		powerup3 = powerupSheet.grabImage(5, 1, 19, 20); // light green, gain $100 + $10 for every upgrade bought
		powerup4 = powerupSheet.grabImage(1, 2, 20, 20); // yellow, increases damage
		powerup5 = powerupSheet.grabImage(3, 2, 20, 20); // light blue, slows enemies on screen
		powerup6 = powerupSheet.grabImage(2, 2, 19, 20); // brown, black hole?
		powerup7 = powerupSheet.grabImage(6, 2, 20, 20); // orange
		powerup8 = powerupSheet.grabImage(7, 2, 20, 20); // pink, 
		
		shipExplosion[0] = shipExplosionSheet.grabImage(4, 2, 70, 70);
		shipExplosion[1] = shipExplosionSheet.grabImage(6, 2, 70, 70);
		shipExplosion[2] = shipExplosionSheet.grabImage(3, 3, 70, 70);
		shipExplosion[3] = shipExplosionSheet.grabImage(6, 3, 70, 70);
		shipExplosion[4] = shipExplosionSheet.grabImage(2, 4, 70, 70);
		shipExplosion[5] = shipExplosionSheet.grabImage(6, 4, 70, 70);
		shipExplosion[6] = shipExplosionSheet.grabImage(4, 5, 70, 70);
		shipExplosion[7] = shipExplosionSheet.grabImage(6, 5, 70, 70);
		
		bulletExplosion[0] = shipExplosionSheet.grabImage(1, 6, 70, 70);
		bulletExplosion[1] = shipExplosionSheet.grabImage(2, 6, 70, 70);
		bulletExplosion[2] = shipExplosionSheet.grabImage(3, 6, 70, 70);
		bulletExplosion[3] = shipExplosionSheet.grabImage(4, 6, 70, 70);
		bulletExplosion[4] = shipExplosionSheet.grabImage(5, 6, 70, 70);
		bulletExplosion[5] = shipExplosionSheet.grabImage(6, 6, 70, 70);
		
		missileExplosion = explosionSheet.grabImage(2, 1, 28, 30);
		
		//Enemy sprites
		enemy1 = shipSheet.grabImage(2, 1, 64, 64);
		enemy2 = shipSheet.grabImage(3, 1, 64, 64);
		enemy3 = shipSheet.grabImage(1, 3, 64, 64);
		enemy4 = shipSheet.grabImage(2, 2, 64, 64);
		enemy5 = shipSheet.grabImage(2, 3, 64, 64);
		enemy6 = shipSheet.grabImage(3, 2, 64, 64);
		enemy7 = shipSheet.grabImage(3, 3, 64, 64);
		boss1 = bossSheet.grabImage(1, 1, 300, 350);
		
		enemyBlast1 = enemyBlastSheet.grabImage(1, 1, 15, 32);
		enemyBlast2 = enemyBlastSheet.grabImage(2, 1, 15, 32);
		enemyBlast3 = enemyBlastSheet.grabImage(3, 1, 15, 32);
		enemyBlast4 = enemyBlastSheet.grabImage(4, 1, 15, 32);
		enemyBlast5 = enemyBlastSheet.grabImage(3, 2, 15, 32);
		enemyBlast6 = enemyBlastSheet.grabImage(2, 2, 15, 32);
		enemyBlast7 = enemyBlastSheet.grabImage(1, 2, 15, 32);
		enemyBlast8 = enemyBlastSheet.grabImage(4, 2, 15, 32);
		bossBlast = explosionSheet.grabImage(1, 4, 30, 30);

		enemyMissile1 = missileSheet.grabImage(2, 6, 20, 20);
		enemyMissile2 = missileSheet.grabImage(2, 3, 20, 20);
		enemyMissile3 = missileSheet.grabImage(5, 3, 20, 20);
		enemyMissile4 = missileSheet.grabImage(5, 5, 20, 20);
		
		
	}
	
}
