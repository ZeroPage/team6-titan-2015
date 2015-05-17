package view.menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class TitanHelpMenu extends JMenu {
    public TitanHelpMenu() {
        super("Help");

        setMnemonic(KeyEvent.VK_H);

        add(new AboutMenuItem());
    }

    private class AboutMenuItem extends JMenuItem {
        public AboutMenuItem() {
            super("About...");

            setMnemonic(KeyEvent.VK_A);

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(TitanHelpMenu.this, "About"); // TODO: Fill contents
                }
            });
        }
    }
}
