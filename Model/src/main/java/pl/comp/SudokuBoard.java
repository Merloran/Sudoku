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


import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SudokuBoard implements Observer, Serializable, Cloneable {
    private SudokuField[] board = new SudokuField[81];
    private SudokuSolver solver;
    private List<SudokuRow> rows = Arrays.asList(new SudokuRow[9]);
    private List<SudokuColumn> columns = Arrays.asList(new SudokuColumn[9]);
    private List<SudokuBox> boxes = Arrays.asList(new SudokuBox[9]);

    public SudokuBoard(SudokuSolver solver) {
        for (int x = 0; x < 81; x++) {
            board[x] = new SudokuField();
            board[x].setObserver(this);
        }
        for (int i = 0; i < 9; i++) {
            rows.set(i, new SudokuRow());
            columns.set(i, new SudokuColumn());
            boxes.set(i, new SudokuBox());
        }
        this.solver = solver;
    }

    public void solveGame() {
        solver.solve(this);
        checkBoard();
    }

    public boolean checkBoard() {
        for (int x = 0; x < 9; x++) {
            if (!getRow(x).verify()) {
                return false;
            }
        }

        for (int x = 0; x < 9; x++) {
            if (!getColumn(x).verify()) {
                return false;
            }
        }

        for (int x = 0; x < 9; x++) {
            if (!getBox(x / 3, x % 3).verify()) {
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

    public void set(int x, int y, int value, boolean notify) {
        if (x < 0 || x > 8 || y < 0 || y > 8) {
            return;
        }
        board[x * 9 + y].setFieldValue(value, notify);
    }

    public SudokuRow getRow(int y) {
        if (y < 0 || y > 8) {
            return null;
        }
        for (int i = 0; i < 9; i++) {
            rows.get(y).setField(i, board[y * 9 + i]);
        }
        return rows.get(y);
    }

    public SudokuColumn getColumn(int x) {
        if (x < 0 || x > 8) {
            return null;
        }
        for (int i = 0; i < 9; i++) {
            columns.get(x).setField(i, board[x + i * 9]);
        }
        return columns.get(x);
    }

    public SudokuBox getBox(int x, int y) {
        if (x < 0 || x > 2 || y < 0 || y > 2) {
            return null;
        }
        for (int i = 0; i < 9; i++) {
            boxes.get(x * 3 + y).setField(i, board[x * 27 + 9 * (i / 3) + y * 3 + i % 3]);
        }
        return boxes.get(x * 3 + y);
    }

    @Override
    public void update(Observable observable) {
        checkBoard();
    }

    @Override
    public String toString() {
        ToStringBuilder result = new ToStringBuilder(this);
        for (int i = 0; i < 81; i++) {
            result.append(board[i].getFieldValue());
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuBoard board1)) {
            return false;
        }

        return new EqualsBuilder().append(board, board1.board).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(board).toHashCode();
    }


    @Override
    public SudokuBoard clone() throws CloneNotSupportedException {
        SudokuBoard clone = (SudokuBoard) super.clone();
        SudokuField[] copy = new SudokuField[81];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = board[i].clone();
        }
        clone.board = copy;
        return clone;
    }
}
