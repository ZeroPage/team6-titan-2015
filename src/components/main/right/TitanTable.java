package components.main.right;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;

public class TitanTable extends JTable {
    private ArrayList<IntPair> squares;

    private static final Color[] colors = {
            new Color(0, 152, 207), new Color(255, 233, 0),
            new Color(255, 115, 119), new Color(239, 44, 193)
    };

    public TitanTable() {
        // Super Constructor
        super(null);

        // Init fields
        squares = new ArrayList<>();

        // Init Table
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setTableHeader(null);
        setEnabled(false);
    }

    public void setTableContents(String[] names, boolean[][] data) {
        // Init variables
        int dataSize = names.length;
        int tableSize = names.length + 1;
        DefaultTableModel tableModel = new DefaultTableModel(0, tableSize);
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

        if (row > 0 && column > 0) {
            IntPair current = new IntPair(row - 1, column - 1);
            int colorNumber = -1;

            for (IntPair pair : squares) {
                if (pair.contains(current)) {
                    colorNumber++;
                }
            }

            if (colorNumber >= 0) {
                component.setBackground(colors[colorNumber % colors.length]);
            }
        }


        return component;
    }

    public void addSquare(int from, int to) {
        squares.add(new IntPair(from, to));
    }

    public void removeAllSquare() {
        squares.clear();
    }

    private class IntPair {
        private int low;
        private int high;

        public IntPair(int a, int b) {
            this.low = Math.min(a, b);
            this.high = Math.max(a, b);
        }

        public boolean contains(IntPair pair) {
            return pair.low >= this.low && pair.high <= this.high;
        }
    }
}
