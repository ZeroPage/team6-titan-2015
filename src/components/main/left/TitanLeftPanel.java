package components.main.left;

import controller.TitanMainController;

import javax.swing.*;
import java.awt.*;

public class TitanLeftPanel extends JPanel {
    private TitanTree tree;
    private TitanLeftToolBar toolBar;

    public TitanLeftPanel() {
        // Init Panel
        super(new BorderLayout());

        // Add components
        tree = new TitanTree();
        toolBar = new TitanLeftToolBar();

        JScrollPane scrollPane = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(toolBar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public TitanTree getTree() {
        return tree;
    }

    public TitanLeftToolBar getToolBar() {
        return toolBar;
    }
}
