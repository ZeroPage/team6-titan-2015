package components.main.right;

import controller.TitanMainController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TitanTable extends JTable {
    public TitanTable() {
        // Init Table
        super(null);
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setTableHeader(null);
    }
}
