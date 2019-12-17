import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;

public class MainMenu {
	
	private HellFireGame game;
	
	
	public MainMenu(HellFireGame game) {
		this.game = game;
	}
	
	public void render(Graphics g) {
		Font f = new Font("SansSerif", Font.BOLD, 50);
		g.setFont(f);
		g.setColor(Color.RED);
		g.drawString("HELLFIRE", getCenterX("HELLFIRE", g, f), 125);
		f = new Font("SansSerif", Font.BOLD, 30);
		g.setFont(f);
		g.drawString("Designed by Oz Anisman", getCenterX("Designed by Oz Anisman", g, f), 195);
		f = new Font("SansSerif", Font.BOLD, 30);
		g.setFont(f);
		g.fillRect(235, 250, 170, 50);
		g.fillRect(235, 350, 170, 50);
		g.fillRect(235, 450, 170, 50);
		g.setColor(Color.BLACK);
		g.drawString("PLAY", getCenterX("PLAY", g, f), 287);
		g.drawString("TUTORIAL", getCenterX("TUTORIAL", g, f), 387);
		g.drawString("SETTINGS", getCenterX("SETTINGS", g, f), 487);
		
	}
	
	public static int getCenterX(String str, Graphics g, Font f) {
		///// From http://www.java-gaming.org/index.php/topic,25375. //////
			FontMetrics metrics = g.getFontMetrics(f);
			Rectangle2D rect = metrics.getStringBounds(str, g); 
			int strWidth = (int) rect.getWidth();
			int strHeight = (int) rect.getHeight();
			int centerX = 640/2;
			int centerY = 720/2;
			int strx = centerX - (strWidth/2);
			int stry = centerY - (strHeight/2);
		///////////////////////////////////////////////////////////////
			return strx;
	}
	
}
