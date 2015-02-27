package edu.ucsb.cs56.projects.games.cs56_games_maze;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollPane;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.File;
import java.lang.Exception;
import java.io.*;

/**
   Class where the JTable for the High Score table is created.
   @author Victor Porter
   @version 2/26/2015 for proj 2, cs56, S13
*/

public class HighScoreTable{

	private HighScoreSaver scoreSaver;
	private ArrayList<MazeHighScore> highScores;

    public HighScoreTable() {

    scoreSaver = new HighScoreSaver("ABCDEF.ser");


    try{
    	if(scoreSaver.hasEmptyFile() != true) { highScores = scoreSaver.getHighScoreList();}
    }catch(IOException e){
    	System.err.println("read file error");
    	e.printStackTrace();
    }



	//initialize JFrame: set visible and size
	JFrame frame = new JFrame("High Scores");
	frame.setVisible(true);
	frame.setSize(300,300);

	String rowData[][] = new String[highScores.size()][2];
	String columnNames[] = { "Name", "Time", "Score"};

	for(int count = 0; count < highScores.size(); count++){
		MazeHighScore currentHighScore = highScores.get(count);
		rowData[count][0] = currentHighScore.getName();
		rowData[count][1] = Long.toString(currentHighScore.getTime());
		rowData[count][2] = "100";
	}
	JTable table = new JTable(rowData, columnNames);

	JScrollPane scrollPane = new JScrollPane(table);
    frame.add(scrollPane, BorderLayout.CENTER);
    frame.setSize(300, 150);
    frame.setVisible(true);

    }





}
