package components;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DSMFileChooser extends JFileChooser {
    public static final String SUFFIX = "dsm";

    public DSMFileChooser() {
        // Init fileChooser
        setFileSelectionMode(JFileChooser.FILES_ONLY);
        setFileFilter(new FileNameExtensionFilter("DSM File (*." + SUFFIX + ")", SUFFIX));
    }
}
