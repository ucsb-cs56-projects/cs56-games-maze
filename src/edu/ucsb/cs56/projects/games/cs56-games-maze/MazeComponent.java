package edu.ucsb.cs56.projects.games.cs56_games_maze;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.lang.Math;


/**
 * A class extending JComponent that is used to draw all of the information held in
 * a MazeGrid (the markers, as well as the remaining closed walls of each cell)
 *
 * @author Jake Staahl
 * @author Evan West
 * @version 5/14/13 for proj1, cs56, S13
 * @see MazeGrid
 */
public class MazeComponent extends JComponent implements MouseListener {
    private MazeGrid grid;
    private int cellWidth;
    private int red;
    private int green;
    private int blue;
    private Color SolutionColor;
    private Color EndColor;
    private Color StartColor;
    private Color SideColor;
    private boolean rect;
    private boolean isPaused;

    /**
     * Construct a MazeComponent to draw this MazeGrid grid, with the width of each
     * Cell being drawn at cellWidth pixels wide and tall
     *
     * @param grid the MazeGrid this MazeComponent will be drawing
     */
    public MazeComponent(MazeGrid grid, int cellWidth, Color backgroundColor, boolean r) {
        this.red = backgroundColor.getRed();
        this.green = backgroundColor.getGreen();
        this.blue = backgroundColor.getBlue();

        Color c;

        c = Color.RED;
        EndColor = new Color(Math.abs(c.getRed() - red),Math.abs(c.getGreen() - green),Math.abs(c.getBlue() - blue));

        c = Color.CYAN;
        this.grid = grid;
        StartColor = new Color(Math.abs(c.getRed() - red),Math.abs(c.getGreen() - green),Math.abs(c.getBlue() - blue));

        SideColor = new Color(Math.abs(red - 255), Math.abs(green - 255), Math.abs(blue - 255));

        SolutionColor = new Color((2*SideColor.getRed() + red) / 3, (2*SideColor.getGreen() + green) / 3, (2*SideColor.getBlue() + blue) / 3);

        this.cellWidth = cellWidth;
        addMouseListener(this);
        this.setFocusable(true);
        this.rect = r;
        this.isPaused = false;
    }


    /**
     * Method in JComponent overrided to draw this MazeGrid
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // draw each cell in the grid using drawCell()

        if (!isPaused) {
            Cell a = new Cell(0, 0);
            for (; a.row < grid.getRows(); a.row++) {
                for (; a.col < grid.getCols(); a.col++) {
                    drawCell(g2, a);
                }
                a.col = 0;
            }
        }

    }

    /**
     * Method in JComponent overrided to set the preferred size of window space that this
     * component gets
     */
    public Dimension getPreferredSize() {
        return new Dimension(grid.getCols() * cellWidth, grid.getRows() * cellWidth);
    }

    /**
     * Method in JComponent overrided to set the minimum size of window space that this
     * component gets
     */
    public Dimension getMinSize() {
        return new Dimension(grid.getCols() * cellWidth, grid.getRows() * cellWidth);
    }

    /**
     * Draws the markers and closed walls of a given cell a in the Graphics2D object g2
     *
     * @param g2 the Graphics2D object within which this Cell is being drawn
     * @param a  the Cell that is being drawn
     */
    public void drawCell(Graphics2D g2, Cell a) {
        // paint the proper markers for the Cell in the proper order
        if (this.grid.hasMarker(a, MazeGrid.NULL_MARKER)) //do not draw
            return;
        if (this.grid.hasMarker(a, MazeGrid.SOLUTION_MARKER)) //solution
            this.paintSolve(g2, a);
        else if (this.grid.hasMarker(a, MazeGrid.END_MARKER)) //finish
            this.paintEnd(g2, a);
        else if (this.grid.hasMarker(a, MazeGrid.START_MARKER)) //start
            this.paintStart(g2, a);
        if (this.grid.hasMarker(a, MazeGrid.PLAYER_MARKER)) { //player
            this.paintShape(g2, a);
        }

        // paint the walls of the Cell
        short directions = this.grid.getCellDirections(a);

        g2.setColor(SideColor);

        if ((directions & MazeGrid.DIR_RIGHT) == 0) {
            Line2D.Float wall = new Line2D.Float(this.cellWidth * a.col + this.cellWidth - 1,
                    this.cellWidth * a.row + 0,
                    this.cellWidth * a.col + this.cellWidth - 1,
                    this.cellWidth * a.row + this.cellWidth - 1);
            g2.draw(wall);
        }
        if ((directions & MazeGrid.DIR_UP) == 0) {
            Line2D.Float wall = new Line2D.Float(this.cellWidth * a.col + 0,
                    this.cellWidth * a.row + 0,
                    this.cellWidth * a.col + this.cellWidth - 1,
                    this.cellWidth * a.row + 0);
            g2.draw(wall);
        }
        if ((directions & MazeGrid.DIR_LEFT) == 0) {
            Line2D.Float wall = new Line2D.Float(this.cellWidth * a.col + 0,
                    this.cellWidth * a.row + 0,
                    this.cellWidth * a.col + 0,
                    this.cellWidth * a.row + this.cellWidth - 1);
            g2.draw(wall);
        }
        if ((directions & MazeGrid.DIR_DOWN) == 0) {
            Line2D.Float wall = new Line2D.Float(this.cellWidth * a.col + 0,
                    this.cellWidth * a.row + this.cellWidth - 1,
                    this.cellWidth * a.col + this.cellWidth - 1,
                    this.cellWidth * a.row + this.cellWidth - 1);
            g2.draw(wall);
        }
    }

    /**
     * How MazeGrid.END_MARKER should be painted. Change this if you want endMarker to be painted differently.
     */
    private void paintEnd(Graphics2D g2, Cell a) {

        g2.setColor(EndColor);
        g2.fill(new Rectangle2D.Float(this.cellWidth * a.col, this.cellWidth * a.row, this.cellWidth, this.cellWidth));

        //g2.fill(new Rectangle2D.Double(this.cellWidth*a.col + (0.4*this.cellWidth)-1,this.cellWidth*a.row + (0.4*this.cellWidth)-1,0.4*this.cellWidth,0.4*this.cellWidth));
    }

    /**
     * How MazeGrid.START_MARKER should be painted. Change this if you want start marker to be painted differently.
     */
    private void paintStart(Graphics2D g2, Cell a) {
        g2.setColor(StartColor);

        g2.fill(new Rectangle2D.Float(this.cellWidth * a.col, this.cellWidth * a.row, this.cellWidth, this.cellWidth));
        //g2.fill(new Rectangle2D.Double(this.cellWidth*a.col + (0.4*this.cellWidth)-1, this.cellWidth*a.row + (0.4*this.cellWidth)-1, 0.4*this.cellWidth,0.4*this.cellWidth));
    }

    /**
     * How MazeGrid.SOLUTION_MARKER should be painted. Change this if you want solutioin marker to be painted differently.
     */
    private void paintSolve(Graphics2D g2, Cell a) {
        g2.setColor(SolutionColor);

        g2.fill(new Rectangle2D.Float(this.cellWidth * a.col, this.cellWidth * a.row, this.cellWidth, this.cellWidth));
    }

    /**
     * How MazeGrid.PLAYER_MARKER should be painted. Change this if you want player marker to be painted differently.
     */
    private void paintShape(Graphics2D g2, Cell a) {

        g2.setColor(SideColor);

        //g2.fill(new Rectangle2D.Float(this.cellWidth*a.col, this.cellWidth*a.row, this.cellWidth, this.cellWidth));
        if (rect == true)
            g2.fill(new Rectangle2D.Double(this.cellWidth * a.col + (0.4 * this.cellWidth) - 1, this.cellWidth * a.row + (0.4 * this.cellWidth) - 1, 0.4 * this.cellWidth, 0.4 * this.cellWidth));
        else
            g2.fill(new Ellipse2D.Double(this.cellWidth * a.col + (0.4 * this.cellWidth) - 1, this.cellWidth * a.row + (0.4 * this.cellWidth) - 1, 0.4 * this.cellWidth, 0.4 * this.cellWidth));
    }


    public void setbackgroundColor(Color backgroundColor) {
        this.red = backgroundColor.getRed();
        this.green = backgroundColor.getGreen();
        this.blue = backgroundColor.getBlue();

        Color c;

        c = Color.RED;
        EndColor = new Color(Math.abs(c.getRed() - red),Math.abs(c.getGreen() - green),Math.abs(c.getBlue() - blue));

        c = Color.CYAN;
        this.grid = grid;
        StartColor = new Color(Math.abs(c.getRed() - red),Math.abs(c.getGreen() - green),Math.abs(c.getBlue() - blue));

        SideColor = new Color(Math.abs(red - 255), Math.abs(green - 255), Math.abs(blue - 255));

        SolutionColor = new Color((2*SideColor.getRed() + red) / 3, (2*SideColor.getGreen() + green) / 3, (2*SideColor.getBlue() + blue) / 3);
    }

    public void setShape(boolean rect) {
        this.rect = rect;
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    /**
     * Sets MazeGrid associated with this MazeComponent
     *
     * @param mg New MazeGrid to associate with this component
     */
    public void setMazeGrid(MazeGrid mg) {
        this.grid = mg;
    }

    /**
     * MouseListener implementation to catch focus on click
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        requestFocusInWindow();
    }

    /**
     * Functionless implementation for MouseListener interface
     */
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Functionless implementation for MouseListener interface
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Functionless implementation for MouseListener interface
     */
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Functionless implementation for MouseListener interface
     */
    public void mousePressed(MouseEvent e) {
    }
}
