package MazeApp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import java.awt.*;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ExporterTest {

    private static boolean isReallyHeadless() {
        if (GraphicsEnvironment.isHeadless()) {
            return true;
        }
        else {
            return false;
        }
    }
    // this test will only run on a local environment that shows a GUI
    @Test
    @DisabledIf("isReallyHeadless")
    void exportMaze() {
        App app = new App();
        Maze maze = new Maze(400, 400, 40, true, "Arrows", "AUTOR", "TITLE", null, null, null, null);

        GUIMazeViewer mazeViewer = new GUIMazeViewer(app, maze);
        mazeViewer.display();
        new Exporter(mazeViewer).exportMaze(maze.getTitle(), "./");

        File f = new File("./" + maze.getTitle() + ".jpeg");
        assertTrue(f.exists());

        maze = null;
    }
}