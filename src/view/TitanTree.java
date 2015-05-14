package view;

import controller.TitanMainController;

import javax.swing.*;

public class TitanTree extends JTree {
    private TitanMainController controller;

    public TitanTree(TitanMainController controller) {
        // Init Tree
        super();

        // Init fields
        this.controller = controller;
    }
}
