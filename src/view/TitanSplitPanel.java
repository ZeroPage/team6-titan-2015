package view;

import javax.swing.*;

public class TitanSplitPanel extends JSplitPane {
    public static final int INIT_DIVIDER_LOCATION = 100;

    public TitanSplitPanel() {
        super(JSplitPane.HORIZONTAL_SPLIT, new JPanel(), new JPanel()); // FIXME - temp panels

        setOneTouchExpandable(true);
        setDividerLocation(INIT_DIVIDER_LOCATION);
    }
}
