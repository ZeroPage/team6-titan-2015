package components;

import components.main.TitanToolBar;
import components.main.left.TitanLeftPanel;
import components.main.right.TitanTable;
import components.menu.TitanMenuBar;

import javax.swing.*;
import java.awt.*;

public class TitanFrame extends JFrame {
    public static final String FRAME_TITLE = "TITAN";

    public static final int INIT_FRAME_HEIGHT = 500;
    public static final int INIT_FRAME_WIDTH = 600;

    private TitanMenuBar titanMenuBar;
    private TitanToolBar titanToolBar;
    private TitanLeftPanel titanLeftPanel;
    private TitanTable titanTable;

    public TitanFrame() {
        // Init Frame
        super();

        setTitle(FRAME_TITLE);
        setSize(INIT_FRAME_WIDTH, INIT_FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Init fields
        titanMenuBar = new TitanMenuBar();
        titanToolBar = new TitanToolBar();
        titanLeftPanel = new TitanLeftPanel();
        titanTable = new TitanTable();

        // Add components
        Container contentPane = getContentPane();
        JPanel panel = new JPanel(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, titanLeftPanel, titanTable);
        splitPane.setOneTouchExpandable(true);

        panel.add(titanToolBar, BorderLayout.NORTH);
        panel.add(splitPane, BorderLayout.CENTER);

        contentPane.setLayout(new BorderLayout());
        contentPane.add(titanMenuBar, BorderLayout.NORTH);
        contentPane.add(panel, BorderLayout.CENTER);
    }

    public TitanMenuBar getTitanMenuBar() {
        return titanMenuBar;
    }

    public TitanToolBar getTitanToolBar() {
        return titanToolBar;
    }

    public TitanLeftPanel getTitanLeftPanel() {
        return titanLeftPanel;
    }

    public TitanTable getTitanTable() {
        return titanTable;
    }
}
