package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuPartTest {
    private SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

    @Test
    public void testVerify() {
        assertFalse(board.getColumn(1).verify());
        board.solveGame();
        board.getColumn(1).setField(1, 1);
        board.getColumn(1).setField(2, 1);
        assertFalse(board.getColumn(1).verify());
        board.solveGame();
        assertTrue(board.getColumn(1).verify());
    }

    @Test
    public void testSet() {
        board.getRow(1).setField(1, 1);
        board.getRow(1).setField(9, 1);
        board.getRow(1).setField(-1, 1);
    }

}