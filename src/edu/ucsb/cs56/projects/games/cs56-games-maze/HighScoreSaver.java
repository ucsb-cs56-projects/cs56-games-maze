package edu.ucsb.cs56.projects.games.cs56_games_maze;

import java.io.File;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.Exception;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.*;

/*
  class to save high scores serialized

  @author Zakary Blake
  @see MazeHighScore.java
*/

public class HighScoreSaver{

  ArrayList<MazeHighScore> savedScores;


  // write a new high score to the file
  public boolean addNewHighScore(MazeHighScore thisHS) throws IOException{
    FileOutputStream outstream = null;
    ObjectOutputStream oso = null;

      try{
      outstream = new FileOutputStream("hs.ser");
      oso = new ObjectOutputStream(outstream);
      oso.writeObject(thisHS);
    }catch(IOException e){
          System.out.println("write file error");
          return false;
      }finally{
          oso.close();
          return true;
        }

  }

  // read high scores from the file and display
  public ArrayList<MazeHighScore> getScores() throws IOException{
    //
    MazeHighScore tempHighScore;
    FileInputStream instream = null;
    ObjectInputStream osi = null;


    try{
      instream = new FileInputStream("hs.ser");
      osi = new ObjectInputStream(instream);
      savedScores = new ArrayList<MazeHighScore>();
      // read high scores until exception
      while(true){
        try{
        // If there are no more objects to read, EOFEXCEPTION should break loop
            Object x = osi.readObject();
            tempHighScore = (MazeHighScore) x;  // cast object to correct type
            savedScores.add(tempHighScore);  // add objects to the arraylist

          }catch(EOFException e){
              break; // exit while loop
          }
        }
        // at this point, we should have an arraylist of MazeHighScore objects
        // time to sort
        MazeScoreCompare mazeScoreCompare = new MazeScoreCompare();
        Collections.sort(savedScores,mazeScoreCompare); // sort call

    }catch(IOException e){
      System.out.println("read file error");
      return null;
    }finally{
      osi.close();
      System.out.println("Successfully returning Arraylist");
      return savedScores;
    }


  }



}
