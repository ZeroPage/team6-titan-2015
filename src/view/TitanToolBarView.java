package view;

import components.main.TitanToolBar;
import controller.TitanMainController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitanToolBarView {
    private TitanMainController controller;
    private TitanToolBar toolBar;
    private Container parent;

    public TitanToolBarView(TitanMainController controller, TitanToolBar toolBar, Container parent) {
        this.controller = controller;
        this.toolBar = toolBar;
        this.parent = parent;

        initListeners();
    }

    public void setEnabled(boolean enabled) {
        toolBar.setEnabledAll(enabled);
        toolBar.getOpenDSMButton().setEnabled(true);
    }

    private void initListeners() {
        // ToolBar
        toolBar.getOpenDSMButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openDSM(parent);
            }
        });

    }
}
