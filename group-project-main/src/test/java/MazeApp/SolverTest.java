package MazeApp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {

    Solver obj;

    @BeforeEach
    public void beforeEach() throws Exception {
        obj = null;
    }

    @AfterEach
    public void tearDown() throws Exception {
        obj = null;
    }

    @Test
    void checkSolvability() {
        Maze maze;

        maze = new Maze(100, 100, 10, true, "Arrows", "TITLE", "AUTHOR", null, null, null, null);
        assertTrue(new Solver(maze).checkSolvability());
        maze = null;
        maze = new Maze(1000, 1000, 5, true, "Arrows", "TITLE", "AUTHOR", null, null, null, null);
        assertTrue(new Solver(maze).checkSolvability());
        maze = null;
        maze = new Maze(1000, 100, 20, true, "Arrows", "TITLE", "AUTHOR", null, null, null, null);
        assertTrue(new Solver(maze).checkSolvability());
        maze = null;
        maze = new Maze(100, 100, 10, true, "Arrows", "TITLE", "AUTHOR", null, null, null, null);
        assertTrue(new Solver(maze).checkSolvability());
        maze = null;
        maze = new Maze(100, 100, 10, false, "Arrows", "TITLE", "AUTHOR", null, null, null, null);
        assertFalse(new Solver(maze).checkSolvability());
        maze = null;
    }

    @Test
    void exploredPercentageFunctional() {
        Maze maze;
        maze = new Maze(100, 100, 10, false, "Arrows", "TITLE", "AUTHOR", null, null, null, null);
        double explored = new Solver(maze).explored;
        assertEquals(0, explored);

//        maze = new mock up maze with unique explored percentage;
//        double explored = new Solver(maze).explored;
//        assertEquals(explored, unique explored percentage);

    }


    @Test
    void checkDeadEndFunctional() {
        Maze maze;


        maze = new Maze(100, 100, 10, false, "Arrows", "TITLE", "AUTHOR", null, null, null, null);
        assertEquals("0% DEAD END CELLS",new Solver(maze).checkDeadEnd());

//        maze = new mock up maze with unique dead end percentage;
//        double explored = new Solver(maze).explored;
//        assertEquals(new Solver(maze).checkDeadEnd(), unique deadend percentage);
    }
}