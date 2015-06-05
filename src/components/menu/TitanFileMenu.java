package components.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class TitanFileMenu extends JMenu {
    private NewDSMMenuItem newDSMMenuItem;
    private OpenDSMMenuItem openDSMMenuItem;
    private SaveDSMMenuItem saveDSMMenuItem;
    private SaveASDSMMenuItem saveAsDSMMenuItem;
    private NewClusterMenuItem newClusterMenuItem;
    private LoadClusterMenuItem loadClusterMenuItem;
    private SaveClusterMenuItem saveClusterMenuItem;
    private SaveAsClusterMenuItem saveAsClusterMenuItem;
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
        saveAsDSMMenuItem = new SaveASDSMMenuItem();
        newClusterMenuItem = new NewClusterMenuItem();
        loadClusterMenuItem = new LoadClusterMenuItem();
        saveClusterMenuItem = new SaveClusterMenuItem();
        saveAsClusterMenuItem = new SaveAsClusterMenuItem();
        exitMenuItem = new ExitMenuItem();

        components = new ArrayList<>();
        components.add(newDSMMenuItem);
        components.add(openDSMMenuItem);
        components.add(saveDSMMenuItem);
        components.add(saveAsDSMMenuItem);
        components.add(newClusterMenuItem);
        components.add(loadClusterMenuItem);
        components.add(saveClusterMenuItem);
        components.add(saveAsClusterMenuItem);
        components.add(exitMenuItem);


        // Init Contents
        add(newDSMMenuItem);
        add(openDSMMenuItem);
        addSeparator();
        add(saveDSMMenuItem);
        add(saveAsDSMMenuItem);
        addSeparator();
        add(newClusterMenuItem);
        add(loadClusterMenuItem);
        addSeparator();
        add(saveClusterMenuItem);
        add(saveAsClusterMenuItem);
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

    public SaveASDSMMenuItem getSaveAsDSMMenuItem() {
        return saveAsDSMMenuItem;
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
            
            setIcon(new ImageIcon(getClass().getResource("/resource/export.png")));

            setMnemonic(KeyEvent.VK_N);
            setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        }
    }

    public class OpenDSMMenuItem extends JMenuItem {
        public OpenDSMMenuItem() {
            super("Open DSM...");
            
            setIcon(new ImageIcon(getClass().getResource("/resource/open-dsm.png")));

            setMnemonic(KeyEvent.VK_O);
            setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        }
    }

    public class SaveDSMMenuItem extends JMenuItem {
        public SaveDSMMenuItem() {
            super("Save DSM");
        }
    }


    public class SaveASDSMMenuItem extends JMenuItem {
        public SaveASDSMMenuItem() {
            super("Save DSM AS...");
        }
    }

    public class NewClusterMenuItem extends JMenuItem {
        public NewClusterMenuItem() {
            super("New Clustering");
            
            setIcon(new ImageIcon(getClass().getResource("/resource/new-clsx.png")));

            setMnemonic(KeyEvent.VK_N);
        }
    }

    public class LoadClusterMenuItem extends JMenuItem {
        public LoadClusterMenuItem() {
            super("Load Clustering...");
            
            setIcon(new ImageIcon(getClass().getResource("/resource/open-clsx.png")));

            setMnemonic(KeyEvent.VK_L);
            setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK + KeyEvent.SHIFT_MASK));
        }
    }

    public class SaveClusterMenuItem extends JMenuItem {
        public SaveClusterMenuItem() {
            super("Save Clustering");
            
            setIcon(new ImageIcon(getClass().getResource("/resource/save-clsx.png")));

            setMnemonic(KeyEvent.VK_S);
            setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
        }
    }

    public class SaveAsClusterMenuItem extends JMenuItem {
        public SaveAsClusterMenuItem() {
            super("Save Clustering As...");
            
            setIcon(new ImageIcon(getClass().getResource("/resource/save-clsx-as.png")));

            setMnemonic(KeyEvent.VK_A);
        }
    }

    public class ExitMenuItem extends JMenuItem {
        public ExitMenuItem() {
            super("Exit");

            setMnemonic(KeyEvent.VK_X);
        }
    }
}