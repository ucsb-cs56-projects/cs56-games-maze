package edu.ucsb.cs56.projects.games.cs56_games_maze;

/**
 * NewStepGenerator runs off the newest added cell each step.
 * The algorithm runs by creating a GenChain and calling stepNew() in each call to
 * step().
 *
 * @author Jakob Staahl
 * @version MazeGame CP1 for CS56, Spring 2012
 */
public class NewStepGenerator extends MazeGenerator {
    private GenChain chain;
    private int distance;

    /**
     * Constructs a NewStepGenerator with this MazeGrid grid
     *
     * @param grid the MazeGrid this NewStepGenerator will run its maze generating
     *             algorithm on
     * @see MazeGrid
     */
    public NewStepGenerator(MazeGrid grid) {
        this.grid = grid;
        this.distance = 1;

        // choose a random point in the grid to start at
        int row = (int) (Math.random() * grid.getRows());
        int col = (int) (Math.random() * grid.getCols());

        // initialize chain at that point
        this.chain = new GenChain(this.grid, new Cell(row, col));
    }

    /**
     * Constructs a NewStepGenerator where each step is a tunnel of distance number of cells (or  less if a tunel of that length cannot be constructed due to an obstruction)
     *
     * @param grid     the MazeGrid this NewStepGenerator will run its maze generating algorithm on
     * @param distance the length of the tunnels generated at each step
     * @see MazeGrid
     */
    public NewStepGenerator(MazeGrid grid, int distance) {
        this.grid = grid;
        this.distance = distance;

        // choose a random point in the grid to start at
        int row = (int) (Math.random() * grid.getRows());
        int col = (int) (Math.random() * grid.getCols());

        // initialize chain at that point
        this.chain = new GenChain(this.grid, new Cell(row, col));
    }

    /**
     * Generates the entire Maze in MazeGrid
     */

    public void generate() {
        while (chain.stepNew(this.distance)) ;
    }

    /**
     * Steps the maze generating algorithm once
     * Best used for watching the maze being generating by calling
     * MazeComponent.repaint() and Thread.sleep() between each step
     *
     * @return returns true if the generator can still step, false if the maze
     * has stepped to completion
     */
    public boolean step() {
        return chain.stepNew(this.distance);
    }
}