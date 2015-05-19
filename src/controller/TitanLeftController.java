package controller;

import model.TitanDSM;
import view.main.left.TitanLeftToolBar;
import view.main.left.TitanTree;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TitanLeftController {
    // Tree related
    private TitanTree tree;
    private TreeModel model;

    // Toolbar related
    private TitanLeftToolBar toolBar;

    public TitanLeftController(TitanTree tree, TitanLeftToolBar toolBar) {
        initTree(tree);
        initToolBar(toolBar);
    }

    public void setDSM(TitanDSM dsm) {
        // FIXME: Temporary implementation
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        int size = dsm.getSize();

        for (int i = 0; i < size; i++) {
            root.add(new DefaultMutableTreeNode(dsm.getName(i)));
        }

        this.model = new DefaultTreeModel(root);
        tree.setModel(this.model);

        toolBar.getCollapseButton().setEnabled(true);
        toolBar.getExpandButton().setEnabled(true);
        toolBar.getNewButton().setEnabled(true);
    }

    private void initTree(TitanTree tree) {
        this.tree = tree;
        this.model = tree.getModel();

        // Init Listeners
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) { // Check right click
                    int selectedRow = tree.getRowForLocation(e.getX(), e.getY());

                    if (selectedRow > 0) { // Check valid position excluding root
                        TreePath selectedPath = tree.getPathForRow(selectedRow);
                        Object selectedComponent = selectedPath.getLastPathComponent();

                        if (!tree.getModel().isLeaf(selectedComponent)) {
                            checkSelection();
                        }
                    }
                }
            }
        });

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                checkSelection();
            }
        });
    }

    private void initToolBar(TitanLeftToolBar toolBar) {
        this.toolBar = toolBar;

        for (Component component : toolBar.getComponents()) {
            component.setEnabled(false);
        }

        // TODO: Fill
    }

    private void checkSelection() {
        TreePath[] paths = tree.getSelectionPaths();

        if (paths == null) {
            return;
        }

        boolean sameGroup = true;
        boolean canMoveUp = true;
        boolean canMoveDown = true;
        boolean canDelete = true;

        DefaultMutableTreeNode lastParent = null;
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

        for (TreePath path : paths) {
            DefaultMutableTreeNode self = (DefaultMutableTreeNode) path.getLastPathComponent();

            if (root == self) {
                sameGroup = false;
                canMoveDown = false;
                canMoveUp = false;
                canDelete = false;

                continue;
            }

            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) path.getParentPath().getLastPathComponent();
            int childIndex = model.getIndexOfChild(parent, self);
            int parentSize = model.getChildCount(parent);

            canMoveUp &= childIndex != 0;
            canMoveDown &= childIndex != parentSize - 1;

            if (lastParent != null) {
                sameGroup &= lastParent == parent;
            }

            lastParent = parent;
        }

        toolBar.getUngroupButton().setEnabled(sameGroup);
        toolBar.getMoveDownButton().setEnabled(canMoveDown);
        toolBar.getMoveUpButton().setEnabled(canMoveUp);
        toolBar.getDeleteButton().setEnabled(canDelete);
    }
}
