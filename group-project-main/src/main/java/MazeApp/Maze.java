package MazeApp;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * maze class, generate and solve the mass
 *
 * @author Zayed
 */
public class Maze {
    // start and stop positions of the maze
    private final Point start;
    private final Point stop;
    private final int cellSize; // dimensions in units of cells
    private final String title;
    private final String author;
    private final String mazeType;
    private final BufferedImage imageLogo2;
    private final BufferedImage startImagePath;
    private final BufferedImage endImagePath;
    private int mazeID; //Unique identifier for saved mazes
    //image testing
    private CellState[][] maze; // main data structure
    private LocalDateTime dateCreated;
    private String dateCreatedFormatted;
    private LocalDateTime dateEdited;
    private String dateEditedFormatted;
    private java.awt.Image image;
    private java.awt.Image startImage;
    private java.awt.Image endImage;
    private Image logoImage;
    private BufferedImage imageLogo1;
    private int cols;
    private int rows;

    /**
     * Constructor for new maze
     *
     * @param width        width of maze
     * @param height       height of maze
     * @param size         cell size
     * @param autoGenerate toggle for running generator on creation
     * @param mazeType     end types for the maze (from dropdown selection)
     * @param author       author's name
     * @param title        title of maze
     * @param logoImage1   logo image 1
     * @param logoImage2   logo image 2
     * @param startImage   starting image - only valid where GAMETYPE is images
     * @param endImage     ending image - only valid where GAMETYPE is images
     */
    public Maze(int width, int height, int size, boolean autoGenerate, String mazeType, String author, String title, BufferedImage logoImage1, BufferedImage logoImage2, BufferedImage startImage, BufferedImage endImage) {
        size = Math.abs(size);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.dateCreatedFormatted = myFormatObj.format(LocalDateTime.now());

        this.title = title;
        this.author = author;
        this.mazeType = mazeType;
        cols = Math.abs(width) / size;
        rows = Math.abs(height) / size;

        // make them biggest odd number smaller or equal
        cols = cols - 1 + cols % 2;
        rows = rows - 1 + rows % 2;
        startImagePath = startImage;
        endImagePath = endImage;
        cellSize = size;
        imageLogo1 = logoImage1;
        if (logoImage2 != null) {
            imageLogo2 = logoImage2;
        } else {
            imageLogo2 = logoImage1;
        }
        ///  Image handling


        start = new Point(0, 1);
        stop = new Point(cols - 1, rows - 2);

        configureDefaultLayout(autoGenerate);

        if (autoGenerate) {
            setMaze(new Generator(this).generate());
            //solve();
        }
        if (imageLogo1 != null) {
            insertLogo();
        }
    }

    /**
     * Constructor to load from database
     *
     * @param width      width of maze
     * @param height     height of maze
     * @param size       cell size
     * @param ID         id of maze from DB
     * @param maze       array of maze CellStates
     * @param mazeType   end types for the maze (from dropdown selection)
     * @param author     author's name
     * @param title      title of maze
     * @param logoImage1 logo image 1
     * @param logoImage2 logo image 2
     * @param startImage starting image - only valid where GAMETYPE is images
     * @param endImage   ending image - only valid where GAMETYPE is images
     */
    public Maze(int width, int height, int size, int ID, CellState[][] maze, String mazeType, String author, String title, BufferedImage logoImage1, BufferedImage logoImage2, BufferedImage startImage, BufferedImage endImage) {
        size = Math.abs(size);
        this.title = title;
        this.author = author;
        this.mazeType = mazeType;
        this.mazeID = ID;

        cols = Math.abs(width) / size;
        rows = Math.abs(height) / size;

        // make them biggest odd number smaller or equal
        cols = cols - 1 + cols % 2;
        rows = rows - 1 + rows % 2;


        cellSize = size;

        start = new Point(0, 1);
        stop = new Point(cols - 1, rows - 2);

        this.maze = maze;

        startImagePath = startImage;
        endImagePath = endImage;
        imageLogo1 = logoImage1;
        if (logoImage2 != null) {
            imageLogo2 = logoImage2;
        } else {
            imageLogo2 = logoImage1;
        }


    }

    /**
     * getter for dateCreated
     * @return date created
     */
    public String getCreatedTime() { return dateCreatedFormatted; }

    /**
     * getter for mazeType
     * @return mazeType
     */
    public String getMazeType() {
        return mazeType;
    }

    /**
     * getter for cols
     * @return cols
     */
    public int getCols() {
        return cols;
    }

    /**
     * getter for rows
     * @return rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * getter for width
     * @return width
     */
    public int getWidth() {
        return getCols() * getCellSize();
    }

    /**
     * getter for height
     * @return height
     */
    public int getHeight() {
        return getRows() * getCellSize();
    }

    /**
     * getter for cellsize
     * @return cellsize
     */
    public int getCellSize() {
        return cellSize;
    }

    /**
     * getter for start
     * @return start
     */
    public Point getStart() {
        return start;
    }

    /**
     * getter for stop
     * @return stop
     */
    public Point getStop() {
        return stop;
    }

    /**
     * getter for maze
     * @return maze
     */
    public CellState[][] getMaze() {
        return maze;
    }

    /**
     * setter for maze
     * @param maze the updated maze to set
     */
    public void setMaze(CellState[][] maze) {
        this.maze = maze;
    }

    /**
     * setter for individual maze cell
     * @param col the target cell's column
     * @param row the target cell's row
     * @param cell the target cell state
     */
    public void setMazeCell(int col, int row, CellState cell) {
        this.maze[col][row] = cell;
    }

    /**
     * getter for imageLocationBufferedImage
     * @return imageLocationBufferedImage
     */
    public BufferedImage getImageLogo1() {
        return imageLogo1;
    }

    /**
     * setter for imageLocationBufferedImage
     * @param imageLogo1 the image to set
     */
    public void setImageLogo1(BufferedImage imageLogo1) {
        this.imageLogo1 = imageLogo1;
    }

    /**
     * getter for title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * getter for author
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * getter for maze id
     * @return mazeID
     */
    public int getMazeID() {
        return mazeID;
    }

    /**
     * setter for mazeID
     * @param mazeID the new value of mazeID
     */
    public void setMazeID(int mazeID) {
        this.mazeID = mazeID;
    }

    /**
     * getter for previousImage
     * @return previousImage
     */
    public BufferedImage getImageLogo2() {
        return imageLogo2;
    }

    /**
     * getter for startImagePath
     * @return startImagePath
     */
    public BufferedImage getStartImagePath() {
        return startImagePath;
    }

    /**
     * getter for endImagePath
     * @return endImagePath
     */
    public BufferedImage getEndImagePath() {
        return endImagePath;
    }

    /**
     * initialize maze array
     */
    private void configureDefaultLayout(boolean autoGenerate) {

        maze = new CellState[cols][rows];

        // walls all around
        int i, j;
        if (!autoGenerate) {
            for (i = 1; i < cols - 1; i += 2) {
                for (j = 1; j < rows - 1; j += 2) {
                    maze[i][j] = CellState.WALL;
                    maze[i + 1][j] = CellState.WALL;
                    maze[i][j + 1] = CellState.WALL;
                }
            }
        } else {
            // inside walls and empty cells
            for (i = 1; i < cols - 1; i += 2) {
                for (j = 1; j < rows - 1; j += 2) {
                    maze[i][j] = CellState.EMPTY;
                    maze[i + 1][j] = CellState.WALL;
                    maze[i][j + 1] = CellState.WALL;
                }
            }
        }
        for (i = 2; i < cols - 2; i += 2) {
            for (j = 2; j < rows - 2; j += 2) {
                maze[i][j] = CellState.WALL;
            }
        }


        for (j = 0; j < rows; j++) {
            maze[0][j] = CellState.EXTERNALWALL;
            maze[cols - 1][j] = CellState.EXTERNALWALL;
        }
        for (i = 0; i < cols; i++) {
            maze[i][0] = CellState.EXTERNALWALL;
            maze[i][rows - 1] = CellState.EXTERNALWALL;
        }

    }


    private void insertLogo() {
        //find a random location to place image
        Random rand = new Random();
        int upperbound = cols;
        int upperboundY = rows;
        int logoLocationX;
        int logoLocationY;
        // Finds a section of the maze that doesn't include the solution and places the LOGOCEll there.

        do {
            logoLocationX = rand.nextInt(upperbound);
            logoLocationY = rand.nextInt(upperboundY);
            if (logoLocationX == 0) {
                logoLocationX = logoLocationX + 1;
            }
            if (logoLocationY == 0) {
                logoLocationY = logoLocationY + 1;
            }
        } while (maze[logoLocationX][logoLocationY] != CellState.WALL);

        maze[logoLocationX][logoLocationY] = CellState.LOGOCELL;
    }

    /**
     * clears any cells set as solution in the maze
     */
    public void clearSolution() {
        int i, j;
        // inside walls and empty cells
        for (i = 1; i < cols - 1; i += 1) {
            for (j = 1; j < rows - 1; j += 1) {
                if (maze[i][j] == CellState.SOLUTION) {
                    maze[i][j] = CellState.PATH;
                }
            }
        }
    }

    /**
     * draw the maze
     *
     * @param g            tool to draw
     * @param startEndType the end type of the maze
     */
    public void draw(Graphics g, String startEndType) {

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                CellState state = maze[i][j];
                Color color;

                int x = i * cellSize;
                int y = j * cellSize;

                color = switch (state) {
                    case EMPTY, PATH -> Color.WHITE;
                    case SOLUTION -> new Color(0.0f, 0.0f, 1.0f, 1.0f);
                    case LOGOCELL -> Color.CYAN;
                    default -> Color.BLACK; // wall
                };
                // ENTRY

                //handles user input for the entry
                //picks what type of exit to display according to the user input
                if (i == start.x && j == start.y) {
                    if (startEndType.equals("Openings")) {
                        maze[start.x][start.y] = CellState.OPENING;
                        color = Color.WHITE;
                    } else if (startEndType.equals("Arrows")) {
                        try {
                            image = ImageIO.read(getClass().getClassLoader().getResource("entry-arrow.png"));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        int width = image.getWidth(null);
                        int height = image.getHeight(null);
                        g.drawImage(image, x, y, x + cellSize, y + cellSize, 0, 0, width, height, null);
                        continue;
                    }
                    //  EXIT
                }
                    //handle user input for the exit
                    //picks what type of exit to display according to the user input
                    else if (i == stop.x && j == stop.y) {
                         if (startEndType.equals("Openings")) {
                             maze[stop.x][stop.y] = CellState.OPENING;
                             color = Color.WHITE;
                         } else if (startEndType.equals("Arrows")) {
                             try {
                                 image = ImageIO.read(getClass().getClassLoader().getResource("entry-arrow.png"));
                             } catch (IOException e) {
                                 throw new RuntimeException(e);
                             }
                             int width = image.getWidth(null);
                             int height = image.getHeight(null);
                             int locationX = i * cellSize;
                             int locationY = j * cellSize;

                             g.drawImage(image, locationX, locationY, locationX + cellSize, locationY + cellSize, 0, 0, width, height, null);
                             continue;
                         }
                     }

                if (state == CellState.LOGOCELL) {
                    ImageIcon logoInsert = new ImageIcon(imageLogo2);
                    logoImage = logoInsert.getImage();
                    int width = logoImage.getWidth(null);
                    int height = logoImage.getHeight(null);
                    g.drawImage(logoImage, x, y, x + cellSize, y + cellSize, 0, 0, width, height, null);


                } else if (state == CellState.LOGOCELL2) {
                    logoImage = imageLogo1;
                    int width = logoImage.getWidth(null);
                    int height = logoImage.getHeight(null);
                    g.drawImage(logoImage, x, y, x + cellSize, y + cellSize, 0, 0, width, height, null);
                } else {
                    g.setColor(color);
                    g.fillRect(x, y, cellSize, cellSize);
                }

            }
        }
        if (startEndType.equals("Images")) {

            //  Start image

            startImage = startImagePath;
            int width = startImage.getWidth(null);
            int height = startImage.getHeight(null);
            if (cols < 16 || rows < 16) {
                maze[1][1] = CellState.PATH;
                g.drawImage(startImage, cellSize, cellSize, cellSize + cellSize, cellSize + cellSize, 0, 0, width, height, null);

            } else {
                maze[1][1] = CellState.PATH;
                maze[2][1] = CellState.PATH;
                maze[1][2] = CellState.PATH;
                maze[2][2] = CellState.PATH;
                g.drawImage(startImage, cellSize, cellSize, cellSize * 2 + cellSize, cellSize * 2 + cellSize, 0, 0, width, height, null);

            }

            //  Finish image

            endImage = endImagePath;
            int endImageWidth = endImage.getWidth(null);
            int endImageHeight = endImage.getHeight(null);
            int endImageLocationX = (cols - 3) * cellSize;
            int endImageLocationY = (rows - 3) * cellSize;

            if (cols < 16 || rows < 16) {
                endImageLocationX = (cols - 2) * cellSize;
                endImageLocationY = (rows - 2) * cellSize;
                maze[cols - 2][rows - 2] = CellState.PATH;
                g.drawImage(endImage, endImageLocationX, endImageLocationY, cellSize + endImageLocationX, cellSize + endImageLocationY, 0, 0, endImageWidth, endImageHeight, null);
            } else {
                maze[cols - 2][rows - 2] = CellState.PATH;
                maze[cols - 2][rows - 3] = CellState.PATH;
                maze[cols - 3][rows - 2] = CellState.PATH;
                maze[cols - 3][rows - 3] = CellState.PATH;
                g.drawImage(endImage, endImageLocationX, endImageLocationY, cellSize * 2 + endImageLocationX, cellSize * 2 + endImageLocationY, 0, 0, endImageWidth, endImageHeight, null);
            }


        }
    }


    /**
     * Cell State, as in the state every cell in the maze is
     * - {@link #WALL}
     * - {@link #EXTERNALWALL}
     * - {@link #EMPTY}
     * - {@link #PATH}
     * - {@link #SOLUTION}
     * - {@link #OPENING}
     *
     * @author Zayed
     */
    public enum CellState {
        /**
         * Wall: defines a wall cell (walls are black)
         */
        WALL,
        /**
         * External Wall of the maze: defines a wall cell (walls are black)
         * External walls of the maze cannot be changed when editing a maze
         */
        EXTERNALWALL,

        /**
         * Empty: Not a wall cell, a path nor a solution, placeholder for future paths
         * (like null)
         */
        EMPTY,

        /**
         * Path: Represents a path cell (paths are white)
         */
        PATH,

        /**
         * Solution: Represents a solution cell (paths are blue)
         */
        SOLUTION,

        /**
         * Opening: Represents an opening in the maze, only placed if the user has
         * selected opening as their start/end
         */
        OPENING,

        /**
         * LOGOCELL: Represents cells which will have a logo placed on it
         */
        LOGOCELL,
        /**
         * LOGOCELL2: if there is a second logo that needs to be placed on the maze
         */
        LOGOCELL2
    }
}




