package pl.comp;

import java.util.Random;

public class Difficulty {
        public enum LEVEL {
            EASY(0), MEDIUM(1), HARD(2);
            int lev;
            LEVEL(int lev) {
                this.lev = lev;
            }
        }

        public Difficulty(SudokuBoard board, LEVEL level) {
            Random rand = new Random();
            int x;
            int y;
            for (int i = 0; i < (level.lev + 1) * 10; i++) {
                x = rand.nextInt(9);
                y = rand.nextInt(9);
                if (board.get(x, y) != 0) {
                    board.set(x, y, 0, false);
                } else {
                    i--;
                }
            }
        }
}
