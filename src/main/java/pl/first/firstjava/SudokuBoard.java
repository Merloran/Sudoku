package pl.first.firstjava;

import java.util.Random;

public class SudokuBoard {
    private int[][] board = new int[9][9];
    private SudokuSolver solver = new BacktrackingSudokuSolver();

    public void solveGame() {
        solver.solve(this);
    }

    public int get(int x, int y) {
        return board[x][y];
    }

    public void set(int x, int y, int value) {
        board[x][y] = value;
    }
}
