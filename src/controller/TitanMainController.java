package controller;

import model.NotPositiveException;
import model.TitanDSM;
import model.WrongDSMFormatException;
import view.TitanDataView;
import view.TitanMainView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class TitanMainController {
    // Models
    private TitanDSM dsm;
    private TreeModel treeModel;

    // Views
    private TitanMainView view;

    public TitanMainController() {
        this.view = new TitanMainView(this);

        this.view.getMenuView().setEnabled(false);
        this.view.getToolBarView().setEnabled(false);
        this.view.getDataView().setToolBarEnabled(false);
    }

    public void openDialog() {
        view.showFrame();
    }

    public TitanDSM getDSM(){
        return dsm;
    }

    public void newDSM(Component parent) {
        String userInput = JOptionPane.showInputDialog(parent, "Input Size: ", 10);

        if (userInput != null) {
            try {
                int size = Integer.valueOf(userInput);
                dsm = new TitanDSM(size);
                setDSM(dsm);
            } catch (NumberFormatException | NotPositiveException exception) {
                JOptionPane.showMessageDialog(parent, "Invalid Input", "ERROR", JOptionPane.ERROR_MESSAGE);
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
                dsm = new TitanDSM(fileChooser.getSelectedFile());
                setDSM(dsm);
            } catch (IOException | WrongDSMFormatException exception) {
                JOptionPane.showMessageDialog(parent, "Filed to open file.", "ERROR", JOptionPane.ERROR_MESSAGE);
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
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();

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
                int childIndex = treeModel.getIndexOfChild(parent, self);
                int parentSize = treeModel.getChildCount(parent);

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
        DefaultMutableTreeNode[] rows = dataView.getVisibleRows((DefaultMutableTreeNode) dataView.getTreeModel().getRoot(), false);
        ArrayList<String> names = new ArrayList<>();
        ArrayList<int[]> tempGroups = new ArrayList<>();

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

            names.add(row.toString());
            currentRow++;
        }

        int finalSize = names.size();
        boolean[][] data = new boolean[finalSize][finalSize]; // TODO
        int[][] group = new int[finalSize][finalSize];

        for (int[] tempGroup : tempGroups) {
            for (int i = tempGroup[0]; i <= tempGroup[1]; i++) {
                for (int j = tempGroup[0]; j <= tempGroup[1]; j++) {
                    group[i][j]++;
                }
            }
        }

        view.getDataView().drawTable(names.toArray(new String[finalSize]), data, group);
    }

    // TODO: implements needed
    public void groupItems(DefaultMutableTreeNode[] items) {
        System.out.println("GROUP");

        for (DefaultMutableTreeNode item : items) {
            System.out.println(item);
        }
    }

    public void ungroupItems(DefaultMutableTreeNode[] items) {
        System.out.println("UNGROUP");

        for (DefaultMutableTreeNode item : items) {
            System.out.println(item);
        }
    }

    public void moveUpItems(DefaultMutableTreeNode[] items) {
        System.out.println("UP");

        for (DefaultMutableTreeNode item : items) {
            System.out.println(item);
        }
    }

    public void moveDownItems(DefaultMutableTreeNode[] items) {
        System.out.println("DOWN");

        for (DefaultMutableTreeNode item : items) {
            System.out.println(item);
        }
    }

    public void deleteItems(DefaultMutableTreeNode[] items) {
        System.out.println("DELETE");

        for (DefaultMutableTreeNode item : items) {
            System.out.println(item);
        }
    }

    public void newItem(String name) {
        System.out.println("NEW");
        System.out.println(name);
    }

    private void setDSM(TitanDSM dsm) {
        this.dsm = dsm;

        if (dsm == null) {
            return;
        }

        // FIXME: Temporary implementation
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root", true);

        DefaultMutableTreeNode tempGroup = new DefaultMutableTreeNode("test.group", true);
        DefaultMutableTreeNode tempGroup2 = new DefaultMutableTreeNode("test.group.2", true);
        tempGroup.add(new DefaultMutableTreeNode("test_1", false));
        tempGroup.add(new DefaultMutableTreeNode("test_2", false));
        tempGroup.add(new DefaultMutableTreeNode("test_3", false));

        tempGroup2.add(new DefaultMutableTreeNode("test_4", false));
        tempGroup2.add(new DefaultMutableTreeNode("test_5", false));

        tempGroup.add(tempGroup2);
        root.add(tempGroup);

        int size = dsm.getSize();
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            root.add(new DefaultMutableTreeNode(dsm.getName(i), false));
            names.add(dsm.getName(i));
        }

        this.treeModel = new DefaultTreeModel(root, true);

        view.getDataView().setTreeModel(this.treeModel);
        view.getMenuView().setEnabled(true);
        view.getToolBarView().setEnabled(true);
        view.getDataView().setToolBarEnabled(true);
        view.getDataView().setToolBarPartialEnabled(false, false, false, false, false);

        drawTable();
    }
}
