package view;

import components.main.left.TitanLeftToolBar;
import components.main.left.TitanTree;
import components.main.right.TitanTable;
import controller.TitanMainController;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class TitanDataView {
    private TitanMainController controller;
    private TitanTree tree;
    private TitanTable table;
    private TitanLeftToolBar toolBar;
    private Container parent;

    public TitanDataView(TitanMainController controller, TitanTree tree, TitanTable table, TitanLeftToolBar toolBar, Container parent) {
        this.controller = controller;
        this.tree = tree;
        this.table = table;
        this.toolBar = toolBar;
        this.parent = parent;

        initListeners();
    }

    public DefaultMutableTreeNode[] getSelectedRows() {
        ArrayList<DefaultMutableTreeNode> rows = new ArrayList<>();

        TreePath[] paths = tree.getSelectionPaths();

        if (paths != null) {
            for (TreePath path : paths) {
                rows.add((DefaultMutableTreeNode) path.getLastPathComponent());
            }
        }

        return rows.toArray(new DefaultMutableTreeNode[rows.size()]);
    }

    public DefaultMutableTreeNode[] getVisibleRows(DefaultMutableTreeNode root, boolean excludeExpanded) {
        ArrayList<DefaultMutableTreeNode> rows = new ArrayList<>();

        Enumeration<DefaultMutableTreeNode> nodes = root.preorderEnumeration();
        nodes.nextElement(); // exclude root

        while (nodes.hasMoreElements()) {
            DefaultMutableTreeNode node = nodes.nextElement();

            TreePath path = new TreePath(node.getPath());

            if (tree.isVisible(path) && !(excludeExpanded && tree.isExpanded(path))) {
                rows.add(node);
            }
        }

        return rows.toArray(new DefaultMutableTreeNode[rows.size()]);
    }

    public void setToolBarEnabled(boolean enabled) {
        toolBar.setEnabledAll(enabled);
    }

    public void setToolBarPartialEnabled(boolean group, boolean ungroup, boolean up, boolean down, boolean delete) {
        toolBar.getGroupButton().setEnabled(group);
        toolBar.getUngroupButton().setEnabled(ungroup);
        toolBar.getMoveUpButton().setEnabled(up);
        toolBar.getMoveDownButton().setEnabled(down);
        toolBar.getDeleteButton().setEnabled(delete);
    }

    public void setTreeModel(TreeModel treeModel) {
        tree.setModel(treeModel);
    }
    public TreeModel getTreeModel() {
        return tree.getModel();
    }

    public void redrawTable() {
        // FIXME: Temporary Implementation, Maybe should be in the controller...?
        DefaultMutableTreeNode[] rows = getVisibleRows((DefaultMutableTreeNode) tree.getModel().getRoot(), false);
        ArrayList<String> names = new ArrayList<>();

        table.removeAllSquare();

        int currentRow = 0;
        for (DefaultMutableTreeNode row : rows) {
            if (row.getAllowsChildren()) {
                if (tree.isExpanded(new TreePath(row.getPath()))) {
                    // exclude from table but still affect coloring
                    table.addSquare(currentRow, currentRow + getVisibleRows(row, true).length - 1);
                    continue;
                } else {
                    table.addSquare(currentRow, currentRow);
                }
            }
            names.add(row.toString());
            currentRow++;
        }

        table.setTableContents(names.toArray(new String[names.size()]), new boolean[names.size()][names.size()]);
    }

    private void initListeners() {
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                controller.checkSelection(tree.getSelectionPaths());
            }
        });
    }
}
