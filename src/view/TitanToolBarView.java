package view;

import components.TitanToolBar;
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

    public void setDefaultEnabled() {
        toolBar.setEnabledAll(false);
        toolBar.getOpenDSMButton().setEnabled(true);
    }

    public void setEnabledAll(boolean enabled) {
        toolBar.setEnabledAll(enabled);
    }

    private void initListeners() {
        // ToolBar
        toolBar.getOpenDSMButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openDSM(parent);
            }
        });

        // TODO: Total 5 Listeners
    }
}
