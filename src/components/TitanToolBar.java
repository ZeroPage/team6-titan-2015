package components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TitanToolBar extends JToolBar {
    private OpenDSMButton openDSMButton;
    private RedrawButton redrawButton;
    private NewClusterButton newClusterButton;
    private OpenClusterButton openClusterButton;
    private SaveClusterButton saveClusterButton;
    private SaveAsClusterButton saveAsClusterButton;

    private ArrayList<Component> components;

    public TitanToolBar() {
        super();

        // Init fields
        openDSMButton = new OpenDSMButton();
        redrawButton = new RedrawButton();
        newClusterButton = new NewClusterButton();
        openClusterButton = new OpenClusterButton();
        saveClusterButton = new SaveClusterButton();
        saveAsClusterButton = new SaveAsClusterButton();

        components = new ArrayList<>();
        components.add(openDSMButton);
        components.add(redrawButton);
        components.add(newClusterButton);
        components.add(openClusterButton);
        components.add(saveClusterButton);
        components.add(saveAsClusterButton);

        // Add components
        add(openDSMButton);
        add(redrawButton);
        addSeparator();
        add(newClusterButton);
        add(openClusterButton);
        add(saveClusterButton);
        add(saveAsClusterButton);
    }

    public OpenDSMButton getOpenDSMButton() {
        return openDSMButton;
    }

    public RedrawButton getRedrawButton() {
        return redrawButton;
    }

    public NewClusterButton getNewClusterButton() {
        return newClusterButton;
    }

    public OpenClusterButton getOpenClusterButton() {
        return openClusterButton;
    }

    public SaveClusterButton getSaveClusterButton() {
        return saveClusterButton;
    }

    public SaveAsClusterButton getSaveAsClusterButton() {
        return saveAsClusterButton;
    }

    public void setEnabledAll(boolean enabled) {
        for (Component component : components) {
            component.setEnabled(enabled);
        }
    }

    public class OpenDSMButton extends JButton {
        public OpenDSMButton() {
            super();
            setIcon(new ImageIcon("res/open-dsm.png"));
            setToolTipText("Open DSM");

        }
    }

    public class RedrawButton extends JButton {
        public RedrawButton() {
            super();
            setIcon(new ImageIcon("res/redraw.png"));
            setToolTipText("Redraw");

        }
    }

    public class NewClusterButton extends JButton {
        public NewClusterButton() {
            super();
            setIcon(new ImageIcon("res/new-clsx.png"));
            setToolTipText("New Clustering");
        }
    }

    public class OpenClusterButton extends JButton {
        public OpenClusterButton() {
            super();
            setIcon(new ImageIcon("res/open-clsx.png"));
            setToolTipText("Open Clustering");
        }
    }

    public class SaveClusterButton extends JButton {
        public SaveClusterButton() {
            super();
            setIcon(new ImageIcon("res/save-clsx.png"));
            setToolTipText("Save Clustering");
        }
    }

    public class SaveAsClusterButton extends JButton {
        public SaveAsClusterButton() {
            super();
            setIcon(new ImageIcon("res/save-clsx-as.png"));
            setToolTipText("Save Clustering As");
        }
    }
}
