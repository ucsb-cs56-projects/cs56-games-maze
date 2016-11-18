package edu.ucsb.cs56.projects.games.cs56_games_maze;

import javax.swing.*;
import java.awt.*;

/**
 * Created by arthursilverstein on 11/18/16.
 */
public class HomeScreen extends JPanel {

    private JFrame frame;
    private JLabel title;
    private JButton startButton;
    private JButton instructionsButton;

    private String[] arguments; //we need to store the command line args to pass to MazeGui

    public HomeScreen(String[] args) {
        arguments = args;

        title = new JLabel();
        startButton = new JButton();
        instructionsButton = new JButton();

        startButton.addActionListener((e) -> {
            System.out.println("stub for startbutton");
            new MazeGui(arguments).run();
            frame.setVisible(false);
            frame.dispose();
        });
        instructionsButton.addActionListener((e) -> {
            System.out.println("stub for instruction button");
        });
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        title.setText("CS56 Maze Game");
        this.add(title);
        startButton.setText("Start");
        this.add(startButton);
        instructionsButton.setText("Instructions");
        this.add(instructionsButton);
        this.setPreferredSize(new Dimension(500, 300));
    }

    public void start() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

}
