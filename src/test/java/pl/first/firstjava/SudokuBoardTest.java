package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {
    private SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

    @Test
    public void testSolveGame() {
        board.solveGame();

        int[] values = new int[9];

        //Sprawdzenie poprawności w wierszach
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                values[board.get(x, y) - 1]++;
            }
            for (int i : values) {
                assertEquals(i, x + 1);
            }
        }

        values = new int[9];

        //Sprawdzenie poprawności w kolumnach
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                values[board.get(y, x) - 1]++;
            }
            for (int i : values) {
                assertEquals(i, x + 1);
            }
        }

        values = new int[9];

        //Sprawdzenie poprawności w blokach 3x3
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        values[board.get(i + 3 * x, j + 3 * y) - 1]++;
                        assertTrue(board.get(i + 3 * x, j + 3 * y) > 0 && board.get(i + 3 * x, j + 3 * y) < 10);
                    }
                }
                for (int i : values) {
                    assertEquals(i, x * 3 + y + 1);
                }
            }
        }
    }

    @Test
    void testGet() {
        assertEquals(board.get(-1,0),-1);
        board.set(0, 0, 1);
        assertEquals(board.get(0,0),1);
    }

    @Test
    void testSet() {
        board.set(0, 0, 1);
        assertEquals(board.get(0,0),1);
        board.set(0, 0, 100);
        assertEquals(board.get(0,0),1);
        board.set(-1, 0, 1);
        assertEquals(board.get(-1,0),-1);
    }
}