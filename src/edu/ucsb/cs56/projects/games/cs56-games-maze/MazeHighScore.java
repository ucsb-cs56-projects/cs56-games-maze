package edu.ucsb.cs56.projects.games.cs56_games_maze;

import java.io.Serializable;

/** Class that holds data for  one maze score.
    Holds no references to maze object, so must be associated with by something else.

    @author Evan West
    @version CS56 S13 UCSB
 */
public class MazeHighScore implements Serializable, Comparable<MazeHighScore>{

    private String name;
    private long time;

    /** Constructor for high score
	@param name Name string to associate with score (prompted from player)
	@param time Time in milliseconds that player took to complete the maze
    */
    public MazeHighScore(String name, long time){
	this.name=name;
	this.time=time;
    }

    /** @return Name associated with the high score */
    public String getName(){
	return this.name;
    }
    
    /** @return Time in milliseconds of this score */
    public long getTime(){
	return this.time;
    }

    /** Override to allow comparison of scores, smallest time is first
	@param other Another MazeHighScore object to compare this one to
    */
    @Override
    public int compareTo(MazeHighScore other){
	if (other==null){
	    throw new NullPointerException();
	}
	Long otherTime = new Long(other.getTime());
	return -1*otherTime.compareTo(this.time);
    }
}
