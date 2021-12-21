package pl.comp;

import javafx.beans.property.SimpleIntegerProperty;

public class BindSudokuForm extends SimpleIntegerProperty {
    public BindSudokuForm(SudokuField field) {
        super(field.getFieldValue());
        this.addListener((v, oldValue, newValue) -> field.setFieldValue(newValue.intValue(), false) );
    }
}
