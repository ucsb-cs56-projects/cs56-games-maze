package edu.ucsb.cs56.projects.games.cs56_games_maze;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


public class MazeGameSave implements Serializable{

    private MazeGrid grid;
    private MazeSettings settings;
    private MazePlayer player;
    private long timeElapsed;
    private ArrayList<MazeHighScore> scores;


    /** Constructor for save game object, player moved back to start, use for win state
	@param grid Object representing the maze layout grid
	@param settings Settings which the grid must be played with
     */
    public MazeGameSave(MazeGrid grid, MazeSettings settings)
    {
	this(grid,settings,new MazePlayer(grid),0);
    }


    /** Constructor for save game object, stores player position
	@param grid Object representing the maze layout grid
	@param settings Settings which the grid must be played with
	@param player Player position and state to store
	@param elapsed Time elapsed during current gameplay instance
     */
    public MazeGameSave(MazeGrid grid, MazeSettings settings, MazePlayer player, long timeElapsed)
    {
	this.grid = grid;
	this.settings = settings;
	this.player = player;
	this.scores = new ArrayList<MazeHighScore>();
	this.timeElapsed=timeElapsed;
    }
    public void addHighScore(MazeHighScore s){
	this.scores.add(s);
	Collections.sort(this.scores);
    }


    public MazeGrid getGrid(){
	return this.grid;
    }

    public MazePlayer getPlayer(){
	return this.player;
    }

    public MazeSettings getSettings(){
	return this.settings;
    }

    public boolean hasHighScores(){
	return this.scores.size()>0;
    }

    public MazeHighScore getHighScore(){
	if(this.scores.size()>0)
	    return this.scores.get(0);
	else
	    return null;
    }

    public long getTimeElapsed(){
	return this.timeElapsed;
    }

    public void setTimeElapsed(long elapsed){
	this.timeElapsed=elapsed;
    }

    public void resetPlayer(){
	this.player = new MazePlayer(this.grid);
    }
}
