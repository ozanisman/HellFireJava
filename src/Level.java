import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Level {
	
	private HellFireGame game;
	private Textures texture;
	private int level;
	private boolean money = true;
	private Enemy enemy1, enemy2, enemy3, enemy4, enemy5, enemy6, enemy7;
	private Boss boss1;
	
	private int[] enemies = new int[8];
	private int counter = 0;
	private int numSpawnable = 0;
	private boolean spawnEnemies = false;

	public Level(int level, HellFireGame game, Textures texture) {
		this.level = level;
		this.game = game;
		this.texture = texture;
		try {
			File f = new File("LevelData.txt");
			FileReader fileReader = new FileReader(f);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			int index = 1;
			String line = "";
			while(index <= level) {
				line = bufferedReader.readLine();
				index++;
			}
			index = 0;
			for(int i = 0; i < enemies.length; i++) {
				int enemyNum = 0;
				if(index < line.length()) {
					char currentChar = line.charAt(index);
					while(currentChar != ' ' && index < line.length()) {
						currentChar = line.charAt(index);
						if(currentChar == '/') {
							break;
						}
						if(currentChar != ' ') {
							enemyNum *= 10;
							enemyNum += Integer.parseInt(String.valueOf(currentChar));
						}
						index++;
					}
				}
				enemies[i] = enemyNum;
			}
			bufferedReader.close();
		} catch (IOException e) {
			System.out.println("Level File Not Found!");
		}
		
		//////////////////////////////////////////////////64, 64, money, health, shootingSpeed, bulletDamage, velocityY, image, bulletImage/////////
		enemy1 = new Enemy(Math.random() * 510 + 64, -64, 64, 64, 10, 10, 80, 5, 1, texture.enemy1, texture.enemyBlast1, game, texture);
		enemy2 = new Enemy(Math.random() * 510 + 64, -64, 64, 64, 20, 20, 90, 10, 1, texture.enemy2, texture.enemyBlast2, game, texture);
		enemy3 = new Enemy(Math.random() * 510 + 64, -64, 64, 64, 40, 40, 70, 30, 1, texture.enemy3, texture.enemyBlast3, game, texture);
		enemy4 = new Enemy(Math.random() * 510 + 64, -64, 64, 64, 70, 70, 90, 50, 0.75, texture.enemy4, texture.enemyBlast4, game, texture);
		enemy5 = new Enemy(Math.random() * 510 + 64, -64, 64, 64, 150, 150, 90, 60, 0.75, texture.enemy5, texture.enemyBlast5, game, texture);
		enemy6 = new Enemy(Math.random() * 510 + 64, -64, 64, 64, 300, 200, 100, 75, 0.75, texture.enemy6, texture.enemyBlast6, game, texture);
		enemy7 = new Enemy(Math.random() * 510 + 64, -64, 64, 64, 500, 500, 110, 90, 0.5, texture.enemy7, texture.enemyBlast7, game, texture);
		//////////////////////////////////////money, health/////////////////////
		boss1 = new Boss(145, -300, 350, 300, 10000, 50000, texture.boss1, texture.enemyBlast7, game, texture);
	}
	
	public void tick() {
		if(level == 35) {
			if(money) {
				money = false;
				game.getPlayer().money(100 * level / 5);
			}
			if(spawnEnemies && enemies[7] > 0) {
				game.getSpawner().addBoss(boss1);
				enemies[7]--;
			} else if(spawnEnemies && game.getSpawner().getBoss().isEmpty()) {
				game.nextLevel();
			}
			counter++;
		} else {
			if(!game.getPlayer().getPause()) {
				if(level % 5 == 0 && money) {
					money = false;
					game.getPlayer().money(100 * level / 5);
				}
				if(spawnEnemies && counter >= 30) {
					int random = getRandomSpawn();
					if(random != -1 && enemies[random] > 0) {
						Enemy tempEnemy;
						if(random == 0) {
							tempEnemy = enemy1.clone();
							tempEnemy.setX(Math.random() * 510 + 64);
							game.getSpawner().addEnemy(tempEnemy);
						} else if(random == 1) {
							tempEnemy = enemy2.clone();
							tempEnemy.setX(Math.random() * 510 + 64);
							game.getSpawner().addEnemy(tempEnemy);
						} else if(random == 2) {
							tempEnemy = enemy3.clone();
							tempEnemy.setX(Math.random() * 510 + 64);
							game.getSpawner().addEnemy(tempEnemy);
						} else if(random == 3) {
							tempEnemy = enemy4.clone();
							tempEnemy.setX(Math.random() * 510 + 64);
							game.getSpawner().addEnemy(tempEnemy);
						} else if(random == 4) {
							tempEnemy = enemy5.clone();
							tempEnemy.setX(Math.random() * 510 + 64);
							game.getSpawner().addEnemy(tempEnemy);
						} else if(random == 5) {
							tempEnemy = enemy6.clone();
							tempEnemy.setX(Math.random() * 510 + 64);
							game.getSpawner().addEnemy(tempEnemy);
						} else if(random == 6) {
							tempEnemy = enemy7.clone();
							tempEnemy.setX(Math.random() * 510 + 64);
							game.getSpawner().addEnemy(tempEnemy);
						}
						enemies[random]--;
						counter = 0;
					} else if(game.getSpawner().getEnemy().isEmpty()) {
						game.nextLevel();
					}
				}
				counter++;
			}
		}
	}
	
	public void render(Graphics g) {
		if(!spawnEnemies && counter < 150) {
			if(level == 35) {
				game.getPlayer().increaseHealth(1000);
				g.setColor(Color.RED);
				g.setFont(new Font("SansSerif", Font.BOLD, 35));
				g.drawString("Final Boss", game.getMainMenu().getCenterX("Final Boss", g, g.getFont()), 100);
				g.setFont(new Font("SansSerif", Font.BOLD, 25));
				g.drawString("Your Armor Has Been Restored To Full", game.getMainMenu().getCenterX("Your Armor Has Been Restored To Full", g, g.getFont()), 150);
			} else {
				g.setColor(Color.RED);
				g.setFont(new Font("SansSerif", Font.BOLD, 35));
				g.drawString("Wave " + level, game.getMainMenu().getCenterX("Wave " + level, g, g.getFont()), 100);
				g.setFont(new Font("SansSerif", Font.BOLD, 25));
				if(level % 5 == 0) {
					g.drawString("Wave Bonus: $" + (100 * level / 5), game.getMainMenu().getCenterX("Wave Bonus: $" + (100 * level / 5), g, g.getFont()), 150);
				}
			}
		} else {
			startSpawning();
		}
	}
	
	public int getRandomSpawn() {
		int num = 0;
		for(int i = 0; i < enemies.length; i++) {
			if(enemies[i] > 0) {
				num++;
			}
		}
		if(num == 0) {
			return -1;
		}
		int random = (int)(Math.random() * num);
		for(int i = random; i < enemies.length; i++) {
			if(enemies[i] > 0) {
				return i;
			}
		}
		return -1;
	}
	
	public void startSpawning() {
		this.spawnEnemies = true;
	}
}
