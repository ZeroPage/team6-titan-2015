package view;

import components.ClusterFileChooser;
import components.DSMFileChooser;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TitanFileChooseView {
    private DSMFileChooser dsmFileChooser;
    private ClusterFileChooser clusterFileChooser;

    private File lastFile;
    private File lastDSMFile;
    private File lastClusterFile;

    private Component parent;

    public TitanFileChooseView(Component parent) {
        dsmFileChooser = new DSMFileChooser();
        clusterFileChooser = new ClusterFileChooser();
        this.parent = parent;

        lastFile = new File(".");
        lastDSMFile = null;
        lastClusterFile = null;
    }

    public File getLastDSMFile() {
        return lastDSMFile;
    }

    public File getLastClusterFile() {
        return lastClusterFile;
    }

    public void clearLastDSMFile() {
        lastDSMFile = null;
    }

    public void clearLastClusterFile() {
        lastClusterFile = null;
    }

    public File openDSM() {
        dsmFileChooser.setCurrentDirectory(lastFile);
        int result = dsmFileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            lastFile = dsmFileChooser.getSelectedFile();
            lastDSMFile = dsmFileChooser.getSelectedFile();

            return lastFile;
        } else {
            return null;
        }
    }

    public File saveDSM() {
        dsmFileChooser.setCurrentDirectory(lastFile);
        int result = dsmFileChooser.showSaveDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            lastFile = dsmFileChooser.getSelectedFile();
            lastDSMFile = dsmFileChooser.getSelectedFile();

            return lastFile;
        } else {
            return null;
        }
    }

    public File openCluster() {
        clusterFileChooser.setCurrentDirectory(lastFile);
        int result = clusterFileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            lastFile = clusterFileChooser.getSelectedFile();
            lastClusterFile = clusterFileChooser.getSelectedFile();

            return lastFile;
        } else {
            return null;
        }
    }

    public File saveCluster() {
        clusterFileChooser.setCurrentDirectory(lastFile);
        int result = clusterFileChooser.showSaveDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            lastFile = clusterFileChooser.getSelectedFile();
            lastClusterFile = clusterFileChooser.getSelectedFile();

            return lastFile;
        } else {
            return null;
        }
    }


}
