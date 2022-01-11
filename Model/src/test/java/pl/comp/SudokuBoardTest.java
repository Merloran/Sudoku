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
        try {
            board.get(-1, 0);
            fail();
        } catch (InvalidValueException e) {
            System.out.println("Exception");
        }
        try {
            board.get(9,0);
            fail();
        } catch (InvalidValueException e) {
            System.out.println("Exception");
        }
        try {
            board.get(0,-1);
            fail();
        } catch (InvalidValueException e) {
            System.out.println("Exception");
        }
        try {
            board.get(0,9);
            fail();
        } catch (InvalidValueException e) {
            System.out.println("Exception");
        }
        board.set(0, 0, 1, false);
        assertEquals(board.get(0,0),1);
        board.get(0,0);
    }

    @Test
    public void testCheckBoard() {
        board.checkBoard();
        board.set(0,0,1, false);
        board.set(0,1,1, false);
        board.checkBoard();
        board.set(0,1,0, false);
        board.set(1,0,1, false);
        board.checkBoard();
        board.set(1,0,0, false);
        board.set(1,1,1, false);
        board.checkBoard();
    }

    @Test
    public void testSet() {
        try {
            board.set(-1, 0, 2, false);
            fail();
        } catch (InvalidValueException e) {
            System.out.println("Exception");
        }
        try {
            board.set(9, 0, 4, false);
            fail();
        } catch (InvalidValueException e) {
            System.out.println("Exception");
        }
        try {
            board.set(0, -1, 5, false);
            fail();
        } catch (InvalidValueException e) {
            System.out.println("Exception");
        }
        try {
            board.set(0, 9, 6, false);
            fail();
        } catch (InvalidValueException e) {
            System.out.println("Exception");
        }
        board.set(0, 0, 1, false);
        assertEquals(board.get(0,0), 1);
        board.set(1, 2, 3, true);
    }

    @Test
    public void testGetRow() {
        try {
            board.getRow(9);
            fail();
        } catch (InvalidValueException e) {
            System.out.println("Exception");
        }
        try {
            board.getRow(-1);
            fail();
        } catch (InvalidValueException e) {
            System.out.println("Exception");
        }
    }

    @Test
    public void testGetColumn() {
        try {
            board.getColumn(9);
            fail();
        } catch (InvalidValueException e) {
            System.out.println("Exception");
        }
        try {
            board.getColumn(-1);
            fail();
        } catch (InvalidValueException e) {
            System.out.println("Exception");
        }
    }

    @Test
    public void testGetBox() {
        try {
            board.getBox(3, 0);
            fail();
        } catch (InvalidValueException e) {
            System.out.println("Exception");
        }
        try {
            board.getBox(0, 3);
            fail();
        } catch (InvalidValueException e) {
            System.out.println("Exception");
        }
        try {
            board.getBox(-1, 0);
            fail();
        } catch (InvalidValueException e) {
            System.out.println("Exception");
        }
        try {
            board.getBox(0, -1);
            fail();
        } catch (InvalidValueException e) {
            System.out.println("Exception");
        }
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
        SudokuBoard board1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard board2 = new SudokuBoard(new BacktrackingSudokuSolver());
        assertEquals(board1, board1);
        assertEquals(board1, board2);
        assertNotEquals(board1, new SudokuField());
    }

    @Test
    public void testToString() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append("0,".repeat(81));
        builder.deleteCharAt(builder.length() - 1);
        builder.append("]");
        assertEquals(board.toString(), "pl.comp.SudokuBoard@" + Integer.toHexString(System.identityHashCode(board)) + builder);
    }

    @Test
    public void testHashCode(){
        SudokuBoard board1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard board2 = new SudokuBoard(new BacktrackingSudokuSolver());
        assertEquals(board1.hashCode(), board2.hashCode());
        board1.set(1, 1, 1,false);
        assertNotEquals(board1.hashCode(),board2.hashCode());
    }

    @Test
    public void testCloneable() throws CloneNotSupportedException {
        SudokuBoard board1 = new SudokuBoard(new BacktrackingSudokuSolver());
        board1.solveGame();
        SudokuBoard board2 = board1.clone();
        assertEquals(board1, board2);
        board1.solveGame();
        assertNotEquals(board1, board2);
    }
}