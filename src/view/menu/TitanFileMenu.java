package view.menu;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class TitanFileMenu extends JMenu {
    public static final int SHORTCUT_KEY = KeyEvent.VK_F;

    public TitanFileMenu() {
        super("File");

        // Init Menu
        setMnemonic(SHORTCUT_KEY);

        // Init Contents
        add(new OpenMenuItem());
        addSeparator();
        add(new NewMenuItem());
        add(new LoadMenuItem());
        addSeparator();
        add(new SaveMenuItem());
        add(new SaveAsMenuItem());
        addSeparator();
        add(new ExportAsMenu());
        addSeparator();
        add(new ExitMenuItem());
    }

    private class OpenMenuItem extends JMenuItem {
        public OpenMenuItem() {
            super("Open DSM...");

            setMnemonic(KeyEvent.VK_O);
            setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        }
    }

    private class NewMenuItem extends JMenuItem {
        public NewMenuItem() {
            super("New Clustering");

            setMnemonic(KeyEvent.VK_N);
        }
    }

    private class LoadMenuItem extends JMenuItem {
        public LoadMenuItem() {
            super("Load Clustering...");

            setMnemonic(KeyEvent.VK_L);
            setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK + KeyEvent.SHIFT_MASK));
        }
    }

    private class SaveMenuItem extends JMenuItem {
        public SaveMenuItem() {
            super("Save Clustering");

            setMnemonic(KeyEvent.VK_S);
            setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
        }
    }

    private class SaveAsMenuItem extends JMenuItem {
        public SaveAsMenuItem() {
            super("Save Clustering As...");

            setMnemonic(KeyEvent.VK_A);
        }
    }

    private class ExportAsMenu extends JMenu {
        public ExportAsMenu() {
            super("Export As");

            setMnemonic(KeyEvent.VK_E);

            add(new DSMMenuItem());
        }

        private class DSMMenuItem extends JMenuItem {
            public DSMMenuItem() {
                super("DSM...");

                setMnemonic(KeyEvent.VK_D);
            }
        }
    }

    private class ExitMenuItem extends JMenuItem {
        public ExitMenuItem() {
            super("Exit");

            setMnemonic(KeyEvent.VK_X);
        }
    }
}