package MazeApp;

import javax.swing.*;
import java.awt.*;

/**
 * Main App GUI components
 */
public class App {
    /**
     * The primary maze data source object - referred to in database transactions by all components
     */
    public DBMazeDataSource mazeData;

    /**
     * MazeApp.App constructor - starts GUI
     */
    public App() {
        this.mazeData = new DBMazeDataSource();
        try {
        this.ViewMainMenu();
    }
        catch(HeadlessException e) {
        // okay - only in non-gui environments -- if we do anything here the app will hang
            System.out.println("Headless mode is not supported by this application. Please use a Java enviornment that has a screen.");
            //System.exit(1);
    }
    }

    /**
     * main function called when application starts
     *
     * @param args command line arguments (not implemented)
     */
    public static void main(String[] args) {
        App app = new App();
    }

    /**
     * starts GUI for Main Menu
     */
    public void ViewMainMenu() {

            //SwingUtilities.invokeLater(new GUIMainMenu(this));
            new GUIMainMenu(this);

    }

    /**
     * starts GUI for Index
     */
    public void ViewIndex() {

            SwingUtilities.invokeLater(new GUIMazeIndex(this));
            //new GUIMazeIndex(this);

    }

    /**
     * starts GUI for Main Menu
     */
    public void CreateNew() {
        SwingUtilities.invokeLater(new GUIMazeSettings(this));
    }

}
