package edu.ucsb.cs56.S12.jstaahl.issue769;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.InputMap;
import javax.swing.ActionMap;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.Action;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.*;


/**
   Class where the MazeGui is constructed.  This is also the main class and contains the main method
*/

public class MazeGui implements ActionListener{

    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu menu;
    private MazeTimerBar timerBar;
    private MazeGrid grid;
    private MazeComponent mc;
    private MazeGenerator mg;
    private MazePlayer player;
    private Timer drawTimer;
    private int genChainLength = 50;
    private int genChainLengthFlux = 50;
    private int stepGenDistance = 1;
    private int rows = 10;
    private int cols = 10;
    private int cellWidth = 10;
    private int startRow = 0;
    private int startCol = 0;
    private int endRow = rows-1;
    private int endCol = cols-1;
    private int genType = 1;
    
    public static final int MULTI_CHAIN_GEN = 1;
    public static final int ALT_STEP_GEN = 2;
    public static final int NEW_STEP_GEN = 3;

    public static void main(final String[] args){
	SwingUtilities.invokeLater(new Runnable(){
		public void run(){
		    new MazeGui(args).run();
		}
	    });
    }

    public MazeGui(String[] args){

	// check for command line arguments, initialize variables accordingly
	if (args.length != 0 && args.length != 2 && args.length != 5 && args.length != 9) {
	    System.out.println("Improper number of command line arguments: " + args.length);
	    System.out.println("Type ant run for proper usage.");
	    return;
	}
	switch (args.length) { // NOTE: no break statements.. cases flow through
	case 9:
	    startRow           = Integer.parseInt(args[5]);
	    startCol           = Integer.parseInt(args[6]);
	    endRow             = Integer.parseInt(args[7]);
	    endCol             = Integer.parseInt(args[8]);
	case 5:
	    rows               = Integer.parseInt(args[2]);
	    cols               = Integer.parseInt(args[3]);
	    cellWidth          = Integer.parseInt(args[4]);
	    // set endRow and endCol to rows-1 and cols-1 respectively, but
	    // only if they weren't already explicitly defined under (case 9:)
	    endRow             = args.length != 9 ? rows-1 : endRow;
	    endCol             = args.length != 9 ? cols-1 : endCol;
	case 2:
	    genChainLength     = Integer.parseInt(args[0]);
	    genChainLengthFlux = Integer.parseInt(args[1]);
	}

	// initialize the JFrame
	frame = new JFrame();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setTitle("Maze Game by Jakob Staahl");

	//initialize timer/controls bar
	this.timerBar = new MazeTimerBar(this);
	frame.add(timerBar, BorderLayout.SOUTH);

	//initialize menu bar and menus
	this.menuBar = new JMenuBar();
	this.menu = new JMenu("Menu");
	ButtonGroup group = new ButtonGroup();
	JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("Multi Chain Generator");
	rbMenuItem.setSelected(true);
	rbMenuItem.setActionCommand("multi_chain_gen");
	rbMenuItem.addActionListener(this);
	group.add(rbMenuItem);
	menu.add(rbMenuItem);
	rbMenuItem = new JRadioButtonMenuItem("Alt Step Generator");
	rbMenuItem.setActionCommand("alt_step_gen");
	rbMenuItem.addActionListener(this);
	group.add(rbMenuItem);
	menu.add(rbMenuItem);
	rbMenuItem = new JRadioButtonMenuItem("New Step Generator");
	rbMenuItem.setActionCommand("new_step_gen");
	rbMenuItem.addActionListener(this);
	group.add(rbMenuItem);
	menu.add(rbMenuItem);
	this.menuBar.add(this.menu);

	frame.setJMenuBar(this.menuBar);

	// initialize the MazeGrid, MazeComponent, and MazeGenerator
	this.grid = new MazeGrid(rows, cols);
	this.mc = new MazeComponent(grid, cellWidth);
	frame.add(mc);
	frame.pack();
	frame.setVisible(true);
	this.mg = new MultipleChainGenerator(grid, genChainLength, genChainLengthFlux);

	//initialize the player
	this.player = new MazePlayer(this.grid);
	//set up player keybinds
	Action playerMoveAction = new AbstractAction(){
		public void actionPerformed(ActionEvent e){
		    if(player!=null){
			switch(e.getActionCommand()){
			case "w":
			    player.move(MazeGrid.DIR_UP);
			    break;
			case"s":
			    player.move(MazeGrid.DIR_DOWN);
			    break;
			case "a":
			    player.move(MazeGrid.DIR_LEFT);
			    break;
			case "d":
			    player.move(MazeGrid.DIR_RIGHT);
			    break;
			}
			mc.repaint();
			if(grid.isAtFinish(player.getPosition()))
			    wonMaze();
		    }
		}
	    };
	InputMap inputMap = ((JPanel)frame.getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

	inputMap.put(KeyStroke.getKeyStroke("W"),"player_up");
	inputMap.put(KeyStroke.getKeyStroke("S"),"player_down");
	inputMap.put(KeyStroke.getKeyStroke("A"),"player_left");
	inputMap.put(KeyStroke.getKeyStroke("D"),"player_right");

	ActionMap actionmap = ((JPanel)frame.getContentPane()).getActionMap();

	actionmap.put("player_up", playerMoveAction);
	actionmap.put("player_down", playerMoveAction);
	actionmap.put("player_left", playerMoveAction);
	actionmap.put("player_right", playerMoveAction);
	mc.requestFocus();
    }
    
    public void run() {
	// generate the maze in steps (rather than all at once using MazeGenerator.generate())
	// repaint() in between each step to watch it grow
	// for performance only draw every 10 steps
	if(drawTimer!=null)
	    drawTimer.stop();
	drawTimer = new Timer(1, new ActionListener() {
		int i=0;
		public void actionPerformed(ActionEvent e){
		    ++i;
		    if(mg.step() && i%10==0)
			frame.repaint();
		    else if(i%10==0){
			((Timer)e.getSource()).stop();
			timerBar.startTimer();
			grid.markStartFinish();
			player.setVisible(true);
			mc.repaint();
		    }
		}
	    });
	drawTimer.start();
    }

    public void newMaze() {
	timerBar.stopTimer();
	this.grid = new MazeGrid(rows, cols);
	this.player = new MazePlayer(this.grid);
	switch(this.genType){
	case MazeGui.MULTI_CHAIN_GEN:
	    this.mg = new MultipleChainGenerator(grid, genChainLength, genChainLengthFlux);
	    break;
	case MazeGui.ALT_STEP_GEN:
	    this.mg = new AltStepGenerator(grid, this.stepGenDistance); 
	    break;
	case MazeGui.NEW_STEP_GEN:
	    this.mg = new NewStepGenerator(grid, this.stepGenDistance);
	    break;
	}
	frame.remove(mc);
	mc = new MazeComponent(grid, cellWidth);
	frame.add(mc);
        frame.pack();
	mc.requestFocus();
	run();
    }

    public void solveMaze() {
	timerBar.stopTimer();
	// display the solution to the maze
	mg.solve(new Cell(startRow, startCol), (byte)0x0,
	    new Cell(endRow, endCol));
	mc.repaint();
    }

    public void actionPerformed(ActionEvent e) {
	if("multi_chain_gen".equals(e.getActionCommand())){
	    this.genType = MazeGui.MULTI_CHAIN_GEN;
	}
	else if("alt_step_gen".equals(e.getActionCommand())){
	    this.genType = MazeGui.ALT_STEP_GEN;
	}
	else if("new_step_gen".equals(e.getActionCommand())){
	    this.genType = MazeGui.NEW_STEP_GEN;
	}
    }

    public void wonMaze(){
	timerBar.stopTimer();
	JOptionPane.showMessageDialog(frame, "Congratulations, you won!\n It took you "+player.getNumMoves()+" moves.", "Victory",JOptionPane.INFORMATION_MESSAGE);
	this.player=null;
    }

}
