package components.data;

import com.sun.istack.internal.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseEvent;

public class TitanTable extends JTable {
    private int[][] group;

    private static final Color[] colors = {
            new Color(0, 152, 207), new Color(255, 233, 0),
            new Color(255, 115, 119), new Color(239, 44, 193)
    };

    public TitanTable() {
        // Super Constructor
        super(null);

        // Init Table
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setTableHeader(null);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setRowSelectionAllowed(false);
    }

    public void setTableContents(String[] names, boolean[][] data, int[][] group) {
        this.group = group;

        // Init variables
        int dataSize = names.length;
        int tableSize = names.length + 1;
        DefaultTableModel tableModel = new DefaultTableModel(0, tableSize) {
            // Non Editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(tableModel);
        TableColumnModel columnModel = getColumnModel();

        // Add Header
        String[] columnNames = new String[tableSize];
        columnNames[0] = "";

        for (int i = 1; i < tableSize; i++) {
            columnNames[i] = String.valueOf(i);
            columnModel.getColumn(i).setMaxWidth(15);
        }

        tableModel.addRow(columnNames);

        // Add Rows
        for (int i = 0; i < dataSize; i++) {
            String[] tempRow = new String[tableSize];
            tempRow[0] = names[i];

            for (int j = 0; j < dataSize; j++) {
                String stringData;
                if (i == j) {
                    stringData = ".";
                } else if (data[i][j]) {
                    stringData = "x";
                } else {
                    stringData = " ";
                }
                tempRow[j + 1] = stringData;
            }
            tableModel.addRow(tempRow);
        }

        // Set first column size
        int maxSize = 0;
        for (int i = 0; i < tableSize; i++) {
            TableCellRenderer renderer = getCellRenderer(i, 0);
            Component component = prepareRenderer(renderer, i, 0);
            maxSize = Math.max(maxSize, component.getPreferredSize().width);
        }
        columnModel.getColumn(0).setMinWidth(maxSize + 10);
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component component = super.prepareRenderer(renderer, row, column);

        component.setBackground(Color.WHITE);
        component.setForeground(Color.BLACK);

        if (group != null) {
            if (row > 0 && column > 0) {
                if (group[row - 1][column - 1] > 0) {
                    component.setBackground(colors[(group[row - 1][column - 1] - 1) % colors.length]);
                }
            }
        }

        return component;
    }

    @Override
    public String getToolTipText(@NotNull MouseEvent event) {
        String tooltip = null;
        Point p = event.getPoint();

        int row = rowAtPoint(p);
        int column = columnAtPoint(p);

        if (row > 0 && column > 0) {
            tooltip = String.format("<html>%s<br>%s<br><b>Click to change value</b></html>",
                    getValueAt(row, 0).toString(), getValueAt(column, 0).toString());
        }

        if (tooltip == null) {
            tooltip = getToolTipText();
        }

        return tooltip;
    }
}
