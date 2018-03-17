package edu.ucsb.cs56.projects.games.cs56_games_maze;

import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.border.EmptyBorder;
import javax.imageio.*;
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

        ImageIcon buttonImg = new ImageIcon("Button.jpg");
        title = new JLabel();

        startButton = new JButton(buttonImg);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setBorder(new EmptyBorder(20, 20, 50, 25));
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
        title.setText("CS56 Maze Game");
        this.setLayout(new BorderLayout());
        //startButton.setLocation(150, 350);
        this.add(startButton, BorderLayout.SOUTH);
        instructionsButton.setText("Instructions");
        this.setPreferredSize(new Dimension(480, 480));
    }

    public void start() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("homebackground2.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("failed. "+ f.listFiles());
        }
        g.drawImage(img, 0, 0, 480, 480, null);
    }

}
