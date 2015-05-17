package view.main;

import controller.TitanMainController;
import view.main.left.TitanLeftPanel;
import view.main.right.TitanTable;

import javax.swing.*;

public class TitanSplitPanel extends JSplitPane {
    private TitanMainController controller;

    public TitanSplitPanel(TitanMainController controller) {
        super(JSplitPane.HORIZONTAL_SPLIT);

        // Init fields
        this.controller = controller;

        // Init SplitPanel
        setOneTouchExpandable(true);

        // Add components
        JScrollPane scrollPane = new JScrollPane(new TitanTable(controller), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setLeftComponent(new TitanLeftPanel(controller));
        setRightComponent(scrollPane);

    }
}
