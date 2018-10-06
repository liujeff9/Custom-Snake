import java.awt.Graphics;
import java.util.*;

public class Snake {
	private LinkedList<Body> snake;
	private Body head;
	Direction direction;
	
	public Snake(Body head) {	
		snake = new LinkedList<Body>();
		this.head = head;
		snake.add(head);
    }
	
	public Snake(Snake copy) {
		snake = copy.snake;
		head = copy.head;
		snake.add(head);
	}
	
	//moves the snake by drawing new body and removing the last
	public void move(Direction newDirection) {
		Body newSegment = null;
        switch (newDirection) {
		case DOWN: 
		    direction = newDirection;
		    newSegment = new Body(head.getX(), head.getY() + 25);
			snake.addFirst(newSegment);
			head = newSegment;
			snake.removeLast();			
			break;
		case UP: 
			direction = newDirection;
			newSegment = new Body(head.getX(), head.getY() - 25);
			snake.addFirst(newSegment);
			head = newSegment;
			snake.removeLast();
			break;
		case LEFT:
			direction = newDirection;
			newSegment = new Body(head.getX() + 25, head.getY());
			snake.addFirst(newSegment);
			head = newSegment;
			snake.removeLast();
			break;
		case RIGHT:
			direction = newDirection;
			newSegment = new Body(head.getX() - 25, head.getY());
			snake.addFirst(newSegment);
			head = newSegment;
			snake.removeLast();
			break;
        }
	}
	
	//grows the snake by adding a new Body to the list
	public void eat(Direction newDirection) {
		Body body = null;
		switch(newDirection) {
		case DOWN:
			body = new Body(head.getX(), head.getY() + 25);
			break;
		case UP:
			body = new Body(head.getX(), head.getY() - 25);
			break;
		case LEFT: 
			body = new Body(head.getX() + 25, head.getY());
			break;
		case RIGHT:
			body = new Body(head.getX() - 25, head.getY());
			break;
		}		
		snake.addLast(body);
	}
	
	//draws the snake
	public void draw(Graphics g) {
		for (Body b : snake) {
			b.draw(g);
		}
	}
	
	//checks if the snake intersects its own body
	public boolean intersect() {
		int count = 0;
		for (Body b : snake) {
			if (b.sameSpot(getHead())) {
				count++;
			}
		}
		return count >= 2;
	}
	
	//checks if the snake contains body part at a location
	public boolean contains(Square s) {
		for (Body b : snake) {
			if (b.getX() == s.getX() && b.getY() == s.getY()) {
				return true;
			}
		}
		return false;
	}
	
	//returns the size
	public int getSize() {
		return snake.size();
	}
	
	//returns the Head
	public Body getHead() {
		return head;
	}
	
	//returns the snake
	public LinkedList<Body> getSnake() {
		return snake;
	}
	
	//returns the Direction
	public Direction getDirection() {
		return direction;
	}
}
