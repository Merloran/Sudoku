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

import java.io.Serializable;
import java.util.Random;

public class BacktrackingSudokuSolver implements SudokuSolver, Serializable, Cloneable {

    //Metoda wyzerowująca planszę i wypełniająca planszę losowo
    @Override
    public void solve(SudokuBoard board) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                board.set(x, y, 0,false);
            }
        }
        beginRandomFill(board);
        fillBoard(0, 0, board);
    }

    //Metoda wypełniająca planszę sudoku
    private void fillBoard(int row, int col, SudokuBoard board) {

        for (int i = 1; i < 10; i++) {
            if (board.get(8, 8) != 0) {
                return;
            }
            if (board.get(row, col) != 0) {
                if (col != 8) {
                    fillBoard(row, col + 1, board);
                } else {
                    fillBoard(row + 1, 0, board);
                }
            }
            if (checkGood(col, row, i, board)) {
                board.set(row, col, i,false);
                if (col != 8) {
                    fillBoard(row, col + 1, board);
                } else {
                    fillBoard(row + 1, 0, board);
                }
                if (board.get(8, 8) == 0) {
                    board.set(row, col, 0,false);
                }
            }
        }
    }

    //Metoda poprawnie wypełniająca 15 losowych pól na planszy losowymi liczbami od 1 do 9
    private void beginRandomFill(SudokuBoard board) {
        Random rand = new Random();
        for (int i = 0, value, col, row; i < 15; i++) {
            value = rand.nextInt(9) + 1;
            row = rand.nextInt(9);
            col = rand.nextInt(9);
            if (!checkGood(col, row, value, board) || row == 8 && col == 8) {
                i--;
            } else if (board.get(row, col) != 0) {
                i--;
            } else {
                board.set(row, col, value,false);
            }
        }
    }

    //Metoda sprawdzająca poprawność wartości wstawianej w dane pole względem całej planszy
    private boolean checkGood(int col, int row, int value, SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            if (board.get(i, col) == value || board.get(row, i) == value) {
                return false;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.get(row - row % 3 + i, col - col % 3 + j) == value) {
                    return false;
                }
            }
        }
        return true;
    }
}
