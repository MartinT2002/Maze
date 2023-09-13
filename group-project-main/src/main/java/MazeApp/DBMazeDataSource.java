package MazeApp;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;


/**
 * Class which stores all database queries for managing the MazeOld application
 */
public class DBMazeDataSource implements IMazeDataSource {

    private static final String QRY_CREATE_DATABASE =
            "CREATE TABLE IF NOT EXISTS `mazes` ("
                    + "`ID`	INTEGER NOT NULL UNIQUE,"
                    + "`name`	TEXT,"
                    + "`author`	TEXT,"
                    + "`datelastupdated`	TEXT DEFAULT current_timestamp,"
                    + "`datecreated`	TEXT DEFAULT current_timestamp,"
                    + "`width`	INTEGER,"
                    + "`height`	INTEGER,"
                    + "`cellsize`	INTEGER,"
                    + "`type`	TEXT,"
                    + "`cells`	BLOB,"
                    + "`image_start`	BLOB,"
                    + "`image_stop`	BLOB,"
                    + "`image_logo1`	BLOB,"
                    + "`image_logo2`	BLOB,"
                    + "PRIMARY KEY(`ID` AUTOINCREMENT)"
                    + ");";
    /**
     * Query list - each query needs a string, and prepared statement and is set in the constructor.
     * referenced week 6 tutorial/lecture materials
     */
    private static final String QRY_GET_MAZE = "SELECT * FROM mazes WHERE id=?";
    private static final String QRY_INSERT_MAZE = "INSERT INTO mazes (name, author, width, height, cellsize, type, cells, image_start, image_stop, image_logo1, image_logo2, datecreated, datelastupdated) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    //private static final String QRY_INSERT_DUPE_MAZE = "INSERT INTO mazes (name, author, datecreated, datelastupdated, width, height, cellsize, type, cells, image_start, image_stop, image_logo1, image_logo2) SELECT name, author, datecreated, datelastupdated, width, height, cellsize, type, cells, image_start, image_stop, image_logo1, image_logo2 FROM mazes WHERE ID = ?";
    private static final String QRY_UPDATE_MAZE = "UPDATE mazes SET name=?, author=?, width=?, height=?, cellsize=?, type=?, cells=?, image_start=?, image_stop=?, image_logo1=?, image_logo2=?, datecreated=?, datelastupdated=? WHERE id = ?";
    private static final String QRY_GET_LAST_ID = "Select last_insert_rowid()";
    private static final String QRY_DELETE_MAZE = "DELETE FROM mazes WHERE ID = ?";

    private final Connection connection;
    private PreparedStatement PS_GET_MAZE, PS_INSERT_MAZE, PS_GET_LAST_ID, PS_UPDATE_MAZE, PS_DELETE_MAZE, PS_INSERT_DUPE_MAZE;

    /**
     * Constructor - runs table set up query and prepares others for use.
     */
    public DBMazeDataSource() {
        connection = DBConnector.getInstance();
        // run/set up queries
        try {
            Statement createDB = connection.createStatement();
            createDB.execute(QRY_CREATE_DATABASE);
            PS_GET_MAZE = connection.prepareStatement(QRY_GET_MAZE);
            PS_INSERT_MAZE = connection.prepareStatement(QRY_INSERT_MAZE);
            //PS_INSERT_DUPE_MAZE = connection.prepareStatement(QRY_INSERT_DUPE_MAZE);
            PS_UPDATE_MAZE = connection.prepareStatement(QRY_UPDATE_MAZE);
            PS_GET_LAST_ID = connection.prepareStatement(QRY_GET_LAST_ID);
            PS_DELETE_MAZE = connection.prepareStatement(QRY_DELETE_MAZE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds Maze to Database
     *
     * @param maze the full maze object to insert
     * @return int of inserted maze id, or zero (0) if failed
     */
    public int addMaze(Maze maze) {

        int lastID = 0;
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);

            // insert the data
            PS_INSERT_MAZE.clearParameters();
            PS_INSERT_MAZE.setString(1, maze.getTitle());
            PS_INSERT_MAZE.setString(2, maze.getAuthor());
            PS_INSERT_MAZE.setInt(3, maze.getWidth());
            PS_INSERT_MAZE.setInt(4, maze.getHeight());
            PS_INSERT_MAZE.setInt(5, maze.getCellSize());
            PS_INSERT_MAZE.setString(6, maze.getMazeType());

            // serialize the MazeCells and create a byte array for storage
            objectStream.writeObject(maze.getMaze());
            byte[] data = byteStream.toByteArray();
            PS_INSERT_MAZE.setBinaryStream(7, new ByteArrayInputStream(data), data.length);


            if (maze.getStartImagePath() != null) {
                byteStream = new ByteArrayOutputStream();
                ImageIO.write(maze.getStartImagePath(), "jpg", byteStream);
                byte[] data2 = byteStream.toByteArray();
                PS_INSERT_MAZE.setBinaryStream(8, new ByteArrayInputStream(data2), data2.length);
            } else {
                PS_INSERT_MAZE.setNull(8, java.sql.Types.BLOB);
            }
            if (maze.getEndImagePath() != null) {
                byteStream = new ByteArrayOutputStream();
                ImageIO.write(maze.getEndImagePath(), "jpg", byteStream);
                byte[] data3 = byteStream.toByteArray();
                PS_INSERT_MAZE.setBinaryStream(9, new ByteArrayInputStream(data3), data3.length);
            } else {
                PS_INSERT_MAZE.setNull(9, java.sql.Types.BLOB);
            }
            if (maze.getImageLogo1() != null) {
                byteStream = new ByteArrayOutputStream();
                ImageIO.write(maze.getImageLogo1(), "jpg", byteStream);
                byte[] data4 = byteStream.toByteArray();
                PS_INSERT_MAZE.setBinaryStream(10, new ByteArrayInputStream(data4), data4.length);
            } else {
                PS_INSERT_MAZE.setNull(10, java.sql.Types.BLOB);
            }

            if (maze.getImageLogo2() != null) {
                byteStream = new ByteArrayOutputStream();
                ImageIO.write(maze.getImageLogo2(), "jpg", byteStream);
                byte[] data5 = byteStream.toByteArray();
                PS_INSERT_MAZE.setBinaryStream(11, new ByteArrayInputStream(data5), data5.length);
            } else {
                PS_INSERT_MAZE.setNull(11, java.sql.Types.BLOB);
            }

            PS_INSERT_MAZE.setString(12, maze.getCreatedTime());
            PS_INSERT_MAZE.setString(13, maze.getCreatedTime());

            PS_INSERT_MAZE.executeUpdate();

            lastID = PS_GET_LAST_ID.executeQuery().getInt(1);

            connection.commit();

            //connection.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return lastID;
    }

    /**
     * Gets the maze list from the database
     *
     * @return Object multidimensional array of all mazes from the database. Note: 100 max.
     */
    public Object[][] getMazeList() {
        Statement delete;
        String SQL = ("SELECT ID, name, author, datelastupdated, datecreated, cellsize  FROM mazes");
        ResultSet result;
        ResultSetMetaData resultSet;
        Object[][] allMazesDataArray = new Object[100][6];
        int columnsNumber;
        try {
            // create a Statement from the connection
            delete = connection.createStatement();

            // insert the data

            result = delete.executeQuery(SQL);
            resultSet = result.getMetaData();
            columnsNumber = resultSet.getColumnCount();
            int counter = 0;
            while (result.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    allMazesDataArray[counter][i - 1] = result.getString(i);

                }
                counter += 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return allMazesDataArray;
    }

    /**
     * Updates Maze in Database
     *
     * @param maze the full maze object to update
     * @return boolean depending on successful result
     */
    public boolean updateMaze(Maze maze) {

        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);

            // insert the data
            PS_UPDATE_MAZE.clearParameters();
            PS_UPDATE_MAZE.setString(1, maze.getTitle());
            PS_UPDATE_MAZE.setString(2, maze.getAuthor());
            PS_UPDATE_MAZE.setInt(3, maze.getWidth());
            PS_UPDATE_MAZE.setInt(4, maze.getHeight());
            PS_UPDATE_MAZE.setInt(5, maze.getCellSize());
            PS_UPDATE_MAZE.setString(6, maze.getMazeType());


            // serialize the MazeCells and create a byte array for storage
            objectStream.writeObject(maze.getMaze());
            byte[] data = byteStream.toByteArray();
            PS_UPDATE_MAZE.setBinaryStream(7, new ByteArrayInputStream(data), data.length);
            if (maze.getStartImagePath() != null) {
                byteStream = new ByteArrayOutputStream();
                ImageIO.write(maze.getStartImagePath(), "jpg", byteStream);
                byte[] data2 = byteStream.toByteArray();
                PS_UPDATE_MAZE.setBinaryStream(8, new ByteArrayInputStream(data2), data2.length);
            } else {
                PS_UPDATE_MAZE.setNull(8, java.sql.Types.BLOB);
            }
            if (maze.getEndImagePath() != null) {
                byteStream = new ByteArrayOutputStream();
                ImageIO.write(maze.getEndImagePath(), "jpg", byteStream);
                byte[] data3 = byteStream.toByteArray();
                PS_UPDATE_MAZE.setBinaryStream(9, new ByteArrayInputStream(data3), data3.length);
            } else {
                PS_UPDATE_MAZE.setNull(9, java.sql.Types.BLOB);
            }
            if (maze.getImageLogo1() != null) {
                byteStream = new ByteArrayOutputStream();
                ImageIO.write(maze.getImageLogo1(), "jpg", byteStream);
                byte[] data4 = byteStream.toByteArray();
                PS_UPDATE_MAZE.setBinaryStream(10, new ByteArrayInputStream(data4), data4.length);
            } else {
                PS_UPDATE_MAZE.setNull(10, java.sql.Types.BLOB);
            }
            if (maze.getImageLogo2() != null) {
                byteStream = new ByteArrayOutputStream();
                ImageIO.write(maze.getImageLogo2(), "jpg", byteStream);
                byte[] data5 = byteStream.toByteArray();
                PS_UPDATE_MAZE.setBinaryStream(11, new ByteArrayInputStream(data5), data5.length);
            } else {
                PS_UPDATE_MAZE.setNull(11, java.sql.Types.BLOB);
            }

            PS_INSERT_MAZE.setString(12, maze.getCreatedTime());
            PS_INSERT_MAZE.setString(13, maze.getCreatedTime());

            PS_UPDATE_MAZE.setInt(14, maze.getMazeID());

            connection.commit();

            PS_UPDATE_MAZE.executeUpdate();

            //connection.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Deletes Maze from Database
     *
     * @param ID the ID of the maze to delete
     * @return boolean depending on successful result
     */
    public boolean deleteMaze(int ID) {
        try {

            // insert the data
            PS_DELETE_MAZE.clearParameters();
            PS_DELETE_MAZE.setInt(1, ID);
            int count = PS_DELETE_MAZE.executeUpdate();

            connection.commit();
            if(count == 1) {
                return true;
            }
            else {
                return false;
            }
            //connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Duplicates Maze into Database
     *
     * @param ID the ID of the maze to duplicate
     * @return boolean depending on successful result
     */
    /*public boolean duplicateMaze(String ID) {
        try {
            PS_INSERT_DUPE_MAZE.clearParameters();
            PS_INSERT_DUPE_MAZE.setString(1, ID);
            PS_INSERT_DUPE_MAZE.executeUpdate();

            //connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }*/

    /**
     * Retrieves single maze from the DB
     *
     * @param ID the ID of the maze to retrieve
     * @return Maze retrieved from the database
     */
    public Maze getMaze(int ID) {

        BufferedImage startImageLocation, endImageLocation, logoLocation, logoLocation2;
        try {

            // insert the data
            PS_GET_MAZE.clearParameters();
            PS_GET_MAZE.setInt(1, ID);

            ResultSet resultSet = PS_GET_MAZE.executeQuery();

            if (resultSet.next()) {
                int width = resultSet.getInt("width");
                int height = resultSet.getInt("height");
                int size = resultSet.getInt("cellsize");
                byte[] data = resultSet.getBytes("cells");
                ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
                ObjectInputStream objectStream = new ObjectInputStream(byteStream);
                Maze.CellState[][] mazeCells = (Maze.CellState[][]) objectStream.readObject();

                byte[] data_startimage = resultSet.getBytes("image_start");
                if (data_startimage != null) {
                    ByteArrayInputStream byteStream_startimage = new ByteArrayInputStream(data_startimage);
                    startImageLocation = ImageIO.read(byteStream_startimage);
                } else {
                    startImageLocation = null;
                }
                byte[] data_endimage = resultSet.getBytes("image_stop");
                if (data_endimage != null) {
                    ByteArrayInputStream byteStream_endimage = new ByteArrayInputStream(data_endimage);
                    endImageLocation = ImageIO.read(byteStream_endimage);
                } else {
                    endImageLocation = null;
                }
                byte[] data_logoimage = resultSet.getBytes("image_logo1");
                if (data_logoimage != null) {
                    ByteArrayInputStream byteStream_logoimage = new ByteArrayInputStream(data_logoimage);
                    logoLocation = ImageIO.read(byteStream_logoimage);
                } else {
                    logoLocation = null;
                }
                byte[] data_logoimage2 = resultSet.getBytes("image_logo2");
                if (data_logoimage2 != null) {
                    ByteArrayInputStream byteStream_logoimage2 = new ByteArrayInputStream(data_logoimage2);
                    logoLocation2 = ImageIO.read(byteStream_logoimage2);
                } else {
                    logoLocation2 = null;
                }
                String mazeType = resultSet.getString("type");
                String author = resultSet.getString("author");
                String title = resultSet.getString("name");
                return new Maze(width, height, size, ID, mazeCells, mazeType, author, title, logoLocation, logoLocation2, startImageLocation, endImageLocation);
            } else {
                return null;
            }
            //connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Safely closes connection to ensure data is saved
     */
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
