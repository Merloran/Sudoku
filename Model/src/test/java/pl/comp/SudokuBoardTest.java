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

class SudokuBoardTest {
    private SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

    @Test
    public void testSolveGame() {
        board.solveGame();

        int[] values = new int[9];

        //Sprawdzenie poprawności w wierszach
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                values[board.get(x, y) - 1]++;
            }
            for (int i : values) {
                assertEquals(i, x + 1);
            }
        }

        values = new int[9];

        //Sprawdzenie poprawności w kolumnach
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                values[board.get(y, x) - 1]++;
            }
            for (int i : values) {
                assertEquals(i, x + 1);
            }
        }

        values = new int[9];

        //Sprawdzenie poprawności w blokach 3x3
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        values[board.get(i + 3 * x, j + 3 * y) - 1]++;
                        assertTrue(board.get(i + 3 * x, j + 3 * y) > 0 && board.get(i + 3 * x, j + 3 * y) < 10);
                    }
                }
                for (int i : values) {
                    assertEquals(i, x * 3 + y + 1);
                }
            }
        }
    }

    @Test
    public void testGet() {
        assertEquals(board.get(-1,0),-1);
        assertEquals(board.get(9,0),-1);
        assertEquals(board.get(0,-1),-1);
        assertEquals(board.get(0,9),-1);
        board.set(0, 0, 1, false);
        assertEquals(board.get(0,0),1);
    }

    @Test
    public void testSet() {
        board.set(0, 0, 1, false);
        board.set(-1, 0, 2, false);
        board.set(9, 0, 4, false);
        board.set(0, -1, 5, false);
        board.set(0, 9, 6, false);
        assertEquals(board.get(0,0), 1);
        board.set(1, 2, 3, true);
    }

    @Test
    public void testGetRow() {
        assertNull(board.getRow(9));
        assertNull(board.getRow(-1));
    }

    @Test
    public void testGetColumn() {
        assertNull(board.getColumn(9));
        assertNull(board.getColumn(-1));
    }

    @Test
    public void testGetBox() {
        assertNull(board.getBox(3, 0));
        assertNull(board.getBox(0, 3));
        assertNull(board.getBox(-1, 0));
        assertNull(board.getBox(0, -1));
        assertNotNull(board.getBox(0,2));
    }

    @Test
    public void testUpdate() {
        board.set(0,0,1, true);
        board.set(0,1,1, true);
        board.set(0,1,2, true);
        board.set(1,0,1, true);
        board.set(1,0,2, true);
    }

    @Test
    public void testEquals() {
        SudokuPart part1 = new SudokuPart();
        SudokuPart part2 = new SudokuPart();
        assertEquals(part1, part2);
        assertEquals(part1, part1);
        assertNotEquals(part1, new SudokuField());
    }

    @Test
    public void testToString() {
        SudokuPart part = new SudokuPart();
        assertEquals(part.toString(), "pl.comp.SudokuPart@" + Integer.toHexString(System.identityHashCode(part)) + "[0,0,0,0,0,0,0,0,0]");
    }

    @Test
    public void testHashCode(){
        SudokuPart part1 = new SudokuPart();
        SudokuPart part2 = new SudokuPart();
        SudokuField field1 = new SudokuField();
        field1.setFieldValue(3,false);
        part1.setField(1,field1);
        part2.setField(1,field1);
        assertEquals(part1, part2);
        assertEquals(part1.hashCode(), part2.hashCode());
        part2.setField(2,field1);
        assertNotEquals(part1.hashCode(),part2.hashCode());
    }
}