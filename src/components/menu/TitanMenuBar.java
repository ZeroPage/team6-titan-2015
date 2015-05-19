package components.menu;

import javax.swing.*;

public class TitanMenuBar extends JMenuBar {
    private TitanFileMenu titanFileMenu;
    private TitanViewMenu titanViewMenu;
    private TitanHelpMenu titanHelpMenu;

    public TitanMenuBar() {
        super();

        // Init Menus
        titanFileMenu = new TitanFileMenu();
        titanViewMenu = new TitanViewMenu();
        titanHelpMenu = new TitanHelpMenu();

        add(titanFileMenu);
        add(titanViewMenu);
        add(titanHelpMenu);
    }

    public TitanFileMenu getTitanFileMenu() {
        return titanFileMenu;
    }

    public TitanViewMenu getTitanViewMenu() {
        return titanViewMenu;
    }

    public TitanHelpMenu getTitanHelpMenu() {
        return titanHelpMenu;
    }
}
