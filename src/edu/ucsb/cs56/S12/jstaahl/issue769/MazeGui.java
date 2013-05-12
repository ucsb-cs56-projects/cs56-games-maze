package edu.ucsb.cs56.S12.jstaahl.issue769;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;


/**
   Class where the MazeGui is constructed.  This is also the main class and contains the main method
*/

public class MazeGui{

    private JFrame frame;
    private MazeTimerBar timerBar;
    private MazeGrid grid;
    private MazeComponent mc;
    private MazeGenerator mg;
    private Timer drawTimer;
    private int genChainLength = 50;
    private int genChainLengthFlux = 50;
    private int rows = 60;
    private int cols = 60;
    private int cellWidth = 10;
    private int startRow = 0;
    private int startCol = 0;
    private int endRow = rows-1;
    private int endCol = cols-1;
    private int genType = 1;
    
    public static final int MULTI_CHAIN_GEN = 1;
    public static final int ALT_STEP_GEN = 2;
    public static final int NEW_STEP_GEN = 3;

    public static void main(final String[] args){
	SwingUtilities.invokeLater(new Runnable(){
		public void run(){
		    new MazeGui(args).run();
		}
	    });
    }

    public MazeGui(String[] args){

	// check for command line arguments, initialize variables accordingly
	if (args.length != 0 && args.length != 2 && args.length != 5 && args.length != 9) {
	    System.out.println("Improper number of command line arguments: " + args.length);
	    System.out.println("Type ant run for proper usage.");
	    return;
	}
	switch (args.length) { // NOTE: no break statements.. cases flow through
	case 9:
	    startRow           = Integer.parseInt(args[5]);
	    startCol           = Integer.parseInt(args[6]);
	    endRow             = Integer.parseInt(args[7]);
	    endCol             = Integer.parseInt(args[8]);
	case 5:
	    rows               = Integer.parseInt(args[2]);
	    cols               = Integer.parseInt(args[3]);
	    cellWidth          = Integer.parseInt(args[4]);
	    // set endRow and endCol to rows-1 and cols-1 respectively, but
	    // only if they weren't already explicitly defined under (case 9:)
	    endRow             = args.length != 9 ? rows-1 : endRow;
	    endCol             = args.length != 9 ? cols-1 : endCol;
	case 2:
	    genChainLength     = Integer.parseInt(args[0]);
	    genChainLengthFlux = Integer.parseInt(args[1]);
	}

	// initialize the JFrame
	frame = new JFrame();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setTitle("Maze Game by Jakob Staahl");

	//initialize timer/controls bar
	this.timerBar = new MazeTimerBar(this);
	frame.add(timerBar, BorderLayout.SOUTH);

	// initialize the MazeGrid, MazeComponent, and MazeGenerator
	this.grid = new MazeGrid(rows, cols);
	this.mc = new MazeComponent(grid, cellWidth);
	frame.add(mc);
	frame.pack();
	frame.setVisible(true);
	this.mg = new MultipleChainGenerator(grid, genChainLength, genChainLengthFlux);
    }
    
    public void run() {
	// generate the maze in steps (rather than all at once using MazeGenerator.generate())
	// repaint() in between each step to watch it grow
	// for performance only draw every 10 steps
	if(drawTimer!=null)
	    drawTimer.stop();
	drawTimer = new Timer(1, new ActionListener() {
		int i=0;
		public void actionPerformed(ActionEvent e){
		    ++i;
		    if(mg.step() && i%10==0)
			frame.repaint();
		    else if(i%10==0){
			((Timer)e.getSource()).stop();
			timerBar.startTimer();
			grid.markStartFinish();
			mc.repaint();
		    }
		}
	    });
	drawTimer.start();
    }

    public void newMaze() {
	timerBar.stopTimer();
	this.grid = new MazeGrid(rows, cols);
	this.mg = new MultipleChainGenerator(grid, genChainLength, genChainLengthFlux);
	frame.remove(mc);
	mc = new MazeComponent(grid, cellWidth);
	frame.add(mc);
        frame.pack();
	run();
    }

    public void solveMaze() {
	timerBar.stopTimer();
	// display the solution to the maze
	mg.solve(new Cell(startRow, startCol), (byte)0x0,
	    new Cell(endRow, endCol));
	mc.repaint();
    }

}