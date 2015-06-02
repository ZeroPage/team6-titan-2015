package view;

import components.data.*;
import controller.TitanMainController;

import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

public class TitanDataView {
    private TitanMainController controller;

    private TitanTree tree;
    private TitanTable table;
    private TitanDataToolBar toolBar;

    private TitanGroupPopupMenu groupPopupMenu;
    private TitanItemPopupMenu itemPopupMenu;

    private Component parent;

    private DefaultMutableTreeNode[] lastElements;

    public TitanDataView(TitanMainController controller, TitanDataPanel dataPanel, Component parent) {
        this.controller = controller;
        this.tree = dataPanel.getTree();
        this.table = dataPanel.getTable();
        this.toolBar = dataPanel.getToolBar();
        this.parent = parent;
        this.lastElements = null;

        this.groupPopupMenu = new TitanGroupPopupMenu();
        this.itemPopupMenu = new TitanItemPopupMenu();

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

    public DefaultMutableTreeNode getSelectedRow() {
        DefaultMutableTreeNode[] rows = getSelectedRows();

        if (rows == null) {
            return null;
        } else {
            return rows[0];
        }
    }

    public DefaultMutableTreeNode[] getVisibleRows(DefaultMutableTreeNode root, boolean excludeExpanded) {
        ArrayList<DefaultMutableTreeNode> rows = new ArrayList<>();

        Enumeration<DefaultMutableTreeNode> nodes = root.preorderEnumeration();

        while (nodes.hasMoreElements()) {
            DefaultMutableTreeNode node = nodes.nextElement();

            TreePath path = getRelativePath(node);

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
        return tree.isExpanded(getRelativePath(path));
    }

    public void drawTree() {
        Enumeration<DefaultMutableTreeNode> nodes = getRoot().preorderEnumeration();

        ArrayList<TreePath> expanded = new ArrayList<>();
        TreePath[] selected = tree.getSelectionPaths();

        while (nodes.hasMoreElements()) {
            DefaultMutableTreeNode currentNode = nodes.nextElement();
            TreePath currentPath = getRelativePath(currentNode);

            if (tree.isExpanded(currentPath)) {
                expanded.add(currentPath);
            }
        }

        ((DefaultTreeModel) tree.getModel()).reload();
        collapseAll();

        for (TreePath path : expanded) {
            tree.expandPath(getRelativePath(path));
        }

        if (selected != null) {
            for (TreePath path : selected) {
                tree.addSelectionPath(getRelativePath(path));
            }
        }
    }

    public void drawTable(DefaultMutableTreeNode[] elements, boolean[][] data, int[][] color, boolean showRowLabel) {
        lastElements = elements;

        String[] names = new String[elements.length];

        for (int i = 0; i < elements.length; i++) {
            names[i] = String.valueOf(i + 1) + " ";

            if (showRowLabel) {
                names[i] += elements[i].toString();
            }
        }

        table.setTableContents(names, data, color);
    }

    public void collapseAll() {
        Enumeration<DefaultMutableTreeNode> nodes = getRoot().postorderEnumeration();

        while (nodes.hasMoreElements()) {
            tree.collapsePath(getRelativePath(nodes.nextElement()));
        }
    }

    public void expandAll() {
        Enumeration<DefaultMutableTreeNode> nodes = getRoot().preorderEnumeration();

        while (nodes.hasMoreElements()) {
            tree.expandPath(getRelativePath(nodes.nextElement()));
        }
    }

    // Needs due to forking
    private TreePath getRelativePath(DefaultMutableTreeNode target) {
        TreeNode[] path = target.getPath();

        return new TreePath(Arrays.copyOfRange(path, getRoot().getLevel(), path.length));
    }

    private TreePath getRelativePath(TreePath target) {
        return getRelativePath((DefaultMutableTreeNode) target.getLastPathComponent());
    }

    private void initListeners() {
        // Tree
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                controller.checkSelection(tree.getSelectionPaths());
            }
        });


        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) { // Check right click
                    int selectedRow = tree.getRowForLocation(e.getX(), e.getY());

                    if (selectedRow > 0) { // Check valid position excluding root
                        TreePath selectedPath = tree.getPathForRow(selectedRow);
                        DefaultMutableTreeNode selectedComponent = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();

                        tree.setSelectionPath(selectedPath);

                        if (selectedComponent.getAllowsChildren()) {
                            groupPopupMenu.show(tree, e.getX(), e.getY());
                        } else {
                            itemPopupMenu.show(tree, e.getX(), e.getY());
                        }
                    }
                }
            }
        });

        // Table
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                int row = table.rowAtPoint(e.getPoint());
                int column = table.columnAtPoint(e.getPoint());

                if (row > 0 && column > 0) {
                    DefaultMutableTreeNode from = lastElements[row - 1];
                    DefaultMutableTreeNode to = lastElements[column - 1];

                    if (!from.getAllowsChildren() && !to.getAllowsChildren()) {
                        controller.changeItemValue(from, to);
                        controller.drawTable();
                    }
                }
            }
        });

        // Popup Menus
        groupPopupMenu.getDuplicateMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.copyTree(getSelectedRow());
            }
        });

        groupPopupMenu.getForkMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.forkTree(getSelectedRow());
            }
        });

        groupPopupMenu.getSortMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.sortGroup(getSelectedRow());
            }
        });

        groupPopupMenu.getRenameMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.renameElement(getSelectedRow());
            }
        });

        itemPopupMenu.getRenameMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.renameElement(getSelectedRow());
            }
        });


        // ToolBar Buttons
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
                controller.groupItems(getSelectedRows());
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
                controller.newItem();
            }
        });

        toolBar.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.deleteItems(getSelectedRows());
            }
        });

        toolBar.getPartitionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.partition();
            }
        });
    }
}
