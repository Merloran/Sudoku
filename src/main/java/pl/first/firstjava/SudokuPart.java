package pl.first.firstjava;

public class SudokuPart {
    private SudokuField[] fields = new SudokuField[9];

    SudokuPart() {
        for (int i = 0; i < 9; i++) {
            fields[i] = new SudokuField();
        }
    }

    public boolean verify() {
        int[] values = new int[9];
        for (int x = 0; x < 9; x++) {
            if (fields[x].getFieldValue() == 0) {
                return false;
            }
            values[fields[x].getFieldValue() - 1]++;
        }
        for (int i : values) {
            if (i != 1) {
                return false;
            }
        }
        return true;
    }


    public void setField(int x, int value) {
        if (x < 0 || x > 8) {
            return;
        }

        fields[x].setFieldValue(value);
    }
}
