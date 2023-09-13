package MazeApp;

import java.awt.*;
import java.util.Stack;

/**
 * Generator class is used to automatically generate mazes of a select size.
 */
public class Generator {
    private final int cols;
    private final int rows;
    private final Point start;

    private final Maze.CellState[][] maze;


    /**
     * Generates a maze of specified size
     *
     * @param maze semi-completed maze for generating onto
     */
    public Generator(Maze maze) {
        this.maze = maze.getMaze();
        this.start = maze.getStart();
        this.cols = maze.getCols();
        this.rows = maze.getRows();

    }


    /**
     * Runs generation for the maze
     *
     * @return completely generated maze
     */
    public Maze.CellState[][] generate() {
        Point current, next;
        Stack<Point> history = new Stack<>();

        int nToVisit = (cols - 1) * (rows - 1) / 4;
        //int nToVisit = (cols) * (rows) / 4;
        int nVisited = 1;

        current = new Point(start.x + 1, start.y);
        maze[current.x][current.y] = Maze.CellState.PATH;

        while (nVisited < nToVisit) {
            next = checkNext(current);

            if (next != null) {

                int x = (current.x + next.x) / 2;
                int y = (current.y + next.y) / 2;
                maze[x][y] = Maze.CellState.PATH;

                history.push(current);
                current = next;
                maze[current.x][current.y] = Maze.CellState.PATH;

                nVisited++;
            } else if (!history.empty()) {
                current = history.pop();
            }
        }

        return maze;

    }

    /**
     * check around for another cell to go on
     *
     * @param current -> current cell
     * @return returns a random valid next cell Point, or null if none.
     */
    private Point checkNext(Point current) {

        final int n = 4; // number of neighbors

        // the options of cells
        Point[] options = {new Point(current.x, current.y + 2), new Point(current.x, current.y - 2),
                new Point(current.x + 2, current.y), new Point(current.x - 2, current.y)};

        boolean[] goodIndices = new boolean[n]; // the options
        int nGood = 0; // number of good

        for (int i = 0; i < n; i++) {
            Point c = options[i];

            boolean good = c.x >= 0 && c.x < cols && c.y >= 0 && c.y < rows && maze[c.x][c.y] == Maze.CellState.EMPTY;
            //boolean good = c.x >= 0 && c.x < cols+1 && c.y >= 0 && c.y < rows+1 && maze[c.x][c.y] == target;
            goodIndices[i] = good;

            if (good)
                nGood++;
        }

        if (nGood == 0)
            return null; // if there are no neighbors

        int rand = (int) (Math.random() * n);
        while (!goodIndices[rand]) {
            rand = (int) (Math.random() * n);
        }

        return options[rand]; // return the random neighbor

    }
}
