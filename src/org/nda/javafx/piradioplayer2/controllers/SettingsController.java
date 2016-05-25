package org.nda.javafx.piradioplayer2.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.nda.javafx.piradioplayer2.config.MyConfig;

/**
 * Created by NDA on 25.05.2016.
 */
public class SettingsController {


    @FXML
    private  TextField txtUser;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtHost;

    @FXML
    private Button  btnSave;


    @FXML
    private void initialize(){
        MyConfig config = MyConfig.getInstance();
        txtHost.setText(config.getProperty("host"));
        txtUser.setText(config.getProperty("user"));
        txtPassword.setText(config.getProperty("password"));

        txtUser.setDisable(config.getProperty("user") == "");
        txtPassword.setDisable(config.getProperty("password") == "");

        btnSave.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            config.setProperty("host", txtHost.getText());

            if (!txtUser.isDisabled()){
                config.setProperty("user", txtUser.getText());
                config.setProperty("password", txtPassword.getText());
            }

            config.save();
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
        });
    }
}
