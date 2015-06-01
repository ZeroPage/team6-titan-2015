package components;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DSMFileChooser extends JFileChooser {
    public DSMFileChooser() {
        // Init fileChooser
        setFileSelectionMode(JFileChooser.FILES_ONLY);
        setFileFilter(new FileNameExtensionFilter("DSM File (*.dsm)", "dsm"));
    }
}
