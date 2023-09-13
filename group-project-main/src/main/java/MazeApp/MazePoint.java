package MazeApp;

import java.awt.*;

/**
 * MazePoint is an extension of Point, including a reference to a parent Point for backtracking.
 * source - <a href="https://stackoverflow.com/questions/16366448/maze-solving-with-breadth-first-search">link</a>
 * modified to be more efficient
 */
public class MazePoint extends Point {
    private final MazePoint parent;

    /**
     * @param x      the x coordinate
     * @param y      the y coordinate
     * @param parent the parent Point
     */
    public MazePoint(int x, int y, MazePoint parent) {
        super(x, y);
        this.parent = parent;
    }

    /**
     * Gets parent Point
     *
     * @return Parent Point
     */
    public MazePoint getParent() {
        return this.parent;
    }

}
