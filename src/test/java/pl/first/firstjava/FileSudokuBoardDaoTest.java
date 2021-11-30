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
package pl.first.firstjava;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {

    FileSudokuBoardDao dao = new FileSudokuBoardDao("D:\\plik.txt");
    SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

    @Test
    void testRead() {
        dao.write(board);
        dao.read();
        dao = new FileSudokuBoardDao("ALFA:\\plik");
        try {
            dao.read();
        } catch (RuntimeException e) {
            System.out.println("Exception caught read");
        }
    }

    @Test
    void testWrite() {
        dao.write(board);
        dao.read();
        dao = new FileSudokuBoardDao("ALFA:\\plik");
        try {
            dao.write(board);
        } catch (RuntimeException e) {
            System.out.println("Exception caught write");
        }
    }

    @Test
    void testClose() throws Exception {
        dao.close();
    }
}