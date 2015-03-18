package edu.ucsb.cs56.projects.games.cs56_games_maze;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/** Serializable container for holding all necessary game state data.
    Used for both in-progress saves and end-of-game saves.
    In case of in-progress saves, saves player position and time elapsed.
    Always saves maze configuration and associated settings.

@author Evan West
@version CS56 S13 UCSB
 */
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


    /** Adds a new score for this maze, then resorts array of scores so high score always on top
	@param s Score to add to this save game
     */
    public void addHighScore(MazeHighScore s){
	this.scores.add(s);
	Collections.sort(this.scores);
    }

    /**
       @return MazeGrid object representing layout/state of maze
    */
    public MazeGrid getGrid(){
	return this.grid;
    }

    /**
       @return MazePlayer object representing the current player position for in-progress saves or starting position for finished saves.
     */
    public MazePlayer getPlayer(){
	return this.player;
    }

    /**
       @return settings for this saved game
     */
    public MazeSettings getSettings(){
	return this.settings;
    }

    /** check whether there are any high scores associated with this game
	@return whether any high scores saved
     */
    public boolean hasHighScores(){
	return this.scores.size()>0;
    }

    /**
       @return MazeHighScore representing highest score saved in this file
     */
    public MazeHighScore getHighScore(){
	if(this.scores.size()>0)
	    return this.scores.get(0);
	else
	    return null;
    }

    /**
      @return String of saved scores
    */
    public String getAllScoresString(){
      //this.sortScores();

      String result="\nMaze ScoreBoard\n";

      for(MazeHighScore thisScore:this.scores){
        int i=1;
        result = result + i++ +". "+ thisScore.getName() + "  " + thisScore.getTime()/1000.0+  " s.\n";

      }
      result +="\n";
      return result;

    }

    /**
       @return milliseconds elapsed for in-progress saves
     */
    public long getTimeElapsed(){
	return this.timeElapsed;
    }

    /**
       Sets time elapsed in this file, set to 0 for not-in-progress saves
       @param elapsed Milliseconds elapsed during this game session
     */
    public void setTimeElapsed(long elapsed){
	this.timeElapsed=elapsed;
    }

    /**
       Resets player position to maze start as per settings
     */
    public void resetPlayer(){
	this.player = new MazePlayer(this.grid);
    }
}
