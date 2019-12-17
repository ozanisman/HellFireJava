import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HellFireMain { //////////////////////ACTUAL GAME\\\\\\\\\\\\\\\\\\\\\\\
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 720;
	public static JFrame window = new JFrame("HellFire");
	
	public static void main(String[] args) {
		window.setResizable(false);
		
		HellFireGame game = new HellFireGame(window);
		game.init();
		game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		game.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		game.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		
		
		
		window.requestFocusInWindow();
		window.add(game);
		window.pack();
        HellFireListener listener = new HellFireListener(game);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2);
		
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        
        game.start();
        
        
	}
	
	
}