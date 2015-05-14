package view;

import controller.TitanMainController;

import javax.swing.*;
import java.awt.*;

public class TitanFrame extends JFrame {
    public static final String FRAME_TITLE = "TITAN";

    public static final int INIT_FRAME_HEIGHT = 500;
    public static final int INIT_FRAME_WIDTH = 600;

    private TitanMainController controller;

    public TitanFrame(TitanMainController controller) {
        super();

        // Init fields
        this.controller = controller;

        // Init Frame
        setLayout(new BorderLayout());
        setTitle(FRAME_TITLE);
        setSize(INIT_FRAME_WIDTH, INIT_FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Init MenuBar
        add(new TitanMenuBar(controller), BorderLayout.NORTH);

        // Init MainPanel
        add(new TitanMainPanel(controller), BorderLayout.CENTER);

    }
}
