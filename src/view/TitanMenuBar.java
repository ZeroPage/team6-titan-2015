package view;

import view.menu.TitanFileMenu;
import view.menu.TitanHelpMenu;
import view.menu.TitanViewMenu;

import javax.swing.*;

public class TitanMenuBar extends JMenuBar {
    public TitanMenuBar() {
        super();

        // Init Menus
        add(new TitanFileMenu());
        add(new TitanViewMenu());
        add(new TitanHelpMenu());
    }
}
