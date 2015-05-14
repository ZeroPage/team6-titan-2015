package view.left;

import controller.TitanMainController;

import javax.swing.*;

public class TitanLeftPopupMenu extends JPopupMenu {
    private TitanMainController controller;

    public TitanLeftPopupMenu(TitanMainController controller) {
        // Init fields
        this.controller = controller;

        // Init PopupMenu
        add(new RenameMenuItem());
    }

    private class RenameMenuItem extends JMenuItem {
        public RenameMenuItem() {
            super("Rename");
        }
    }

    // TODO: Add menus
}
