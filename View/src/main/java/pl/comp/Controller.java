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

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Controller implements Initializable {
    private static SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
    private static SudokuBoard boardCopy = new SudokuBoard(new BacktrackingSudokuSolver());
    private static String lang = "PL";
    private static ResourceBundle bundle = ResourceBundle.getBundle("pl.comp.bundles.Language", new Locale(lang));
    private static final Logger logger = Logger.getLogger(App.class.getName());
    private Difficulty difficulty;
    private Label[][] fields;
    private BindSudokuForm[][] fieldBind;
    private Label selectedField;

    @FXML
    private GridPane pane;
    @FXML
    private RadioButton rb1;
    @FXML
    private RadioButton rb2;
    @FXML
    private RadioButton rb3;
    @FXML
    private ComboBox<Label> langBox;
    @FXML
    private MenuItem saveButton;
    @FXML
    private void startGame(ActionEvent event) throws IOException {
        board.solveGame();
        Stage stage = (Stage) rb1.getScene().getWindow();
        if (rb1.isSelected()) {
            difficulty = new Difficulty(board, Difficulty.LEVEL.EASY);
            logger.info(bundle.getString("LevelInfo") + bundle.getString("RadioEasy"));

        }
        if (rb2.isSelected()) {
            difficulty = new Difficulty(board, Difficulty.LEVEL.MEDIUM);
            logger.info(bundle.getString("LevelInfo") + bundle.getString("RadioMedium"));
        }
        if (rb3.isSelected()) {
            difficulty = new Difficulty(board, Difficulty.LEVEL.HARD);
            logger.info(bundle.getString("LevelInfo") + bundle.getString("RadioHard"));
        }
        try {
            boardCopy = board.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Blad przy klonowaniu!");
        }

        bundle = ResourceBundle.getBundle("pl.comp.bundles.Language", new Locale(lang));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Game.fxml"));
        loader.setResources(bundle);
        logger.info(bundle.getString("GameInfo"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(App.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    private void changeLang(ActionEvent event) throws IOException {
        if (langBox.getSelectionModel().isSelected(0)) {
            lang = "PL";
        }
        if (langBox.getSelectionModel().isSelected(1)) {
            lang = "ENG";
        }
        Stage stage = (Stage) rb1.getScene().getWindow();

        bundle = ResourceBundle.getBundle("pl.comp.bundles.Language", new Locale(lang));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Form.fxml"));
        loader.setResources(bundle);

        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(App.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void backMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();

        bundle = ResourceBundle.getBundle("pl.comp.bundles.Language", new Locale(lang));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Form.fxml"));
        loader.setResources(bundle);
        logger.info(bundle.getString("BackInfo"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(App.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void saveBoard(ActionEvent event) throws IOException {
        SudokuBoardDaoFactory daoFactory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> file = daoFactory.getFileDao(".\\sudoku.txt");
        Dao<SudokuBoard> fileCopy = daoFactory.getFileDao(".\\sudokuCopy.txt");
        file.write(board);
        fileCopy.write(boardCopy);
    }

    @FXML
    private void loadBoard(ActionEvent event) throws IOException {
        SudokuBoardDaoFactory daoFactory = new SudokuBoardDaoFactory();

        try ( Dao<SudokuBoard> file = daoFactory.getFileDao(".\\sudoku.txt") ) {
            board = file.read();
        } catch (Exception e) {
            System.out.println("Brak zapisanej gry");
        }

        try ( Dao<SudokuBoard> fileCopy = daoFactory.getFileDao(".\\sudokuCopy.txt") ) {
            boardCopy = fileCopy.read();
        } catch (Exception e) {
            System.out.println("Brak zapisanej gry");
        }

        bundle = ResourceBundle.getBundle("pl.comp.bundles.Language", new Locale(lang));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("Game.fxml"));
        loader.setResources(bundle);
        pane.getChildren().clear();
        initialize(App.class.getResource("Game.fxml"), loader.getResources());
    }

    @FXML
    private void selectField(int x, int y) {
        if (board.checkBoard()) {
            if (selectedField != null) {
                selectedField.setStyle(selectedField.getStyle() +
                        "-fx-background-color: brown;");

            } else {
                Stage stage = (Stage) pane.getScene().getWindow();
                stage.getScene().setOnKeyReleased(keyEvent -> {
                    if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
                        fillField(keyEvent);
                    }
                });
            }
            selectedField = fields[x][y];
            selectedField.setStyle(selectedField.getStyle() +
                    "-fx-background-color: white;");
        }
    }

    @FXML
    private void fillField(Event event) {
        if (selectedField != null) {
            String tekst = Character.toString(event.getSource().toString().charAt(event.getSource().toString().length() - 2));
            if (event instanceof KeyEvent) {
                tekst = "";
            }
            selectedField.setText(tekst);
            if (!board.checkBoard()) {
                selectedField.setTextFill(Color.color(1,0,0));
                saveButton.setDisable(true);
            } else {
                selectedField.setTextFill(Color.color(0,1,0));
                saveButton.setDisable(false);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(url.equals(App.class.getResource("Form.fxml"))) {
            ToggleGroup difficulty = new ToggleGroup();
            rb1.setToggleGroup(difficulty);
            rb1.setSelected(true);
            rb2.setToggleGroup(difficulty);
            rb3.setToggleGroup(difficulty);
            ListCell<Label> buttonCell = new ListCell<>() {
                @Override protected void updateItem(Label item, boolean isEmpty) {
                    super.updateItem(item, isEmpty);
                    setText(item == null ? "" : item.getText());
                }
            };
            langBox.setButtonCell(buttonCell);
            if (lang == null) {
                lang = "PL";
            }
            for (int i = 0; i < langBox.getItems().size(); i++) {
                if (langBox.getItems().get(i).getText().equals(lang)) {
                    langBox.getSelectionModel().select(i);
                    break;
                }
            }
        }

        if(url.equals(App.class.getResource("Game.fxml"))) {
            fields = new Label[9][9];
            fieldBind = new BindSudokuForm[9][9];
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    fields[x][y] = new Label();
                    setFieldStyle(x, y);
                    fieldBind[x][y] = new BindSudokuForm(board, x, y);
                    Bindings.bindBidirectional(fields[x][y].textProperty(), fieldBind[x][y], new Converter());
                    if (boardCopy.get(x, y) == 0) {
                        int finalX = x;
                        int finalY = y;
                        fields[x][y].setOnMouseClicked(mouseEvent -> selectField(finalX, finalY));
                        fields[x][y].setTextFill(Color.color(0,1,0));
                    }
                    pane.add(fields[x][y], x, y);
                }
            }
        }
    }

    private void setFieldStyle(int x, int y) {
        fields[x][y].setStyle("-fx-alignment: center;" +
                              "-fx-font-weight: bold;" +
                              "-fx-font-size: 12pt;" +
                              "-fx-font-family: 'Comic Sans MS';" +
                              "-fx-border-style: solid;" +
                              "-fx-border-width: 1px;" +
                              "-fx-border-color: black;" +
                              "-fx-background-color: brown;");
        fields[x][y].setTextFill(Color.GOLD);
        fields[x][y].setPrefSize(35, 35);
        if((x+1) % 3 == 0 && (y+1) % 3 == 0 && x != 8 && y != 8) {
            fields[x][y].setStyle( fields[x][y].getStyle() +
                    "-fx-border-width: 1px 5px 5px 1px;");
        } else if((y+1) % 3 == 0 && y != 8) {
            fields[x][y].setStyle( fields[x][y].getStyle() +
                    "-fx-border-width: 1px 1px 5px 1px;");
        } else if((x+1) % 3 == 0 && x != 8) {
            fields[x][y].setStyle( fields[x][y].getStyle() +
                    "-fx-border-width: 1px 5px 1px 1px;");
        }
    }
}
