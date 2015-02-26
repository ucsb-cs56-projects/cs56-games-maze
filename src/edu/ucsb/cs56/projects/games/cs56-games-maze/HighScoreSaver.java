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

  HighScoreSaver(String filename){ // CTOR
    myFile = new File(filename);
  }

  // write method for clearing the arraylist, write to an empty file...(empty file is creating null pointer exception)


  // pass this function an arraylist and it will write the objects to .ser file
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

  public boolean hasEmptyFile(){
    if (myFile==null) return false;
    if (myFile.length()!=0) return false;
    return true;
  }


  // returns all the scores read from the .ser file in an array
  public ArrayList<MazeHighScore> getHighScoreList() throws IOException{
    //
    MazeHighScore tempHighScore;
    FileInputStream instream = null;
    ObjectInputStream osi = null;


    try{
      System.out.println(">BEGIN");
      instream = new FileInputStream(myFile);
      System.out.println(">END");
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
        //MazeScoreCompare mazeScoreCompare = new MazeScoreCompare();
        //Collections.sort(savedScores,mazeScoreCompare); // sort call

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
