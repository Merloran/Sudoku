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


import java.util.Arrays;
import java.util.List;

public class SudokuBoard implements Observer {
    private SudokuField[] board = new SudokuField[81];
    private SudokuSolver solver;
    private List<SudokuRow> rows = Arrays.asList(new SudokuRow[9]);
    private List<SudokuColumn> columns = Arrays.asList(new SudokuColumn[9]);
    private List<SudokuBox> boxes = Arrays.asList(new SudokuBox[9]);
    private boolean checked = false;

    public SudokuBoard(SudokuSolver solver) {
        for (int x = 0; x < 81; x++) {
            board[x] = new SudokuField();
        }

        for (int x = 0; x < 9; x++) {
            rows.set(x, new SudokuRow());
            rows.get(x).setObserver(this);
            columns.set(x, new SudokuColumn());
            columns.get(x).setObserver(this);
            boxes.set(x, new SudokuBox());
            boxes.get(x).setObserver(this);
            for (int y = 0; y < 9; y++) {
                rows.get(x).setField(y, board[x * 9 + y].getFieldValue(), false);
            }
        }

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                columns.get(y).setField(x, board[x * 9 + y].getFieldValue(), false);
            }
        }

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        boxes.get(x * 3 + y).setField(i * 3 + j,
                                board[i * 9 + j + y * 3 + x * 27].getFieldValue(), false);
                    }
                }
            }
        }

        this.solver = solver;
    }

    public void solveGame() {
        solver.solve(this);
        checked = checkBoard();
    }

    private boolean checkBoard() {
        for (SudokuRow i : rows) {
            if (!i.verify()) {
                return false;
            }
        }

        for (SudokuColumn i : columns) {
            if (!i.verify()) {
                return false;
            }
        }

        for (SudokuBox i : boxes) {
            if (!i.verify()) {
                return false;
            }
        }

        return true;
    }

    public int get(int x, int y) {
        if (x < 0 || x > 8 || y < 0 || y > 8) {
            return -1;
        }
        return board[x * 9 + y].getFieldValue();
    }

    public void set(int x, int y, int value) {
        if (x < 0 || x > 8 || y < 0 || y > 8) {
            return;
        }
        board[x * 9 + y].setFieldValue(value);
        if (rows.get(x).getField(y) != value) {
            rows.get(x).setField(y, value, false);
        }
        if (columns.get(y).getField(x) != value) {
            columns.get(y).setField(x, value, false);
        }
        int i = (x - x % 3) + (y - y % 3) / 3;
        if (boxes.get(i).getField((x % 3) * 3 + y % 3) != value) {
            boxes.get(i).setField((x % 3) * 3 + y % 3, value, false);
        }
    }

    public SudokuRow getRow(int y) {
        if (y < 0 || y > 8) {
            return null;
        }
        return rows.get(y);
    }

    public SudokuColumn getColumn(int x) {
        if (x < 0 || x > 8) {
            return null;
        }
        return columns.get(x);
    }

    public SudokuBox getBox(int x, int y) {
        if (x < 0 || x > 2 || y < 0 || y > 2) {
            return null;
        }
        return boxes.get(x * 3 + y);
    }

    @Override
    public void update(Observable observable, int x) {
        for (int i = 0; i < 9; i++) {
            if (columns.get(i) == observable) {
                set(x, i, columns.get(i).getField(x));
                break;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (rows.get(i) == observable) {
                set(x, i, columns.get(i).getField(x));
                break;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (boxes.get(i) == observable) {
                set(i - i % 3 + x / 3, (i % 3) * 3 + x % 3, boxes.get(i).getField(x));
                break;
            }
        }
        checked = checkBoard();
    }

    public boolean isChecked() {
        return checked;
    }
}
