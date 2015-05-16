package view.menu;

import controller.TitanMainController;
import view.menu.TitanFileMenu;
import view.menu.TitanHelpMenu;
import view.menu.TitanViewMenu;

import javax.swing.*;

public class TitanMenuBar extends JMenuBar {
    private TitanMainController controller;

    public TitanMenuBar(TitanMainController controller) {
        super();

        // Init fields
        this.controller = controller;

        // Init Menus
        add(new TitanFileMenu(controller));
        add(new TitanViewMenu(controller));
        add(new TitanHelpMenu());
    }
}
