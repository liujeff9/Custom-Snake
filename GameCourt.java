import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.IOException;
import java.util.Random;

@SuppressWarnings("serial")
public class GameCourt extends JPanel {

	private Snake snake;
	private Food food;
	private FastFood fastFood;
	private MovingFood movingFood;
	private ReverseFood reverseFood;
	private Timer timer;
	private JLabel status;
	private Score scoreKeeper;
	private int score;
	private String name;
	private boolean reverse;

	private Direction direction;
	private Random r = new Random();
	private GameMode mode;

	public static final int COURT_WIDTH = 750;
	public static final int COURT_HEIGHT = 750;
	public static final int INTERVAL = 90;

	public enum GameMode {
		PLAYING, LOSE, INSTRUCTIONS, SCORES, WIN;
	}

	public GameCourt(JLabel status) {

		// creates a scoreKeeper
		try {
			scoreKeeper = Score.make("files/HighScores.txt");
		} catch (IOException e) {
		}

		// adds black border
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// creates a timer
		timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});

		// starts the timer
		timer.start();

		setFocusable(true);

		// adds keyListener to change the direction of the snake or GameMode
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (mode == GameMode.PLAYING) {
					if (e.getKeyCode() == KeyEvent.VK_UP && !reverse) {
						changeDirection(Direction.UP);
					} else if (e.getKeyCode() == KeyEvent.VK_DOWN && !reverse) {
						changeDirection(Direction.DOWN);
					} else if (e.getKeyCode() == KeyEvent.VK_LEFT && !reverse) {
						changeDirection(Direction.RIGHT);
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !reverse) {
						changeDirection(Direction.LEFT);
					} else if (e.getKeyCode() == KeyEvent.VK_UP && reverse) {
						changeDirection(Direction.DOWN);
					} else if (e.getKeyCode() == KeyEvent.VK_DOWN && reverse) {
						changeDirection(Direction.UP);
					} else if (e.getKeyCode() == KeyEvent.VK_LEFT && reverse) {
						changeDirection(Direction.LEFT);
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && reverse) {
						changeDirection(Direction.RIGHT);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (mode == GameMode.INSTRUCTIONS) {
						mode = GameMode.PLAYING;
					}
					if (mode == GameMode.LOSE) {
						reset();
						mode = GameMode.INSTRUCTIONS;
					}
				} else if (e.getKeyCode() == KeyEvent.VK_P) {
					if (mode == GameMode.INSTRUCTIONS) {
						mode = GameMode.SCORES;
					}
				}
			}
		});

		this.status = status;
	}

	// resets the game state, clears the score and creates a new snake
	public void reset() {
		snake = new Snake(new Body(500, 500));
		food = new Food(r.nextInt(30) * 25, r.nextInt(30) * 25);
		fastFood = null;
		movingFood = null;
		reverseFood = null;
		score = 0;
		name = null;
		reverse = false;
		direction = Direction.LEFT;
		mode = GameMode.INSTRUCTIONS;
		requestFocusInWindow();
	}

	// one step
	void tick() {
		if (mode == GameMode.PLAYING) {

			// updates the score
			status.setText("Score: " + score);

			// checks if snake has hit a wall
			if (hasHit()) {
				lost();
			}

			// snake moves
			snake.move(direction);

			// checks if the snake has hit itself
			if (snake.intersect()) {
				lost();
			}

			// checks if the snake has eaten any of the foods
			// if a fast food is eaten, the internal of the timer changes
			if (snake.getHead().sameSpot(food)) {
				timer.stop();
				reverse = false;
				grow();
			} else if (snake.getHead().sameSpot(fastFood)) {
				timer.stop();
				reverse = false;
				timer = new Timer(fastFood.changeSpeed(), new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						tick();
					}
				});
				grow();
			} else if (snake.getHead().sameSpot(movingFood)) {
				timer.stop();
				reverse = false;
				grow();
			} else if (snake.getHead().sameSpot(reverseFood)) {
				timer.stop();
				reverse = true;
				grow();
			}
			
			// if a MovingFood is present, generate random movement
			int tempHelp = r.nextInt(4) + 1;
			if (movingFood != null && !movingFood.intersect(snake, tempHelp)) {
				movingFood.move(tempHelp, r.nextInt(100));
			}
			
			// if a ReverseFood is present, after a certain count, it moves
			if (reverseFood != null) {
				reverseFood.move(tempHelp, snake);
			}

			// win condition
			if (score == 8000) {
				mode = GameMode.WIN;
			}
		}
		repaint();
	}

	// snake grows and then a random food spawns
	public void grow() {
		snake.eat(direction);
		score += 10;
		int next = r.nextInt(100);
		if (next > 75) {
			generateFastFood();
		} else if  (next <= 75 && next > 71){
			generateReverseFood();
		} else if (next < 15) {
			generateMovingFood();
		} else if (next <= 71 && next >= 15) {
			generateFood();
		}
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// draws the game depending on the state of the game
		if (mode == GameMode.PLAYING) {
			snake.draw(g);
			if (food != null) {
				food.draw(g);
			} else if (fastFood != null) {
				fastFood.draw(g);
			} else if (movingFood != null) {
				movingFood.draw(g);
			} else {
				reverseFood.draw(g);
			}
		} else if (mode == GameMode.INSTRUCTIONS) {
			int tempX = 200;
			g.drawString("				                      INSTRUCTIONS", tempX, 300);
			g.drawString("Use the  arrow  keys  to  guide the green Slither.io", tempX, 320);
			g.drawString("Eat flies, some foods will have different effects", tempX, 340);
			g.drawString("Stupid RED has no effect, Vitamin B12 ORANGE adjusts the game speed,", tempX, 360);
			g.drawString("Crazy Crossover BLUE moves around the board randomly, is a wild", tempX, 380);
			g.drawString("one and takes your neck and Intoxicated YELLOW reverses the keys", tempX, 400);
			g.drawString("                  makes random movements", tempX, 420);
			g.drawString("			                   Press SPACE to play", tempX, 460);
			g.drawString("			            Press P to see High Scores", tempX, 480);
		} else if (mode == GameMode.LOSE) {
			g.drawString("You hit some Jawn", 250, 375);
			g.drawString(name + ", your score is: " + score, 250, 400);
			g.drawString("Press SPACE to return to the Main Menu", 250, 450);
		} else if (mode == GameMode.SCORES) {
			g.drawString("High Scores", 150, 330);
			g.drawString(scoreKeeper.getHighScores(), 150, 350);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}

	// changes the direction of the snake, cannot move backwards if size > 1
	public void changeDirection(Direction newDirection) {
		switch (newDirection) {
		case DOWN:
			if (direction != Direction.UP || snake.getSize() == 1) {
				direction = newDirection;
			}
			break;
		case UP:
			if (direction != Direction.DOWN || snake.getSize() == 1) {
				direction = newDirection;
			}
			break;
		case LEFT:
			if (direction != Direction.RIGHT || snake.getSize() == 1) {
				direction = newDirection;
			}
			break;
		case RIGHT:
			if (direction != Direction.LEFT || snake.getSize() == 1) {
				direction = newDirection;
			}
			break;
		}
	}

	// lose condition, changes the screen and writes to the score list
	public void lost() {
		mode = GameMode.LOSE;
		status.setText("Game Over");
		name = JOptionPane.showInputDialog(new JFrame(), "Game over. Enter your name:");
		try {
			if (name != null) {
				scoreKeeper.write(score, "ID" + r.nextInt(1000));
			} else {
			scoreKeeper.write(score, name);
			}
		} catch (IOException e) {
		}
	}

	// checks if the snake has hit the walls
	public boolean hasHit() {
		int headX = snake.getHead().getX();
		int headY = snake.getHead().getY();
		return headY < 0 || headX < 0 || headX >= 750 || headY >= 750;
	}

	// creates a new Food and removes all others
	public void generateFood() {
		while (true) {
			Food newFood = new Food(r.nextInt(30) * 25, r.nextInt(30) * 25);
			if (!snake.contains(newFood)) {
				fastFood = null;
				movingFood = null;
				reverseFood = null;
				food = newFood;
				break;
			}
		}
	}

	// creates a FastFood and removes all others
	public void generateFastFood() {
		while (true) {
			FastFood newFood = new FastFood(r.nextInt(30) * 25, r.nextInt(30) * 25);
			if (!snake.contains(newFood)) {
				food = null;
				movingFood = null;
				reverseFood = null;
				fastFood = newFood;
				break;
			}
		}
	}

	// creates a MovingFood and removes all others
	public void generateMovingFood() {
		while (true) {
			MovingFood newFood = new MovingFood(r.nextInt(29) * 25 + 25, r.nextInt(29) * 25 + 25);
			if (!snake.contains(newFood)) {
				food = null;
				fastFood = null;
				reverseFood = null;
				movingFood = newFood;
				break;
			}
		}
	}
	
	// creates a ReverseFood and removes all others
	public void generateReverseFood() {
		while (true) {
			ReverseFood newFood = new ReverseFood(r.nextInt(29) * 25 + 25, r.nextInt(29) * 25 + 25);
			if (!snake.contains(newFood)) {
				food = null;
				fastFood = null;
				movingFood = null;
				reverseFood = newFood;
				break;
			}
		}
	}

	// BELOW ARE FUNCTIONS FOR TESTING PURPOSES

	public Food getFood() {
		return food;
	}

	public FastFood getFastFood() {
		return fastFood;
	}

	public MovingFood getMovingFood() {
		return movingFood;
	}
	
	public ReverseFood getReverseFood() {
		return reverseFood;
	}

	public Snake getSnake() {
		return snake;
	}

	public int getScore() {
		return score;
	}
}
