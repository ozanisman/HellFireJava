import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SettingsMenu {
	
	private HellFireGame game;
	public boolean mouseControl = true;
	public boolean hacksOn;
	public boolean musicMuted;
	
	public SettingsMenu(HellFireGame game) {
		this.game = game;
		musicMuted = game.getMusicMuted();
		hacksOn = game.getHacksOn();
	}
	
	public void render(Graphics g) {
		String str = "SETTINGS";
		Font f = new Font("SansSerif", Font.BOLD, 50);
		g.setFont(f);
		g.setColor(Color.RED);
		g.drawString("SETTINGS", MainMenu.getCenterX("SETTINGS", g, f), 150);
		g.setFont(new Font("SansSerif", Font.BOLD, 30));
		g.fillRect(510, 670, 110, 40);
		g.fillRect(50, 250, 305, 50);
		g.fillRect(50, 325, 130, 50);
		g.fillRect(50, 400, 130, 50);
		g.setColor(Color.BLACK);
		g.drawString("MENU", 522, 701);
		g.drawString("CONTROL SCHEME", 60, 287);
		g.drawString("HACKS", 60, 362);
		g.drawString("MUSIC", 60, 437);
		g.setColor(Color.GREEN);
		g.fillRect(400, 250, 150, 50);
		g.fillRect(400, 325, 150, 50);
		g.fillRect(400, 400, 150, 50);
		g.setColor(Color.BLACK);
		if(mouseControl) {
			g.drawString("MOUSE", 420, 287);
		} else {
			g.drawString("WASD", 431, 287);
		}
		if(hacksOn) {
			g.drawString("ON", 453, 362);
		} else {
			g.drawString("OFF", 453, 362);
		}
		if(musicMuted) {
			g.drawString("OFF", 453, 437);
		} else {
			g.drawString("ON", 453, 437);
		}
	}
	
}
