package edu.ucsb.cs56.S12.jstaahl.issue769;
import javax.swing.JFrame;
import java.awt.*;

/**
   Class where the MazeGui is constructed.  This is also the main class and contains the main method
*/

public class MazeGui {
    
    public static void main(String[] args) {
	int genChainLength = 50;
	int genChainLengthFlux = 50;
        int rows = 60;
	int cols = 60;
	int cellWidth = 10;
	int startRow = 0;
	int startCol = 0;
	int endRow = rows-1;
	int endCol = cols-1;

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
	JFrame frame = new JFrame();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setTitle("Maze Game by Jakob Staahl");

	// initialize the MazeGrid, MazeComponent, and MazeGenerator
	MazeGrid grid = new MazeGrid(rows, cols);
	MazeComponent mc = new MazeComponent(grid, cellWidth);
	frame.add(mc);
	frame.pack();
	frame.setVisible(true);
	MazeGenerator mg = new MultipleChainGenerator(grid, genChainLength, genChainLengthFlux);

	// generate the maze in steps (rather than all at once using MazeGenerator.generate())
	// repaint() and sleep() in between each step to watch it grow
	while(mg.step()) {
	    mc.repaint();
	    try {
		Thread.sleep(1);
	    }
	    catch (InterruptedException ie) {
		System.out.println(ie);
	    }
	}

	// display the solution to the maze
	mg.solve(new Cell(startRow, startCol), (byte)0x0,
		 new Cell(endRow, endCol));
	mc.repaint();
    }
}