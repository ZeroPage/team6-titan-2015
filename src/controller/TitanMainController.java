package controller;

import model.*;
import view.TitanDataView;
import view.TitanMainView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TitanMainController {
    // Models
    private TreeData treeData;
    private DefaultMutableTreeNode root;

    // View
    private TitanMainView view;

    public TitanMainController() {
        // Init view
        this.view = new TitanMainView(this);

        this.view.getMenuView().setEnabled(false);
        this.view.getToolBarView().setEnabled(false);
        this.view.getDataView().setToolBarEnabled(false);
    }

    public void openDialog() {
        view.showFrame();
    }

    public void newDSM(Component parent) {
        String userInput = JOptionPane.showInputDialog(parent, "Input Size: ", 10);

        if (userInput != null) {
            try {
                int size = Integer.valueOf(userInput);
                setTreeData(new TreeData(size));
            } catch (NumberFormatException | NotPositiveException | WrongDSMFormatException | IOException exception) {
                JOptionPane.showMessageDialog(parent, "Invalid Input", "ERROR", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
            }
        }
    }

    public void openDSM(Component parent) {
        // Init fileChooser
        JFileChooser fileChooser = new JFileChooser(new File("."));
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("DSM File (*.dsm)", "dsm"));

        // Show FileChooser
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                setTreeData(new TreeData(fileChooser.getSelectedFile()));
            } catch (IOException | WrongDSMFormatException exception) {
                JOptionPane.showMessageDialog(parent, "Filed to open file.", "ERROR", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
            }
        }
    }

    public void openCluster(Component parent) {
        // Init fileChooser
        JFileChooser fileChooser = new JFileChooser(new File("."));
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Cluster File (*.clsx)", "clsx"));

        // Show FileChooser
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                treeData.loadClusterData(fileChooser.getSelectedFile());
                setTreeRoot(treeData.getTree());
            } catch (IOException | WrongXMLNamespaceException e) {
                JOptionPane.showMessageDialog(parent, "Filed to open file.", "ERROR", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public void checkSelection(TreePath[] paths) {
        boolean canGroup = true;
        boolean canUngroup = true;
        boolean canMoveUp = true;
        boolean canMoveDown = true;
        boolean canDelete = true;

        if (paths == null || paths.length == 0) {
            canGroup = false;
            canUngroup = false;
            canMoveUp = false;
            canMoveDown = false;
            canDelete = false;
        } else {
            DefaultMutableTreeNode lastParent = null;
            DefaultMutableTreeNode root = view.getDataView().getRoot();

            for (TreePath path : paths) {
                DefaultMutableTreeNode self = (DefaultMutableTreeNode) path.getLastPathComponent();

                if (root == self) {
                    canGroup = false;
                    canMoveDown = false;
                    canMoveUp = false;
                    canDelete = false;
                    canUngroup = false;

                    continue;
                }

                DefaultMutableTreeNode parent = (DefaultMutableTreeNode) path.getParentPath().getLastPathComponent();

                int childIndex = parent.getIndex(self);
                int parentSize = parent.getChildCount();

                canMoveUp &= childIndex != 0;
                canMoveDown &= childIndex != parentSize - 1;
                canUngroup &= self.getAllowsChildren();

                if (lastParent != null) {
                    canGroup &= lastParent == parent;
                }

                lastParent = parent;
            }
        }

        view.getDataView().setToolBarPartialEnabled(canGroup, canUngroup, canMoveUp, canMoveDown, canDelete);
    }

    public void drawTable() {
        TitanDataView dataView = view.getDataView();
        DefaultMutableTreeNode[] rows = dataView.getVisibleRows(view.getDataView().getRoot(), false);
        ArrayList<DefaultMutableTreeNode> selectedRows = new ArrayList<>();
        ArrayList<int[]> tempGroups = new ArrayList<>();

        // Prepare names
        int currentRow = 0;
        for (DefaultMutableTreeNode row : rows) {
            if (row.getAllowsChildren()) {
                if (dataView.isExpanded(new TreePath(row.getPath()))) {
                    // exclude from table but still affect coloring
                    tempGroups.add(new int[] {currentRow, currentRow + dataView.getVisibleRows(row, true).length - 1});
                    continue;
                } else {
                    tempGroups.add(new int[]{currentRow, currentRow});
                }
            }

            selectedRows.add(row);
            currentRow++;
        }

        int finalSize = selectedRows.size();
        String[] name = new String[finalSize];
        boolean[][] data = new boolean[finalSize][finalSize];
        int[][] group = new int[finalSize][finalSize];

        // Prepare Name
        for (int i = 0; i < finalSize; i++) {
            String prefix = "";
            if (view.getMenuView().isShowRowLabelsSelected()) {
                prefix = (i + 1) + " ";
            }

            name[i] = prefix + selectedRows.get(i);
        }

        // Prepare Data
        for (int i = 0; i < finalSize; i++) {
            for (int j = 0; j < finalSize; j++) {
                try {
                    data[i][j] = treeData.getDSMValue(selectedRows.get(i), selectedRows.get(j));
                } catch (ArrayIndexOutOfBoundsException exception) {
                    exception.printStackTrace();
                }
            }
        }

        // Prepare groups
        for (int[] tempGroup : tempGroups) {
            for (int i = tempGroup[0]; i <= tempGroup[1]; i++) {
                for (int j = tempGroup[0]; j <= tempGroup[1]; j++) {
                    group[i][j]++;
                }
            }
        }

        view.getDataView().drawTable(name, data, group);
    }

    public void groupItems(Component parent, DefaultMutableTreeNode[] items) {
        String userInput = JOptionPane.showInputDialog(parent, "New Group name: ", "new_group_name");

        if (userInput != null) {
            treeData.groupElement(new ArrayList<DefaultMutableTreeNode>(Arrays.asList(items)), userInput);
            drawTree();
        }
    }

    public void ungroupItems(DefaultMutableTreeNode[] items) {
        for (DefaultMutableTreeNode item : items) {
            try {
                treeData.freeGroup(item);
                drawTree();
            } catch (NodeNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void moveUpItems(DefaultMutableTreeNode[] items) {
        for (DefaultMutableTreeNode item : items) {
            try {
                treeData.repositionElement(item, item.getParent().getIndex(item) - 1);
            } catch (NodeNotFoundException e) {
                e.printStackTrace();
            }
        }

        drawTree();
    }

    public void moveDownItems(DefaultMutableTreeNode[] items) {
        // reverse
        for (int i = 0; i < items.length / 2; i++) {
            DefaultMutableTreeNode temp = items[i];
            items[i] = items[items.length - (i + 1)];
            items[items.length - (i + 1)] = temp;
        }


        for (DefaultMutableTreeNode item : items) {
            try {
                treeData.repositionElement(item, item.getParent().getIndex(item) + 1);
            } catch (NodeNotFoundException e) {
                e.printStackTrace();
            }
        }

        drawTree();
    }

    public void deleteItems(DefaultMutableTreeNode[] items) {
        for (DefaultMutableTreeNode item : items) {
            try {
                treeData.removeElement(item);
            } catch (NodeNotFoundException e) {
                e.printStackTrace();
            }
        }

        drawTree();
    }

    public void newItem(Component parent) {
        String name = JOptionPane.showInputDialog(parent, "Input new item name:");

        if (name != null) {
            try {
                treeData.addElement(root, name);
                drawTree();
            } catch (NodeNotFoundException e) {
                JOptionPane.showMessageDialog(parent, "Filed to add new item.", "ERROR", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void setTreeData(TreeData treeData) {
        this.treeData = treeData;

        view.getMenuView().setEnabled(true);
        view.getToolBarView().setEnabled(true);
        view.getDataView().setToolBarEnabled(true);
        view.getDataView().setToolBarPartialEnabled(false, false, false, false, false);

        setTreeRoot(treeData.getTree());
    }

    private void drawTree() {
        view.getDataView().drawTree();
        drawTable();
    }

    private void setTreeRoot(DefaultMutableTreeNode root) {
        this.root = root;

        view.getDataView().setTreeRoot(root);
        drawTree();
    }
}
