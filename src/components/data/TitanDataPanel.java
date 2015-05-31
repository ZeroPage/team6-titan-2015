package components.data;

import javax.swing.*;
import java.awt.*;

public class TitanDataPanel extends JSplitPane {
    private TitanTree tree;
    private TitanDataToolBar toolBar;
    private TitanTable table;

    public TitanDataPanel() {
        // Init Panel
        super(JSplitPane.HORIZONTAL_SPLIT);

        // Add components
        tree = new TitanTree();
        toolBar = new TitanDataToolBar();
        table = new TitanTable();

        JPanel leftPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        leftPanel.add(toolBar, BorderLayout.NORTH);
        leftPanel.add(scrollPane, BorderLayout.CENTER);


        setLeftComponent(leftPanel);
        setRightComponent(new JScrollPane(table));
        setOneTouchExpandable(true);
    }

    public TitanTree getTree() {
        return tree;
    }

    public TitanDataToolBar getToolBar() {
        return toolBar;
    }

    public TitanTable getTable() {
        return table;
    }
}
