package components.main.left;

import controller.TitanMainController;

import javax.swing.*;

public class TitanLeftPopupMenu extends JPopupMenu {
    private RenameMenuItem renameMenuItem;
    private SortMenuItem sortMenuItem;
    private DuplicateMenuItem duplicateMenuItem;
    private ForkMenuItem forkMenuItem;

    public TitanLeftPopupMenu() {
        // Init fields
        renameMenuItem = new RenameMenuItem();
        sortMenuItem = new SortMenuItem();
        duplicateMenuItem = new DuplicateMenuItem();
        forkMenuItem = new ForkMenuItem();

        // Init PopupMenu
        add(renameMenuItem);
        add(sortMenuItem);
        addSeparator();
        add(duplicateMenuItem);
        add(forkMenuItem);
    }

    public RenameMenuItem getRenameMenuItem() {
        return renameMenuItem;
    }

    public SortMenuItem getSortMenuItem() {
        return sortMenuItem;
    }

    public DuplicateMenuItem getDuplicateMenuItem() {
        return duplicateMenuItem;
    }

    public ForkMenuItem getForkMenuItem() {
        return forkMenuItem;
    }

    public class RenameMenuItem extends JMenuItem {
        public RenameMenuItem() {
            super("Rename");
        }
    }

    public class SortMenuItem extends JMenuItem {
        public SortMenuItem() {
            super("Sort");
        }
    }

    public class DuplicateMenuItem extends JMenuItem {
        public DuplicateMenuItem() {
            super("Duplicate");
        }
    }

    public class ForkMenuItem extends JMenuItem {
        public ForkMenuItem() {
            super("Fork");
        }
    }
}
