package view;

import javax.swing.*;

public class TitanToolBar extends JToolBar{
    public TitanToolBar() {
        super();

        add(new OpenButton());
    }

    private class OpenButton extends JButton {
        public OpenButton() {
            super();
            setIcon(new ImageIcon("res/temp.jpg"));
            setToolTipText("Open DSM");

        }
    }
}
