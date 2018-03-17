package edu.ucsb.cs56.projects.games.cs56_games_maze;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.beans.*;


public class ColorChooser extends JDialog {
    private ColorPanel panel;

    public ColorChooser(MazeGui gm) {
        super();
        this.setTitle("Color Chooser");
        this.panel = new ColorPanel(gm);
        this.setContentPane(this.panel);
        this.pack();
    }
}
