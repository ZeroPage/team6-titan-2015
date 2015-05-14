package view;

import controller.TitanMainController;

import javax.swing.*;
import java.awt.*;

public class TitanMainPanel extends JPanel {
    private TitanMainController controller;

    public TitanMainPanel(TitanMainController controller) {
        setLayout(new BorderLayout());

        // Init fields
        this.controller = controller;

        // Add components
        add(new TitanToolBar(controller), BorderLayout.NORTH);
        add(new TitanSplitPanel(controller), BorderLayout.CENTER);
    }
}
