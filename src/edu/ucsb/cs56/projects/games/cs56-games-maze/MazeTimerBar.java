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
public class MazeTimerBar extends JPanel implements FocusListener{

    private JTextField timerField;
    private JButton newButton;
    private JButton solveButton;
    private long startTime;
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

	//this.timerField.setFocusable(false);
	//setFocusable(false);
	addFocusListener(this);
    }

    /** Prints component, currently unnecessary override
     */
    public void printComponent(Graphics g){
	super.printComponent(g);
	Graphics2D g2d = (Graphics2D)g;
    }
    
    /** Starts gameplay timer
     */
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
	return System.currentTimeMillis()-this.startTime;
    }

    public void focusGained(FocusEvent e) {
        displayMessage("Focus gained", e);
    }
    
    public void focusLost(FocusEvent e) {
        displayMessage("Focus lost", e);
    }
    
    void displayMessage(String prefix, FocusEvent e) {
        System.out.println(prefix
	    + (e.isTemporary() ? " (temporary):" : ":")
	    +  e.getComponent().getClass().getName()
	    + "; Opposite component: " 
	    + (e.getOppositeComponent() != null ?
			   e.getOppositeComponent().getClass().getName() : "null")
	    + "\n"); 
    }

}