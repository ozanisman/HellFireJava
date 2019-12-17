import java.util.*;
import java.awt.*;

public class Spawner {	
	private ArrayList<Entity> bullets = new ArrayList<Entity>();
	private ArrayList<Entity> enemies = new ArrayList<Entity>();
	private ArrayList<Entity> boss = new ArrayList<Entity>();
	private ArrayList<Entity> powerups = new ArrayList<Entity>();
	Entity holder;
	HellFireGame game;
	
	public Spawner(HellFireGame game) {
		this.game = game;
	}
	
	public void tick() {
		for(int i = 0; i < bullets.size(); i++) {
			holder = bullets.get(i);
			if(holder.getY() < 0 || holder.getY() > 630) {
				removeBullet(holder);
				i--;
			}
			holder.tick();
		}
		
		for(int i = 0; i < enemies.size(); i++) {
			
			holder = enemies.get(i);
			if(holder.getY() > 610) {
				removeEnemy(holder);
				game.getPlayer().takeEscapeDamage();
				i--;
			}
			holder.tick();
		}
		
		for(int i = 0; i < boss.size(); i++) {
			holder = boss.get(i);
			holder.tick();
		}
		
		for(int i = 0; i < powerups.size(); i++) {
			holder = powerups.get(i);
			if(holder.getY() > 640) {
				removePowerup(holder);
				i--;
			}
			holder.tick();
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < bullets.size(); i++) {
			holder = bullets.get(i);
			holder.render(g);
		}
		
		for(int i = 0; i < enemies.size(); i++) {
			holder = enemies.get(i);
			holder.render(g);
		}
		
		for(int i = 0; i < boss.size(); i++) {
			holder = boss.get(i);
			holder.render(g);
		}
		
		for(int i = 0; i < powerups.size(); i++) {
			holder = powerups.get(i);
			holder.render(g);
		}
	}
	
	public void addBullet(Entity b) {
		bullets.add(b);
	}
	
	public void removeBullet(Entity holder) {
		bullets.remove(holder);
	}
	
	public ArrayList<Entity> getBullet() {
		return bullets;
	}
	
	public void addEnemy(Entity e) {
		enemies.add(e);
	}
	
	public void removeEnemy(Entity holder) {
		enemies.remove(holder);
	}
	
	public ArrayList<Entity> getEnemy() {
		return enemies;
	}
	
	public void addBoss(Entity e) {
		boss.add(e);
	}
	
	public void removeBoss(Entity holder) {
		boss.remove(holder);
	}
	
	public ArrayList<Entity> getBoss() {
		return boss;
	}
	
	public void addPowerup(Powerup p) {
		powerups.add(p);
	}
	
	public void removePowerup(Entity holder) {
		powerups.remove(holder);
	}
	
	public ArrayList<Entity> getPowerup() {
		return powerups;
	}
	
	public void restart() {
		bullets.clear();
		enemies.clear();
		boss.clear();
		powerups.clear();
	}
}
