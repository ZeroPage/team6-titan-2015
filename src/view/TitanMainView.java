package view;

import components.TitanFrame;
import components.main.TitanToolBar;
import components.main.left.TitanLeftToolBar;
import components.main.left.TitanTree;
import components.main.right.TitanTable;
import components.menu.TitanFileMenu;
import components.menu.TitanMenuBar;
import components.menu.TitanViewMenu;
import controller.TitanMainController;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

public class TitanMainView {
    private TitanMainController controller;

    private TitanFrame titanFrame;
    private TitanMenuBar titanMenuBar;
    private TitanToolBar titanToolBar;
    private TitanLeftToolBar titanLeftToolBar;
    private TitanTree titanTree;
    private TitanTable titanTable;

    public TitanMainView(TitanMainController controller) {
        this.controller = controller;

        titanFrame = new TitanFrame();
        titanMenuBar = titanFrame.getTitanMenuBar();
        titanToolBar = titanFrame.getTitanToolBar();
        titanLeftToolBar = titanFrame.getTitanLeftPanel().getToolBar();
        titanTree = titanFrame.getTitanLeftPanel().getTree();
        titanTable = titanFrame.getTitanTable();

        initListeners(this.titanFrame);
    }

    public void showFrame() {
        this.titanFrame.setVisible(true);
    }

    public DefaultMutableTreeNode[] getSelectedRows() {
        ArrayList<DefaultMutableTreeNode> rows = new ArrayList<>();

        TreePath[] paths = titanTree.getSelectionPaths();

        if (paths != null) {
            for (TreePath path : paths) {
                rows.add((DefaultMutableTreeNode) path.getLastPathComponent());
            }
        }

        return rows.toArray(new DefaultMutableTreeNode[rows.size()]);
    }

    public void setMenuBarEnabled(boolean enabled) {
        TitanFileMenu fileMenu = titanMenuBar.getTitanFileMenu();
        fileMenu.setEnabledAll(enabled);
        fileMenu.getNewDSMMenuItem().setEnabled(true);
        fileMenu.getOpenDSMMenuItem().setEnabled(true);
        fileMenu.getExitMenuItem().setEnabled(true);

        TitanViewMenu viewMenu = titanMenuBar.getTitanViewMenu();
        viewMenu.getRedrawMenuItem().setEnabled(enabled);
        viewMenu.getShowRowLabelMenuItem().setEnabled(enabled);
    }

    public void setToolBarEnabled(boolean enabled) {
        titanToolBar.setEnabledAll(enabled);
        titanToolBar.getOpenDSMButton().setEnabled(true);
    }

    public void setLeftToolBarEnabled(boolean enabled) {
        titanLeftToolBar.setEnabledAll(enabled);
    }

    public void setLeftToolBarPartialEnabled(boolean group, boolean ungroup, boolean up, boolean down, boolean delete) {
        titanLeftToolBar.getGroupButton().setEnabled(group);
        titanLeftToolBar.getUngroupButton().setEnabled(ungroup);
        titanLeftToolBar.getMoveUpButton().setEnabled(up);
        titanLeftToolBar.getMoveDownButton().setEnabled(down);
        titanLeftToolBar.getDeleteButton().setEnabled(delete);
    }

    public void setTreeModel(TreeModel treeModel) {
        titanFrame.getTitanLeftPanel().getTree().setModel(treeModel);
    }

    public void redrawTable() {
        // FIXME: Temporary Implementation
        DefaultMutableTreeNode[] rows = getVisibleRows((DefaultMutableTreeNode) titanTree.getModel().getRoot(), false);
        ArrayList<String> names = new ArrayList<>();

        titanTable.removeAllSquare();

        int currentRow = 0;
        for (DefaultMutableTreeNode row : rows) {
            if (row.getAllowsChildren()) {
                if (titanTree.isExpanded(new TreePath(row.getPath()))) {
                    // exclude from table but still affect coloring
                    titanTable.addSquare(currentRow, currentRow + getVisibleRows(row, true).length - 1);
                    continue;
                } else {
                    titanTable.addSquare(currentRow, currentRow);
                }
            }
            names.add(row.toString());
            currentRow++;
        }

        titanTable.setTableContents(names.toArray(new String[names.size()]), new boolean[names.size()][names.size()]);
    }

    private DefaultMutableTreeNode[] getVisibleRows(DefaultMutableTreeNode root, boolean excludeExpanded) {
        ArrayList<DefaultMutableTreeNode> rows = new ArrayList<>();

        Enumeration<DefaultMutableTreeNode> nodes = root.preorderEnumeration();
        nodes.nextElement(); // exclude root

        while (nodes.hasMoreElements()) {
            DefaultMutableTreeNode node = nodes.nextElement();

            TreePath path = new TreePath(node.getPath());

            if (titanTree.isVisible(path) && !(excludeExpanded && titanTree.isExpanded(path))) {
                rows.add(node);
            }
        }

        return rows.toArray(new DefaultMutableTreeNode[rows.size()]);
    }

    private void initListeners(TitanFrame frame) {
        // FileMenu
        TitanFileMenu fileMenu = titanMenuBar.getTitanFileMenu();

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

        fileMenu.getExitMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // View
        titanMenuBar.getTitanViewMenu().getRedrawMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                redrawTable();
            }
        });

        // Help
        titanMenuBar.getTitanHelpMenu().getAboutMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "About"); // TODO: Fill contents
            }
        });

        // ToolBar
        titanToolBar.getOpenDSMButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openDSM(frame);
            }
        });

        // Tree
        titanTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                controller.checkSelection(titanTree.getSelectionPaths());
            }
        });


    }
}
