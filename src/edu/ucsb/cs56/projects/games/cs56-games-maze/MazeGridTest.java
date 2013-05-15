package edu.ucsb.cs56.projects.games.cs56_games_maze;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MazeGridTest {
    public static MazeGrid grid;
    
    // create a grid, carve a path from one cell to an adjacent cell
    // test if the proper directions were added to those cells in the grid
    @Test
    public void test_carvePath1() {
	int rows = 60;
	int cols = 60;
	grid = new MazeGrid(rows, cols);
	Cell a = new Cell(0, 0);
	Cell b = new Cell(0, 1);
	grid.carvePath(a, b);
	assertEquals(MazeGrid.DIR_RIGHT, grid.getCellDirections(a));
	assertEquals(MazeGrid.DIR_LEFT, grid.getCellDirections(b));
    }
    @Test
    public void test_carvePath2() {
	int rows = 60;
	int cols = 60;
	grid = new MazeGrid(rows, cols);
	Cell a = new Cell(0, 0);
	Cell b = new Cell(1, 0);
	grid.carvePath(a, b);
	assertEquals(MazeGrid.DIR_DOWN, grid.getCellDirections(a));
	assertEquals(MazeGrid.DIR_UP, grid.getCellDirections(b));
    }
    @Test
    public void test_carvePath3() {
	int rows = 60;
	int cols = 60;
	grid = new MazeGrid(rows, cols);
	Cell a = new Cell(0, 1);
	Cell b = new Cell(0, 0);
	grid.carvePath(a, b);
	assertEquals(MazeGrid.DIR_LEFT, grid.getCellDirections(a));
	assertEquals(MazeGrid.DIR_RIGHT, grid.getCellDirections(b));
    }
    @Test
    public void test_carvePath4() {
	int rows = 60;
	int cols = 60;
	grid = new MazeGrid(rows, cols);
	Cell a = new Cell(1, 0);
	Cell b = new Cell(0, 0);
	grid.carvePath(a, b);
	assertEquals(MazeGrid.DIR_UP, grid.getCellDirections(a));
	assertEquals(MazeGrid.DIR_DOWN, grid.getCellDirections(b));
    }

    // create a grid, carve a path from one cell to an adjacent cell
    // then test to see that canMove() method works properly 
    @Test
    public void test_canMov1() {
	int rows = 60;
	int cols = 60;
	grid = new MazeGrid(rows, cols);
	Cell a = new Cell(0, 0);
	Cell b = new Cell(0, 1);
	grid.carvePath(a, b);
	assertEquals(true, grid.canMove(a, MazeGrid.DIR_RIGHT));
    }
    @Test
    public void test_canMove2() {
	int rows = 60;
	int cols = 60;
	grid = new MazeGrid(rows, cols);
	Cell a = new Cell(0, 0);
	Cell b = new Cell(0, 1);
	grid.carvePath(a, b);
	assertEquals(false, grid.canMove(b, MazeGrid.DIR_RIGHT));
    }

    // create a grid, then test to see that direction() works properly
    @Test
    public void test_direction1() {
	int rows = 60;
	int cols = 60;
	grid = new MazeGrid(rows, cols);
	Cell a = new Cell(0, 0);
	Cell b = new Cell(0, 1);
	assertEquals(MazeGrid.DIR_RIGHT, grid.direction(a, b));
    }
    @Test
    public void test_direction2() {
	int rows = 60;
	int cols = 60;
	grid = new MazeGrid(rows, cols);
	Cell a = new Cell(0, 0);
	Cell b = new Cell(1, 0);
	assertEquals(MazeGrid.DIR_DOWN, grid.direction(a, b));
    }
    @Test
    public void test_direction3() {
	int rows = 60;
	int cols = 60;
	grid = new MazeGrid(rows, cols);
	Cell a = new Cell(0, 1);
	Cell b = new Cell(0, 0);
	assertEquals(MazeGrid.DIR_LEFT, grid.direction(a, b));
    }
    @Test
    public void test_direction4() {
	int rows = 60;
	int cols = 60;
	grid = new MazeGrid(rows, cols);
	Cell a = new Cell(1, 0);
	Cell b = new Cell(0, 0);
	assertEquals(MazeGrid.DIR_UP, grid.direction(a, b));
    }

    // create a grid, then test to see that direction() works properly
    @Test
    public void test_getCell1() {
	int rows = 60;
	int cols = 60;
	grid = new MazeGrid(rows, cols);
	Cell a = new Cell(0, 0);
	Cell b = new Cell(0, 1);
	assertEquals(b, grid.getCell(a, MazeGrid.DIR_RIGHT));
    }
    @Test
    public void test_getCell2() {
	int rows = 60;
	int cols = 60;
	grid = new MazeGrid(rows, cols);
	Cell a = new Cell(0, 0);
	Cell b = new Cell(1, 0);
	assertEquals(b, grid.getCell(a, MazeGrid.DIR_DOWN));
    }
    @Test
    public void test_getCell3() {
	int rows = 60;
	int cols = 60;
	grid = new MazeGrid(rows, cols);
	Cell a = new Cell(0, 1);
	Cell b = new Cell(0, 0);
	assertEquals(b, grid.getCell(a, MazeGrid.DIR_LEFT));
    }
    @Test
    public void test_getCell4() {
	int rows = 60;
	int cols = 60;
	grid = new MazeGrid(rows, cols);
	Cell a = new Cell(1, 0);
	Cell b = new Cell(0, 0);
	assertEquals(b, grid.getCell(a, MazeGrid.DIR_UP));
    }

    // create a grid, then mark a cell using markCell
    // test to see that hasMarker() works properly
    @Test
    public void test_hasMarker1() {
	int rows = 60;
	int cols = 60;
	grid = new MazeGrid(rows, cols);
	Cell a = new Cell(0, 0);
	grid.markCell(a, MazeGrid.MARKER1);
	assertEquals(true, grid.hasMarker(a, MazeGrid.MARKER1));
    }
    @Test
    public void test_hasMarker2() {
	int rows = 60;
	int cols = 60;
	grid = new MazeGrid(rows, cols);
	Cell a = new Cell(0, 0);
	grid.markCell(a, MazeGrid.MARKER2);
	assertEquals(true, grid.hasMarker(a, MazeGrid.MARKER2));
    }
    @Test
    public void test_hasMarker3() {
	int rows = 60;
	int cols = 60;
	grid = new MazeGrid(rows, cols);
	Cell a = new Cell(0, 0);
	grid.markCell(a, MazeGrid.MARKER3);
	assertEquals(true, grid.hasMarker(a, MazeGrid.MARKER3));
    }

    // create a grid, then carve a path between two cells
    // test to see that isVisited() works properly
    @Test
    public void test_isVisited1() {
	int rows = 60;
	int cols = 60;
	grid = new MazeGrid(rows, cols);
	Cell a = new Cell(0, 0);
	Cell b = new Cell(0, 1);
	grid.carvePath(a, b);
	assertEquals(true, grid.isVisited(a) && grid.isVisited(b));
    }
    @Test
    public void test_isVisited2() {
	int rows = 60;
	int cols = 60;
	grid = new MazeGrid(rows, cols);
	Cell a = new Cell(0, 0);
	Cell b = new Cell(0, 1);
	Cell c = new Cell(1, 0);
	grid.carvePath(a, b);
	assertEquals(false, grid.isVisited(c));
    }
		
}