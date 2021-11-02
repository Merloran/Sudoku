package pl.first.firstjava;

public class SudokuRow {
    private SudokuField[] row = new SudokuField[9];
    public boolean verify() {
        int[] values = new int[9];
        for (int x = 0; x < 9; x++) {
            if (row[x].getFieldValue() == 0) {
                return false;
            }
            values[row[x].getFieldValue() - 1]++;
        }
        for (int i : values) {
            if (i != 1) {
                return false;
            }
        }
        return true;
    }


}
