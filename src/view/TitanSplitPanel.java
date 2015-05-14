package view;

import controller.TitanMainController;
import view.left.TitanLeftPanel;

import javax.swing.*;

public class TitanSplitPanel extends JSplitPane {
    public static final int INIT_DIVIDER_LOCATION = 200;

    private TitanMainController controller;

    public TitanSplitPanel(TitanMainController controller) {
        super(JSplitPane.HORIZONTAL_SPLIT);

        // Init fields
        this.controller = controller;

        // Init SplitPanel
        setOneTouchExpandable(true);
        setDividerLocation(INIT_DIVIDER_LOCATION);

        // Add components
        JScrollPane scrollPane = new JScrollPane(new TitanTable(controller), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setLeftComponent(new TitanLeftPanel(controller));
        setRightComponent(scrollPane);

    }
}
