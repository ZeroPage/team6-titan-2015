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

    public void setFileMenuEnabled(boolean enabled) {
        menuBar.getTitanFileMenu().setEnabled(enabled);
    }

    public void setEnabledAll(boolean enabled) {
        TitanFileMenu fileMenu = menuBar.getTitanFileMenu();
        fileMenu.setEnabledAll(enabled);

        TitanViewMenu viewMenu = menuBar.getTitanViewMenu();
        viewMenu.getRedrawMenuItem().setEnabled(enabled);
        viewMenu.getShowRowLabelMenuItem().setEnabled(enabled);
    }

    public void setDefaultEnabled() {
        TitanFileMenu fileMenu = menuBar.getTitanFileMenu();
        fileMenu.setEnabledAll(false);
        fileMenu.getNewDSMMenuItem().setEnabled(true);
        fileMenu.getOpenDSMMenuItem().setEnabled(true);
        fileMenu.getExitMenuItem().setEnabled(true);

        TitanViewMenu viewMenu = menuBar.getTitanViewMenu();
        viewMenu.getRedrawMenuItem().setEnabled(false);
        viewMenu.getShowRowLabelMenuItem().setEnabled(false);
    }

    public boolean isShowRowLabelsSelected() {
        return menuBar.getTitanViewMenu().getShowRowLabelMenuItem().isSelected();
    }

    private void initListeners() {
        // FileMenu
        TitanFileMenu fileMenu = menuBar.getTitanFileMenu();

        fileMenu.getNewDSMMenuItem().addActionListener(e -> controller.newDSM());

        fileMenu.getOpenDSMMenuItem().addActionListener(e -> controller.openDSM());

        fileMenu.getSaveDSMMenuItem().addActionListener(e -> controller.saveDSM());

        fileMenu.getSaveAsDSMMenuItem().addActionListener(e -> controller.saveAsDSM());

        fileMenu.getSaveClusterMenuItem().addActionListener(e -> controller.saveCluster());

        fileMenu.getLoadClusterMenuItem().addActionListener(e -> controller.openCluster());

        fileMenu.getNewClusterMenuItem().addActionListener(e -> controller.newCluster());

        fileMenu.getSaveAsClusterMenuItem().addActionListener(e -> controller.saveAsCluster());

        fileMenu.getExitMenuItem().addActionListener(e -> controller.disposeDialog());

        // View
        menuBar.getTitanViewMenu().getRedrawMenuItem().addActionListener(e -> controller.drawTree());

        // Help
        menuBar.getTitanHelpMenu().getAboutMenuItem().addActionListener(e ->
                JOptionPane.showMessageDialog(parent, "Team 6\n" + "20135262 안미리\n" + "20131298 원준연\n" +
                        "20135796 조영준\n" + "20132811 박희정\n" + "20132465 안용호")
        );
    }
}
