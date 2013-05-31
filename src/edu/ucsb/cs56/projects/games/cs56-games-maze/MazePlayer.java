package edu.ucsb.cs56.projects.games.cs56_games_maze;

/**
   Class to represent active player in maze game

   @author Evan West
   @version 5/14/13 for proj1, cs56, S13
*/
public class MazePlayer{
    private Cell position;
    private MazeGrid grid;
    private int numMoves;
    private boolean visible;

    /** No-arg constructor, create player at 0,0
	@param grid The MazeGrid on which the player will be played
     */
    public MazePlayer(MazeGrid grid){
	this.grid = grid;
	this.position = new Cell(0,0);
	this.numMoves=0;
	this.visible=false;
	this.grid.setPlayer(this);
    }

    public MazePlayer(MazeGrid grid, Cell position){
	this.grid=grid;
	this.position=position;
	this.numMoves=0;
	this.visible=false;
	this.grid.setPlayer(this);
    }

    /** Moves the player one space in specificed direction is possible, otherwise does nothing
	@param direction Bitwise indicator of direction, as defined in MazeGui
	@see MazeGui
     */
    public void move(short direction){
	if(this.grid.canMove(this.position,direction)){
	    this.numMoves++;
	    grid.unmarkCell(position, MazeGrid.MARKER4);
	    switch(direction){
	    case MazeGrid.DIR_RIGHT:
		position.col+=1;
		break;
	    case MazeGrid.DIR_UP:
		position.row-=1;
		break;
	    case MazeGrid.DIR_LEFT:
		position.col-=1;
		break;
	    case MazeGrid.DIR_DOWN:
		position.row+=1;
		break;
	    }
	    //if(this.visible) grid.markCell(position, MazeGrid.MARKER4);
	    this.grid.updatePlayerPosition();
	}
	//else return, cannot move
    }

    /** Sets player to be visible (enables)
	@deprecated
     */
    public void setVisible(boolean visible){
	this.visible = visible;
	remark();
    }

    /** Re-marks position of player on grid */
    public void remark(){
	if(this.visible) grid.markCell(position, MazeGrid.MARKER4);
    }

    /** @return Cell representing current position of player */
    public Cell getPosition(){
	return this.position;
    }

    /** @return number of successful moves the player has made so far */
    public int getNumMoves(){
	return this.numMoves;
    }

}
