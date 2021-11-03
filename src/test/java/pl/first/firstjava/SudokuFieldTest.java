package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {
    private SudokuField field = new SudokuField();

    @Test
    public void testSet() {
        assertEquals(field.getFieldValue(), 0);
        field.setFieldValue(-1);
        assertEquals(field.getFieldValue(), 0);
        field.setFieldValue(10);
        assertEquals(field.getFieldValue(), 0);
        field.setFieldValue(1);
        assertEquals(field.getFieldValue(), 1);
    }

}