package MazeApp;

/**
 * Provides all Maze DB functionality required by the app
 */
public interface IMazeDataSource {

    /**
     * Adds Maze to Database
     *
     * @param maze the full maze object to insert
     * @return int of ID insert maze, or 0 if failed
     */
    int addMaze(Maze maze);

    /**
     * Updates Maze in Database
     *
     * @param maze the full maze object to update
     * @return boolean depending on successful result
     */
    boolean updateMaze(Maze maze);

    /**
     * Deletes Maze from Database
     *
     * @param ID the ID of the maze to delete
     * @return boolean depending on successful result
     */
    boolean deleteMaze(int ID);

    /**
     * Retrieves single maze from the DB
     *
     * @param ID the ID of the maze to retrieve
     * @return requested maze object
     */
    Maze getMaze(int ID);

    /**
     * Retrieves partial Mazes from DB
     * Note this is a basic list of names/IDs only for the index. Use {@link #getMaze(int) getMaze()} to obtain a complete object.
     *
     * @return Object array of Mazes from the DB.
     */
    Object[][] getMazeList();

    /**
     * Safely closes connection to ensure data is saved
     */
    void close();

}
