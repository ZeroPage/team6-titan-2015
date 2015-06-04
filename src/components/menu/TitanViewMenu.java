package components.menu;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class TitanViewMenu extends JMenu {
    private RedrawMenuItem redrawMenuItem;
    private ShowRowLabelMenuItem showRowLabelMenuItem;

    public TitanViewMenu() {
        super("View");

        setMnemonic(KeyEvent.VK_V);

        // Init fields
        redrawMenuItem = new RedrawMenuItem();
        showRowLabelMenuItem = new ShowRowLabelMenuItem();

        // Init components
        add(redrawMenuItem);
        add(showRowLabelMenuItem);
    }

    public RedrawMenuItem getRedrawMenuItem() {
        return redrawMenuItem;
    }

    public ShowRowLabelMenuItem getShowRowLabelMenuItem() {
        return showRowLabelMenuItem;
    }

    public class RedrawMenuItem extends JMenuItem {
        public RedrawMenuItem() {
            super("Redraw", new ImageIcon("res/redraw.png"));

            setMnemonic(KeyEvent.VK_R);
            setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        }
    }

    public class ShowRowLabelMenuItem extends JCheckBoxMenuItem {
        public ShowRowLabelMenuItem() {
            super("Show Row Labels");
            setSelected(true);

            setMnemonic(KeyEvent.VK_L);
        }
    }
}
