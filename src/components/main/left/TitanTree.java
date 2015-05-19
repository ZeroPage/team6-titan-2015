package components.main.left;

import controller.TitanMainController;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TitanTree extends JTree {
    public TitanTree() {
        // Init Tree
        super();
        setModel(null);
    }

    // Init fields
    /*
    addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) { // Check right click
                int selectedRow = getRowForLocation(e.getX(), e.getY());

                if (selectedRow > 0) { // Check valid position excluding root
                    TreePath selectedPath = getPathForRow(selectedRow);
                    Object selectedComponent = selectedPath.getLastPathComponent();

                    if (!getModel().isLeaf(selectedComponent)) {
                        popupMenu.show(this, e.getX(), e.getY());
                    }
                }
            }
        }
    });
    */
}
