package components.menu;

import controller.TitanMainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class TitanFileMenu extends JMenu {
    private NewDSMMenuItem newDSMMenuItem;
    private OpenDSMMenuItem openDSMMenuItem;
    private SaveDSMMenuItem saveDSMMenuItem;
    private NewClusterMenuItem newClusterMenuItem;
    private LoadClusterMenuItem loadClusterMenuItem;
    private SaveClusterMenuItem saveClusterMenuItem;
    private SaveAsClusterMenuItem saveAsClusterMenuItem;
    private ExportAsMenu exportAsMenu;
    private ExitMenuItem exitMenuItem;

    private ArrayList<Component> components;

    public TitanFileMenu() {
        super("File");

        // Init Menu
        setMnemonic(KeyEvent.VK_F);

        // Init fields
        newDSMMenuItem = new NewDSMMenuItem();
        openDSMMenuItem = new OpenDSMMenuItem();
        saveDSMMenuItem = new SaveDSMMenuItem();
        newClusterMenuItem = new NewClusterMenuItem();
        loadClusterMenuItem = new LoadClusterMenuItem();
        saveClusterMenuItem = new SaveClusterMenuItem();
        saveAsClusterMenuItem = new SaveAsClusterMenuItem();
        exportAsMenu = new ExportAsMenu();
        exitMenuItem = new ExitMenuItem();

        components = new ArrayList<>();
        components.add(newDSMMenuItem);
        components.add(openDSMMenuItem);
        components.add(saveDSMMenuItem);
        components.add(newClusterMenuItem);
        components.add(loadClusterMenuItem);
        components.add(saveClusterMenuItem);
        components.add(saveAsClusterMenuItem);
        components.add(exportAsMenu);
        components.add(exitMenuItem);


        // Init Contents
        add(newDSMMenuItem);
        add(openDSMMenuItem);
        addSeparator();
        add(saveDSMMenuItem);
        addSeparator();
        add(newClusterMenuItem);
        add(loadClusterMenuItem);
        addSeparator();
        add(saveClusterMenuItem);
        add(saveAsClusterMenuItem);
        addSeparator();
        add(exportAsMenu);
        addSeparator();
        add(exitMenuItem);
    }

    public NewDSMMenuItem getNewDSMMenuItem() {
        return newDSMMenuItem;
    }

    public OpenDSMMenuItem getOpenDSMMenuItem() {
        return openDSMMenuItem;
    }

    public SaveDSMMenuItem getSaveDSMMenuItem() {
        return saveDSMMenuItem;
    }

    public NewClusterMenuItem getNewClusterMenuItem() {
        return newClusterMenuItem;
    }

    public LoadClusterMenuItem getLoadClusterMenuItem() {
        return loadClusterMenuItem;
    }

    public SaveClusterMenuItem getSaveClusterMenuItem() {
        return saveClusterMenuItem;
    }

    public SaveAsClusterMenuItem getSaveAsClusterMenuItem() {
        return saveAsClusterMenuItem;
    }

    public ExportAsMenu getExportAsMenu() {
        return exportAsMenu;
    }

    public ExitMenuItem getExitMenuItem() {
        return exitMenuItem;
    }

    public void setEnabledAll(boolean enabled) {
        for (Component component : components) {
            component.setEnabled(enabled);
        }
    }

    public class NewDSMMenuItem extends JMenuItem {
        public NewDSMMenuItem() {
            super("New DSM");
        }
    }

    public class OpenDSMMenuItem extends JMenuItem {
        public OpenDSMMenuItem() {
            super("Open DSM...", new ImageIcon("res/open-dsm.png"));

            setMnemonic(KeyEvent.VK_O);
            setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        }
    }

    public class SaveDSMMenuItem extends JMenuItem {
        public SaveDSMMenuItem() {
            super("Save DSM...");
        }
    }

    public class NewClusterMenuItem extends JMenuItem {
        public NewClusterMenuItem() {
            super("New Clustering", new ImageIcon("res/new-clsx.png"));

            setMnemonic(KeyEvent.VK_N);
        }
    }

    public class LoadClusterMenuItem extends JMenuItem {
        public LoadClusterMenuItem() {
            super("Load Clustering...", new ImageIcon("res/open-clsx.png"));

            setMnemonic(KeyEvent.VK_L);
            setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK + KeyEvent.SHIFT_MASK));
        }
    }

    public class SaveClusterMenuItem extends JMenuItem {
        public SaveClusterMenuItem() {
            super("Save Clustering", new ImageIcon("res/save-clsx.png"));

            setMnemonic(KeyEvent.VK_S);
            setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
        }
    }

    public class SaveAsClusterMenuItem extends JMenuItem {
        public SaveAsClusterMenuItem() {
            super("Save Clustering As...", new ImageIcon("res/save-clsx-as.png"));

            setMnemonic(KeyEvent.VK_A);
        }
    }

    public class ExportAsMenu extends JMenu {
        public ExportDSMMenuItem exportDSMMenuItem;

        public ExportAsMenu() {
            super("Export As");

            setMnemonic(KeyEvent.VK_E);

            exportDSMMenuItem = new ExportDSMMenuItem();

            add(exportDSMMenuItem);
        }

        public class ExportDSMMenuItem extends JMenuItem {
            public ExportDSMMenuItem() {
                super("DSM...", new ImageIcon("res/dsm.png"));

                setMnemonic(KeyEvent.VK_D);
            }
        }
    }

    public class ExitMenuItem extends JMenuItem {
        public ExitMenuItem() {
            super("Exit");

            setMnemonic(KeyEvent.VK_X);
        }
    }
}