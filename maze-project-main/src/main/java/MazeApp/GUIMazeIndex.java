package MazeApp;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

/**
 * GUI for Maze Index panel<br />
 * This menu was built using sources code provided by the NETBEANS interactive GUI builder
 */
public class GUIMazeIndex extends JPanel implements Runnable, ListSelectionListener {
    private static final Object[] mazeSelectedInfo = new Object[6];
    private final App parentApp;
    private final Object[][] mazeData;
    private final JTable table;
    private JFrame windowFrame;

    /**
     * Constructor for GUI Index
     *
     * @param parent the calling App object
     */
    public GUIMazeIndex(App parent) {
        super(new GridLayout(1, 0));

        //super("Maze Search");
        this.parentApp = parent;
        mazeData = parentApp.mazeData.getMazeList();
        JPanel control = new JPanel();
        control.setLayout(new BoxLayout(control, BoxLayout.X_AXIS));
        table = new JTable(new GUIMazeIndex.TableModel());
        table.setPreferredScrollableViewportSize(new Dimension(800, 150));
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);


        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        JLabel jLabelMazeIndex = new JLabel();
        jLabelMazeIndex.setText("   SELECT MAZE   ");
        jLabelMazeIndex.setBackground(new Color(168, 22, 22));
        jLabelMazeIndex.setFont(new Font("Segoe UI", Font.BOLD, 16)); // NOI18N
        jLabelMazeIndex.setForeground(new Color(168, 22, 22));

        JButton jButtonMazeSelection = new JButton();
        jButtonMazeSelection.setText("LOAD");
        jButtonMazeSelection.setBackground(new Color(168, 22, 22));
        jButtonMazeSelection.setFont(new Font("Segoe UI", Font.BOLD, 14)); // NOI18N
        jButtonMazeSelection.setForeground(new Color(255, 255, 255));
        jButtonMazeSelection.addActionListener(evt -> MazeSelected());

        //Add the scroll pane to this panel.
        ListSelectionModel listModel = table.getSelectionModel();
        listModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listModel.addListSelectionListener(this);


        control.add(jLabelMazeIndex);
        control.add(scrollPane);
        control.add(jButtonMazeSelection);
        this.add(control);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI() {
        //Create and set up the window.
        windowFrame = new JFrame("Mazes in Database");
        windowFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        windowFrame.setLocationRelativeTo(null);
        //Create and set up the content pane.

        this.setOpaque(true); //content panes must be opaque
        windowFrame.setContentPane(this);

        //Display the window.
        windowFrame.pack();
        windowFrame.setVisible(true);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int[] sel;
        Object value;
        if (!e.getValueIsAdjusting()) {
            sel = table.getSelectedRows();
            if (sel.length > 0) {
                for (int i = 0; i < 6; i++) {
                    // get data from JTable
                    TableModel tm = (TableModel) table.getModel();
                    value = tm.getValueAt(sel[0], i);
                    mazeSelectedInfo[i] = value;
                }
            }
        }
    }

    private void MazeSelected() {
        //pass this information to something that would display the maze
        if (mazeSelectedInfo[0] == null) {
            JOptionPane.showMessageDialog(null, "Select a maze from the table\nThen hit load");
            return;
        }
        int ID = Integer.parseInt((String) mazeSelectedInfo[0]);
        Maze toDraw = parentApp.mazeData.getMaze(ID);
        GUIMazeViewer draw = new GUIMazeViewer(parentApp, toDraw);
        draw.display();
        windowFrame.dispose();
    }

    public void run() {
        createAndShowGUI();
    }

    class TableModel extends AbstractTableModel {
        private final String[] columnNames = {"ID",
                "Title",
                "Author",
                "Edited",
                "Created",
                "Cell Size"};

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return mazeData.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return mazeData[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            return false;
        }

    }

}