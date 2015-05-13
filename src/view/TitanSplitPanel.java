package view;

import javax.swing.*;

public class TitanSplitPanel extends JSplitPane {
    public static final int INIT_DIVIDER_LOCATION = 200;

    public TitanSplitPanel() {
        super(JSplitPane.HORIZONTAL_SPLIT);

        JScrollPane scrollPane = new JScrollPane(new TitanTable(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setLeftComponent(new TitanLeftPanel());
        setRightComponent(scrollPane);


        setOneTouchExpandable(true);
        setDividerLocation(INIT_DIVIDER_LOCATION);
    }
}
