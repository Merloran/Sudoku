package pl.comp;

import javafx.beans.property.SimpleIntegerProperty;

public class BindSudokuForm extends SimpleIntegerProperty {
    public BindSudokuForm(SudokuBoard  board, int x, int y) {
        super(board.get(x, y));
        this.addListener((v, oldValue, newValue) -> board.set(x, y, newValue.intValue(), false) );
    }
}
