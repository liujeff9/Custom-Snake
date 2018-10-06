import java.awt.Color;
import java.awt.Graphics;

public class ReverseFood extends Food {
	
	int count;

	public ReverseFood(int x, int y) {
		super(x, y);
		count = 0;
	}
	
	public void move(int x, Snake snake) {
		count++;
		if (!intersect(snake, x) && count > 20 && count < 35) {
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
	
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval(getX(), getY(), 24, 24);
	}

}
