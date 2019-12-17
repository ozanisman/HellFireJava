import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class HellFireGame extends Canvas implements Runnable {
	
	public JFrame window;
	public static final int WIDTH = 640;
	public static final int HEIGHT = 720;
	private boolean running = false;
	private Thread thread;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private BufferedImage shipSheet, missileSheet, laserSheet, blastSheet, explosionSheet, shipExplosionSheet, powerupSheet, shieldSheet, enemyBlastSheet, bossSheet, background, background2;
	private int backgroundCounter = 0;
	private Player player;
	private Spawner spawner;
	private Textures texture;
	private STATE State = STATE.MENU;
	private MainMenu mainMenu;
	private UpgradesMenu upgradesMenu;
	private TutorialMenu tutorialMenu;
	private SettingsMenu settingsMenu;
	private Level[] level = new Level[getLevels()];
	private int currentLevel = 1;
	private Sound sound;
	private boolean musicMuted = false;
	private int musicCounter= 5340;
	private boolean hacksOn = false;
	
	
	public HellFireGame(JFrame window) {
		this.window = window;
	}
	
	public enum STATE {
		MENU,
		GAME,
		UPGRADE,
		TUTORIAL,
		SETTINGS
	}
	
	public void init() {
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			shipSheet = loader.loadImage("images/shipSheet.png");
			missileSheet = loader.loadImage("images/missilesheet.png");
			laserSheet = loader.loadImage("images/laserSheet.png");
			blastSheet = loader.loadImage("images/blastSheet.png");
			explosionSheet = loader.loadImage("images/explosionSheet.png");
			shipExplosionSheet = loader.loadImage("images/shipexplosionsheet.png");
			powerupSheet = loader.loadImage("images/powerupSheet.png");
			shieldSheet = loader.loadImage("images/shieldsheet.png");
			enemyBlastSheet = loader.loadImage("images/enemyblastsheet.png");
			bossSheet = loader.loadImage("images/boss1.png");
			background = loader.loadImage("images/background.jpg");
			background2 = loader.loadImage("images/background.jpg");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		texture = new Textures(this);
		player = new Player(288, 500, this, texture, 64, 64);
		mainMenu = new MainMenu(this);
		upgradesMenu = new UpgradesMenu(this);
		tutorialMenu = new TutorialMenu(this, texture);
		settingsMenu = new SettingsMenu(this);
		spawner = new Spawner(this);
		sound = new Sound();
		for(int i = 0; i < level.length; i++) {
			level[i] = new Level(i + 1, this, texture);
		}
	}
	
	public void restart() {
		setVisibleCursor();
		currentLevel = 1;
		player.restart();
		spawner.restart();
		for(int i = 0; i < level.length; i++) {
			level[i] = new Level(i + 1, this, texture);
		}
		tutorialMenu.setStage(0);
	}
	
	public synchronized void start() {
		if(running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	
	public synchronized void stop() {
		if(!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				updates++;
				frames++;
				delta--;
			}

			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println(updates + " Ticks, Fps " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();
		
	}
	
	private void tick() {
		requestFocusInWindow();
		if(musicCounter == 5340 && !musicMuted) {
			musicCounter = 0;
			sound.stopSound();
			sound.playSound();
		} else if(musicCounter < 5340) {
			musicCounter++;
		}
		if(State == STATE.GAME) {
			if(!player.getMouseControl()) {
				setVisibleCursor();
			}
			if(currentLevel != level.length + 1) {
				level[currentLevel - 1].tick();
			} else {
				player.gameOver(true);
			}
			player.tick();
			spawner.tick();
			
		} else if(State == STATE.MENU || State == STATE.UPGRADE || State == STATE.SETTINGS || State == STATE.TUTORIAL) {
			setVisibleCursor();
		}
		if(State == STATE.UPGRADE) {
			upgradesMenu.tick();
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		//DRAW HERE/////////////////////////////
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		if(backgroundCounter == 900) {
			backgroundCounter = 0;
		}
		g.drawImage(background, 0, -180 + backgroundCounter, this);
		g.drawImage(background2, 0, -1080 + backgroundCounter, this);
		if(musicCounter % 3 == 0) {
			backgroundCounter++;
		}
		
		if(State == STATE.GAME) {
			spawner.render(g);
			player.render(g);
			if(currentLevel != level.length + 1) {
				level[currentLevel - 1].render(g);
			}
		} else if(State == STATE.MENU) {
			mainMenu.render(g);
		} else if(State == STATE.UPGRADE) {
			upgradesMenu.render(g);
		} else if(State == STATE.TUTORIAL) {
			tutorialMenu.render(g);
		} else if(State == STATE.SETTINGS) {
			settingsMenu.render(g);
		}
		
		/////////////////////////////////////////
		
		g.dispose();
		bs.show();
	}
	
	public void setInvisibleCursor() {
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		window.setCursor(blankCursor);
	}
	
	public void setVisibleCursor() {
		window.setCursor(Cursor.getDefaultCursor());
	}
	
	public BufferedImage getShipSheet() {
		return shipSheet;
	}
	
	public BufferedImage getMissileSheet() {
		return missileSheet;
	}
	
	public BufferedImage getLaserSheet() {
		return laserSheet;
	}
	
	public BufferedImage getBlastSheet() {
		return blastSheet;
	}
	
	public BufferedImage getExplosionSheet() {
		return explosionSheet;
	}
	
	public BufferedImage getPowerupSheet() {
		return powerupSheet;
	}
	
	public BufferedImage getShipExplosionSheet() {
		return shipExplosionSheet;
	}
	
	public BufferedImage getShieldSheet() {
		return shieldSheet;
	}
	
	public BufferedImage getEnemyBlastSheet() {
		return enemyBlastSheet;
	}
	
	public BufferedImage getBossSheet() {
		return bossSheet;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Spawner getSpawner() {
		return spawner;
	}
	
	public Textures getTexture() {
		return texture;
	}
	
	public Boolean checkState(String state) {
		if(state == "menu" && State == STATE.MENU) {
			return true;
		} else if(state == "game" && State == STATE.GAME) {
			return true;
		} else if(state == "upgrades" && State == STATE.UPGRADE) {
			return true;
		} else if(state == "settings" && State == STATE.SETTINGS) {
			return true;
		} else if(state == "tutorial" && State == STATE.TUTORIAL) {
			return true;
		}
		return false;
	}
	
	public void setStateGame() {
		State = STATE.GAME;
		player.upgrade();
	}
	
	public void setStateMenu() {
		setVisibleCursor();
		State = STATE.MENU;
		restart();
	}
	
	public void setStateUpgrade() {
		setVisibleCursor();
		State = STATE.UPGRADE;
	}
	
	public void setStateTutorial() {
		setVisibleCursor();
		State = STATE.TUTORIAL;
	}
	
	public void setStateSettings() {
		setVisibleCursor();
		State = STATE.SETTINGS;
	}
	
	public MainMenu getMainMenu() {
		return mainMenu;
	}
	
	public UpgradesMenu getUpgradesMenu() {
		return upgradesMenu;
	}
	
	public TutorialMenu getTutorialMenu() {
		return tutorialMenu;
	}
	
	public SettingsMenu getSettingsMenu() {
		return settingsMenu;
	}

	public void nextLevel() {
		currentLevel++;
	}
	
	public int getLevels() {
		int counter = 0;
		try {
			FileReader fileReader = new FileReader("LevelData.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = bufferedReader.readLine();
			while(line != null) {
				line = bufferedReader.readLine();
				counter++;
			}
			
			bufferedReader.close();
		} catch (IOException e) {
			System.out.println("Level File Not Found!");
		}
		return counter;
	}
	
	public boolean getHacksOn() {
		return hacksOn;
	}
	
	public void setHacksOn(boolean b) {
		hacksOn = b;
	}
	
	public boolean getMusicMuted() {
		return musicMuted;
	}
	
	public void setMusicMuted(boolean b) {
		musicMuted = b;
	}
	
	public Sound getSound() {
		return sound;
	}
}