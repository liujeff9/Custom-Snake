import java.awt.Color;
import java.awt.Graphics;

public class Food extends Square {
	
    public Food(int x, int y) {
        super(x, y);
    }
    
    //draws RED circle
    public void draw(Graphics g) {
    	g.setColor(Color.RED);
        g.fillOval(getX(), getY(), 24, 24);
    }
}