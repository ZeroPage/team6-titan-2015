package components.data;

import javax.swing.*;

public class TitanItemPopupMenu extends JPopupMenu {
    private RenameMenuItem renameMenuItem;
    public TitanItemPopupMenu() {
        // Init fields
        renameMenuItem = new RenameMenuItem();

        // Init PopupMenu
        add(renameMenuItem);
    }

    public RenameMenuItem getRenameMenuItem() {
        return renameMenuItem;
    }

    public class RenameMenuItem extends JMenuItem {
        public RenameMenuItem() {
            super("Rename");
        }
    }
}
