package MazeApp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

/**
 * GUI for Maze Viewing - handles input and drawing<br />
 * This menu was built using sources code provided by the NETBEANS interactive GUI builder
 */
public class GUIMazeViewer extends JPanel {


    private final int rightMenuWidth = 0;
    private final boolean AUTOGENERATE;
    private final String GAMETYPE;
    private final String AUTHOR;
    private final String TITLE;
    private final Maze maze; // maze
    private final App parentApp;
    private final Solver solver;
    private int WIDTH;// = GUIMazeSettings.widthInput;// width in pixels taken from user input
    private int HEIGHT;// = GUIMazeSettings.heightInput; // height in pixels taken from user input
    private int CELLSIZE;//  = GUIMazeSettings.cellSizeInput; // size of cells in maze
    private boolean anotherImage;
    private BufferedImage newImageLocation;
    private boolean isSolvable;
    private Point clickDragLast;
    private Maze.CellState clickDragState;
    private JCheckBox jDisplaySolution;
    private JLabel solveIndicator;
    private JLabel jLabelPercentageExplored, jLabelPercentageDeadEnds;
    private JFrame windowFrame;


    /**
     * Constructor for an entirely new maze object
     *
     * @param parent       the calling App object
     * @param COLS         number of columns in the maze
     * @param ROWS         number of rows in the maze
     * @param autoGenerate toggle for running generator on creation
     * @param GAMETYPE     end types for the maze (from dropdown selection)
     * @param AUTHOR       author's name
     * @param TITLE        title of maze
     * @param LOGOIMAGE1   logo image 1
     * @param LOGOIMAGE2   logo image 2
     * @param STARTIMAGE   starting image - only valid where GAMETYPE is images
     * @param ENDIMAGE     ending image - only valid where GAMETYPE is images
     */
    public GUIMazeViewer(App parent, int COLS, int ROWS, boolean autoGenerate, String GAMETYPE, String AUTHOR, String TITLE, BufferedImage LOGOIMAGE1, BufferedImage LOGOIMAGE2, BufferedImage STARTIMAGE, BufferedImage ENDIMAGE) {
        // Determine the size of the cells according the size of the maze

        if (COLS > 75 || ROWS > 75) {
            this.WIDTH = COLS * 10;
            this.HEIGHT = ROWS * 10;
            this.CELLSIZE = 10;
        } else if (COLS > 50 || ROWS > 50) {
            this.WIDTH = COLS * 20;
            this.HEIGHT = ROWS * 20;
            this.CELLSIZE = 20;
        } else if (COLS > 25 || ROWS > 25) {
            this.WIDTH = COLS * 30;
            this.HEIGHT = ROWS * 30;
            this.CELLSIZE = 30;
        } else {
            this.WIDTH = COLS * 40;
            this.HEIGHT = ROWS * 40;
            this.CELLSIZE = 40;
        }

        this.AUTOGENERATE = autoGenerate;
        this.GAMETYPE = GAMETYPE;
        this.AUTHOR = AUTHOR;
        this.TITLE = TITLE;
        this.parentApp = parent;


        maze = new Maze(WIDTH, HEIGHT, CELLSIZE, AUTOGENERATE, GAMETYPE, TITLE, AUTHOR, LOGOIMAGE1, LOGOIMAGE2, STARTIMAGE, ENDIMAGE);
        this.WIDTH = maze.getCols() * maze.getCellSize();
        this.HEIGHT = maze.getRows() * maze.getCellSize();
        this.solver = new Solver(maze);

        canvasSetup();
    }

    /**
     * Constructor for an existing maze object
     *
     * @param parent the calling App object
     * @param maze   existing maze object
     */
    public GUIMazeViewer(App parent, Maze maze) {
        this.parentApp = parent;
        this.maze = maze;
        this.AUTOGENERATE = false;
        this.GAMETYPE = maze.getMazeType();
        this.AUTHOR = maze.getAuthor();
        this.TITLE = maze.getTitle();
        this.WIDTH = maze.getCols() * maze.getCellSize();
        this.HEIGHT = maze.getRows() * maze.getCellSize();
        this.solver = new Solver(maze);

        canvasSetup();
    }

    /**
     * just to setup the canvas to our desired settings and sizes
     */
    private void canvasSetup() {

        this.setPreferredSize(new Dimension(WIDTH + rightMenuWidth, HEIGHT));
        this.setMaximumSize(new Dimension(WIDTH + rightMenuWidth, HEIGHT));
        this.setMinimumSize(new Dimension(WIDTH + rightMenuWidth, HEIGHT));

        this.setFocusable(true);

        this.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mousePressed(MouseEvent e) {

                int col = Math.abs(e.getX() / maze.getCellSize());
                int row = Math.abs(e.getY() / maze.getCellSize());
                if (isRightMouseButton(e)) {
                    if (e.getX() <= WIDTH && e.getY() <= HEIGHT) {
                        Maze.CellState cellstate = maze.getMaze()[col][row];
                        updateImageState(col, row, cellstate);

                    }


                } else if (isLeftMouseButton(e)) {
                    if (e.getX() <= WIDTH && e.getY() <= HEIGHT) {
                        Maze.CellState cellstate = maze.getMaze()[col][row];
                        clickDragLast = new Point(col, row);

                        super.mousePressed(e);
                        updateCellState(col, row, cellstate);

                    }
                }


            }

            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                clickDragState = null;
                // After changes have been made, update the display of if the maze is solvable
                boolean isSolvable = solver.checkSolvability();
                if (isSolvable) {
                    solveIndicator.setBackground(new Color(0x54FF00));
                } else {
                    solveIndicator.setBackground(new Color(0xFF0000));
                }
                maze.clearSolution();
                getParent().repaint();

                //update explored label
                double exploredPercentage = solver.explored;
                jLabelPercentageExplored.setText(String.format("%.0f", exploredPercentage) + "% EXPLORED CELLS");

                //update dead end label
                String deadEndPercentage = solver.checkDeadEnd();
                jLabelPercentageDeadEnds.setText(deadEndPercentage);
                getParent().repaint();
                boolean showSolution = jDisplaySolution.isSelected();
                if (showSolution) {
                    solver.checkSolvability();
                    getParent().repaint();
                } else {
                    maze.clearSolution();
                    getParent().repaint();
                }
            }

        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                int col = Math.abs(e.getX() / maze.getCellSize());
                int row = Math.abs(e.getY() / maze.getCellSize());
                if (e.getX() <= WIDTH && e.getY() <= HEIGHT && clickDragState != null) {
                    Point clickDragNow = new Point(col, row);
                    if (clickDragNow != clickDragLast) {
                        clickDragLast = clickDragNow;
                        Maze.CellState oldState = maze.getMaze()[col][row];
                        updateCellState(col, row, oldState, clickDragState);
                    }

                }
            }
        });


    }

    private void updateImageState(int col, int row, Maze.CellState oldState) {
        if (oldState == Maze.CellState.LOGOCELL || oldState == Maze.CellState.LOGOCELL2) {
            // remove the image
            maze.setMazeCell(col, row, Maze.CellState.WALL);
            //maze.clearSolution();
            getParent().repaint();
        } else if (oldState == Maze.CellState.WALL) {
            if (newImageLocation == null) {
                if (oldState == Maze.CellState.WALL) {
                    maze.setMazeCell(col, row, Maze.CellState.LOGOCELL);
                    //maze.clearSolution();
                    getParent().repaint();
                }
            } else if (newImageLocation != null) {
                maze.setImageLogo1(newImageLocation);
                maze.setMazeCell(col, row, Maze.CellState.LOGOCELL2);
                //maze.clearSolution();
                getParent().repaint();
            }
        }
    }

    private void updateCellState(int col, int row, Maze.CellState oldState) {
        switch (oldState) {
            case EMPTY, PATH, SOLUTION -> {
                maze.setMazeCell(col, row, Maze.CellState.WALL);
                clickDragState = Maze.CellState.WALL;
            }
            case WALL -> {
                maze.setMazeCell(col, row, Maze.CellState.PATH);
                clickDragState = Maze.CellState.PATH;
            }
        }
        maze.clearSolution();
        //maze.solve(); //---- keeps breaking
        getParent().repaint();
    }

    private void updateCellState(int col, int row, Maze.CellState oldState, Maze.CellState newState) {
        switch (oldState) {
            case EMPTY, PATH, SOLUTION, WALL -> maze.setMazeCell(col, row, newState);
        }
        maze.clearSolution();
        //maze.solve(); //---- keeps breaking
        getParent().repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        // Draw the background
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, +50, HEIGHT);

        maze.draw(g, GAMETYPE); // draw the game
    }

    /**
     * Displays this GUI element
     */
    public void display() {
        windowFrame = new JFrame();
        if (TITLE == null) {
            windowFrame.setTitle("Maze Editor");
        } else {
            windowFrame.setTitle(TITLE + " by " + AUTHOR);
        }

        windowFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        windowFrame.add(this);
        this.repaint();

        JButton jButtonImage = new JButton();
        jButtonImage.setText("INSERT LOGO");
        jButtonImage.setBackground(new Color(168, 22, 22));
        jButtonImage.setFont(new Font("Segoe UI", Font.BOLD, 12)); // NOI18N
        jButtonImage.setForeground(new Color(255, 255, 255));
        jButtonImage.addActionListener(evt -> InsertImage());

        JButton jButtonHelp = new JButton();
        jButtonHelp.setText("HELP");
        jButtonHelp.setBackground(new Color(168, 22, 22));
        jButtonHelp.setFont(new Font("Segoe UI", Font.BOLD, 12)); // NOI18N
        jButtonHelp.setForeground(new Color(255, 255, 255));
        jButtonHelp.addActionListener(evt -> Help());
        JButton jButtonSolve = new JButton();
        jButtonSolve.setText("  SOLVE   ");
        jButtonSolve.setBackground(new Color(44, 168, 22));
        jButtonSolve.setFont(new Font("Segoe UI", Font.BOLD, 12)); // NOI18N
        jButtonSolve.setForeground(new Color(255, 255, 255));
        jButtonSolve.addActionListener(evt -> SolveMaze());
        if (!isSolvable) {
            jButtonSolve.setBackground(new Color(255, 0, 0));
        } else {
            jButtonSolve.setBackground(new Color(44, 168, 22));
        }
        JLabel jLabelSolveHeading = new JLabel();
        jLabelSolveHeading.setFont(new Font("Segoe UI", Font.BOLD, 13)); // NOI18N
        jLabelSolveHeading.setForeground(new Color(168, 22, 22));
        jLabelSolveHeading.setText("SOLVABILITY TOOLS:");

        JButton jButtonSave = new JButton();
        jButtonSave.setText("SAVE");
        jButtonSave.setBackground(new Color(168, 22, 22));
        jButtonSave.setFont(new Font("Segoe UI", Font.BOLD, 12)); // NOI18N
        jButtonSave.setForeground(new Color(255, 255, 255));
        jButtonSave.addActionListener(evt -> SaveMaze());
        //jButtonSave.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        /*JButton jButtonDuplicate = new JButton();
        jButtonDuplicate.setText("DUPLICATE");
        jButtonDuplicate.setBackground(new Color(168, 22, 22));
        jButtonDuplicate.setFont(new Font("Segoe UI", Font.BOLD, 12)); // NOI18N
        jButtonDuplicate.setForeground(new Color(255, 255, 255));
        jButtonDuplicate.addActionListener(evt -> DuplicateMaze());*/

        JButton jButtonDelete = new JButton();
        jButtonDelete.setText("DELETE");
        jButtonDelete.setBackground(new Color(168, 22, 22));
        jButtonDelete.setFont(new Font("Segoe UI", Font.BOLD, 12)); // NOI18N
        jButtonDelete.setForeground(new Color(255, 255, 255));
        jButtonDelete.addActionListener(evt -> DeleteMaze());

        JButton jButtonExport = new JButton();
        jButtonExport.setText("EXPORT");
        jButtonExport.setBackground(new Color(168, 22, 22));
        jButtonExport.setFont(new Font("Segoe UI", Font.BOLD, 12)); // NOI18N
        jButtonExport.setForeground(new Color(255, 255, 255));
        jButtonExport.addActionListener(evt -> ExportMaze());

        JButton jButtonClose = new JButton();
        jButtonClose.setText("CLOSE");
        jButtonClose.setBackground(new Color(168, 22, 22));
        jButtonClose.setFont(new Font("Segoe UI", Font.BOLD, 12)); // NOI18N
        jButtonClose.setForeground(new Color(255, 255, 255));
        jButtonClose.addActionListener(evt -> CloseMaze());
        //jButtonClose.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        jDisplaySolution = new JCheckBox();
        jDisplaySolution.setFont(new Font("Segoe UI", Font.BOLD, 12)); // NOI18N
        jDisplaySolution.setText("DISPLAY SOLUTION");
        jDisplaySolution.addActionListener(evt -> DisplaySolution());
        solveIndicator = new JLabel();
        solveIndicator.setFont(new Font("Segoe UI", Font.BOLD, 12)); // NOI18N
        solveIndicator.setText("  SOLVABLE");
        solveIndicator.setOpaque(true);
        solveIndicator.setBackground(new Color(0x54FF00));

        jLabelPercentageExplored = new JLabel();
        jLabelPercentageExplored.setFont(new Font("Segoe UI", Font.BOLD, 12)); // NOI18N
        jLabelPercentageExplored.setText(String.format("%.0f", solver.explored) + "% EXPLORED CELLS");

        jLabelPercentageDeadEnds = new JLabel();
        jLabelPercentageDeadEnds.setFont(new Font("Segoe UI", Font.BOLD, 12)); // NOI18N
        jLabelPercentageDeadEnds.setText(solver.checkDeadEnd());


        windowFrame.add(panel, BorderLayout.LINE_END);

        //NETBEANS SOURCECODE
        GroupLayout panelLayout = new GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addComponent(solveIndicator, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(panelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jButtonDelete, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButtonClose, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButtonSave, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButtonExport, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        //.addComponent(jButtonDuplicate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButtonImage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButtonHelp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jDisplaySolution, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabelPercentageExplored, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabelSolveHeading, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabelPercentageDeadEnds, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButtonHelp)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonImage)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                //.addComponent(jButtonDuplicate)
                                //.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonDelete)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonClose)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonSave)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonExport)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelSolveHeading)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(solveIndicator)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDisplaySolution)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelPercentageExplored)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelPercentageDeadEnds)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        //END OF NETBEANS SOURECODE
        windowFrame.setResizable(false);
        //frame.add(this);
        windowFrame.pack();
        windowFrame.setLocationRelativeTo(null);
        windowFrame.setVisible(true);
        this.setDoubleBuffered(true);

        //this.repaint();
    }

    private void DisplaySolution() {
        boolean checked = jDisplaySolution.isSelected();
        if (checked) {
            //want to display solution here
            boolean currentlySolvable = SolveMaze();
            if (!currentlySolvable) {
                JOptionPane.showMessageDialog(null,
                        "Maze not currently Solvable",
                        "Maze solvable?",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            maze.clearSolution();
            getParent().repaint();
        }
    }

    private void SaveMaze() {
        boolean done = false;
        int saved;
        if (maze.getMazeID() == 0) {
            saved = parentApp.mazeData.addMaze(maze);
            if (saved > 0) {
                done = true;
            }
        } else {
            done = parentApp.mazeData.updateMaze(maze);
            saved = maze.getMazeID();
        }

        if (done) {
            maze.setMazeID(saved);
            JOptionPane.showMessageDialog(null,
                    "Maze saved to database (ID:" + saved + ")",
                    "SaveMaze",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Maze not saved to database, please try again.",
                    "SaveMaze",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void CloseMaze() {

        int confirm = JOptionPane.showConfirmDialog(null,
                "Close maze? Make sure you have saved any changed work",
                "Close maze?",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            windowFrame.dispose();
        }
    }

    /*private void DuplicateMaze() {

        int id = maze.getMazeID();

        if (id == 0) {
            JOptionPane.showMessageDialog(null,
                    "Unable to duplicate maze",
                    "DuplicateMaze",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            boolean duplicated = parentApp.mazeData.duplicateMaze(String.valueOf(id));

            if (duplicated) {
                JOptionPane.showMessageDialog(null,
                        "Maze duplicated",
                        "DuplicateMaze",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }*/

    /**
     * clears solution and prints a new one (if one exists)
     *
     * @return whether the maze is solved or not
     */
    private boolean SolveMaze() {
        maze.clearSolution();
        getParent().repaint();
        isSolvable = solver.checkSolvability();
        getParent().repaint();
        return isSolvable;

    }

    /**
     * Displays help dialog for user instructions
     */
    private void Help() {

        JOptionPane.showMessageDialog(null,
                "Left click on a path or wall to switch it.\nTry clicking and dragging too!\nRight click on images to remove\nRight click on walls to add an image",
                "Help",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Deletes maze, if possible & user confirms
     */
    private void DeleteMaze() {

        int id = maze.getMazeID();

        if (id == 0) {
            JOptionPane.showMessageDialog(null,
                    "Unable to delete maze - it's not saved yet!",
                    "DeleteMaze",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {

            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete the maze?\nThis is permanent.",
                    "Delete Maze?",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {


                boolean deleted = parentApp.mazeData.deleteMaze(id);

                if (deleted) {
                    JOptionPane.showMessageDialog(null,
                            "Maze Deleted, this window will now close.",
                            "DeleteMaze",
                            JOptionPane.INFORMATION_MESSAGE);
                    windowFrame.dispose();
                }
            }
        }
    }

    /**
     * Exports Maze as an image
     */
    private void ExportMaze() {
        GUIExportSelection selectFolder = new GUIExportSelection();
        String fileLocation = selectFolder.getSaveFile();
        if(fileLocation == null){
            JOptionPane.showMessageDialog(null,
                    "You must select a folder\n to export an image",
                    "ExportMaze Error",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        new Exporter(this).exportMaze(maze.getTitle(), fileLocation);

        JOptionPane.showMessageDialog(null,
                "Exported "+ maze.getTitle() +".jpeg to file",
                "ExportMaze",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Inserts a LogoImage (max 2)
     */
    private void InsertImage() {
        anotherImage = true;
        GUIImageSelection newWind = new GUIImageSelection("Logo");
        String ImageLocation = newWind.getLocation("Logo");
        try {
            newImageLocation = ImageIO.read(new File(ImageLocation));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}


