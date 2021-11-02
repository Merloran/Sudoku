package pl.first.firstjava;

public class SudokuBox {
    private SudokuField[] box = new SudokuField[9];

    public boolean verify() {
        int[] values = new int[9];
        for (int x = 0; x < 9; x++) {
            if (box[x].getFieldValue() == 0) {
                return false;
            }
            values[box[x].getFieldValue() - 1]++;
        }
        for (int i : values) {
            if (i != 1) {
                return false;
            }
        }
        return true;
    }

}
