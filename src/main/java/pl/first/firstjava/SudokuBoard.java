package pl.first.firstjava;

import java.util.Random;

public class SudokuBoard {
    private int[][] board;

    //Metoda wyzerowująca planszę i wypełniająca planszę losowo
    public void fillBoard() {
        board = new int[9][9];
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                board[i][j] = 0;
            }
        }
        beginRandomFill();
        fillBoard(0, 0);
    }

    //Metoda wypełniająca planszę sudoku
    private void fillBoard(int row, int col) {

        for(int i = 1; i < 10; i++) {
            if(board[8][8] != 0) return;
            if(board[row][col] != 0) {
                if(col != 8) fillBoard(row, col + 1);
                else fillBoard(row + 1, 0);
            }
            if( checkGood(col, row, i) ) {
                board[row][col] = i;
                if(col != 8) fillBoard(row, col + 1);
                else fillBoard(row + 1, 0);
                if(board[8][8] == 0) board[row][col] = 0;
            }
        }
    }

    //Metoda poprawnie wypełniająca 15 losowych pól na planszy losowymi liczbami od 1 do 9
    private void beginRandomFill() {
        Random rand = new Random();
        for(int i = 0, value, col, row; i < 15; i++) {
            value = rand.nextInt(9) + 1;
            row = rand.nextInt(9);
            col = rand.nextInt(9);
            if(!checkGood(col, row, value) || board[row][col] != 0 || (row == 8 && col == 8)) {
                i--;
            } else {
                board[row][col] = value;
            }
        }
    }

    //Metoda sprawdzająca poprawność wartości wstawianej w dane pole względem całej planszy
    private boolean checkGood(int col, int row, int value) {
        for(int i = 0; i < 9; i++) {
            if(board[i][col] == value || board[row][i] == value) {
                return false;
            }
        }
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if (board[row - row % 3 + i][col - col % 3 + j] != value) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    //Metoda wyświetlająca planszę do sudoku
    public void print() {
        for(int i = 0; i < 9; i++) {
            if(i % 3 == 0 && i != 0) {
                System.out.println();
            }
            for(int j = 0; j < 9; j++) {
                if(j % 3 == 0 && j != 0) {
                    System.out.print(" ");
                }
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getBoardValue(int row, int col) {
        return board[row][col];
    }
}
