/**
 * MIT License
 * Copyright (c) 2021 Maciej Wójcik, Maciej Poncyleusz
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package pl.comp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {
    private SudokuField field = new SudokuField();

    @Test
    public void testSet() {
        assertEquals(field.getFieldValue(), 0);
        try {
            field.setFieldValue(-1, false);
            fail();
        } catch (InvalidValueException e) {
            System.out.println("Exception");
        }
        assertEquals(field.getFieldValue(), 0);
        try {
            field.setFieldValue(10,false);
            fail();
        } catch (InvalidValueException e) {
            System.out.println("Exception");
        }
        assertEquals(field.getFieldValue(), 0);
        field.setFieldValue(1, false);
        assertEquals(field.getFieldValue(), 1);
    }

    @Test
    public void testEquals() {
        SudokuField field1 = new SudokuField();
        SudokuField field2 = new SudokuField();
        field1.setFieldValue(2,false);
        field2.setFieldValue(2,false);
        assertEquals(field1, field2);
        field2.setFieldValue(3,false);
        assertNotEquals(field1, field2);
        assertEquals(field1, field1);
        assertNotEquals(field1, new SudokuRow());
    }

    @Test
    public void testToString() {
        SudokuField field1 = new SudokuField();
        assertEquals(field1.toString(), "pl.comp.SudokuField@" + Integer.toHexString(System.identityHashCode(field1)) + "[value=0]");
    }

    @Test
    public void testHashCode() {
        SudokuField field1 = new SudokuField();
        SudokuField field2 = new SudokuField();
        field1.setFieldValue(2,false);
        field2.setFieldValue(2,false);
        assertEquals(field1.hashCode(), field2.hashCode());
        field2.setFieldValue(3,false);
        assertNotEquals(field1.hashCode(), field2.hashCode());
    }

    @Test
    public void  testComparable() {
        SudokuField field1 = new SudokuField();
        SudokuField field2 = new SudokuField();
        SudokuField field3 = new SudokuField();

        field1.setFieldValue(1,false);
        field2.setFieldValue(4,false);
        field3.setFieldValue(7,false);

        assertEquals(field1.compareTo(field2), -3);
        assertEquals(field2.compareTo(field2), 0);
        assertEquals(field3.compareTo(field2), 3);
        try {
            field1.compareTo(null);
            fail();
        } catch (NullPointerException e) {
            System.out.println("Got Exception");
        }
    }

    @Test
    public void testCloneable() throws CloneNotSupportedException {

        SudokuField field1 = new SudokuField();
        field1.setFieldValue(4,false);
        SudokuField field2 = field1.clone();

        assertEquals(field1, field2);
        field2.setFieldValue(1, false);
        assertNotEquals(field1, field2);
    }
}