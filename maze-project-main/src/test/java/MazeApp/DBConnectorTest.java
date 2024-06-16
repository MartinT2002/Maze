package MazeApp;

import org.junit.jupiter.api.*;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DBConnectorTest {

    Connection connection;
    public DBMazeDataSource mazeData;


    @Test
    public void getInstance() {
        connection = DBConnector.getInstance();
    }

    @Test
    public void DBMazeDataSource_SetUp() {
        mazeData = new DBMazeDataSource();
        File f = new File("./maze-app.db");
        assertTrue(f.exists());
    }

    @Test
    public void DBMazeDataSource_Close() {
        connection = DBConnector.getInstance();
        mazeData = new DBMazeDataSource();
        mazeData.close();
        try {
            assertEquals(true, connection.isClosed());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}