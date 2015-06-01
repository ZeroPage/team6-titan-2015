package components;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ClusterFileChooser extends JFileChooser {
    public ClusterFileChooser() {
        setFileSelectionMode(JFileChooser.FILES_ONLY);
        setFileFilter(new FileNameExtensionFilter("Cluster File (*.clsx)", "clsx"));
    }
}
