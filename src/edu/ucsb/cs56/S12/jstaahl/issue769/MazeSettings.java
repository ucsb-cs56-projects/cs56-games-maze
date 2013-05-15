package edu.ucsb.cs56.S12.jstaahl.issue769;

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
    }
}