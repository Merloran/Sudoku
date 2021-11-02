package pl.first.firstjava;


public class SudokuBoard {
    private int[][] board;
    private SudokuSolver solver;

    public SudokuBoard(SudokuSolver solver) {
        board = new int[9][9];
        this.solver = solver;
    }

    public void solveGame() {
        solver.solve(this);
    }

    public boolean checkBoard() {
        int[] values = new int[9];

        //Sprawdzenie poprawności w wierszach
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (board[x][y] == 0) {
                    return false;
                }
                values[board[x][y] - 1]++;
            }
            for (int i : values) {
                if (i != x + 1) {
                    return false;
                }
            }
        }

        values = new int[9];

        //Sprawdzenie poprawności w kolumnach
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                values[board[y][x] - 1]++;
            }
            for (int i : values) {
                if (i != x + 1) {
                    return false;
                }
            }
        }

        values = new int[9];

        //Sprawdzenie poprawności w blokach 3x3
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        values[board[i + 3 * x][j + 3 * y] - 1]++;
                    }
                }
                for (int i : values) {
                    if (i != x * 3 + y + 1) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public int get(int x, int y) {
        if (x < 0 || x > 8 || y < 0 || y > 8) {
            return -1;
        }
        return board[x][y];
    }

    public void set(int x, int y, int value) {
        if (x < 0 || x > 8 || y < 0 || y > 8) {
            return;
        }
        if (value < 0 || value > 9) {
            return;
        }
        board[x][y] = value;
    }
}
