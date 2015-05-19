package view;

import components.TitanFrame;
import components.main.TitanToolBar;
import components.main.left.TitanLeftToolBar;
import components.main.left.TitanTree;
import components.menu.TitanFileMenu;
import components.menu.TitanHelpMenu;
import components.menu.TitanMenuBar;
import components.menu.TitanViewMenu;
import controller.TitanMainController;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitanMainView {
    private TitanMainController controller;

    private TitanFrame frame;

    public TitanMainView(TitanMainController controller) {
        this.controller = controller;
        this.frame = new TitanFrame();

        initListeners(this.frame);
    }

    public void showFrame() {
        this.frame.setVisible(true);
    }

    public void setMenuBarEnabled(boolean enabled) {
        TitanFileMenu fileMenu = frame.getTitanMenuBar().getTitanFileMenu();
        fileMenu.setEnabledAll(enabled);
        fileMenu.getNewDSMMenuItem().setEnabled(true);
        fileMenu.getOpenDSMMenuItem().setEnabled(true);

        TitanViewMenu viewMenu = frame.getTitanMenuBar().getTitanViewMenu();
        viewMenu.getRedrawMenuItem().setEnabled(enabled);
        viewMenu.getShowRowLabelMenuItem().setEnabled(enabled);
    }

    public void setToolBarEnabled(boolean enabled) {
        frame.getTitanToolBar().setEnabledAll(enabled);
        frame.getTitanToolBar().getOpenDSMButton().setEnabled(true);
    }

    public void setLeftToolBarEnabled(boolean enabled) {
        frame.getTitanLeftPanel().getToolBar().setEnabledAll(enabled);
    }

    public void setLeftToolBarPartialEnabled(boolean group, boolean ungroup, boolean up, boolean down, boolean delete) {
        TitanLeftToolBar leftToolBar = frame.getTitanLeftPanel().getToolBar();

        leftToolBar.getGroupButton().setEnabled(group);
        leftToolBar.getUngroupButton().setEnabled(ungroup);
        leftToolBar.getMoveUpButton().setEnabled(up);
        leftToolBar.getMoveDownButton().setEnabled(down);
        leftToolBar.getDeleteButton().setEnabled(delete);
    }

    public void setTreeModel(TreeModel treeModel) {
        frame.getTitanLeftPanel().getTree().setModel(treeModel);
    }

    private void initListeners(TitanFrame frame) {
        // FileMenu
        TitanFileMenu fileMenu = frame.getTitanMenuBar().getTitanFileMenu();

        fileMenu.getNewDSMMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.newDSM(frame);
            }
        });

        fileMenu.getOpenDSMMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openDSM(frame);
            }
        });

        // Help
        frame.getTitanMenuBar().getTitanHelpMenu().getAboutMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "About"); // TODO: Fill contents
            }
        });

        // ToolBar
        TitanToolBar toolBar = frame.getTitanToolBar();
        toolBar.getOpenDSMButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openDSM(frame);
            }
        });

        // Tree
        TitanTree tree = frame.getTitanLeftPanel().getTree();
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                controller.checkSelection(tree.getSelectionPaths());
            }
        });


    }
}
