import java.awt.Graphics;

public abstract class Square {
	private int x;
	private int y;
	
	public Square(int x, int y) {	
		this.x = clip(x);
		this.y = clip(y);
	}
	
	//makes sure the integer is within the grid
	public int clip(int x) {
		if (x > 30 * 25) {
			return 30 * 25;
		} else if (x < 0) {
			return 0;
		} else {
			return x;
		}
	}
	
	//returns the x value
	public int getX() {
		return x;
	}
	
	//returns the y value
	public int getY() {
		return y;
	}
	
	//changes the x value by int x
	public void moveX(int x) {
		this.x += x;
	}
	
	//changes the y value by int y
	public void moveY(int y) {
		this.y += y;
	}
	
	//returns true if two Squares share the same position
	public boolean sameSpot(Square s) {
		if (s == null) {
			return false;
		}
		return x == s.getX() && y == s.getY();
	}
	
	//classes that extend this will have a draw function
	public abstract void draw(Graphics g);
}
