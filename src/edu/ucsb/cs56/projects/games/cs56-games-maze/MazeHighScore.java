package edu.ucsb.cs56.projects.games.cs56_games_maze;

import java.io.Serializable;

public class MazeHighScore implements Serializable, Comparable<MazeHighScore>{

    private String name;
    private long time;

    public MazeHighScore(String name, long time){
	this.name=name;
	this.time=time;
    }

    public String getName(){
	return this.name;
    }
    
    public long getTime(){
	return this.time;
    }

    public int compareTo(MazeHighScore other){
	if (other==null){
	    throw new NullPointerException();
	}
	Long otherTime = new Long(other.getTime());
	return -1*otherTime.compareTo(this.time);
    }
}
