package edu.ucsb.cs56.projects.games.cs56_games_maze;

/**
 Class to hold settings information for one game.
 Made to be passed into settings editor window.
 @author Evan West
 @version 5/14/13 for proj1, cs56, S13
*/

public class MazeSettings
{
    public int genChainLength;
    public int genChainLengthFlux;
    public int stepGenDistance;
    public int rows;
    public int cols;
    public int cellWidth;
    public int startRow;
    public int startCol;
    public int endRow;
    public int endCol;
    public int genType;
    public boolean progReveal;
    public int progRevealRadius;
    public boolean progDraw;
    public int progDrawSpeed;

    /** No-arg constructor, creates all default values
     */
    public MazeSettings(){
	this.genChainLength=50;
	this.genChainLengthFlux=50;
	this.stepGenDistance=2;
	this.rows=20;
	this.cols=20;
	this.cellWidth=10;
	this.startRow=0;
	this.startCol=0;
	this.endRow=rows-1;
	this.endCol=cols-1;
	this.genType=1;
	this.progReveal=false;
	this.progRevealRadius=3;
	this.progDraw=true;
	this.progDrawSpeed=10;
    }
}
