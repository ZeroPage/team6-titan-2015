package components.main.left;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TitanTree extends JTree {
    public TitanLeftPopupMenu popupMenu;

    public TitanTree() {
        // Init Tree
        super();
        setModel(null);

        // Init fields
        popupMenu = new TitanLeftPopupMenu();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) { // Check right click
                    int selectedRow = getRowForLocation(e.getX(), e.getY());

                    if (selectedRow > 0) { // Check valid position excluding root
                        TreePath selectedPath = getPathForRow(selectedRow);
                        DefaultMutableTreeNode selectedComponent = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();

                        if (selectedComponent.getAllowsChildren()) {
                            setSelectionPath(selectedPath);
                            popupMenu.show(TitanTree.this, e.getX(), e.getY());
                        }
                    }
                }
            }
        });
    }
}
