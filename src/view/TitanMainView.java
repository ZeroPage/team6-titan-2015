package view;

import components.TitanFrame;
import controller.TitanMainController;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TitanMainView {
    private TitanMainController controller;

    private TitanFrame titanFrame;

    private TitanMenuView menuView;
    private TitanToolBarView toolBarView;
    private TitanDataView dataView;

    private boolean forked;

    public TitanMainView(TitanMainController controller, boolean forked) {
        this.controller = controller;
        this.titanFrame = new TitanFrame();

        this.menuView = new TitanMenuView(controller, titanFrame.getTitanMenuBar(), titanFrame);
        this.toolBarView = new TitanToolBarView(controller, titanFrame.getTitanToolBar(), titanFrame);
        this.dataView = new TitanDataView(controller, titanFrame.getTitanDataPanel(), titanFrame);

        this.forked = forked;

        if (forked) {
            titanFrame.setTitle(titanFrame.getTitle() + " *FORKED*");
        }

        titanFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        initListeners();
    }

    public void setSubTitle(String subTitle) {
        titanFrame.setSubTitle(subTitle);
    }

    public void setVisible(boolean visible) {
        if (forked) {
            getMenuView().setFileMenuEnabled(false);
            getToolBarView().setEnabledAll(false);
        }

        this.titanFrame.setVisible(visible);
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

    private void initListeners() {
        titanFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (forked) {
                    titanFrame.dispose();
                } else {
                    if (controller.checkExit(titanFrame)) {
                        System.exit(0);
                    }
                }
            }
        });
    }
}
