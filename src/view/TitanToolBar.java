package view;

import controller.TitanMainController;

import javax.swing.*;

public class TitanToolBar extends JToolBar{
    private TitanMainController controller;

    public TitanToolBar(TitanMainController controller) {
        super();

        // Init fields
        this.controller = controller;

        // Add components
        add(new OpenButton());
    }

    private class OpenButton extends JButton {
        public OpenButton() {
            super();
            setIcon(new ImageIcon("res/temp.jpg"));
            setToolTipText("Open DSM");

        }
    }

    // TODO: Add Buttons
}
