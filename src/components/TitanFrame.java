package components;

import components.data.TitanDataPanel;
import components.menu.TitanMenuBar;

import javax.swing.*;
import java.awt.*;

public class TitanFrame extends JFrame {
    public static final String DEFAULT_TITLE = "TITAN";

    public static final int INIT_FRAME_HEIGHT = 500;
    public static final int INIT_FRAME_WIDTH = 600;

    private TitanMenuBar titanMenuBar;
    private TitanToolBar titanToolBar;
    private TitanDataPanel titanDataPanel;

    private String title;
    private String subTitle;

    public TitanFrame() {
        // Init Frame
        super();

        setTitle(DEFAULT_TITLE);
        setSize(INIT_FRAME_WIDTH, INIT_FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int) screen.getWidth() / 5, (int) screen.getHeight() / 5);

        // Init fields
        titanMenuBar = new TitanMenuBar();
        titanToolBar = new TitanToolBar();
        titanDataPanel = new TitanDataPanel();

        // Add components
        Container contentPane = getContentPane();
        JPanel panel = new JPanel(new BorderLayout());

        panel.add(titanToolBar, BorderLayout.NORTH);
        panel.add(titanDataPanel, BorderLayout.CENTER);

        contentPane.setLayout(new BorderLayout());
        contentPane.add(titanMenuBar, BorderLayout.NORTH);
        contentPane.add(panel, BorderLayout.CENTER);
    }

    public void setTitle(String title) {
        this.title = title;
        updateTitle();
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        updateTitle();
    }

    private void updateTitle() {
        String newTitle = title;

        if (subTitle != null) {
            newTitle += " (" + subTitle + ")";
        }

        super.setTitle(newTitle);
    }

    public TitanMenuBar getTitanMenuBar() {
        return titanMenuBar;
    }

    public TitanToolBar getTitanToolBar() {
        return titanToolBar;
    }

    public TitanDataPanel getTitanDataPanel() {
        return titanDataPanel;
    }
}
