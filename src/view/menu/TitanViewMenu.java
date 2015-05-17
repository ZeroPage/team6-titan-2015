package view.menu;

import controller.TitanMainController;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class TitanViewMenu extends JMenu {
    private TitanMainController controller;

    public TitanViewMenu(TitanMainController controller) {
        super("View");


        setMnemonic(KeyEvent.VK_V);

        // Init fields
        this.controller = controller;

        // Init components
        add(new RedrawMenuItem());
        add(new ShowRowLabelMenuItem());
    }

    private class RedrawMenuItem extends JMenuItem {
        public RedrawMenuItem() {
            super("Redraw");

            setMnemonic(KeyEvent.VK_R);
            setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        }
    }

    private class ShowRowLabelMenuItem extends JCheckBoxMenuItem {
        public ShowRowLabelMenuItem() {
            super("Show Row Labels");

            setMnemonic(KeyEvent.VK_L);
        }
    }
}
