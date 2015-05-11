package view.menubar;

import view.menubar.menu.*;

import javax.swing.*;

public class TitanMenuBar extends JMenuBar {
    public TitanMenuBar() {
        super();

        // Init Menus
        add(new TitanFileMenu());
        add(new TitanMetricsMenu());
        add(new TitanViewMenu());
        add(new TitanHelpMenu());
    }
}
