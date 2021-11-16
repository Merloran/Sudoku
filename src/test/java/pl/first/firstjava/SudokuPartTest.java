package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuPartTest {
    private SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

    @Test
    public void testVerify() {
        assertFalse(board.getColumn(1).verify());
        board.solveGame();
        board.getColumn(1).setField(1, 1, true);
        board.getColumn(1).setField(2, 1, true);
        assertFalse(board.getColumn(1).verify());
        board.solveGame();
        assertTrue(board.getColumn(1).verify());
    }

    @Test
    public void testSet() {
        board.getRow(1).setField(1, 1, true);
        board.getRow(1).setField(9, 1, true);
        board.getRow(1).setField(-1, 1, true);
    }

    @Test
    public void testGet() {
        assertEquals(board.getRow(0).getField(-1), -1);
        assertEquals(board.getRow(0).getField(10), -1);
    }

}