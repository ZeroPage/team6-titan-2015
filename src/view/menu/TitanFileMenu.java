package view.menu;

import controller.TitanMainController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class TitanFileMenu extends JMenu {
    private TitanMainController controller;

    public TitanFileMenu(TitanMainController controller) {
        super("File");

        // Init fields
        this.controller = controller;

        // Init Menu
        setMnemonic(KeyEvent.VK_F);

        // Init Contents
        add(new NewDSMMenuItem());
        add(new OpenDSMMenuItem());
        addSeparator();
        add(new SaveDSMMenuItem());
        addSeparator();
        add(new NewClusterMenuItem());
        add(new LoadClusterMenuItem());
        addSeparator();
        add(new SaveClusterMenuItem());
        add(new SaveAsClusterMenuItem());
        addSeparator();
        add(new ExportAsMenu());
        addSeparator();
        add(new ExitMenuItem());
    }

    private class NewDSMMenuItem extends JMenuItem {
        public NewDSMMenuItem() {
            super("New DSM");

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.newDSM(NewDSMMenuItem.this);
                }
            });
        }
    }

    private class OpenDSMMenuItem extends JMenuItem {
        public OpenDSMMenuItem() {
            super("Open DSM...");

            setMnemonic(KeyEvent.VK_O);
            setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.openDSM(OpenDSMMenuItem.this);
                }
            });
        }
    }

    private class SaveDSMMenuItem extends JMenuItem {
        public SaveDSMMenuItem() {
            super("Save DSM...");
        }
    }

    private class NewClusterMenuItem extends JMenuItem {
        public NewClusterMenuItem() {
            super("New Clustering");

            setMnemonic(KeyEvent.VK_N);
        }
    }

    private class LoadClusterMenuItem extends JMenuItem {
        public LoadClusterMenuItem() {
            super("Load Clustering...");

            setMnemonic(KeyEvent.VK_L);
            setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK + KeyEvent.SHIFT_MASK));
        }
    }

    private class SaveClusterMenuItem extends JMenuItem {
        public SaveClusterMenuItem() {
            super("Save Clustering");

            setMnemonic(KeyEvent.VK_S);
            setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
        }
    }

    private class SaveAsClusterMenuItem extends JMenuItem {
        public SaveAsClusterMenuItem() {
            super("Save Clustering As...");

            setMnemonic(KeyEvent.VK_A);
        }
    }

    private class ExportAsMenu extends JMenu {
        public ExportAsMenu() {
            super("Export As");

            setMnemonic(KeyEvent.VK_E);

            add(new ExportDSMMenuItem());
        }

        private class ExportDSMMenuItem extends JMenuItem {
            public ExportDSMMenuItem() {
                super("DSM...");

                setMnemonic(KeyEvent.VK_D);
            }
        }
    }

    private class ExitMenuItem extends JMenuItem {
        public ExitMenuItem() {
            super("Exit");

            setMnemonic(KeyEvent.VK_X);

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
        }
    }
}