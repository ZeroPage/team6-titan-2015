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
        toolBar.getNewDSMButton().setEnabled(true);
    }

    public void setEnabledAll(boolean enabled) {
        toolBar.setEnabledAll(enabled);
    }

    private void initListeners() {
        // ToolBar
        toolBar.getNewDSMButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.newDSM();
            }
        });

        toolBar.getOpenDSMButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openDSM();
            }
        });

        toolBar.getRedrawButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.drawTree();
            }
        });

        toolBar.getNewClusterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.newCluster();
            }
        });

        toolBar.getOpenClusterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openCluster();
            }
        });

        toolBar.getSaveClusterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveCluster();
            }
        });

        toolBar.getSaveAsClusterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveAsCluster();
            }
        });
    }
}
