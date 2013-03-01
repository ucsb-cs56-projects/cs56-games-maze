package edu.ucsb.cs56.S12.jstaahl.issue769;
import java.util.ArrayList;

/**
   A GenChain is a primary tool used by subclasses of MazeGenerator to carve paths through
   a MazeGrid. A GenChain upon which only stepNew() is called is a direct instantiation of
   the recursive backtracking algorithm. The different derivatives of MazeGenerator use
   GenChains in different ways to produce slightly different algorithms.

   A GenChain is an ArrayList of Cells

   Take a look at the Subclasses of MazeGenerator
   @author Jakob Staahl
   @version MazeGame CP1 for CS56, Spring 2012
   @see MazeGenerator
*/
public class GenChain extends ArrayList<Cell> {
    //NOTE: cells removed from the oldest end are not actually removed, startingIndex is simply incremented
    private int startingIndex; 
    private MazeGrid grid;
    private int numCarvedCells;

    /**
       Constructs a new GenChain in this MazeGrid grid at this Cell a
    */
    public GenChain(MazeGrid grid, Cell a) {
	this.grid = grid;
	this.add(a);
	this.numCarvedCells = 1;
	this.startingIndex = 0;

	this.grid.markCell(a, MazeGrid.MARKER3);
    }

    /**
       Steps the GenChain from the newest added cell once. A step for a GenChain either carves
       one more cell in the path, adding that Cell to the ArrayList,  or if there are no
       adjacent unvisited cells at this point to grow the path to, the newest added cell is
       removed. The next subsequent call to stepNew() tries again at the newest added cell
       (the chain has backtracked to try again at that point).

       stepNew() can be run on a single GenChain and it will return true untill it has carved
       paths to the entire space it is enclosed within (either the entire grid, or the enclosing of 
       unvisited cells it has been spawned within)
       To see what a maze generated this way looks like, visit:
       http://weblog.jamisbuck.org/2011/2/7/maze-generation-algorithm-recap
       Then scroll down to the "Growing Tree Algorithm" and in the "Cell Selectoin Method" pulldown
       menu select "Newest" and click "Run"

       @return returns true if there are still cells in this chain. returns false if it is empty,
       meaning there are no more adjacent unvisited cells to carve to.
    */
    public boolean stepNew() {
	if (this.size() - this.startingIndex == 0) return false;
	ArrayList<Cell> adjUnvisited = grid.getAdjacentUnvisitedCells(this.get(this.size()-1));
	
	// if there are no adjacent unvisited cells around the newest
	// added cell, remove it and return
	if (adjUnvisited == null) {
	    this.remove(this.size()-1);
	    return this.size() - this.startingIndex != 0;
	}

	// choose an adjacent unvisited cell at random, and carve to
	// it in the grid, and add it to this
	int randCell = (int)(Math.random()*adjUnvisited.size());
	this.grid.carvePath(this.get(this.size()-1), adjUnvisited.get(randCell));
	this.add(adjUnvisited.get(randCell));
	this.numCarvedCells++;

	// return false if startingIndex is at the end of this list (the
	// list is "empty"). true otherwise
	return this.size() - this.startingIndex != 0;
    }

    /**
       Steps the GenChain from the oldest added cell once. A step for a GenChain either carves
       one more cell in the path, adding that Cell to the ArrayList,  or if there are no
       adjacent unvisited cells at this point to grow the path to, the oldest added cell is
       removed. The next subsequent call to stepOld() tries again at the newest added cell
       (the chain has backtracked to try again at that point).

       stepOld() can be run on a single GenChain and it will return true untill it has carved
       paths to the entire space it is enclosed within (either the entire grid, or the enclosing of 
       unvisited cells it has been spawned within).

       NOTE: Derivatives of MazeGenerator that implement step() by simply calling stepOld() on a
       single GenChain makes for a very boring maze due to the nature of the algorithm. A generator
       whos algorithm runs by simply calling stepOld() over and over forces a very boring pattern.
       To see what this looks like, visit:
       http://weblog.jamisbuck.org/2011/2/7/maze-generation-algorithm-recap
       Then scroll down to the "Growing Tree Algorithm" and in the "Cell Selectoin Method" pulldown
       menu select "Oldest" and click "Run"

       @return returns true if there are still cells in this chain. returns false if it is empty,
       meaning there are no more adjacent unvisited cells to carve to.
    */
    public boolean stepOld() {
	if (this.size() - this.startingIndex == 0) return false;
	ArrayList<Cell> adjUnvisited = grid.getAdjacentUnvisitedCells(this.get(startingIndex));

	// if there are no adjacent unvisited cells around the oldest
	// added cell, remove it and return
	if (adjUnvisited == null) {
	    this.startingIndex++;
	    return this.size() - this.startingIndex != 0;
	}

	// choose an adjacent unvisited cell at random, and carve to
	// it in the grid, and add it to this
	int randCell = (int)(Math.random()*adjUnvisited.size());
	this.grid.carvePath(this.get(startingIndex), adjUnvisited.get(randCell));
	this.add(adjUnvisited.get(randCell));
	this.numCarvedCells++;

	// return false if startingIndex is at the end of this list (the
	// list is "empty"). true otherwise
	return this.size() - this.startingIndex != 0;
    }

    /**
      Get the newest Cell that has unvisited Cells adjacent
      to it, removing all other Cells up until that point.
      useful for creating a new GenChain to pick up where
      this one left off
      
      @return the newest Cell that has unvisited Cells adjacent to it
    */
    public Cell newestContinuableCell() {
	// remove cells from the new end until one with adjacent
	// unvisited cells is reached
	while(this.size() - this.startingIndex > 0 && this.grid.getAdjacentUnvisitedCells(this.get(this.size()-1)) == null)
	    this.remove(this.size()-1);
	// return the newest Cell, null if there are no more
	return this.size() - this.startingIndex > 0 ? this.get(this.size()-1) : null;	
    }

    /**
      Get the oldest Cell that has unvisited Cells adjacent
      to it, removing all other Cells up until that point.
      useful for creating a new GenChain to pick up where
      this one left off
      
      @return the oldest Cell that has unvisited Cells adjacent to it
    */
    public Cell oldestContinuableCell() {
	// increment startingIndex until a Cell with adjacent
	// unvisited cells is reached
	while(this.size() - this.startingIndex > 0 && this.grid.getAdjacentUnvisitedCells(this.get(this.startingIndex)) == null)
	    this.startingIndex++;
	// return Cell at startingIndex, null if startingIndex is at
	// the end of the list
	return this.size() - this.startingIndex > 0 ? this.get(this.size()-1) : null;
    }

    /**
       runs just like stepNew(), but carves a tunnel of distance number of cells (or shorter if
       an obstacle is reached before that number of cells). Makes for plainer mazes, less erratic
       GenChains.

       @return returns true if there are still cells in this chain. returns false if it is empty,
       meaning there are no more adjacent unvisited cells to carve to.
    */
    public boolean stepNew(int distance) {
	if (this.size() - this.startingIndex == 0) return false;
	ArrayList<Cell> adjUnvisited = grid.getAdjacentUnvisitedCells(this.get(this.size()-1));
	
     	// if there are no adjacent unvisited cells around the newest
	// added cell, remove it and return
	if (adjUnvisited == null) {
	    this.remove(this.size()-1);
	    return this.size() - this.startingIndex != 0;
	}

	// from the newest added Cell choose an adjacent unvisited cell
	// at random, and carve to it in the grid. Continue carving to
	// Cells in the samedirection for distance number of Cells, or
	// until it is no longer possible to do so
	int randCell = (int)(Math.random()*adjUnvisited.size());
	byte dir = this.grid.direction(this.get(this.size()-1), adjUnvisited.get(randCell));
	this.grid.carvePath(this.get(this.size()-1), adjUnvisited.get(randCell));
	this.add(adjUnvisited.get(randCell));
	this.numCarvedCells++;
	for (int i = 1; i < distance; i++) {
	    Cell a = this.grid.getCell(this.get(this.size()-1), dir);
	    if (a != null && !this.grid.isVisited(a)) {
		this.grid.carvePath(this.get(this.size()-1), a);
		this.add(a);
		this.numCarvedCells++;
	    }
	    else break;
	}

	// return false if startingIndex is at the end of this list (the
	// list is "empty"). true otherwise
	return this.size() - this.startingIndex != 0;
    }

    /**
       runs just like stepOld(), but carves a tunnel of distance number of cells (or shorter if
       an obstacle is reached before that number of cells). Makes for plainer mazes, less erratic
       GenChains.

       @return returns true if there are still cells in this chain. returns false if it is empty,
       meaning there are no more adjacent unvisited cells to carve to.
    */
    public boolean stepOld(int distance) {
	if (this.size() - this.startingIndex == 0) return false;
	ArrayList<Cell> adjUnvisited = grid.getAdjacentUnvisitedCells(this.get(startingIndex));

	if (adjUnvisited == null) {
	    this.startingIndex++;
	    return this.size() - this.startingIndex != 0;
	}

	// from the oldest added Cell choose an adjacent unvisited cell
	// at random, and carve to it in the grid. Continue carving to
	// Cells in the samedirection for distance number of Cells, or
	// until it is no longer possible to do so
	int randCell = (int)(Math.random()*adjUnvisited.size());
	byte dir = this.grid.direction(this.get(startingIndex), adjUnvisited.get(randCell));
	this.grid.carvePath(this.get(startingIndex), adjUnvisited.get(randCell));
	this.add(adjUnvisited.get(randCell));
	this.numCarvedCells++;
	for (int i = 1; i < distance; i++) {
	    Cell a = this.grid.getCell(this.get(this.size()-1), dir);
	    if (a != null && !this.grid.isVisited(a)) {
		this.grid.carvePath(this.get(this.size()-1), a);
		this.add(a);
		this.numCarvedCells++;
	    }
	    else break;
	}

	// return false if startingIndex is at the end of this list (the
	// list is "empty"). true otherwise
	return this.size() - this.startingIndex != 0;
    }

    /**
       Returns the number of cells this GenChain has carved to

       @return the length of this GenChain
    */
    public int getLength() { return this.numCarvedCells; }

    /**
       Returns the original Cell this GenChain was constructed with and eminated from

       @return the starting cell of this GenChain
    */
    public Cell getStartingCell() { return this.get(0); }
}