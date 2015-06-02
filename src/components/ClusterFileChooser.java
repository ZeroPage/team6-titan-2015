package components;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ClusterFileChooser extends JFileChooser {
    public static final String SUFFIX = "clsx";

    public ClusterFileChooser() {
        setFileSelectionMode(JFileChooser.FILES_ONLY);
        setFileFilter(new FileNameExtensionFilter("Cluster File (*." + SUFFIX + ")", SUFFIX));
    }
}
