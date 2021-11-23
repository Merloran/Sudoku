/**
 * MIT License
 *
 * Copyright (c) 2021 Maciej Wójcik, Maciej Poncyleusz
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuPartTest {
    private SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
    private SudokuField field = new SudokuField();

    @Test
    public void testVerify() {
        board.solveGame();
        board.set(1, 1, 1, false);
        board.set(1, 2, 1, false);
        assertFalse(board.getColumn(1).verify());
    }

    @Test
    public void testSet() {
        field.setFieldValue(1, false);
        board.getRow(1).setField(1, field);
        board.getRow(1).setField(9, field);
        board.getRow(1).setField(-1, field);
        SudokuPart part = new SudokuPart();
        SudokuField field = new SudokuField();
        field.setFieldValue(1, false);
        for (int i = 0; i < 9; i ++) {
            part.setField(i, field);
        }
        part.verify();
    }

}