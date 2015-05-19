package view.main.left;

import controller.TitanMainController;

import javax.swing.*;

public class TitanTree extends JTree {
    private TitanMainController mainController;
    private JPopupMenu popupMenu;

    public TitanTree(TitanMainController mainController) {
        // Init Tree
        super();
        setModel(null);

        // Init fields
        this.mainController = mainController;
        this.popupMenu = new TitanLeftPopupMenu(mainController);
    }

    public void showPopup(int x, int y) {
        popupMenu.show(this, x, y);
    }
}
