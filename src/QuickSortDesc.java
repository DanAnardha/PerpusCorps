
import javax.swing.JTable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Danuar Anardha
 */
public class QuickSortDesc {
    public static void quickSortAsc(JTable table, int colIndex, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivotIndex = (left + right) / 2;
        Object pivotValue = table.getValueAt(pivotIndex, colIndex);

        int partitionIndex = partition(table, colIndex, left, right, pivotValue);
        quickSortAsc(table, colIndex, left, partitionIndex - 1);
        quickSortAsc(table, colIndex, partitionIndex, right);
    }

    private static int partition(JTable table, int colIndex, int left, int right, Object pivotValue) {
        while (left <= right) {
            while (((Comparable) table.getValueAt(left, colIndex)).compareTo(pivotValue) > 0) {
                left++;
            }
            while (((Comparable) table.getValueAt(right, colIndex)).compareTo(pivotValue) < 0) {
                right--;
            }
            if (left <= right) {
                // Swap rows
                for (int i = 0; i < table.getColumnCount(); i++) {
                    Object temp = table.getValueAt(left, i);
                    table.setValueAt(table.getValueAt(right, i), left, i);
                    table.setValueAt(temp, right, i);
                }
                left++;
                right--;
            }
        }
        return left;
    }
}
