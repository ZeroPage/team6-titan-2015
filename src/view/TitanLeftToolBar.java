package view;

import controller.TitanMainController;

import javax.swing.*;

public class TitanLeftToolBar extends JToolBar{
    private TitanMainController controller;

    public TitanLeftToolBar(TitanMainController controller) {
        // Init ToolBar
        super(JToolBar.HORIZONTAL);
        setFloatable(false);

        // Init fields
        this.controller = controller;

        // Add components
        add(new ExpandButton());
    }

    private class ExpandButton extends JButton {
        public ExpandButton() {
            super();

            setIcon(new ImageIcon("res/temp.jpg"));
            setToolTipText("Expand All");
        }
    }

    // TODO: Add components
}
