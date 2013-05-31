package edu.ucsb.cs56.projects.games.cs56_games_maze;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.beans.*;

public class MazeSettingsDialog extends JDialog{

    private MazeSettings settings;
    private MazeSettingsPanel panel;

    public MazeSettingsDialog(MazeSettings settings){
	super();
	this.setTitle("Settings");
	this.settings=settings;
	this.panel = new MazeSettingsPanel(settings);
	this.setContentPane(this.panel);
	this.pack();
    }

}
