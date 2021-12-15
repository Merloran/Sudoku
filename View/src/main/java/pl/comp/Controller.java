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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private static SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
    private Difficulty difficulty;

    @FXML
    private GridPane pane;
    @FXML
    private RadioButton rb1;
    @FXML
    private RadioButton rb2;
    @FXML
    private RadioButton rb3;
    @FXML
    private void startGame(ActionEvent event) throws IOException {
        board.solveGame();

        Stage stage = (Stage) rb1.getScene().getWindow();
        if (rb1.isSelected()) {
            difficulty = new Difficulty(board, Difficulty.LEVEL.EASY);
        }
        if (rb2.isSelected()) {
            difficulty = new Difficulty(board, Difficulty.LEVEL.MEDIUM);
        }
        if (rb3.isSelected()) {
            difficulty = new Difficulty(board, Difficulty.LEVEL.HARD);
        }

        Parent root = FXMLLoader.load(App.class.getResource("Game.fxml"));
        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(App.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);

        stage.show();
    }
    @FXML
    private void backMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Form.fxml"));
        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(App.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(url.equals(App.class.getResource("Form.fxml"))) {
            ToggleGroup difficulty = new ToggleGroup();
            rb1.setToggleGroup(difficulty);
            rb1.setSelected(true);
            rb2.setToggleGroup(difficulty);
            rb3.setToggleGroup(difficulty);
        }
        if(url.equals(App.class.getResource("Game.fxml"))) {
            TextField[][] fields = new TextField[9][9];
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    fields[x][y] = new TextField();
                    fields[x][y].setStyle("-fx-alignment: center;" +
                                          "-fx-font-weight: bold;" +
                                          "-fx-font-size: 12pt;" +
                                          "-fx-font-family: 'Comic Sans MS';");
                    if (board.get(x, y) != 0) {
                        fields[x][y].setText(Integer.toString(board.get(x, y)));
                        fields[x][y].setDisable(true);
                    }
                    pane.add(fields[x][y], x, y);
                }
            }
        }
    }
}
