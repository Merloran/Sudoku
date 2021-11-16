package pl.first.firstjava;
import java.util.ArrayList;
import java.util.List;


public class SudokuBoard {
    private SudokuField[] board = new SudokuField[81];
    private SudokuSolver solver;
//    private SudokuRow[] rows = new SudokuRow[9];
    private List<SudokuRow> rows = new ArrayList<SudokuRow>(9);
    private List<SudokuColumn> columns = new ArrayList<SudokuColumn>(9);
    private List<SudokuBox> boxes = new ArrayList<SudokuBox>(9);

    public SudokuBoard(SudokuSolver solver) {
        for (int x = 0; x < 81; x++) {
            board[x] = new SudokuField();
        }

        for (int x = 0; x < 9; x++) {
            rows.set(x, new SudokuRow());
            columns.set(x,new SudokuColumn());
            boxes.set(x,new SudokuBox());
            for (int y = 0; y < 9; y++) {
                rows.get(x).setField(y, board[x * 9 + y].getFieldValue());
            }
        }

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                columns.get(y).setField(x, board[x * 9 + y].getFieldValue());
            }
        }

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        boxes.get(x * 3 + y).setField(i * 3 + j,
                                board[i * 9 + j + y * 3 + x * 27].getFieldValue());
                    }
                }
            }
        }

        this.solver = solver;
    }

    public void solveGame() {
        solver.solve(this);

        for (int x = 0; x < 9; x++) {
            rows.set(x, new SudokuRow());
            columns.set(x, new SudokuColumn());
            boxes.set(x, new SudokuBox());
            for (int y = 0; y < 9; y++) {
                rows.get(x).setField(y, board[x * 9 + y].getFieldValue());
            }
        }

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                columns.get(y).setField(x, board[x * 9 + y].getFieldValue());
            }
        }

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        boxes.get(x * 3 + y).setField(i * 3 + j,
                                board[i * 9 + j + y * 3 + x * 27].getFieldValue());
                    }
                }
            }
        }

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
        rows.get(x).setField(y, value);
        columns.get(y).setField(x, value);
        boxes.get((x - x % 3) + (y - y % 3) / 3).setField((x % 3) * 3 + y % 3, value);
        checkBoard();
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

}
