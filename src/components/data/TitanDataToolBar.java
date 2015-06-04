package components.data;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TitanDataToolBar extends JToolBar {
    private ExpandButton expandButton;
    private CollapseButton collapseButton;
    private GroupButton groupButton;
    private UngroupButton ungroupButton;
    private MoveUpButton moveUpButton;
    private MoveDownButton moveDownButton;
    private NewButton newButton;
    private DeleteButton deleteButton;
    private PartitionButton partitionButton;

    private ArrayList<Component> components;

    public TitanDataToolBar() {
        // Init ToolBar
        super(JToolBar.HORIZONTAL);
        setFloatable(false);

        // Init fields
        this.expandButton = new ExpandButton();
        this.collapseButton = new CollapseButton();
        this.groupButton = new GroupButton();
        this.ungroupButton = new UngroupButton();
        this.moveUpButton = new MoveUpButton();
        this.moveDownButton = new MoveDownButton();
        this.newButton = new NewButton();
        this.deleteButton = new DeleteButton();
        this.partitionButton = new PartitionButton();

        this.components = new ArrayList<>();
        this.components.add(expandButton);
        this.components.add(collapseButton);
        this.components.add(groupButton);
        this.components.add(ungroupButton);
        this.components.add(moveUpButton);
        this.components.add(moveDownButton);
        this.components.add(newButton);
        this.components.add(deleteButton);
        this.components.add(partitionButton);

        // Add components
        add(expandButton);
        add(collapseButton);
        addSeparator();
        add(groupButton);
        add(ungroupButton);
        addSeparator();
        add(moveUpButton);
        add(moveDownButton);
        addSeparator();
        add(newButton);
        add(deleteButton);
        addSeparator();
        add(partitionButton);
    }

    public ExpandButton getExpandButton() {
        return expandButton;
    }

    public CollapseButton getCollapseButton() {
        return collapseButton;
    }

    public GroupButton getGroupButton() {
        return groupButton;
    }

    public UngroupButton getUngroupButton() {
        return ungroupButton;
    }

    public MoveUpButton getMoveUpButton() {
        return moveUpButton;
    }

    public MoveDownButton getMoveDownButton() {
        return moveDownButton;
    }

    public NewButton getNewButton() {
        return newButton;
    }

    public DeleteButton getDeleteButton() {
        return deleteButton;
    }

    public PartitionButton getPartitionButton() {
        return partitionButton;
    }

    public void setEnabledAll(boolean enabled) {
        for (Component component : components) {
            component.setEnabled(enabled);
        }
    }

    public class ExpandButton extends JButton {
        public ExpandButton() {
            super();

            setIcon(new ImageIcon("res/expand.png"));
            setToolTipText("Expand All");
        }
    }

    public class CollapseButton extends JButton {
        public CollapseButton() {
            super();

            setIcon(new ImageIcon("res/collapse.png"));
            setToolTipText("Collapse All");
        }
    }

    public class GroupButton extends JButton {
        public GroupButton() {
            super();

            setIcon(new ImageIcon("res/group.png"));
            setToolTipText("Group");
        }
    }

    public class UngroupButton extends JButton {
        public UngroupButton() {
            super();

            setIcon(new ImageIcon("res/ungroup.png"));
            setToolTipText("Ungroup");
        }
    }

    public class MoveUpButton extends JButton {
        public MoveUpButton() {
            super();

            setIcon(new ImageIcon("res/up.png"));
            setToolTipText("Move Up");
        }
    }

    public class MoveDownButton extends JButton {
        public MoveDownButton() {
            super();

            setIcon(new ImageIcon("res/down.png"));
            setToolTipText("Move Down");
        }
    }

    public class NewButton extends JButton {
        public NewButton() {
            super();

            setIcon(new ImageIcon("res/new-dsm-line.png"));
            setToolTipText("New Row");
        }
    }

    public class DeleteButton extends JButton {
        public DeleteButton() {
            super();

            setIcon(new ImageIcon("res/delete.png"));
            setToolTipText("Delete");
        }
    }

    public class PartitionButton extends JButton {
        public PartitionButton() {
            super();

            setIcon(new ImageIcon("res/dsm.png"));
            setToolTipText("Partition");
        }
    }
}
