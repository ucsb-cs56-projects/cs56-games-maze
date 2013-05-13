package edu.ucsb.cs56.S12.jstaahl.issue769;

public class MazePlayer{
    private Cell position;
    private MazeGrid grid;
    private int numMoves;
    private boolean visible;
    private byte herpderp = (byte)0x80;

    public MazePlayer(MazeGrid grid){
	this.grid = grid;
	this.position = new Cell(0,0);
	this.numMoves=0;
	this.visible=false;
    }

    public void move(byte direction){
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
	    if(this.visible) grid.markCell(position, MazeGrid.MARKER4);
	}
	//else return, cannot move
    }

    public void setVisible(boolean visible){
	this.visible = visible;
	remark();
    }

    public void remark(){
	if(this.visible) grid.markCell(position, MazeGrid.MARKER4);
    }

    public Cell getPosition(){
	return this.position;
    }

    public int getNumMoves(){
	return this.numMoves;
    }

}