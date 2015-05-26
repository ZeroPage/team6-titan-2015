package view;

import components.TitanFrame;
import components.main.left.TitanLeftPanel;
import controller.TitanMainController;

public class TitanMainView {
    private TitanMainController controller;

    private TitanFrame titanFrame;

    private TitanMenuView menuView;
    private TitanToolBarView toolBarView;
    private TitanDataView dataView;

    public TitanMainView(TitanMainController controller) {
        this.controller = controller;

        titanFrame = new TitanFrame();

        TitanLeftPanel left = titanFrame.getTitanLeftPanel();
        this.menuView = new TitanMenuView(controller, titanFrame.getTitanMenuBar(), titanFrame);
        this.toolBarView = new TitanToolBarView(controller, titanFrame.getTitanToolBar(), titanFrame);
        this.dataView = new TitanDataView(controller, left.getTree(), titanFrame.getTitanTable(), left.getToolBar(), titanFrame);
    }

    public void showFrame() {
        this.titanFrame.setVisible(true);
    }

    public TitanMenuView getMenuView() {
        return menuView;
    }

    public TitanToolBarView getToolBarView() {
        return toolBarView;
    }

    public TitanDataView getDataView() {
        return dataView;
    }
}
