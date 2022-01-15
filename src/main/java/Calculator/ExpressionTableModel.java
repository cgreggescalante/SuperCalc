package Calculator;

import javax.swing.table.DefaultTableModel;

public class ExpressionTableModel extends DefaultTableModel {
    public ExpressionTableModel(int rowCount, int columnCount) {
        super(rowCount, columnCount);
    }

    @Override
    public Class<?> getColumnClass(int col) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 1;
    }
}
