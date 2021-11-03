package pl.first.firstjava;


public class SudokuBoard implements Observer {
    private SudokuField[] board = new SudokuField[81];
    private SudokuSolver solver;
    private SudokuRow[] rows = new SudokuRow[9];
    private SudokuColumn[] columns = new SudokuColumn[9];
    private SudokuBox[] boxes = new SudokuBox[9];
    private boolean checked = false;

    public SudokuBoard(SudokuSolver solver) {
        for (int x = 0; x < 81; x++) {
            board[x] = new SudokuField();
        }

        for (int x = 0; x < 9; x++) {
            rows[x] = new SudokuRow();
            rows[x].setObserver(this);
            columns[x] = new SudokuColumn();
            columns[x].setObserver(this);
            boxes[x] = new SudokuBox();
            boxes[x].setObserver(this);
            for (int y = 0; y < 9; y++) {
                rows[x].setField(y, board[x * 9 + y].getFieldValue(), false);
            }
        }

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                columns[y].setField(x, board[x * 9 + y].getFieldValue(), false);
            }
        }

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        boxes[x * 3 + y].setField(i * 3 + j,
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
        if (rows[x].getField(y) != value) {
            rows[x].setField(y, value, false);
        }
        if (columns[y].getField(x) != value) {
            columns[y].setField(x, value, false);
        }
        int i = (x - x % 3) + (y - y % 3) / 3;
        if (boxes[i].getField((x % 3) * 3 + y % 3) != value) {
            boxes[i].setField((x % 3) * 3 + y % 3, value, false);
        }
    }

    public SudokuRow getRow(int y) {
        if (y < 0 || y > 8) {
            return null;
        }
        return rows[y];
    }

    public SudokuColumn getColumn(int x) {
        if (x < 0 || x > 8) {
            return null;
        }
        return columns[x];
    }

    public SudokuBox getBox(int x, int y) {
        if (x < 0 || x > 2 || y < 0 || y > 2) {
            return null;
        }
        return boxes[x * 3 + y];
    }

    @Override
    public void update(Observable observable, int x) {
        for (int i = 0; i < 9; i++) {
            if (columns[i] == observable) {
                set(x, i, columns[i].getField(x));
                break;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (rows[i] == observable) {
                set(x, i, columns[i].getField(x));
                break;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (boxes[i] == observable) {
                set(i - i % 3 + x / 3, (i % 3) * 3 + x % 3, boxes[i].getField(x));
                break;
            }
        }
        checked = checkBoard();
    }

    public boolean isChecked() {
        return checked;
    }
}
