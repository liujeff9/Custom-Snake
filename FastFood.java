import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class FastFood extends Food {
	
	private static int speed;
	Random r = new Random();

	public FastFood(int x, int y) {
		super(x, y);
		speed = 90;
	}
	
	//changes the int value of the FastFood
	public int changeSpeed() {
		int random = r.nextInt(100);
		if (random < 50) {
			speed = r.nextInt(30) + 60;
		} else {
			speed = 150 - r.nextInt(30);
		}
		return speed;
	}
	
	//draw ORANGE circle
	public void draw(Graphics g) {
		g.setColor(Color.ORANGE);
        g.fillOval(getX(), getY(), 24, 24);
	}
	
	//FastFood moves, expect it does not
	public void move() {
		moveX(0);
		moveY(0);
	}
	
	//returns the speed for testing purposes
	public int getSpeed() {
		return speed;
	}
}
