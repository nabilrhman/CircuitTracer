import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The GUI panel class for the output provided by CircuitTracer
 *
 * @author nabilr
 */
public class CircuitTracerGUIPanel extends JPanel
{
    private ArrayList<TraceState> bestPaths;
    private CircuitBoard[] bestPathBoards;
    private JPanel solutionGridPanel;
    private JMenuItem quitMenuItem;
    private JMenuItem aboutMenuItem;

    /**
     * Constructor - constructs the GUI panel based on bestPaths
     * @param bestPaths The ArrayList of bestPaths
     */
    public CircuitTracerGUIPanel(ArrayList<TraceState> bestPaths)
    {
        storeDefaultUIStyle();
        setModifiedUIStyle();

        this.bestPaths = bestPaths;
        this.bestPathBoards = new CircuitBoard[bestPaths.size()];

        for(int i = 0; i < bestPaths.size(); i++)
        {
            bestPathBoards[i] = bestPaths.get(i).getBoard();
        }

        this.setLayout(new BorderLayout());

        solutionGridPanel = new JPanel(new GridLayout(bestPathBoards[0].numRows(), bestPathBoards[0].numCols()));
        generateValuesInSolutionGrid(bestPathBoards[0]);

        this.add(generateMenuBar(), BorderLayout.NORTH);
        this.add(solutionGridPanel, BorderLayout.CENTER);
        this.add(generateSolutionListPanel(bestPathBoards), BorderLayout.EAST);

    }

    /**
     * Refreshes the solution grid based on the solution number.
     * @param solutionNumber The solution number
     */
    private void refreshSolutionGrid(int solutionNumber)
    {
        solutionGridPanel.removeAll();
        generateValuesInSolutionGrid(bestPathBoards[solutionNumber]);
        this.updateUI();

    }

    /**
     * Generate values into the solution grid based on the provided board
     * @param board The board that's values will be generated
     */
    private void generateValuesInSolutionGrid(CircuitBoard board)
    {
        for(int i = 0; i < board.numRows(); i++)
        {
            for (int j = 0; j < board.numCols(); j++)
            {
                JButton gridButton = new JButton(Character.toString(board.charAt(i, j)));

                if(gridButton.getText().equals("T"))
                {
                    gridButton.setForeground(Color.RED);
                    gridButton.setBackground(Color.RED.darker().darker().darker().darker().darker());
                }
                if(gridButton.getText().equals("1"))
                {
                    gridButton.setForeground(Color.GREEN);
                    gridButton.setBackground(Color.GREEN.darker().darker().darker().darker().darker());
                }
                if(gridButton.getText().equals("2"))
                {
                    gridButton.setForeground(Color.CYAN);
                    gridButton.setBackground(Color.CYAN.darker().darker().darker().darker().darker());
                }

                solutionGridPanel.add(gridButton);
            }
        }
    }

    /**
     * Generates panel for solutions list based on bestPathsBoards
     * @param bestPathBoards The boards of the best paths
     * @return A JPanel contains solution list
     */
    private JPanel generateSolutionListPanel(CircuitBoard[] bestPathBoards)
    {
        JPanel solutionListContainerPanel = new JPanel();
        JList solutionList = new JList();
        ListSelectionModel listSelectionModel = solutionList.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionActionListener());

        String[] listData = new String[bestPathBoards.length];

        for(int i = 0; i < bestPathBoards.length; i++)
        {
            listData[i] = "      Solution " + (i + 1) + "      ";
        }

        solutionList.setListData(listData);

        solutionList.setSelectedIndex(0);
        solutionList.setFont(Style.LIST_FONT);
        solutionList.setForeground(Style.PRIMARY_FONT_COLOR);
        solutionList.setBackground(Style.PRIMARY_BACKBROUND_COLOR);
        solutionList.setSelectionBackground(Style.SECONDARY_BACKBROUND_COLOR);
        solutionList.setSelectionForeground(Style.PRIMARY_FONT_COLOR);
        solutionList.setFixedCellHeight(Style.LIST_ITEM_HEIGHT);

        solutionListContainerPanel.add(solutionList);
        solutionListContainerPanel.setBorder(new EmptyBorder(0, 2, 0, 5));
        solutionListContainerPanel.setPreferredSize(new Dimension(100, 0));
        return solutionListContainerPanel;
    }

    /**
     * Generates ManuBar for the panel
     * @return the MenuBar
     */
    public JMenuBar generateMenuBar()
    {
        JMenu fileMenu = new JMenu("File");
        quitMenuItem = new JMenuItem("Quit");
        quitMenuItem.addActionListener(new MenuItemActionListener());

        fileMenu.add(quitMenuItem);

        JMenu helpMenu = new JMenu("Help");
        aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(new MenuItemActionListener());

        helpMenu.add(aboutMenuItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    /**
     * Sets the UI style to the modified UI style.
     */
    private void setModifiedUIStyle()
    {
        UIManager.put("Panel.background", Style.PRIMARY_BACKBROUND_COLOR);
        UIManager.put("Panel.foreground", Style.PRIMARY_FONT_COLOR);
        UIManager.put("OptionPane.background", Style.PRIMARY_BACKBROUND_COLOR);
        UIManager.put("OptionPane.foreground", Style.PRIMARY_FONT_COLOR);

        UIManager.put("Button.background", Style.SECONDARY_BACKBROUND_COLOR);
        UIManager.put("Button.foreground", Style.PRIMARY_FONT_COLOR);
        UIManager.put("Button.font", Style.BUTTON_FONT);

        UIManager.put("Button.select", Style.SECONDARY_BACKBROUND_COLOR);
        UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));

        UIManager.put("Button.border",
                BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Style.PRIMARY_BACKBROUND_COLOR),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        UIManager.put("Label.foreground", Style.PRIMARY_FONT_COLOR);
        UIManager.put("Label.font", Style.PRIMARY_FONT);
    }

    /**
     * Sets UI to the default one.
     */
    private void setDefaultUIStyle()
    {
        UIManager.put("Panel.background", Style.defaultPanelBackground);
        UIManager.put("Panel.foreground", Style.defaultPanelForeground);

        UIManager.put("OptionPane.background", Style.defaultOptionPaneBackground);
        UIManager.put("OptionPane.foreground", Style.defaultOptionPaneForeground);

        UIManager.put("Button.background", Style.defaultButtonBackground);
        UIManager.put("Button.foreground", Style.defaultButtonForeground);
        UIManager.put("Button.font", Style.defaultButtonFont);

        UIManager.put("Button.select", Style.defaultButtonSelect);
        UIManager.put("Button.focus", Style.defaultButtonFocus);

        UIManager.put("Button.border", Style.defaultButtonBorder);

        UIManager.put("Label.foreground", Style.defaultLabelForeground);
        UIManager.put("Label.font", Style.defaultLabelFont);
    }

    /**
     * Stores default UI style in Style
     */
    private void storeDefaultUIStyle()
    {
        Style.defaultPanelBackground = UIManager.get("Panel.background");
        Style.defaultPanelForeground = UIManager.get("Panel.foreground");
        Style.defaultOptionPaneBackground = UIManager.get("OptionPane.background");
        Style.defaultOptionPaneForeground = UIManager.get("OptionPane.foreground");
        Style.defaultButtonBackground = UIManager.get("Button.background");
        Style.defaultButtonForeground = UIManager.get("Button.foreground");
        Style.defaultButtonFont = UIManager.get("Button.font");
        Style.defaultButtonSelect = UIManager.get("Button.select");
        Style.defaultButtonFocus = UIManager.get("Button.focus");
        Style.defaultButtonBorder = UIManager.get("Button.border");
        Style.defaultLabelForeground = UIManager.get("Label.foreground");
        Style.defaultLabelFont = UIManager.get("Label.font");
    }

    /**
     * ListSelectionListener for the listItems
     */
    public class ListSelectionActionListener implements ListSelectionListener
    {
        @Override
        public void valueChanged(ListSelectionEvent e)
        {
            ListSelectionModel listSelectionModel = (ListSelectionModel) e.getSource();

            // Find out which indexes are selected.
            int minIndex = listSelectionModel.getMinSelectionIndex();
            int maxIndex = listSelectionModel.getMaxSelectionIndex();

            for (int i = minIndex; i <= maxIndex; i++)
            {
                if (listSelectionModel.isSelectedIndex(i))
                {
                    refreshSolutionGrid(i);
                }
            }
        }
    }

    /**
     * ActionListener for MenuItems
     */
    public class MenuItemActionListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == aboutMenuItem)
            {
                UIManager.put("Button.font", Style.defaultButtonFont);

                JPanel mainPanel = new JPanel();
                mainPanel.setBackground(Style.PRIMARY_BACKBROUND_COLOR);
                mainPanel.setLayout(new BorderLayout());

                JPanel labelPanel = new JPanel();
                labelPanel.setBackground(Style.PRIMARY_BACKBROUND_COLOR);
                labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

                JLabel applicationNameLabel = new JLabel("CIRCUIT TRACER");
                applicationNameLabel.setFont(Style.HEADING1_FONT);

                labelPanel.add(applicationNameLabel);
                labelPanel.add(new JLabel("Version: 1.0 Alpha"));
                labelPanel.add(new JLabel("Developed by Nabil Rahman"));
                labelPanel.add(new JLabel(" "));
                labelPanel.add(new JLabel("This application was developed as the final project for"));
                labelPanel.add(new JLabel("CS221 - Fall 2017, Boise State University"));
                labelPanel.add(new JLabel(" "));
                labelPanel.add(new JLabel("www.nr-creation.com"));

                mainPanel.add(labelPanel, BorderLayout.CENTER);

                int result = JOptionPane.showConfirmDialog(null, mainPanel, "About", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                setModifiedUIStyle();
            }
            else if(e.getSource() == quitMenuItem)
            {
                System.exit(0);
            }
        }
    }
}
