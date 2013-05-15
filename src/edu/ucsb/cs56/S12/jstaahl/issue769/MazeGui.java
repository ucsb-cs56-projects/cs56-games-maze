package edu.ucsb.cs56.S12.jstaahl.issue769;
import javax.swing.*;
import java.awt.event.*;
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
    private MazeSettings settings;
    private Action playerMoveAction;
    
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

	this.settings = new MazeSettings();

	// check for command line arguments, initialize variables accordingly
	if (args.length != 0 && args.length != 2 && args.length != 5 && args.length != 9) {
	    System.out.println("Improper number of command line arguments: " + args.length);
	    System.out.println("Type ant run for proper usage.");
	    return;
	}
	switch (args.length) { // NOTE: no break statements.. cases flow through
	case 9:
	    settings.startRow = Integer.parseInt(args[5]);
	    settings.startCol = Integer.parseInt(args[6]);
	    settings.endRow = Integer.parseInt(args[7]);
	    settings.endCol = Integer.parseInt(args[8]);
	case 5:
	    settings.rows = Integer.parseInt(args[2]);
	    settings.cols = Integer.parseInt(args[3]);
	    settings.cellWidth = Integer.parseInt(args[4]);
	    // set endRow and endCol to rows-1 and cols-1 respectively, but
	    // only if they weren't already explicitly defined under (case 9:)
	    settings.endRow = args.length != 9 ? settings.rows-1 : settings.endRow;
	    settings.endCol = args.length != 9 ? settings.cols-1 : settings.endCol;
	case 2:
	    settings.genChainLength = Integer.parseInt(args[0]);
	    settings.genChainLengthFlux = Integer.parseInt(args[1]);
	}

	// initialize the JFrame
	this.frame = new JFrame();
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
	menu.addSeparator();
	JCheckBoxMenuItem cbMenuItem = new JCheckBoxMenuItem("Progressive Reveal");
	cbMenuItem.setActionCommand("prog_reveal");
	cbMenuItem.addActionListener(this);
	menu.add(cbMenuItem);
	menu.addSeparator();
	JMenuItem menuItem = new JMenuItem("Settings");
	menuItem.setActionCommand("settings");
	menuItem.addActionListener(this);
	menu.add(menuItem);
	
	this.menuBar.add(this.menu);

	frame.setJMenuBar(this.menuBar);

	// initialize the MazeGrid, MazeComponent, and MazeGenerator
	this.grid = new MazeGrid(settings.rows, settings.cols);
	this.mc = new MazeComponent(grid, settings.cellWidth);
	mc.setFocusable(true);
	frame.add(mc);
	frame.pack();
	frame.setVisible(true);
	this.mg = new MultipleChainGenerator(grid, settings.genChainLength, settings.genChainLengthFlux);

	//initialize the player
	this.player = new MazePlayer(this.grid);
	grid.setPlayer(player);
	//set up player keybinds
	this.playerMoveAction = new PlayerMoveAction();
	//mc.requestFocus();
	remapPlayerKeys(this.playerMoveAction);
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
			//done drawing
			((Timer)e.getSource()).stop();
			timerBar.startTimer();
			grid.markStartFinish();
			if(settings.progReveal)
			    grid.setProgReveal(player, settings.progRevealRadius);
			grid.updatePlayerPosition();
			mc.repaint();
			//mc.requestFocusInWindow();
		    }
		}
	    });
	drawTimer.start();
	//mc.requestFocus();
    }

    public void newMaze() {
	timerBar.stopTimer();
	frame.remove(mc);
	this.grid = new MazeGrid(settings.rows, settings.cols);
	this.mc = new MazeComponent(grid, settings.cellWidth);
	mc.setVisible(true);
	mc.setFocusable(true);
	frame.add(mc);
        frame.pack();
	frame.setVisible(true);
	switch(settings.genType){
	case MazeGui.MULTI_CHAIN_GEN:
	    this.mg = new MultipleChainGenerator(grid, settings.genChainLength, settings.genChainLengthFlux);
	    break;
	case MazeGui.ALT_STEP_GEN:
	    this.mg = new AltStepGenerator(grid, settings.stepGenDistance); 
	    break;
	case MazeGui.NEW_STEP_GEN:
	    this.mg = new NewStepGenerator(grid, settings.stepGenDistance);
	    break;
	}
	this.player = new MazePlayer(this.grid);
	Action playerMoveAction = new PlayerMoveAction();
	//remapPlayerKeys(playerMoveAction);
	//mc.requestFocus();
	run();
    }

    public void solveMaze() {
	timerBar.stopTimer();
	// display the solution to the maze
	mg.solve(new Cell(settings.startRow, settings.startCol), (short)0x0,
	    new Cell(settings.endRow, settings.endCol));
	mc.repaint();
    }

    public void actionPerformed(ActionEvent e) {
	if("multi_chain_gen".equals(e.getActionCommand())){
	    settings.genType = MazeGui.MULTI_CHAIN_GEN;
	}
	else if("alt_step_gen".equals(e.getActionCommand())){
	    settings.genType = MazeGui.ALT_STEP_GEN;
	}
	else if("new_step_gen".equals(e.getActionCommand())){
	    settings.genType = MazeGui.NEW_STEP_GEN;
	}
	else if("settings".equals(e.getActionCommand())){
	    //show settings dialog
	    //JOptionPane.showConfirmDialog(frame, new MazeSettingsPanel(this.settings));
	}
	else if("prog_reveal".equals(e.getActionCommand())){
	    AbstractButton button = (AbstractButton)e.getSource();
	    settings.progReveal=button.getModel().isSelected();
	}
    }

    private void wonMaze(){
	timerBar.stopTimer();
	//JOptionPane.showMessageDialog(frame, "Congratulations, you won!\n It took you "+player.getNumMoves()+" moves.", "Victory",JOptionPane.INFORMATION_MESSAGE);
	this.player=null;
	//mc.requestFocus();
    }

    private void remapPlayerKeys(Action a){
	InputMap inputMap = ((JPanel)this.frame.getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	inputMap.put(KeyStroke.getKeyStroke("W"),"player_up");
	inputMap.put(KeyStroke.getKeyStroke("S"),"player_down");
	inputMap.put(KeyStroke.getKeyStroke("A"),"player_left");
	inputMap.put(KeyStroke.getKeyStroke("D"),"player_right");
	ActionMap actionmap = ((JPanel)this.frame.getContentPane()).getActionMap();
	actionmap.put("player_up",a);
	actionmap.put("player_down",a);
	actionmap.put("player_left",a);
	actionmap.put("player_right",a);
    }

    class PlayerMoveAction extends AbstractAction{
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
	    else{
		System.err.println("NULL player!");
	    }
	}
    }
}
