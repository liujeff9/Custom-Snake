import java.awt.Color;
import java.awt.Graphics;

public class Body extends Square {
	
	public Body(int x, int y) {
		super(x, y);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(getX(), getY(), 23, 23);
	}
}
