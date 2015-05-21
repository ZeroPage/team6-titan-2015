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
        if (paths == null) {
            return;
        }

        boolean canGroup = true;
        boolean canMoveUp = true;
        boolean canMoveDown = true;
        boolean canDelete = true;

        DefaultMutableTreeNode lastParent = null;
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();

        for (TreePath path : paths) {
            DefaultMutableTreeNode self = (DefaultMutableTreeNode) path.getLastPathComponent();

            if (root == self) {
                canGroup = false;
                canMoveDown = false;
                canMoveUp = false;
                canDelete = false;

                continue;
            }

            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) path.getParentPath().getLastPathComponent();
            int childIndex = treeModel.getIndexOfChild(parent, self);
            int parentSize = treeModel.getChildCount(parent);

            canMoveUp &= childIndex != 0;
            canMoveDown &= childIndex != parentSize - 1;

            if (lastParent != null) {
                canGroup &= lastParent == parent;
            }

            lastParent = parent;
        }

        view.setLeftToolBarPartialEnabled(canGroup, false, canMoveUp, canMoveDown, canDelete);
    }

    private void setDSM(TitanDSM dsm) {
        this.dsm = dsm;

        if (dsm == null) {
            return;
        }

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        int size = dsm.getSize();

        for (int i = 0; i < size; i++) {
            root.add(new DefaultMutableTreeNode(dsm.getName(i)));
        }

        this.treeModel = new DefaultTreeModel(root);

        view.setTreeModel(this.treeModel);
        view.setMenuBarEnabled(true);
        view.setToolBarEnabled(true);
        view.setLeftToolBarEnabled(true);
        view.setLeftToolBarPartialEnabled(false, false, false, false, false);
    }
}
