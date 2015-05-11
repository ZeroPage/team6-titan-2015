package view;

import javax.swing.*;
import java.awt.*;

public class TitanMainPanel extends JPanel {
    public TitanMainPanel() {
        setLayout(new BorderLayout());

        add(new TitanToolBar(), BorderLayout.NORTH);
        add(new TitanSplitPanel(), BorderLayout.CENTER);
    }
}
