package view;

import components.menu.TitanFileMenu;
import components.menu.TitanMenuBar;
import components.menu.TitanViewMenu;
import controller.TitanMainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitanMenuView {
    private TitanMainController controller;
    private TitanMenuBar menuBar;
    private Container parent;

    public TitanMenuView(TitanMainController controller, TitanMenuBar menuBar, Container parent) {
        this.controller = controller;
        this.menuBar = menuBar;
        this.parent = parent;

        initListeners();
    }

    public void setEnabled(boolean enabled) {
        TitanFileMenu fileMenu = menuBar.getTitanFileMenu();
        fileMenu.setEnabledAll(enabled);
        fileMenu.getNewDSMMenuItem().setEnabled(true);
        fileMenu.getOpenDSMMenuItem().setEnabled(true);
        fileMenu.getExitMenuItem().setEnabled(true);

        TitanViewMenu viewMenu = menuBar.getTitanViewMenu();
        viewMenu.getRedrawMenuItem().setEnabled(enabled);
        viewMenu.getShowRowLabelMenuItem().setEnabled(enabled);
    }

    public boolean isShowRowLabelsSelected() {
        return menuBar.getTitanViewMenu().getShowRowLabelMenuItem().isSelected();
    }

    private void initListeners() {
        // FileMenu
        TitanFileMenu fileMenu = menuBar.getTitanFileMenu();

        fileMenu.getNewDSMMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.newDSM(parent);
            }
        });

        fileMenu.getOpenDSMMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openDSM(parent);
            }
        });

        fileMenu.getLoadClusterMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openCluster(parent);
            }
        });

        fileMenu.getExitMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // TODO: Total 5 Listeners

        // View
        menuBar.getTitanViewMenu().getRedrawMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.drawTable();
            }
        });

        // Help
        menuBar.getTitanHelpMenu().getAboutMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(parent, "About"); // TODO: Fill contents
            }
        });
    }
}
