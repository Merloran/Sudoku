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

package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {
    private SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
    private BacktrackingSudokuSolver test = new BacktrackingSudokuSolver();

    @Test
    public void testSolveCorrectness() {
        test.solve(board);

        int[] values = new int[9];

        //Sprawdzenie poprawności w wierszach
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                values[board.get(x, y) - 1]++;
            }
            for(int i : values) {
                assertEquals(i, x + 1);
            }
        }

        values = new int[9];

        //Sprawdzenie poprawności w kolumnach
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                values[board.get(y, x) - 1]++;
            }
            for(int i : values) {
                assertEquals(i, x + 1);
            }
        }

        values = new int[9];

        //Sprawdzenie poprawności w blokach 3x3
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        values[board.get(i + 3 * x, j + 3 * y ) - 1]++;
                        assertTrue(board.get(i + 3 * x, j + 3 * y ) > 0 && board.get(i + 3 * x, j + 3 * y ) < 10);
                    }
                }
                for(int i : values) {
                    assertEquals(i, x * 3 + y + 1);
                }
            }
        }
    }

    @Test
    public void testSolveRandomness() {
        test.solve(board);

        StringBuilder result1 = new StringBuilder();
        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {
                result1.append(board.get(row, col));
            }
        }

        test.solve(board);
        StringBuilder result2 = new StringBuilder();
        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {
                result2.append(board.get(row, col));
            }
        }

        assertNotEquals(result1.toString(), result2.toString());
    }
}