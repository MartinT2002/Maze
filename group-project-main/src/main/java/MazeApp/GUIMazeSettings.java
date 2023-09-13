package MazeApp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * GUI for Maze Creation Settings<br />
 * This menu was built using source code provided by the NETBEANS interactive GUI builder<br />
 * With appropriate changes made as required
 */


public class GUIMazeSettings extends JFrame implements Runnable {

    // Variables declaration - do not modify
    int widthInput;
    int cellSizeInput;
    int heightInput;
    boolean autogenInput;
    App parentApp;

    String startEndSelection, authorInput, titleInput;
    JButton jButtonSubmit = new JButton();
    JButton jLogoSelect = new JButton();
    private String logoLocation;
    private String startImageLocation;
    private String endImageLocation;
    private JComboBox<String> jComboBoxType;
    private JLabel jLabelWidth, jLabelHeight, jLabelCellSize, jLabelHeading, jLabelType, jLabelTitle, jLabelAuthor, jLabelBrand, jLabelAutoGenerate, jLabelLogoSelection;
    private JTextField jTextField1, jTextField2, jTextField3, jTextField4, jTextField5;
    private JCheckBox jAutoGenerate = new JCheckBox();

    private JButton jButton1;
    private JButton jButton2;
    private JComboBox<String> jComboBox1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;

    private BufferedImage logoImageBuffered;
    private BufferedImage startImageBuffered;
    private BufferedImage endImageBuffered;

    // End of variables declaration


    /**
     * Constructor for GUI Maze Settings
     *
     * @param parent the calling App object
     */
    public GUIMazeSettings(App parent) {
        this.parentApp = parent;
        initComponents();
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUIMazeSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(GUIMazeSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GUIMazeSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GUIMazeSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>


        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jAutoGenerate = new javax.swing.JCheckBox();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        jLabel1.setText("Columns (5 - 100):");

        jLabel2.setText("Rows (5 - 100);");


        jButton1.setBackground(new java.awt.Color(168, 22, 22));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("CREATE MAZE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("CREATE NEW MAZE");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Arrows", "Openings", "Images"}));


        jLabel5.setText("Start/End Type:");
        jLabel6.setText("Title:");
        jLabel7.setText("Author:");
        jLabel8.setText("MazeCo Designer");
        jLabel9.setText("Insert Logo (Optional):");

        jButton2.setBackground(new java.awt.Color(66, 135, 245));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("LOGO");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Auto-Generate Maze?");


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                                                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addGap(18, 18, 18)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(jComboBox1, 0, 126, Short.MAX_VALUE)
                                                                .addComponent(jTextField2)
                                                                .addComponent(jTextField1)))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel9)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addGap(14, 14, 14))
                                                                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(2, 2, 2)
                                                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jAutoGenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                                                        .addComponent(jTextField4))))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(2, 2, 2)
                                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(jLabel8))
                                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(jButton2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel7)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jAutoGenerate))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        GUIImageSelection newWind = new GUIImageSelection("Select Logo");
        logoLocation = newWind.getLocation("Logo Location");

    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        boolean incorrectInput = false;
        boolean widthError = false;
        boolean heightError = false;
        String nullInput = "";
        titleInput = jTextField4.getText();
        if (titleInput.equals(nullInput)) {
            JOptionPane.showMessageDialog(null, "Maze needs a title!");
            incorrectInput = true;
        }
        authorInput = jTextField5.getText();
        if (authorInput.equals(nullInput)) {
            JOptionPane.showMessageDialog(null, "Maze needs an author!");
            incorrectInput = true;
        }
        try {
            widthInput = Integer.parseInt(jTextField1.getText());

        } catch (NumberFormatException e) {
            incorrectInput = true;
            widthError = true;
        }
        try {
            heightInput = Integer.parseInt(jTextField2.getText());
        } catch (NumberFormatException e) {
            incorrectInput = true;
            heightError = true;
        }

        if (widthInput < 5 || widthInput > 100) {
            incorrectInput = true;
            widthError = true;
        }
        if (heightInput < 5 || heightInput > 100) {
            incorrectInput = true;
            heightError = true;
        }
        if (widthError) {
            JOptionPane.showMessageDialog(null, "Column input is incorrect.\nValue between 5-100 needed");
        }
        if (heightError) {
            JOptionPane.showMessageDialog(null, "Row input is incorrect.\nValue between 5-100 needed");
        }
        if (incorrectInput == true) {
            return;
        }
        startEndSelection = (String) jComboBox1.getSelectedItem();
        /**
         * If the start end selection is images, then the user needs to input their chosen images
         */

        if (startEndSelection == "Images") {
            GUIImageSelection newWind = new GUIImageSelection("Start Image");
            String nullTest = newWind.getLocation("Start Image");
            if (nullTest == null) {
                JOptionPane.showMessageDialog(null,
                        "Must select an Start image\n for image Start type.",
                        "Select Image",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            startImageLocation = nullTest;
            try {
                startImageBuffered = ImageIO.read(new File(startImageLocation));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            GUIImageSelection nextImage = new GUIImageSelection("End Image");
            String secondNullTest = nextImage.getLocation("End Image");
            if (secondNullTest == null) {
                JOptionPane.showMessageDialog(null,
                        "Must select an End image\n for image End type.",
                        "Select Image",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            endImageLocation = secondNullTest;
            try {
                endImageBuffered = ImageIO.read(new File(endImageLocation));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        autogenInput = jAutoGenerate.isSelected();


        if (logoLocation != null) {
            try {
                logoImageBuffered = ImageIO.read(new File(logoLocation));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        GUIMazeViewer newWind = new GUIMazeViewer(parentApp, widthInput, heightInput, autogenInput, startEndSelection, titleInput, authorInput, logoImageBuffered, logoImageBuffered, startImageBuffered, endImageBuffered);
        newWind.display();

        this.dispose();
    }


    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

    }
}

