package view.menu;

import controller.TitanMainController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class TitanFileMenu extends JMenu {
    private TitanMainController controller;

    public TitanFileMenu(TitanMainController controller) {
        super("File");

        // Init fields
        this.controller = controller;

        // Init Menu
        setMnemonic(KeyEvent.VK_F);

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

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Init fileChooser
                    JFileChooser fileChooser = new JFileChooser(new File("."));
                    fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    fileChooser.setFileFilter(new FileNameExtensionFilter("DSM File (*.dsm)", "dsm"));

                    // Show FileChooser
                    int result = fileChooser.showOpenDialog(OpenMenuItem.this);

                    if (result == JFileChooser.APPROVE_OPTION) {
                        try {
                            controller.openDSM(fileChooser.getSelectedFile());
                        } catch (IOException exception) {
                            JOptionPane.showMessageDialog(OpenMenuItem.this, "Filed to open file.", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
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