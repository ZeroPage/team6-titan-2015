package controller;

import model.*;
import view.TitanDataView;
import view.TitanMainView;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import model.exception.ItemAlreadyExistException;
import model.exception.NotPositiveException;
import model.exception.WrongDSMFormatException;
import model.exception.WrongXMLNamespaceException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class TitanMainController {
    // Models
    private TreeData treeData;
    private DefaultMutableTreeNode root;

    // View
    private TitanMainView view;

    // Forked Controllers
    private ArrayList<TitanMainController> forkedControllers;

    private boolean modifiedDSM;
    private boolean modifiedCluster;

    public TitanMainController() {
        this(null, null, false);
    }

    public TitanMainController(TreeData data, DefaultMutableTreeNode root, boolean forked) {
        // Init view
        this.view = new TitanMainView(this, forked);

        this.view.getMenuView().setDefaultEnabled();
        this.view.getToolBarView().setDefaultEnabled();
        this.view.getDataView().setToolBarEnabled(false);

        // Init extra fields
        forkedControllers = new ArrayList<>();

        setTreeData(data);
        setTreeRoot(root);
    }

    public void showDialog() {
        view.showDialog();
    }

    public void disposeDialog() {
        if (!askBeforeExit()) {
            return;
        }

        if (!view.isForked()) {
            clearForked();
        }

        view.disposeDialog();
    }

    public void newDSM() {
        if (!askBeforeExit()) {
            return;
        }

        String userInput = view.showInput("Input Size: ", "10");

        if (userInput != null) {
            try {
                int size = Integer.valueOf(userInput);
                setTreeData(new TreeData(size));
                view.getFileChooseView().clearLastDSMFile();
                modifiedDSM = false;
            } catch (NumberFormatException | NotPositiveException | WrongDSMFormatException | IOException exception) {
                view.showError("Invalid Input");
                exception.printStackTrace();
            }
        }
    }

    public void openDSM() {
        if (!askBeforeExit()) {
            return;
        }

        File file = view.getFileChooseView().openDSM();

        if (file != null) {
            try {
                setTreeData(new TreeData(file));
                modifiedDSM = false;
            } catch (IOException | WrongDSMFormatException exception) {
                view.showError("Failed to open file.");
                exception.printStackTrace();
            }
        }
    }

    public void saveDSM(){
        File file = view.getFileChooseView().getLastDSMFile();

        if(file == null) {
            saveAsDSM();
        } else {
            try {
                treeData.saveDSMData(file);
                modifiedDSM = false;
            } catch (IOException e) {
                view.showError("Failed to save file.");
            }
        }
    }

    public void saveAsDSM() {
        File file = view.getFileChooseView().saveDSM();

        if (file != null) {
            try {
                treeData.saveDSMData(file);
                modifiedDSM = false;
            } catch (IOException exception) {
                view.showError("Failed to save file.");
                exception.printStackTrace();
            }
        }
    }

    public void newCluster() {
        if (!askBeforeExit()) {
            return;
        }

        treeData.setClusterAsDefault();
        setTreeRoot(treeData.getTreeRoot());

        view.getFileChooseView().clearLastClusterFile();
        modifiedCluster = false;

    }

    public void openCluster() {
        if (!askBeforeExit()) {
            return;
        }

        File file = view.getFileChooseView().openCluster();

        if (file != null) {
            try {
                try {
					treeData.loadClusterData(file);
				} catch (ParserConfigurationException | SAXException e) {
					view.showError("Failed with parsing cluster file.");
					e.printStackTrace();
				}
                setTreeRoot(treeData.getTreeRoot());
                modifiedCluster = false;
            } catch (IOException | WrongXMLNamespaceException exception) {
                view.showError("Failed to open file.");
                exception.printStackTrace();
            }
        }
    }

    public void saveCluster(){
        File file = view.getFileChooseView().getLastClusterFile();

        if(file == null) {
            saveAsCluster();
        } else {
            try {
                treeData.saveClusterData(file);
                modifiedCluster = false;
            } catch (IOException e) {
                view.showError("Failed to save file.");
            }
        }
    }

    public void saveAsCluster() {
        File file = view.getFileChooseView().saveCluster();

        if (file != null) {
            try {
                treeData.saveClusterData(file);
                modifiedCluster = false;
            } catch (IOException exception) {
                view.showError("Failed to save file.");
                exception.printStackTrace();
            }
        }
    }

    public void partition() {
        if (!askBeforeExit()) {
            return;
        }

        treeData.partition();
        setTreeRoot(treeData.getTreeRoot());
        modifiedCluster = true;
    }

    // return true if have to exit
    public boolean askBeforeExit() {
        if (modifiedDSM) {
            int result = view.showConfirm("Save DSM?");

            if (result == JOptionPane.YES_OPTION) {
                saveDSM();
            } else if (result == JOptionPane.CANCEL_OPTION) {
                return false;
            }
        }

        if (modifiedCluster) {
            int result = view.showConfirm("Save Cluster?");

            if (result == JOptionPane.YES_OPTION) {
                saveCluster();
            } else if (result == JOptionPane.CANCEL_OPTION) {
                return false;
            }
        }

        return true;
    }

    public void copyTree(DefaultMutableTreeNode newRoot) {
        new TitanMainController(new TreeData(treeData, newRoot), newRoot, false).showDialog();
        disposeDialog();
    }

    public void forkTree(DefaultMutableTreeNode newRoot) {
        TitanMainController forkController = new TitanMainController(treeData, newRoot, true);
        forkedControllers.add(forkController);

        forkController.showDialog();
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

                if (path.getParentPath() == null) {
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

    public void drawTree() {
        view.getDataView().drawTree();
        drawTable();
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

        if (tempGroups.size() > 0) {
            tempGroups.remove(0); // remove root's grouping
        }

        int finalSize = selectedRows.size();
        boolean[][] data = new boolean[finalSize][finalSize];
        int[][] group = new int[finalSize][finalSize];

        // Prepare Data
        for (int i = 0; i < finalSize; i++) {
            for (int j = 0; j < finalSize; j++) {
                try {
                    data[i][j] = treeData.getDSMValue(selectedRows.get(i), selectedRows.get(j));
                } catch (ArrayIndexOutOfBoundsException | NoSuchElementException exception) {
                    exception.printStackTrace();
                    System.err.println(selectedRows.get(i) + " / " + selectedRows.get(j));
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

        boolean showRowLabel = view.getMenuView().isShowRowLabelsSelected();
        DefaultMutableTreeNode[] elements = selectedRows.toArray(new DefaultMutableTreeNode[selectedRows.size()]);

        view.getDataView().drawTable(elements, data, group, showRowLabel);
    }

    public void groupItems(DefaultMutableTreeNode[] items) {
        String userInput = view.showInput("New Group name: ", "new_group_name");

        if (userInput != null) {
            treeData.groupElement(new ArrayList<>(Arrays.asList(items)), userInput);
            modifiedCluster = true;
            drawTree();
        }
    }

    public void ungroupItems(DefaultMutableTreeNode[] items) {
        for (DefaultMutableTreeNode item : items) {
            try {
                treeData.freeGroup(item);
                drawTree();

                modifiedCluster = true;
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                System.err.println(item);
            }
        }
    }

    public void moveUpItems(DefaultMutableTreeNode[] items) {
        for (DefaultMutableTreeNode item : items) {
            try {
                treeData.repositionElement(item, item.getParent().getIndex(item) - 1);
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                System.err.println(item);
            }
        }

        modifiedCluster = true;
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
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                System.err.println(item);
            }
        }

        modifiedCluster = true;
        drawTree();
    }

    public void deleteItems(DefaultMutableTreeNode[] items) {
        for (DefaultMutableTreeNode item : items) {
            try {
                treeData.removeElement(item);
                modifiedDSM = true;
                modifiedCluster = true;
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                System.err.println(item);
            }
        }

        drawTree();
    }

    public void newItem() {
        String name = view.showInput("Input new item name:", "");

        if (name != null) {
            try {
                treeData.addElement(root, name);
                drawTree();
                modifiedDSM = true;
                modifiedCluster = true;
            } catch (NoSuchElementException e) {
                view.showError("Failed to add new item.");
                e.printStackTrace();
                System.err.println(root);
            } catch (ItemAlreadyExistException e) {
				view.showError("Item with same name already exists.");
				e.printStackTrace();
			}
        }
    }

    public void changeItemValue(DefaultMutableTreeNode from, DefaultMutableTreeNode to) {
        treeData.setDSMData(from, to, !treeData.getDSMValue(from, to));

        modifiedDSM = true;
    }

    public void renameElement(DefaultMutableTreeNode node) {
        String newName = view.showInput("Input new name:", "new_name");

        if (newName != null) {
            try {
                treeData.renameElement(node, newName);
            } catch (ItemAlreadyExistException e) {
                view.showError(newName + " already exists.");
            }
        }

        // FIXME: should check which one has been changed
        modifiedCluster = true;
        modifiedDSM = true;

        drawTree();
    }

    public void sortGroup(DefaultMutableTreeNode group) {
        treeData.sortGroupElements(group);
        drawTree();

        modifiedCluster = true;
    }

    // Set New TreeData. Needs when changing DSM.
    private void setTreeData(TreeData treeData) {
        this.treeData = treeData;

        if (this.treeData != null) {

            view.getMenuView().setEnabledAll(true);
            view.getToolBarView().setEnabledAll(true);
            view.getDataView().setToolBarDefaultEnabled(view.isForked());

            setTreeRoot(treeData.getTreeRoot());

            modifiedDSM = false;
            modifiedCluster = false;
        }
    }

    // Set New root. Needs when DSM is same but cluster has been changed (ex. loadingCluster, ...)
    private void setTreeRoot(DefaultMutableTreeNode root) {
        this.root = root;

        if (this.root != null) {
            view.getDataView().setTreeRoot(root);
            drawTree();
        }

        clearForked();

        modifiedCluster = false;
    }

    // Close all forked windows
    private void clearForked() {
        for (TitanMainController controller : forkedControllers) {
            controller.disposeDialog();
            controller.clearForked();
        }

        forkedControllers.clear();

    }
}
