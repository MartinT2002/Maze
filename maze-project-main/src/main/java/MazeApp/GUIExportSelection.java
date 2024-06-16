package MazeApp;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.logging.Logger;

/**
 * GUI for Image Export folder location dialogs
 */
public class GUIExportSelection extends JFrame{
    private String imageLocation;


    /**
     * Constructor for Image Export Location - initialises all components and displays dialog
     */
    public GUIExportSelection() {
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUIExportSelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(GUIExportSelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GUIExportSelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GUIExportSelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        setLocationRelativeTo(null);
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("."));
        fc.setDialogTitle("Select Save Location");
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.showOpenDialog(null);


        if (fc.getSelectedFile() != null) {
            imageLocation = fc.getSelectedFile().getAbsolutePath();
        }
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public String getSaveFile() {

        return imageLocation;
    }
}