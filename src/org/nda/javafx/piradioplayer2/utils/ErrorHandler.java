package org.nda.javafx.piradioplayer2.utils;

import javafx.scene.control.Alert;

/**
 * Created by NDA on 21.05.2016.
 */
public class ErrorHandler {
    public ErrorHandler(Exception error, String description) {
        description = (description == null) ? "В работе программы произошла ошибка" : description;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(description);
        alert.setContentText(error.getMessage());
        alert.showAndWait();
    }
}
