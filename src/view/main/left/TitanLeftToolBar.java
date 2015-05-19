package view.main.left;

import controller.TitanMainController;

import javax.swing.*;

public class TitanLeftToolBar extends JToolBar{
    private TitanMainController controller;

    private JButton expandButton;
    private JButton collapseButton;
    private JButton groupButton;
    private JButton ungroupButton;
    private JButton moveUpButton;
    private JButton moveDownButton;
    private JButton newButton;
    private JButton deleteButton;

    public TitanLeftToolBar(TitanMainController controller) {
        // Init ToolBar
        super(JToolBar.HORIZONTAL);
        setFloatable(false);

        // Init fields
        this.controller = controller;
        this.expandButton = new ExpandButton();
        this.collapseButton = new CollapseButton();
        this.groupButton = new GroupButton();
        this.ungroupButton = new UngroupButton();
        this.moveUpButton = new MoveUpButton();
        this.moveDownButton = new MoveDownButton();
        this.newButton = new NewButton();
        this.deleteButton = new DeleteButton();

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
    }

    public JButton getExpandButton() {
        return expandButton;
    }

    public JButton getCollapseButton() {
        return collapseButton;
    }

    public JButton getGroupButton() {
        return groupButton;
    }

    public JButton getUngroupButton() {
        return ungroupButton;
    }

    public JButton getMoveUpButton() {
        return moveUpButton;
    }

    public JButton getMoveDownButton() {
        return moveDownButton;
    }

    public JButton getNewButton() {
        return newButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    private class ExpandButton extends JButton {
        public ExpandButton() {
            super();

            setIcon(new ImageIcon("res/expand.png"));
            setToolTipText("Expand All");
        }
    }

    private class CollapseButton extends JButton {
        public CollapseButton() {
            super();

            setIcon(new ImageIcon("res/collapse.png"));
            setToolTipText("Collapse All");
        }
    }

    private class GroupButton extends JButton {
        public GroupButton() {
            super();

            setIcon(new ImageIcon("res/group.png"));
            setToolTipText("Group");
        }
    }

    private class UngroupButton extends JButton {
        public UngroupButton() {
            super();

            setIcon(new ImageIcon("res/ungroup.png"));
            setToolTipText("Ungroup");
        }
    }

    private class MoveUpButton extends JButton {
        public MoveUpButton() {
            super();

            setIcon(new ImageIcon("res/up.png"));
            setToolTipText("Move Up");
        }
    }

    private class MoveDownButton extends JButton {
        public MoveDownButton() {
            super();

            setIcon(new ImageIcon("res/down.png"));
            setToolTipText("Move Down");
        }
    }

    private class NewButton extends JButton {
        public NewButton() {
            super();

            setIcon(new ImageIcon("res/new-dsm-line.png"));
            setToolTipText("New Row");
        }
    }

    private class DeleteButton extends JButton {
        public DeleteButton() {
            super();

            setIcon(new ImageIcon("res/delete.png"));
            setToolTipText("Delete");
        }
    }
}
