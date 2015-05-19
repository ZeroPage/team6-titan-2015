package view.main.left;

import controller.TitanMainController;

import javax.swing.*;
import java.awt.*;

public class TitanLeftPanel extends JPanel {
    public TitanLeftPanel(TitanMainController controller) {
        // Init Panel
        super(new BorderLayout());

        // Add components
        TitanTree tree = new TitanTree(controller);
        TitanLeftToolBar toolBar = new TitanLeftToolBar(controller);

        JScrollPane scrollPane = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(toolBar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // connect controller
        controller.setLeftComponents(tree, toolBar);
    }
}
