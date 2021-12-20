/**
 * MIT License
 * Copyright (c) 2021 Maciej Wójcik, Maciej Poncyleusz
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pl.comp;

import java.util.Random;

public class Difficulty {
        public enum LEVEL {
            EASY(0),
            MEDIUM(1),
            HARD(2);

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
