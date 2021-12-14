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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    SudokuBoard board;
    Difficulty difficulty;

    @FXML
    private RadioButton rb1;
    @FXML
    private RadioButton rb2;
    @FXML
    private RadioButton rb3;

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        if (rb1.isSelected()) {
            difficulty = new Difficulty(board, Difficulty.LEVEL.EASY);
        }
        if (rb2.isSelected()) {
            difficulty = new Difficulty(board, Difficulty.LEVEL.MEDIUM);
        }
        if (rb3.isSelected()) {
            difficulty = new Difficulty(board, Difficulty.LEVEL.HARD);
        }

        System.out.println(board);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup difficulty = new ToggleGroup();

        rb1.setToggleGroup(difficulty);
        rb1.setSelected(true);
        rb2.setToggleGroup(difficulty);
        rb3.setToggleGroup(difficulty);
    }
}
