package pl.first.firstjava;

public class SudokuField {
    int value;

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int value) {
        if (value < 0 || value > 9) {
            return;
        }
        this.value = value;
    }

}
