import javax.swing.table.DefaultTableModel;

public class VariableTableModel extends DefaultTableModel {
    public VariableTableModel(int rowCount, int columnCount) {
        super(rowCount, columnCount);
    }

    @Override
    public Class getColumnClass(int col) {
        if (col == 0) {
            return String.class;
        }
        return Double.class;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 1;
    }
}
