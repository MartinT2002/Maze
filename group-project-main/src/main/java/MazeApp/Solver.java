package MazeApp;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static java.lang.Math.round;

/**
 * Determines the solution to a given MazeOld's cells
 */
public class Solver {
    private final Maze mazeToSolve;
    /**
     * the explored % of maze value
     */
    public double explored;
    Point stop, start;
    int cols, rows;

    /**
     * Solves the maze and displays the solution
     *
     * @param maze the maze object
     */
    public Solver(Maze maze) {
        this.mazeToSolve = maze;
        stop = mazeToSolve.getStop();
        start = mazeToSolve.getStart();
        cols = mazeToSolve.getCols();
        rows = mazeToSolve.getRows();
        checkSolvability();
        mazeToSolve.clearSolution();
        checkDeadEnd();
    }

    /**
     * Checks whether there is a solution to the maze
     *
     * @return true if solvable, otherwise false
     */
    public boolean checkSolvability() {
        MazePoint current, next;

        Maze.CellState[][] maze = mazeToSolve.getMaze();

        Queue<MazePoint> frontier = new LinkedList<>();
        Stack<MazePoint> visited = new Stack<>();
        Queue<MazePoint> solution = new LinkedList<>();
        current = new MazePoint(start.x, start.y, null);
        frontier.add(current);
        // run a while loop when frontier stack is not empty
        while (!frontier.isEmpty()) {
            //take top of the frontier stack as current
            current = frontier.remove();
            //return true if the current cell reaches the final exit
            if (current.x == stop.x - 1 && current.y == stop.y) {
                while (current.getParent() != null) {
                    solution.add(current);
                    maze[current.x][current.y] = Maze.CellState.SOLUTION;
                    current = current.getParent();
                }
                explored = exploredPercentage(solution) * 100;

                return true;
            }

            next = new MazePoint(current.x - 1, current.y, current);
            //checks if cell to the left is a new valid path and add to frontier stack
            if ((next.x >= 0 && next.x < cols) && (next.y >= 0 && next.y < rows) && (maze[current.x - 1][current.y]) == Maze.CellState.PATH && (!visited.contains(next))) {
                frontier.add(next);
            }
            next = new MazePoint(current.x + 1, current.y, current);
            //checks if cell to the right is a new valid path and add to frontier stack
            if ((next.x >= 0 && next.x < cols) && (next.y >= 0 && next.y < rows) && (maze[current.x + 1][current.y] == Maze.CellState.PATH && !visited.contains(next))) {
                frontier.add(next);
            }
            next = new MazePoint(current.x, current.y + 1, current);
            //checks if the cell downwards is a new valid path and add to frontier stack
            if ((next.x >= 0 && next.x < cols) && (next.y >= 0 && next.y < rows) && (maze[current.x][current.y + 1] == Maze.CellState.PATH && !visited.contains(next))) {
                frontier.add(next);
            }
            next = new MazePoint(current.x, current.y - 1, current);
            //checks if the cell upwards is a new valid path and add to frontier stack
            if ((next.x >= 0 && next.x < cols) && (next.y >= 0 && next.y < rows) && (maze[current.x][current.y - 1] == Maze.CellState.PATH && !visited.contains(next))) {
                frontier.add(next);
            }
            //add current cell to visited stack
            visited.push(current);
        }
        return false;
    }

    public double exploredPercentage(Queue<MazePoint> solutionCells) {
        double exploredCells;
        double totalPath = 0;
        int i, j;
        Maze.CellState[][] maze = mazeToSolve.getMaze();
        for (i = 1; i < cols - 1; i += 1) {
            for (j = 1; j < rows - 1; j += 1) {
                if (maze[i][j] == Maze.CellState.PATH || maze[i][j] == Maze.CellState.SOLUTION) {
                    totalPath += 1;
                }
            }
        }
        exploredCells = ((solutionCells.size()) / totalPath);
        return exploredCells;
    }

    public String checkDeadEnd() {
        double totalDeadEnd;
        double totalPath = 0;
        int i, j;
        double deadEnd = 0;
        String deadEndPercentage;

        Maze.CellState[][] maze = mazeToSolve.getMaze();

        for (i = 1; i < cols - 1; i += 1) {
            for (j = 1; j < rows - 1; j += 1) {
                int possibleDeadEnd = 0;
                Maze.CellState thisCell;
                thisCell = maze[i][j];

                if (IsPath(thisCell)) {
                    if (DeadEndPotential(maze[i + 1][j], i + 1, j)) {
                        possibleDeadEnd += 1;
                    }
                    if (DeadEndPotential(maze[i - 1][j], i - 1, j)) {
                        possibleDeadEnd += 1;
                    }
                    if (DeadEndPotential(maze[i][j + 1], i, j + 1)) {
                        possibleDeadEnd += 1;
                    }
                    if (DeadEndPotential(maze[i][j - 1], i, j - 1)) {
                        possibleDeadEnd += 1;
                    }
                    if (possibleDeadEnd == 3) {
                        deadEnd += 1;
                    }
                }
            }
        }

        for (i = 1; i < cols - 1; i += 1) {
            for (j = 1; j < rows - 1; j += 1) {
                if (IsPath(maze[i][j])) {
                    totalPath += 1;
                }
            }
        }

        totalDeadEnd = ((deadEnd - 2) / totalPath) * 100;
        totalDeadEnd = round(totalDeadEnd * 100.0) / 100.0;
        deadEndPercentage = String.valueOf(totalDeadEnd);
        if (totalDeadEnd <= 0) {
            return "0% DEAD END CELLS";
        }
        return "" + deadEndPercentage + "% DEAD END CELLS";
    }

    private boolean DeadEndPotential(Maze.CellState refCell, int col, int row) {

        return IsWall(refCell) && !new Point(col, row).equals(start) && !new Point(col, row).equals(stop);
    }

    private boolean IsNotWall(Maze.CellState cell) {
        return cell == Maze.CellState.PATH || cell == Maze.CellState.LOGOCELL || cell == Maze.CellState.LOGOCELL2 || cell == Maze.CellState.SOLUTION;
    }

    private boolean IsWall(Maze.CellState cell) {
        return !IsNotWall(cell);
    }

    private boolean IsPath(Maze.CellState cell) {
        return IsNotWall(cell);
    }

}
