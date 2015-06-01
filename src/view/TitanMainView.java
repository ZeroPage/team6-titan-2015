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
    private TitanFileChooseView fileChooseView;

    private boolean forked;

    public TitanMainView(TitanMainController controller, boolean forked) {
        this.controller = controller;
        this.titanFrame = new TitanFrame();

        this.menuView = new TitanMenuView(controller, titanFrame.getTitanMenuBar(), titanFrame);
        this.toolBarView = new TitanToolBarView(controller, titanFrame.getTitanToolBar(), titanFrame);
        this.dataView = new TitanDataView(controller, titanFrame.getTitanDataPanel(), titanFrame);
        this.fileChooseView = new TitanFileChooseView(titanFrame);

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

    public TitanMenuView getMenuView() {
        return menuView;
    }

    public TitanToolBarView getToolBarView() {
        return toolBarView;
    }

    public TitanDataView getDataView() {
        return dataView;
    }

    public TitanFileChooseView getFileChooseView() {
        return fileChooseView;
    }

    public boolean isForked() {
        return this.forked;
    }

    public void showDialog() {
        if (forked) {
            getMenuView().setFileMenuEnabled(false);
            getToolBarView().setEnabledAll(false);
        }

        this.titanFrame.setVisible(true);
    }

    public void disposeDialog() {
        titanFrame.dispose();
    }


    public void showError(String message) {
        JOptionPane.showMessageDialog(titanFrame, message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public String showInput(String message, String defaultValue) {
        return JOptionPane.showInputDialog(titanFrame, message, defaultValue);
    }

    private void initListeners() {
        titanFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.disposeDialog();
            }
        });
    }
}
