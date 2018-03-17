package edu.ucsb.cs56.projects.games.cs56_games_maze;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.beans.*;


public class ColorPanel extends JPanel {

    private JFrame ColorWindow;
    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JColorChooser colorChooser;

    private MazeGui gm;

    public ColorPanel(MazeGui gm) {
        this.gm = gm;
        JFrame ColorWindow = new JFrame();

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        buttonPanel = new JPanel();

        colorChooser = new JColorChooser();

        this.add(colorChooser);

        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gm.setColor(colorChooser.getColor(), true);

                JDialog parentDialog = (JDialog) (getRootPane().getParent());
                parentDialog.setVisible(false);

                gm.resumeGame(false);
            }
        });

        buttonPanel.add(okButton);

        cancelButton = new JButton("Cancel");

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gm.setColor(colorChooser.getColor(), false);
                JDialog parentDialog = (JDialog) (getRootPane().getParent());
                parentDialog.setVisible(false);
                gm.resumeGame(false);
            }
        });

        buttonPanel.add(cancelButton);

        this.add(buttonPanel);
    }
}