package components.menu;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class TitanHelpMenu extends JMenu {
    private AboutMenuItem aboutMenuItem;

    public TitanHelpMenu() {
        super("Help");

        setMnemonic(KeyEvent.VK_H);

        aboutMenuItem = new AboutMenuItem();

        add(aboutMenuItem);
    }

    public AboutMenuItem getAboutMenuItem() {
        return aboutMenuItem;
    }

    public class AboutMenuItem extends JMenuItem {
        public AboutMenuItem() {
            super("About...");

            setMnemonic(KeyEvent.VK_A);
        }
    }
}
