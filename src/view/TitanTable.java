package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TitanTable extends JTable {
    public TitanTable() {
        super();

        setSampleData();
    }

    private void setSampleData() { // FIXME - remove after full implementation
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("");
        tableModel.addColumn("1");
        tableModel.addColumn("2");

        tableModel.addRow(new Object[] {"foo", ".", ""});
        tableModel.addRow(new Object[] {"bar", "", "."});

        setModel(tableModel);
    }
}
