package edu.ucsb.cs56.projects.games.cs56_games_maze;
import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.awt.event.*;


/**
   Represents the control bar which contains the game timer, New Maze and Solve Maze buttons

   @author Evan West
   @version 5/14/13 for proj1, cs56, S13
*/
public class MazeTimerBar extends JPanel{

    private JTextField timerField;
    private JButton newButton;
    private JButton solveButton;
    private long startTime;
    private long stopTime;
    private long elapsed=0;
    private SimpleDateFormat timerFormat;
    private Timer t;
    private MazeGui parentMazeGui;
    
    /** Constructor for default MazeTimerBar
	@param parent The parent MazeGui instance that created this
     */
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

    /** Prints component, currently unnecessary override
     */
    public void printComponent(Graphics g){
	super.printComponent(g);
	Graphics2D g2d = (Graphics2D)g;
    }

    /** Starts the gameplay timer
     */
    public void startTimer(){
	startTime = System.currentTimeMillis()-this.elapsed;
	t = new Timer(1, new ActionListener() {
		public void actionPerformed(ActionEvent e){
		    //update text field
		    timerField.setText(timerFormat.format(new Date(System.currentTimeMillis()-startTime)));
		}
	    });
	t.start();
    }


    /** Restart gameplay timer
	@return long Value of timer before reset
     */
    public long restartTimer(){
	t.stop();
	long temp = System.currentTimeMillis()-this.startTime;
	this.startTimer();
	return temp;
    }

    /** Stop gameplay timer
	@return long Value of timer before stop
     */
    public long stopTimer(){
	t.stop();
	this.stopTime = System.currentTimeMillis();
	return this.stopTime-this.startTime;
    }

    /** Returns total time elapsed as displayed on the timer.
     */
    public long getTimeElapsed(){
	if(this.startTime>this.stopTime)
	    return System.currentTimeMillis()-this.startTime;
	else
	    return this.stopTime-this.startTime;       
    }

    /** Sets current timer value, used when resuming game state
	@param t Value in milliseconds to set timer to
     */
    public void setTimeElapsed(long t){
	this.elapsed=t;
    }

}
