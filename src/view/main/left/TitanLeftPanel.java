package view.main.left;

import controller.TitanMainController;

import javax.swing.*;
import java.awt.*;

public class TitanLeftPanel extends JPanel {
    private TitanMainController controller;

    public TitanLeftPanel(TitanMainController controller) {
        // Init Panel
        super(new BorderLayout());

        // Init fields
        this.controller = controller;

        // Add components
        JScrollPane scrollPane = new JScrollPane(new TitanTree(controller), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(new TitanLeftToolBar(controller), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
