package util;

import javax.swing.table.DefaultTableModel;

public class JTableUtils {
    public static void clearTable(DefaultTableModel model) {
        model.setRowCount(0);
    }
}
