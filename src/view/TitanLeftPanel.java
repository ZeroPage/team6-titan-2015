package view;

import javax.swing.*;
import java.awt.*;

public class TitanLeftPanel extends JPanel {
    public TitanLeftPanel() {
        super(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(new TitanTree(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(new TitanLeftToolBar(), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
