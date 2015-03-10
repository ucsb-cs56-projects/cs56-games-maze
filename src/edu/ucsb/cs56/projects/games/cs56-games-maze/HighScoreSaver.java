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
  File myFile=null;
  ArrayList<MazeHighScore> savedScores;

  /**
  Constructor that is tied to the file it is created with
  */
  HighScoreSaver(String filename){ // CTOR
    myFile = new File(filename);
  }

  /**
  pass this function an arraylist and it will write the objects to .ser file
  @param scoreList is serialized to a file
  */
  public boolean writeHighScoreList(ArrayList<MazeHighScore> scoreList) throws IOException{
    FileOutputStream outstream = null;
    ObjectOutputStream oso = null;
      try{
      outstream = new FileOutputStream(myFile);
      oso = new ObjectOutputStream(outstream);
      for (MazeHighScore thisScore:scoreList){
        oso.writeObject(thisScore);
        }
    }catch(IOException e){
          System.out.println("write file error");
          return false;
      }finally{
          oso.close();
          return true;
        }

  }
  /**
    Checks that the file tied to this saver is not empty
  */
  public boolean hasEmptyFile(){
    if (myFile==null)
      {
        System.err.println("*****NULL file");
        return false;
      }
    if (myFile.length()!=0) return false;
    return true;
  }
  /**
    Deserializes scores from a file into a returned arrayList
  */
  public ArrayList<MazeHighScore> getHighScoreList() throws IOException{
    //
    MazeHighScore tempHighScore;
    FileInputStream instream = null;
    ObjectInputStream osi = null;


    try{
      instream = new FileInputStream(myFile);
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


    }catch(IOException e){
      e.printStackTrace();
      return null;
    }finally{
      osi.close();
      return savedScores;
    }


  }



}
