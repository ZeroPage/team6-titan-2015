package view.main.left;

import controller.TitanMainController;

import javax.swing.*;

public class TitanLeftPopupMenu extends JPopupMenu {
    private TitanMainController controller;

    public TitanLeftPopupMenu(TitanMainController controller) {
        // Init fields
        this.controller = controller;

        // Init PopupMenu
        add(new RenameMenuItem());
        add(new SortMenuItem());
        addSeparator();
        add(new DuplicateMenuItem());
        add(new ForkMenuItem());
    }

    private class RenameMenuItem extends JMenuItem {
        public RenameMenuItem() {
            super("Rename");
        }
    }

    private class SortMenuItem extends JMenuItem {
        public SortMenuItem() {
            super("Sort");
        }
    }

    private class DuplicateMenuItem extends JMenuItem {
        public DuplicateMenuItem() {
            super("Duplicate");
        }
    }

    private class ForkMenuItem extends JMenuItem {
        public ForkMenuItem() {
            super("Fork");
        }
    }
}
