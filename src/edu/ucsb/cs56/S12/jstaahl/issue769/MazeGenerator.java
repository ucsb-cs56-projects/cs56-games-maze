package edu.ucsb.cs56.S12.jstaahl.issue769;

/** An abstract MazeGenerator class. In order to create a MazeGenerator
    and implement your own maze generating algorithms, you must implement
    the generate() and step() methods using the methods in the MazeGrid class
    to carve the paths into the grid. It is suggested to do this by using
    GenChain objects to actually do the carving through the grid.
 
    @author Jakob Staahl
    @version MazeGame CP1 for CS56, Spring 2012
    @see GenChain
    @see MazeGrid
*/
public abstract class MazeGenerator {
    /** The MazeGrid inside of which the maze will be generated */
    protected MazeGrid grid;

    /**
       Generates the entire Maze in MazeGrid
    */
    public abstract void generate();
    
    /**
       Steps the maze generating algorithm once
       Best used for watching the maze being generating by calling
       MazeComponent.repaint() and Thread.sleep() between each step
       @return returns true if the generator can still step, false if the maze
       has stepped to completion
    */
    public abstract boolean step();

    /**
       Solves the maze by placing a marker in each cell (using the
       marking methods in MazeGrid) in the solution path
       This is a recursive method.
       @param start the cell at which to start solving the maze
       @param end the ending cell to draw the solution path to
       @param direction important only for the recursive aspect
    */
    public boolean solve(Cell start, byte direction, Cell end) {
	// base case. mark the Cell and return true if we are at the finish
	if (start.equals(end)) {
	    this.grid.markCell(start, MazeGrid.MARKER1);
	    return true;
	}

	// call solve() in each direction around this cell (except
	// the direction from which this cell came a.k.a. 'direction')
	// if calling solve() returns true, mark this Cell and return true
	byte inverseDir = this.grid.directionInverse(direction);
	if (inverseDir != MazeGrid.DIR_LEFT && this.grid.canMove(start, MazeGrid.DIR_LEFT)) {
	    if (solve(this.grid.getCell(start, MazeGrid.DIR_LEFT), MazeGrid.DIR_LEFT,  end)) {
		this.grid.markCell(start, MazeGrid.MARKER3);
		return true;
	    }
	}
	if (inverseDir != MazeGrid.DIR_RIGHT && this.grid.canMove(start, MazeGrid.DIR_RIGHT)) {
	    if (solve(this.grid.getCell(start, MazeGrid.DIR_RIGHT), MazeGrid.DIR_RIGHT, end)) {
		this.grid.markCell(start, MazeGrid.MARKER3);
		return true;
	    }
	}
	if (inverseDir != MazeGrid.DIR_UP && this.grid.canMove(start, MazeGrid.DIR_UP)) {
	    if (solve(this.grid.getCell(start, MazeGrid.DIR_UP), MazeGrid.DIR_UP, end)) {
		this.grid.markCell(start, MazeGrid.MARKER3);
		return true;
	    }
	}	    
	if (inverseDir != MazeGrid.DIR_DOWN && this.grid.canMove(start, MazeGrid.DIR_DOWN)) {
	    if (solve(this.grid.getCell(start, MazeGrid.DIR_DOWN), MazeGrid.DIR_DOWN, end)) {
		this.grid.markCell(start, MazeGrid.MARKER3);
		return true;
	    }
	}

	// return false otherwise
	return false;
    }

}