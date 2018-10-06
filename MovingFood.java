import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class MovingFood extends Food {
	Random r = new Random();

	public MovingFood(int x, int y) {
		super(x, y);
	}
	
	//moves MovingFood, takes int for direction
	public void move(int x, int random) {
		if (random < 30) {
			switch (x) {
			case 1:
				if (getY() + 25 < 30 * 25) {
					moveY(25);
				}
				break;
			case 2:
				if (getY() - 25 > 0) {
					moveY(-25);
				}
				break;
			case 3:
				if (getX() - 25 > 0) {
					moveX(-25);
				}
				break;
			case 4:
				if (getX() + 25 < 30 * 25) {
					moveX(25);
				}
				break;
			}
		}
	}
	
	//determines if the food would overlap with the snake
	public boolean intersect(Snake snake, int x) {
		int pX = 0;
		int pY = 0;
		switch (x) {
		case 1:
			pY = getY() + 25;
			pX = this.getX();
			break;
		case 2:
			pY = getY() - 25;
			pX = this.getX();
			break;
		case 3:
			pX = getX() - 25;
			pY = this.getY();
			break;
		case 4:
			pX = getX() + 25;
			pY = this.getY();
			break;
		}
		return snake.contains(new Body(pX, pY));
	}

	//draws BLUE circle
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillOval(getX(), getY(), 24, 24);
	}
}
