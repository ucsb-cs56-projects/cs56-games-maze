package edu.ucsb.cs56.S12.jstaahl.issue769;
import java.util.ArrayList;


/**
   A MazeGrid holds all of the saved maze informtion. The MazeGrid has a 2D byte array
   representing each cell in the grid. Each byte in this array holds the information for
   which walls of a cell are carved open, and the markers that have been set for that cell

   Contains methods for carving paths to cells, getting cells adjacent to others by direction,
   getting information about cells in this grid, etc.

   The markers for a cell can be used in any way desired by classes that wish to work with a
   MazeGrid. For example, markers can be used to save information about which Cells have been
   visited or for marking the solution to the maze (as MazeGenerator does). MazeComponent decides how each Cell marker
   of a Cell gets drawn. 

   @author Jakob Staahl
   @version MazeGame CP1 for CS56, Spring 2012
   @see MazeGenerator
   @see MazeComponent
 */
public class MazeGrid {
    /** The bit representing the direction right */
    public static final byte DIR_RIGHT  = 0x1;
    /** The bit representing the direction up */
    public static final short DIR_UP     = 0x2;
    /** The bit representing the direction left */
    public static final short DIR_LEFT   = 0x4;
    /** The bit representing the direction down */
    public static final short DIR_DOWN   = 0x8;

    /** The bit representing marker1 */
    public static final short MARKER1 = 0x10;
    /** The bit representing marker2 */
    public static final short MARKER2 = 0x20;
    /** The bit representing marker3 */
    public static final short MARKER3 = 0x40;
    /** The bit representing marker4 */
    public static final short MARKER4 = (short)0x80;
    /**the bit representing marker5 */
    public static final short MARKER5 = 0x100;

    private short[][] grid;
    private int rows;
    private int cols;
    private MazePlayer player;
    private int progRevealRadius;
    private boolean progReveal;
    private Cell finish;

    /**
       Constructs a MazeGrid with the given number of rows and cols

       @param rows number of rows to be in this grid
       @param cols number of cols to be in this grid
    */
    public MazeGrid(int rows, int cols) {
	this.rows = rows;
	this.cols = cols;
	grid = new short[this.rows][this.cols];
	finish = new Cell(this.rows-1, this.cols-1);
    }

    /**
       Get a list of all the unvisited cells directly touching one of the four sides
       of this Cell a.
       
       @param a the cell whose adjacent unvisited cells we are interested in
       @return an ArrayList of the adjacent unvisited cells, null if there are none
    */
    public ArrayList<Cell> getAdjacentUnvisitedCells(Cell a) {
	ArrayList<Cell> ret = new ArrayList<Cell>();
	// cycle through the directions, adding adjacent unvisited cells
	// to ret
	for (short dir = MazeGrid.DIR_RIGHT; dir <= MazeGrid.DIR_DOWN; dir = (short)(dir << 1)) {
	    Cell b = this.getCell(a, dir);
	    if (b != null && this.getCellDirections(b) == 0)
		ret.add(b);
	}
	// return ret. return null if ret is empty
	return ret.size() > 0 ? ret : null;
    }
	    
    /**
       Carves a new path from Cell a to Cell b, saving the information of newly opened walls in each
       cell in the 2D short array. Assumes cells a and b are adjacent
       
       @param a the cell the path is being carved from
       @param b the cell the path is being carved to
    */
    public void carvePath(Cell a, Cell b) {
	short dir = direction(a, b);
	this.grid[a.row][a.col] = (short)(this.grid[a.row][a.col] | dir);  // Cell a gains the direction 
	this.grid[b.row][b.col] = (short)(directionInverse(dir)); // Cell b gets the inverse direction
    }

    /**
       Returns the short information pertaining to the direction from Cell a
       to cell b. Assumes that the cells are adjacent, arbitrarily returns the
       short 0xFF otherwise.

       @param a the cell we are getting the direction from
       @param b the cell we are getting the direction to
       @return the directoin from Cell a to Cell b
    */
    public short direction(Cell a, Cell b) {
	if (b.col - a.col ==  1) return MazeGrid.DIR_RIGHT;
	if (b.col - a.col == -1) return MazeGrid.DIR_LEFT;
	if (b.row - a.row ==  1) return MazeGrid.DIR_DOWN;
	if (b.row - a.row == -1) return MazeGrid.DIR_UP;
	// return 0 if these cells aren't adjacent
	return (short)0x0;
    }

    /**
       Used for getting the inverse of direction dir:
       example: the inverse of MazeGrid.DIR_LEFT is MazeGrid.DIR_RIGHT and vice versa
      
       @param dir the direction of which we are getting the inverse
       @return the oppositve direction of dir
    */
    public short directionInverse(short dir) {
	if (dir == MazeGrid.DIR_LEFT ) return MazeGrid.DIR_RIGHT;
	if (dir == MazeGrid.DIR_RIGHT) return MazeGrid.DIR_LEFT;
	if (dir == MazeGrid.DIR_UP)    return MazeGrid.DIR_DOWN;
	if (dir == MazeGrid.DIR_DOWN)  return MazeGrid.DIR_UP;
	// return 0 if dir represented more that one direction
	// or was an invalid direction
	return 0x0;
    }

    /**
       If walking from this Cell a in this direction dir is possible,
       return true. Else, return false.

       @return true if it is possible to move from this Cell a in direction dir,
       false otherwise
    */
    public boolean canMove(Cell a, short dir) {
	return (this.grid[a.row][a.col] & dir) > 0;
    }

    /**
       If this Cell is within the bounds of this MazeGrid's rows and cols,
       return true

       @return whether this Cell is a valid cell within the bounds of this MazeGrid
    */
    public boolean contains(Cell a) {
	return (a.row >= 0 && a.row < this.rows && a.col >= 0 && a.col < this.cols);
    }

    /**
       Gets the Cell adjacent to Cell a, to the direction dir of Cell a
       @param a the Cell adjacent to the one we are looking for
       @param dir the direction, from Cell a, in which the Cell we are looking for is located
       @return the Cell adjaced to Cell a, in the direction of dir. Null if that Cell
       is out of bounds of this MazeGrid
    */
    public Cell getCell(Cell a, short dir) {
	Cell ret = new Cell(a.row + ((dir & 0x8) >> 3) - ((dir & 0x2) >> 1),
			    a.col + ((dir & 0x1) >> 0) - ((dir & 0x4) >> 2));
	return this.contains(ret) ? ret : null;
    }

    /**
       @return true if this cell has been carved to or from (has any opened walls), false otherwise
    */
    public boolean isVisited(Cell a) { return (this.grid[a.row][a.col] & 0x0F) != 0; }

    /** @return the number of rows in this MazeGrid */
    public int getRows() { return this.rows; }

    /** @return the number of cols in this MazeGrid */
    public int getCols() { return this.cols; }
    
    /**
       Returns the short information pertaining to the directions in which this Cell has open walls,
       a.k.a. the directions in which one cran travel from this Cell. Assumes Cell a is in this grid
       @param a the Cell of interest. Assumes this Cell is in this grd
       @return the short information pertaining to the directions this cell holds
    */
    public short getCellDirections(Cell a) {
	return this.grid[a.row][a.col];
    }

    /**
       Save this Marker in this cell
       @param a the Cell of interest
       @param marker the short information peratining to the marker we are saving on this cell
    */
    public void markCell(Cell a, short marker) {
	this.grid[a.row][a.col] = (short)(this.grid[a.row][a.col] | marker);
    }

    /**
       Remove this Marker in this cell
       @param a the Cell of interest
       @param marker the short information peratining to the marker we are removing from this cell
    */
    public void unmarkCell(Cell a, short marker) {
	this.grid[a.row][a.col] = (short)(this.grid[a.row][a.col] & ~marker);
    }

    /**
    For debugging, returns short value of cell
    @param a cell of interest
    @return raw short value of cell
    */
    public short getCellShort(Cell a){
	return this.grid[a.row][a.col];
    }

    /**
       Return true if this Cell a has this marker. Otherwise return false
       @param a the Cell of interest. Assumes this Cell is in this grid
       @param marker the short information pertaining to the marker we are saving on this cell
       @return true if this Cell a has this marker. otherwise return false
    */
    public boolean hasMarker(Cell a, short marker) {
	return ((this.grid[a.row][a.col] & marker) != 0);
    }

    public void markStartFinish(){
	markCell(new Cell(0,0),MazeGrid.MARKER2);
	markCell(new Cell(this.rows-1,this.cols-1),MazeGrid.MARKER1);
    }

    public boolean isAtFinish(Cell a){
	return a.equals(this.finish);
    }

    public void markCellsInRadius(Cell a, int radius, short marker){
	for(int i=0; i<cols; ++i){
	    for(int j=0; j<rows; ++j){
		if(Math.sqrt(
			Math.pow(a.col-i,2)+Math.pow(a.row-j,2)
			     )<radius){
		    markCell(new Cell(j,i),marker);
		}
	    }
	}
    }

    public void unmarkCellsInRadius(Cell a, int radius, short marker){
	for(int i=0; i<cols; ++i){
	    for(int j=0; j<rows; ++j){
		if(Math.sqrt(
			Math.pow(a.col-i,2)+Math.pow(a.row-j,2)
			     )<radius){
		    unmarkCell(new Cell(j,i),marker);
		}
	    }
	}
    }

    public void setProgReveal(MazePlayer p, int progRevealRadius){
	this.progReveal=true;
	this.progRevealRadius=progRevealRadius;
	this.player=p;
	markCellsInRadius(new Cell(0,0),this.rows+this.cols,MazeGrid.MARKER5);
    }

    public void updatePlayerPosition(){
	this.markCell(player.getPosition(), MazeGrid.MARKER4);
	if(this.progReveal)
	    this.unmarkCellsInRadius(this.player.getPosition(), this.progRevealRadius, MazeGrid.MARKER5);
    }

    public void setPlayer(MazePlayer p){
	this.player=p;
    }
}