package MazeApp;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Allows user to export mazes to the filesystem
 */
public class Exporter {

    GUIMazeViewer mazePanel;

    /**
     * Shows GUI for Exporter
     *
     * @param mazePanel the maze loaded into the exporter
     */
    public Exporter(GUIMazeViewer mazePanel) {

        // more to come
        this.mazePanel = mazePanel;
    }


    /**
     * exports the maze as an image
     * @param mazeTitle maze title
     * @param fileLocation folder to save file in
     */
    public void exportMaze(String mazeTitle, String fileLocation) {
        // get panel – the screen currently displaying the maze.
        BufferedImage imagebuf = null;
        try {
            imagebuf = new Robot().createScreenCapture(mazePanel.getBounds());
        } catch (AWTException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Graphics2D graphics2D = null;
        if (imagebuf != null) {
            graphics2D = imagebuf.createGraphics();
        }
        mazePanel.paint(graphics2D);
        try {
            if (imagebuf != null) {
                ImageIO.write(imagebuf, "jpeg", new File(fileLocation+ "\\"+ mazeTitle+".jpeg"));
            }
        } catch (Exception e) {
            //System.out.println("error");
        }
    }
}

