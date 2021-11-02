package pl.first.firstjava;


public class SudokuBoard {
    private SudokuField[] board = new SudokuField[81];
    private SudokuSolver solver;
    private SudokuRow[] rows = new SudokuRow[9];
    private SudokuColumn[] columns = new SudokuColumn[9];
    private SudokuBox[][] boxes = new SudokuBox[3][3];

    public SudokuBoard(SudokuSolver solver) {
        for (int x = 0; x < 81; x++) {
            board[x] = new SudokuField();
        }


        for (int x = 0; x < 9; x++) {
            rows[x] = new SudokuRow();
            columns[x] = new SudokuColumn();
            boxes[x / 3][x % 3] = new SudokuBox();
            for (int y = 0; y < 9; y++) {
                rows[x].setRow(y, board[x * 9 + y]);
            }
        }

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                columns[y].setColumn(x, board[x * 9 + y]);
            }
        }

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        boxes[x][y].setBox(i, j, board[i * 9 + j + y * 3 + x * 27]);
                    }
                }
            }
        }

        this.solver = solver;
    }

    public void solveGame() {
        solver.solve(this);
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

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (!boxes[x][y].verify()) {
                    return false;
                }
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
    }

    public SudokuRow getRow(int y) {
        if (y < 0 || y > 8)
            return null;
        return rows[y];
    }

    public SudokuColumn getColumn(int x) {
        if (x < 0 || x > 8)
            return null;
        return columns[x];
    }

    public SudokuBox getBox(int x, int y) {
        if (x < 0 || x > 2 || y < 0 || y > 2)
            return null;
        return boxes[x][y];
    }

}
