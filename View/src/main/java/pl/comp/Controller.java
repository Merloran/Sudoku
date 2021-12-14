package pl.comp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class Controller {
    private ObservableList list = FXCollections.observableArrayList();
    @FXML private ChoiceBox<String> difficulty;

    @FXML private void handleSubmitButtonAction(ActionEvent event) {
        list.addAll("łatwy", "średni", "trudny");
        difficulty.setItems(list);
    }
}
