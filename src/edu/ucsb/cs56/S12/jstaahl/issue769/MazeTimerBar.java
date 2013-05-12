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
    
    
    public MazeTimerBar(){
	super();
	this.timerField = new JTextField("00:00:000");
	this.add(this.timerField);

	this.newButton = new JButton("New");
	this.add(this.newButton);

	this.solveButton = new JButton("Solve");
	this.add(this.solveButton);

	this.timerFormat = new SimpleDateFormat("mm:ss:SSS");
    }

    public void printComponent(Graphics g){
	super.printComponent(g);
	Graphics2D g2d = (Graphics2D)g;
    }
    
    public void startTimer(){
	startTime = System.currentTimeMillis();
	Timer t2 = new Timer(1, new ActionListener() {
		public void actionPerformed(ActionEvent e){
		    //update text field
		    timerField.setText(timerFormat.format(new Date(System.currentTimeMillis()-startTime)));
		}
	    });
	t2.start();
    }

}