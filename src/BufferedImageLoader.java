import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {
	private BufferedImage image;
	public BufferedImage loadImage(String name) throws IOException {
		image = ImageIO.read(new File(name));
		return image;
	}
}
