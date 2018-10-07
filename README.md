# Custom-Snake

This is a project I created, we I add new elements to a snake game. There are different types of foods that can change the way the game is played! For example, some foods move, some change the speed of the snake, and others that make the game more exciting! The game is created with the Java Swing library.
<br />

IMPLEMENTATION: <br />
    src/Game.java
    	This is the game class containing the main method <br />
    src/Body.java
    	Contains the Square that will make up the snake <br />
    src/Direction.java
    	enum of all possible Directions <br />
    src/FastFood.java
    	Extends Food and helps adjust the speed of the game <br />
    src/Food.java
    	Basic drawing instructions for food, extends Square <br />
    src/GameCourt.java
    	This is the main class that ties all the other classes together when the Game is running <br />
    src/MovingFood.java
    	Extends Food and contains move functions to move the object <br />
    src/ReverseFood.java
    	Extends Food and has its own unique move pattern, determines if controls are reversed <br />
    src/Score.java
    	Creates the reader and write to store high scores <br />
    src/ScoreObject.java
    	Object containing a name and an integer <br />
    src/Snake.java
    	Snake class that contains methods for how it moves <br />
    src/Square.java
    	Fundamental of the program, stores a location and makes up most of the objects <br />
    files/HighScores.txt
    	File that Score reads from which stores all the high scores and the names <br />
    test/GameTest.java
    	Contains JUnit tests for GameCourt, snake movements and food <br />
<br />
CONCEPTS:<br />

Collections: I used a LinkedList of the class Body to store the elements of a snake. This way I could add to the front
		which represented the tile where the snake was headed and then could easily remove the last Body. Additionally, I used a
		LinkedList to store the ScoreObjectis. This way i could easily sort the list by integer values and return the 3 largest values.
<br />
FileIO: I used a buffered reader and write to save the high scores to a File. The user could input their name and it would be written to
		the file, along with with the score the user receives before losing the game. Each time the game runs, it reads the given file and seeks
		for previously set high scores and continues to add scores and saves them. This displays the 3 highest scores along with the user who got
		it.
<br />
Inheritance: Both the foods and snake body extends the Square class. This abstract class has values representing the position and each class
		extending it has a draw method. FastFood, MovingFood and ReverseFood all extend the Food class, each of these foods are drawn 
		differently and move differently.
<br />
Testing: I used JUnit tests to make sure each function was working properly. It tests to make sure foods are generated properly and that there
		is only one of each.
