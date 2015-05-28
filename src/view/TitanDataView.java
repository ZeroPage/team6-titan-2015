package view;

import components.main.left.TitanLeftToolBar;
import components.main.left.TitanTree;
import components.main.right.TitanTable;
import controller.TitanMainController;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public DefaultMutableTreeNode getRoot() {
        return (DefaultMutableTreeNode) tree.getModel().getRoot();
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

    public void setTreeRoot(DefaultMutableTreeNode root) {
        tree.setModel(new DefaultTreeModel(root, true));
    }

    public boolean isExpanded(TreePath path) {
        return tree.isExpanded(path);
    }

    public void drawTree() {
        ((DefaultTreeModel) tree.getModel()).reload();
    }

    public void drawTable(String[] names, boolean[][] data, int[][] color) {
        table.setTableContents(names, data, color);
    }

    public void collapseAll() {
        Enumeration<DefaultMutableTreeNode> nodes = getRoot().postorderEnumeration();

        while (nodes.hasMoreElements()) {
            tree.collapsePath(new TreePath(nodes.nextElement().getPath()));
        }
    }

    public void expandAll() {
        Enumeration<DefaultMutableTreeNode> nodes = getRoot().preorderEnumeration();

        while (nodes.hasMoreElements()) {
            tree.expandPath(new TreePath(nodes.nextElement().getPath()));
        }
    }

    private void initListeners() {
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                controller.checkSelection(tree.getSelectionPaths());
            }
        });

        toolBar.getExpandButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                expandAll();
            }
        });

        toolBar.getCollapseButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collapseAll();
            }
        });

        toolBar.getGroupButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.groupItems(parent, getSelectedRows());
            }
        });

        toolBar.getUngroupButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.ungroupItems(getSelectedRows());
            }
        });

        toolBar.getMoveUpButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.moveUpItems(getSelectedRows());
            }
        });

        toolBar.getMoveDownButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.moveDownItems(getSelectedRows());
            }
        });

        toolBar.getNewButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.newItem(parent);
            }
        });

        toolBar.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.deleteItems(getSelectedRows());
            }
        });
    }
}
