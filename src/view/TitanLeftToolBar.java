package view;

import javax.swing.*;

public class TitanLeftToolBar extends JToolBar{
    public TitanLeftToolBar() {
        super();
        setFloatable(false);

        add(new ExpandButton());
    }

    private class ExpandButton extends JButton {
        public ExpandButton() {
            super();

            setIcon(new ImageIcon("res/temp.jpg"));
            setToolTipText("Expand All");
        }
    }
}
