package MazeApp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * Class to create and maintain database connection
 */
public class DBConnector {

    /**
     * The database connection instance
     */
    private static Connection inst = null;

    /**
     * Constructor to initialise the connection to db.
     */
    private DBConnector() {
        Properties props = new Properties();
        FileInputStream input;

        try {
            System.out.println(System.getProperty("user.dir"));
            // load the properties file for db and define vars
            input = new FileInputStream( "./db.props");
            props.load(input);
            input.close();
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            String schema = props.getProperty("jdbc.schema");
            System.out.println(url);
            // establish the connection
            inst = DriverManager.getConnection(url + "/" + schema, username, password);
            inst.setAutoCommit(false);

        } catch (FileNotFoundException | SQLException e) {
            System.err.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Global method to use the database connection's instance
     *
     * @return a connection to the single db connection
     */
    public static Connection getInstance() {
        try {
            if (inst == null || inst.isClosed()) {
                new DBConnector();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return inst;
    }

}
