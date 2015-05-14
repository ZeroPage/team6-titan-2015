package view.menu;

import controller.TitanMainController;

import javax.swing.*;

public class TitanViewMenu extends JMenu {
    private TitanMainController controller;

    public TitanViewMenu(TitanMainController controller) {
        super("View");

        // Init fields
        this.controller = controller;
    }

    // TODO: Add Components
}
