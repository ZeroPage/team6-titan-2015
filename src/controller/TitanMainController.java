package controller;

import model.TitanDSM;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TitanMainController {
    private TitanDSM dsm;

    public TitanMainController() {

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
            } catch (NumberFormatException exception) {
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
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(parent, "Filed to open file.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
