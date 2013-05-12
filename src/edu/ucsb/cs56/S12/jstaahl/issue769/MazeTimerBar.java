package edu.ucsb.cs56.S12.jstaahl.issue769;
import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;


public class MazeTimerBar extends JPanel{

    private JTextField timerField;
    private JButton newButton;
    private JButton solveButton;
    private long startTime;
    private SimpleDateFormat timerFormat;
    private Timer t;
    private MazeGui parentMazeGui;
    
    public MazeTimerBar(MazeGui parent){
	super();
	this.parentMazeGui=parent;

	this.timerField = new JTextField("00:00:000");
	this.add(this.timerField);

	this.newButton = new JButton("New");
	this.add(this.newButton);
	this.newButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e){
		    parentMazeGui.newMaze();
		}
	    });

	this.solveButton = new JButton("Solve");
	this.add(this.solveButton);
	this.solveButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e){
		    parentMazeGui.solveMaze();
		}
	    });

	this.timerFormat = new SimpleDateFormat("mm:ss:SSS");
    }

    public void printComponent(Graphics g){
	super.printComponent(g);
	Graphics2D g2d = (Graphics2D)g;
    }
    
    public void startTimer(){
	startTime = System.currentTimeMillis();
	t = new Timer(1, new ActionListener() {
		public void actionPerformed(ActionEvent e){
		    //update text field
		    timerField.setText(timerFormat.format(new Date(System.currentTimeMillis()-startTime)));
		}
	    });
	t.start();
    }

    public long restartTimer(){
	t.stop();
	long temp = System.currentTimeMillis()-this.startTime;
	this.startTimer();
	return temp;
    }

    public long stopTimer(){
	t.stop();
	return System.currentTimeMillis()-this.startTime;
    }

}