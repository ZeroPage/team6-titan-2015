package view;

import components.TitanFrame;
import controller.TitanMainController;

public class TitanMainView {
    private TitanFrame titanFrame;

    private TitanMenuView menuView;
    private TitanToolBarView toolBarView;
    private TitanDataView dataView;

    public TitanMainView(TitanMainController controller) {
        titanFrame = new TitanFrame();

        this.menuView = new TitanMenuView(controller, titanFrame.getTitanMenuBar(), titanFrame);
        this.toolBarView = new TitanToolBarView(controller, titanFrame.getTitanToolBar(), titanFrame);
        this.dataView = new TitanDataView(controller, titanFrame.getTitanDataPanel(), titanFrame);
    }

    public void setCloseOperation(int operation) {
        titanFrame.setDefaultCloseOperation(operation);
    }

    public void setSubTitle(String subTitle) {
        titanFrame.setSubTitle(subTitle);
    }

    public void openDialog() {
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
