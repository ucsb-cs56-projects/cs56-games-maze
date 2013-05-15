package edu.ucsb.cs56.projects.games.cs56_games_maze;

/** A Cell is just an object that holds a set of coordinates (row, col)
    the coordinates are public instance variables and accessible by
    cell.row and cell.col

    @author Jakob Staahl
    @version MazeGame CP1 for CS56, Spring 2012
*/

public class Cell {
    public int row;
    public int col;
    
    /**
       Constructs a Cell with the specified coordinates
       @param row the row this cell is in
       @param col the column this cell is in
    */
    public Cell(int row, int col) {
	this.row = row;
	this.col = col;
    }

    /**
       Overrides Object's equals method for easily comparing Cell objects
       Two Cell objects are equal if their coordinates are the same
       @param o the Cell object to compare this one with
    */
    public boolean equals(Object o) {
	if (o instanceof Cell) {
	    Cell c = (Cell)o;
	    return (this.row == c.row && this.col == c.col);
	}
	return false;
    }
}