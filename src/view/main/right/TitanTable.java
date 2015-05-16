package view.main.right;

import controller.TitanMainController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TitanTable extends JTable {
    private TitanMainController controller;

    public TitanTable(TitanMainController controller) {
        // Init Table
        super();
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Init fields
        this.controller = controller;

        // Init data
        setSampleData();
    }

    private void setSampleData() { // TODO: remove after full implementation
        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("");
        tableModel.addColumn("1");
        tableModel.addColumn("2");

        tableModel.addRow(new Object[] {"foo", ".", ""});
        tableModel.addRow(new Object[] {"bar", "", "."});

        setModel(tableModel);
    }
}
