package MazeApp;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class DBMazeDataSourceTest {
    Connection connection;
    App parentApp;

    @BeforeEach
    public void beforeAll() throws Exception {
        parentApp = new App();
    }

    @AfterEach
    public void tearDown() throws Exception {
        parentApp = null;
    }

    @Test
    void DBMazeDataSourceFunctional() {

        connection = DBConnector.getInstance();
        assertNotNull(connection);

    }

    @Test
    void addMaze() {

        Maze maze = new Maze(100, 100, 10, false, "Arrows", "TITLE", "AUTHOR", null, null, null, null);
        parentApp.mazeData.addMaze(maze);

    }

    @Test
    void getMazeList() {

        Object[][] mazeDataList = parentApp.mazeData.getMazeList();
        assertNotNull(mazeDataList);

    }

    @Test
    void updateMaze() {
        Maze maze = new Maze(100, 100, 10, false, "Arrows", "TITLE", "AUTHOR", null, null, null, null);
        int lastID = parentApp.mazeData.addMaze(maze);
        System.out.println(lastID);
        Maze maze_get = parentApp.mazeData.getMaze(lastID);
        maze_get.setMazeCell(2,2,Maze.CellState.PATH);
        assertTrue(parentApp.mazeData.updateMaze(maze_get));
        Maze maze_get2 = parentApp.mazeData.getMaze(lastID);
        assertEquals(Maze.CellState.PATH,maze_get2.getMaze()[2][2]);
    }



    @Test
    void getMazeValid() {
        Maze maze = new Maze(100, 100, 10, false, "Arrows", "TITLE", "AUTHOR", null, null, null, null);
        int lastID = parentApp.mazeData.addMaze(maze);
        System.out.println(lastID);

        Maze maze_get = parentApp.mazeData.getMaze(lastID);

        assertNotNull(maze_get);

    }
    @Test
    void getMazeNotExists() {
        int ID = 185187178;
        assertNull(parentApp.mazeData.getMaze(ID));
    }

    @Test
    void deleteMazeValid() {
        Maze maze = new Maze(100, 100, 10, false, "Arrows", "TITLE", "AUTHOR", null, null, null, null);
        int lastID = parentApp.mazeData.addMaze(maze);
        boolean response = parentApp.mazeData.deleteMaze(lastID);
        assertTrue(response);

    }
    @Test
    void deleteMazeNotExists() {
        int ID = 185187178;
        assertFalse(parentApp.mazeData.deleteMaze(ID));
    }





}