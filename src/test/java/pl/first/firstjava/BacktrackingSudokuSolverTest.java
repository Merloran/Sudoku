package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {
    private SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
    private BacktrackingSudokuSolver test = new BacktrackingSudokuSolver();

    @Test
    public void testSolveCorrectness() {
        test.solve(board);

        int[] values = new int[9];

        //Sprawdzenie poprawności w wierszach
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                values[board.get(x, y) - 1]++;
            }
            for(int i : values) {
                assertEquals(i, x + 1);
            }
        }

        values = new int[9];

        //Sprawdzenie poprawności w kolumnach
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                values[board.get(y, x) - 1]++;
            }
            for(int i : values) {
                assertEquals(i, x + 1);
            }
        }

        values = new int[9];

        //Sprawdzenie poprawności w blokach 3x3
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        values[board.get(i + 3 * x, j + 3 * y ) - 1]++;
                        assertTrue(board.get(i + 3 * x, j + 3 * y ) > 0 && board.get(i + 3 * x, j + 3 * y ) < 10);
                    }
                }
                for(int i : values) {
                    assertEquals(i, x * 3 + y + 1);
                }
            }
        }
    }

    @Test
    public void testSolveRandomness() {
        test.solve(board);

        StringBuilder result1 = new StringBuilder();
        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {
                result1.append(board.get(row, col));
            }
        }

        test.solve(board);
        StringBuilder result2 = new StringBuilder();
        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {
                result2.append(board.get(row, col));
            }
        }

        assertNotEquals(result1.toString(), result2.toString());
    }
}