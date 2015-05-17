package view.main;

import controller.TitanMainController;

import javax.swing.*;

public class TitanToolBar extends JToolBar{
    private TitanMainController controller;

    public TitanToolBar(TitanMainController controller) {
        super();

        // Init fields
        this.controller = controller;

        // Add components
        add(new OpenDSMButton());
        add(new RedrawButton());
        addSeparator();
        add(new NewClusterButton());
        add(new OpenClusterButton());
        add(new SaveClusterButton());
        add(new SaveAsClusterButton());
    }

    private class OpenDSMButton extends JButton {
        public OpenDSMButton() {
            super();
            setIcon(new ImageIcon("res/open-dsm.png"));
            setToolTipText("Open DSM");

        }
    }

    private class RedrawButton extends JButton {
        public RedrawButton() {
            super();
            setIcon(new ImageIcon("res/redraw.png"));
            setToolTipText("Redraw");

        }
    }

    private class NewClusterButton extends JButton {
        public NewClusterButton() {
            super();
            setIcon(new ImageIcon("res/new-clsx.png"));
            setToolTipText("New Clustering");
        }
    }

    private class OpenClusterButton extends JButton {
        public OpenClusterButton() {
            super();
            setIcon(new ImageIcon("res/open-clsx.png"));
            setToolTipText("Open Clustering");
        }
    }

    private class SaveClusterButton extends JButton {
        public SaveClusterButton() {
            super();
            setIcon(new ImageIcon("res/save-clsx.png"));
            setToolTipText("Save Clustering");
        }
    }

    private class SaveAsClusterButton extends JButton {
        public SaveAsClusterButton() {
            super();
            setIcon(new ImageIcon("res/save-clsx-as.png"));
            setToolTipText("Save Clustering As");
        }
    }
}
