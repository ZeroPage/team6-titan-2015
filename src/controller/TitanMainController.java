package controller;

import model.NotPositiveException;
import model.TitanDSM;
import view.main.left.TitanLeftToolBar;
import view.main.left.TitanTree;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TitanMainController {
    // Models
    private TitanDSM dsm;

    // SubController
    private TitanLeftController leftController;

    // Views
    private ArrayList<Component> boundedComponents;
    private boolean componentsEnabled;

    public TitanMainController() {
        boundedComponents = new ArrayList<>();
        componentsEnabled = false;
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
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(parent, "Filed to open file.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void boundComponent(Component component) {
        component.setEnabled(componentsEnabled);
        boundedComponents.add(component);
    }

    public void setLeftComponents(TitanTree titanTree, TitanLeftToolBar toolBar) {
        this.leftController = new TitanLeftController(titanTree, toolBar);
    }

    private void setDSM(TitanDSM dsm) {
        this.dsm = dsm;

        if (dsm == null) {
            disableComponents();
            return;
        }

        enableComponents();
        leftController.setDSM(dsm);
    }

    private void enableComponents() {
        componentsEnabled = true;

        for (Component component : boundedComponents) {
            component.setEnabled(componentsEnabled);
        }
    }

    private void disableComponents() {
        componentsEnabled = false;

        for (Component component : boundedComponents) {
            component.setEnabled(componentsEnabled);
        }
    }
}
