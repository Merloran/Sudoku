package pl.first.firstjava;

public class SudokuColumn {
    private SudokuField[] column = new SudokuField[9];

    public boolean verify() {
        int[] values = new int[9];
        for (int x = 0; x < 9; x++) {
            if (column[x].getFieldValue() == 0) {
                return false;
            }
            values[column[x].getFieldValue() - 1]++;
        }
        for (int i : values) {
            if (i != 1) {
                return false;
            }
        }
        return true;
    }

    public void setColumn(int x, SudokuField field) {
        if (x < 0 || x > 8) {
            return;
        }

        column[x] = field;
    }
}
