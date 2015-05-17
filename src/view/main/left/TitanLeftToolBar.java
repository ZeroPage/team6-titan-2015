package view.main.left;

import controller.TitanMainController;

import javax.swing.*;

public class TitanLeftToolBar extends JToolBar{
    private TitanMainController controller;

    public TitanLeftToolBar(TitanMainController controller) {
        // Init ToolBar
        super(JToolBar.HORIZONTAL);
        setFloatable(false);

        // Init fields
        this.controller = controller;

        // Add components
        add(new ExpandButton());
        add(new CollapseButton());
        addSeparator();
        add(new GroupButton());
        add(new UngroupButton());
        addSeparator();
        add(new MoveUpButton());
        add(new MoveDownButton());
        addSeparator();
        add(new NewButton());
        add(new DeleteButton());
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
