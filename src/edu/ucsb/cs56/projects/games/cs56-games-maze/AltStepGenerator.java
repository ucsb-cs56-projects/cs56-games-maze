package edu.ucsb.cs56.projects.games.cs56_games_maze;

/** AltStepGenerator runs off the newest added cell then the oldest added cell each
    step. The maze it creates branches out in all directions from the random cell it
    starts at. The algorithm works by creating a GenChain and calling stepNew() then
    stepOld() in each call to step().

    AltStepGenerator's algorithm runs just like MultipleChainGenerator's algorithm
    when MultipleChainGenerator's frequency constructor argument is set greater
    than or equal to the area of the entire grid and the frequencyFlux constructor
    argument is set to 0.

    @author Jakob Staahl
    @version MazeGame CP1 for CS56, Spring 2012
    @see GenChain
    @see MultipleChainGenerator
*/

public class AltStepGenerator extends MazeGenerator {
    private GenChain chain;
    private int distance;

    /**
       Constructs an AltStepGenerator with this MazeGrid grid
       @param grid the MazeGrid this AltStepGenerator will run its maze generating
       algorithm on
       @see MazeGrid
    */
    public AltStepGenerator(MazeGrid grid) {
	this.grid = grid;
	this.distance = 1;

	int row = (int)(Math.random()*grid.getRows());
	int col = (int)(Math.random()*grid.getCols());

	this.chain = new GenChain(this.grid, new Cell(row, col));
    }
    
    /**
       Constructs an AltStepGenerator where each step off of both the newest added
       cell and the oldest added cell is a tunnel of distance number of cells (or
       less if a tunel of that length cannot be constructed due to an obstruction)
       @param grid the MazeGrid this AltStepGenerator will run its maze generating
       algorithm on
       @param distance the length of the tunnels generated at each step
       @see MazeGrid
    */
    public AltStepGenerator(MazeGrid grid, int distance) {
	this.grid = grid;
	this.distance = distance;

	int row = (int)(Math.random()*grid.getRows());
	int col = (int)(Math.random()*grid.getCols());

	this.chain = new GenChain(this.grid, new Cell(row, col));
    }

    /**
       Generates the entire Maze in MazeGrid
    */
    public void generate() {
	while(chain.stepNew(this.distance) && chain.stepOld(this.distance));
    }
    
    /**
       Steps the maze generating algorithm once
       Best used for watching the maze being generating by calling
       MazeComponent.repaint() and Thread.sleep() between each step
       @return returns true if the generator can still step, false if the maze
       has stepped to completion
    */
    public boolean step() {
	return (chain.stepNew(this.distance) && chain.stepOld(this.distance));
    }
}