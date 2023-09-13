package MazeApp;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.logging.Logger;

/**
 * GUI for Image Selection dialogs
 */
public class GUIImageSelection extends JFrame {
    private String imageLocation;
    private String guiHeader;

    /**
     * Constructor for Image Selector - initialises all components and displays dialog
     *
     * @param imageType type of image permitted
     */
    public GUIImageSelection(String imageType) {
        guiHeader = imageType;
        initComponents();
    }

    private void initComponents() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GUIMazeSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        setLocationRelativeTo(null);
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("resources"));
        fc.setDialogTitle(guiHeader);
        fc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG images", "jpg");
        fc.addChoosableFileFilter(filter);
        fc.showOpenDialog(null);


        if (fc.getSelectedFile() != null) {
            imageLocation = fc.getSelectedFile().getAbsolutePath();
        }
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * getter for imageLocation
     *
     * @param imageType image format permitted
     * @return the image location
     */
    public String getLocation(String imageType) {
        guiHeader = imageType;
        return imageLocation;
    }
}
