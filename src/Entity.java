import java.awt.*;

public interface Entity {
	
	public void tick();
	public void render(Graphics g);
	
	public Rectangle getBounds();
	public double getY();
	
	public void explode();
	public boolean isTargetable();
	public void takeDamage(double damage);
}
