package controller;

import model.NotPositiveException;
import model.TitanDSM;
import model.WrongDSMFormatException;
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
        this.view.setMenuBarEnabled(false);
        this.view.setToolBarEnabled(false);
        this.view.setLeftToolBarEnabled(false);
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

        view.setLeftToolBarPartialEnabled(canGroup, canUngroup, canMoveUp, canMoveDown, canDelete);
    }

    private void setDSM(TitanDSM dsm) {
        this.dsm = dsm;

        if (dsm == null) {
            return;
        }

        // FIXME: Temporary implementation
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");

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

        view.setTreeModel(this.treeModel);
        view.setMenuBarEnabled(true);
        view.setToolBarEnabled(true);
        view.setLeftToolBarEnabled(true);
        view.setLeftToolBarPartialEnabled(false, false, false, false, false);
        view.redrawTable();
    }
}
