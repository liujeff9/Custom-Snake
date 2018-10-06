import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Game implements Runnable {
	public void run() {
		final JFrame frame = new JFrame("Snake");
		frame.setLocation(0, 0);
		
		//status panels informs user of score
		final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);
        
        //new game screen
        final GameCourt court = new GameCourt(status);
        frame.add(court, BorderLayout.CENTER);
        
        //buttons on top of the GameScreen
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        
        //adds the Main Menu button, which rests the game
        final JButton restart = new JButton("Main Menu");
        restart.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		court.reset();
        	}
        });
        
        //adds button to control panel
        control_panel.add(restart);
        
        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        
        // Start game
        court.reset();
	}
	
	/**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
	}
}
