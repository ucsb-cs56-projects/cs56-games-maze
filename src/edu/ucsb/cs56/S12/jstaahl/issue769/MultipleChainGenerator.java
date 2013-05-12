package edu.ucsb.cs56.S12.jstaahl.issue769;
import java.util.ArrayList;

/** MultipleChainGenerator runs like a string of AltStepGenerator algorithms to create
    more complex mazes. It creates a GenChain, calling stepNew() then stepOld() in each
    call to step() until the number of cells specified by the frequency argument to the 
    MultipleChainGenerator constructor has been carved by that GenChain. At this point, a new
    GenChain is spawned from where that GenChain left off (or the next continuable cell found
    by backtracking). Each newly generated GenChain creates a new point of oirigin for carving
    paths. Many of these points of origin, or nodes for branching, will be scattered all over
    the maze, creating a much more complex maze.

    @author Jakob Staahl
    @version MazeGame CP1 for CS56, Spring 2012
    @see AltStepGenerator
    @see GenChain
*/
public class MultipleChainGenerator extends MazeGenerator {
    private ArrayList<GenChain> chains;
    
    //how often a new chain should be spawned
    private int frequency;
    private int frequencyFlux;
    // the length of the current chain, decided by:
    //         frequency +/- random number from 0 to frequencyFlux
    private int currentLength;

    /**
       Constructs a MultiplChainGenerator with this MazeGrid grid
       @param grid the MazeGrid this MultipleChainGenerator will run its maze generating
       algorithm on
       @param frequency the number of cells a GenChain will carve before a new GenChain
       is spawned to pick up where it left off (smaller number means more nodes for branching
       will be scattered throughout the maze)
       @see MazeGrid
       @see GenChain
    */
    public MultipleChainGenerator(MazeGrid grid, int frequency) {
	this.grid = grid;
	this.frequency = frequency;
	this.currentLength = frequency;
	this.frequencyFlux = 0;
	this.chains = new ArrayList<GenChain>();

	// choose a random point in this grid to start at
	int row = (int)(Math.random()*grid.getRows());
	int col = (int)(Math.random()*grid.getCols());

	// create a GenChain from that point and add it to chains
	GenChain chain = new GenChain(this.grid, new Cell(row, col));
	this.chains.add(chain);
    }


    /**
       Constructs a MultiplChainGenerator with this MazeGrid grid
       @param grid the MazeGrid this MultipleChainGenerator will run its maze generating
       algorithm on
       @param frequency the number of cells a GenChain will carve before a new GenChain
       is spawned to pick up where it left off (smaller number means more nodes for branching
       will be scattered throughout the maze)
       @param frequencyFlux +/- range of randomness in the frequency during maze generation
       @see MazeGrid
    */
    public MultipleChainGenerator(MazeGrid grid, int frequency, int frequencyFlux) {
	this.grid = grid;
	this.frequency = frequency;
	this.frequencyFlux = frequencyFlux;
	this.currentLength = (int)(frequency - frequencyFlux + (Math.random()*frequencyFlux*2));
	this.chains = new ArrayList<GenChain>();

	// choose a random point in this grid to start at
	int row = (int)(Math.random()*grid.getRows());
	int col = (int)(Math.random()*grid.getCols());

	// create a GenChain from that point and add it to chains
	GenChain chain = new GenChain(this.grid, new Cell(row, col));
	this.chains.add(chain);
    }

    /**
       Generates the entire Maze in MazeGrid
    */
    public void generate() {
	while(this.step());
    }
	
    /**
       Steps the maze generating algorithm once
       Best used for watching the maze being generating by calling
       MazeComponent.repaint() and Thread.sleep() between each step
       @return returns true if the generator can still step, false if the maze
       has stepped to completion
    */
    public boolean step() {
	// if there are no more GenChains in chains, return false
	// signifying that the maze is complete
	if (this.chains.size() == 0) return false;
	
	// if the most recent added GenChain has carved 'frequency' number
	// of Cells, get the newest Cell it can continue from, and start a
	// new GenChain from that cell, adding it to chains. If the GenChain
	// has no cells it can continue from, remove it from chains
	if (this.chains.get(this.chains.size()-1).getLength() >= currentLength) {
	    Cell continuableCell = this.chains.get(this.chains.size()-1).newestContinuableCell();
	    if (continuableCell == null)
		this.removeNewestChain();
	    else {
		this.chains.add(new GenChain(this.grid, continuableCell));
		this.currentLength = (int)(frequency - frequencyFlux + (Math.random()*frequencyFlux*2));
	    }
	}

        // call stepNew and stepOld on the newest added GenChain. If the
	// GenChain cannot step any further in either direction, remove it
	if (this.chains.size() > 0 && !(this.chains.get(this.chains.size()-1).stepNew() | this.chains.get(this.chains.size()-1).stepOld()))
	    this.removeNewestChain();
	
	// return true if there are still GenChains in chains (a.k.a. chains the
	// generator can dontinue off of), false otherwise (a.k.a. the generator
	// has completed carving the maze into the MazeGrid
	//In that case, mark start and finish
	return (this.chains.size() > 0);
    }

    private void removeNewestChain() {
	this.chains.remove(this.chains.size()-1);
	if (this.chains.size() > 0) {
	    this.currentLength = this.chains.get(this.chains.size()-1).getLength() +
		(int)(frequency - frequencyFlux + (Math.random()*frequencyFlux*2));
	}
    }
}