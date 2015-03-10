package edu.ucsb.cs56.projects.games.cs56_games_maze;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollPane;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
   Class where the JFrame for the instructions is created.
   @author Sophia Mao
   @version 5/31/13 for proj 2, cs56, S13
*/

public class MazeInstructGui{
    public MazeInstructGui(){

  String rules="";

	try{
	    String line; //String in textarea
	    String fString = "./src/edu/ucsb/cs56/projects/games/cs56-games-maze/instructions.txt"; //String of file name

	    FileInputStream in = new FileInputStream(fString);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));

	    //keep adding to textbox until null (no more text in txt file)
	    while(( line = br.readLine()) != null){
		rules+=line + "\n";
	    }
	}catch(Exception e){
	    //print out which directory user is currently in for debugging purposes
	    System.out.println("no such file exists in " + System.getProperty("user.dir") + " \n exception message: " + e);
	}

  JOptionPane.showMessageDialog(new JFrame(),rules);

    }

}
