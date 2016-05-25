package org.nda.javafx.piradioplayer2.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.nda.javafx.piradioplayer2.config.MyConfig;
import org.nda.javafx.piradioplayer2.models.ModelPlayer;
import org.nda.javafx.piradioplayer2.models.ModelPlaylist;
import org.nda.javafx.piradioplayer2.utils.*;


import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class MainController  {

    private SshClient ssh;
    private PlayerControls player;


    @FXML
    private Button btnConnect;

    @FXML
    private Button btnDisconnect;

    @FXML
    private Label lblConnectStatus;

    @FXML
    private Button btnSettings;

    @FXML
    private Label lblVolume;

    @FXML
    private Slider sliderAlsaVolume;

    @FXML
    private Button btnAlsaVolumeMute;

    @FXML
    private Button btnAlsaVolumePlus;

    @FXML
    private Button btnAlsaVolumeMinus;

    @FXML
    private Button btnPrev;

    @FXML
    private Button btnStop;

    @FXML
    private Button btnPlayPause;

    @FXML
    private Button btnNext;

    @FXML
    private Label lblSongStatus;

    @FXML
    private TextField txtSongTitle;

    public TableView<ModelPlaylist> getTablePlaylist() {
        return tablePlaylist;
    }

    @FXML
    private Pane volumePane;

    @FXML
    private Pane playerPane;

    @FXML
    private ToggleButton btnRandom;

    @FXML
    private ToggleButton btnRepeat;


    @FXML
    TableView<ModelPlaylist> tablePlaylist;
    @FXML
    TableColumn<ModelPlaylist, Integer> idColumn;
    @FXML
    TableColumn<ModelPlaylist, String> songColumn;

    @FXML
    TableColumn<ModelPlaylist, String> removeColumn;

    private void refreshConnectStatus(){
        if (ssh.isConnected()){
            lblConnectStatus.setStyle("-fx-text-fill: green");
            lblConnectStatus.setText("Подключено");
            volumePane.setDisable(false);
            playerPane.setDisable(false);
            btnSettings.setDisable(true);
            btnConnect.setDisable(true);
            btnDisconnect.setDisable(false);

        }else{
            lblConnectStatus.setStyle("-fx-text-fill: red");
            lblConnectStatus.setText("Отключено");
            volumePane.setDisable(true);
            playerPane.setDisable(true);
            btnSettings.setDisable(false);
            btnConnect.setDisable(false);
            btnDisconnect.setDisable(true);

        }
    }

    public void refresh(){
        if (ssh.isConnected()){
            player.setVolume(player.getAmixerVolume());
            player.getSongTitle();
            player.getSongStatus();
            player.getRandomStatus();
            player.getRepeatStatus();
            tablePlaylist.setItems(player.getPlaylist());
            player.getSongStatus();
            if (player.getAmixerMute()) {
                ModelPlayer.getInstance().setVolume("0");
                btnAlsaVolumeMinus.setDisable(true);
                btnAlsaVolumePlus.setDisable(true);
                sliderAlsaVolume.setDisable(true);
            }
        }
    }

    public void refreshSong(){
        if (ssh.isConnected()){
            player.getSongTitle();
            player.getSongStatus();
            player.getRandomStatus();
            player.getRepeatStatus();
            //tablePlaylist.setItems(player.getPlaylist());
        }
    }

    public void refreshSong2(){
        tablePlaylist.getSelectionModel().select(player.getNumberSong()-1);
        refreshSong();
    }


    @FXML
    public void initialize(){

        ssh = SshClient.getInstance();
        player = PlayerControls.getInstance();

        btnRandom.textProperty().bind(ModelPlayer.getInstance().randomProperty());
        btnRepeat.textProperty().bind(ModelPlayer.getInstance().repeatProperty());

        idColumn.setCellValueFactory(param -> param.getValue().getIdProperty().asObject());
        songColumn.setCellValueFactory(param -> param.getValue().getSongProperty());
        removeColumn.setCellValueFactory(param -> param.getValue().getDeleteProperty());


        tablePlaylist.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2){
                int x = tablePlaylist.getSelectionModel().getSelectedItem().getId();
                player.playTo(x);
                refreshSong();
            }

            if (tablePlaylist.getFocusModel().getFocusedCell().getColumn() == 2){
                int x = tablePlaylist.getSelectionModel().getSelectedItem().getId();
                String song = tablePlaylist.getSelectionModel().getSelectedItem().getSong();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Подтверждение удаления файла.");
                alert.setHeaderText("Вы действительно хотите удалить?");
                alert.setContentText(song);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    player.deleteSong(x, song);
                    refresh();
                } else {
                    alert.close();
                }
            }
        });

        btnRandom.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Objects.equals(newValue, "on")) btnRandom.setStyle("-fx-text-fill: green");
            if (Objects.equals(newValue, "off")) btnRandom.setStyle("-fx-text-fill: red");
        });

        btnRepeat.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Objects.equals(newValue, "on")) btnRepeat.setStyle("-fx-text-fill: green");
            if (Objects.equals(newValue, "off")) btnRepeat.setStyle("-fx-text-fill: red");
        });

        txtSongTitle.textProperty().bind(ModelPlayer.getInstance().songProperty());
        lblSongStatus.textProperty().bind(ModelPlayer.getInstance().songStatusProperty());
        lblVolume.textProperty().bind(ModelPlayer.getInstance().volumeProperty());
        sliderAlsaVolume.valueProperty().bindBidirectional(ModelPlayer.getInstance().volumeIntProperty());

        btnAlsaVolumeMute.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            player.toggleMute();
            btnAlsaVolumeMinus.setDisable(!btnAlsaVolumeMinus.isDisable());
            btnAlsaVolumePlus.setDisable(!btnAlsaVolumePlus.isDisabled());
            sliderAlsaVolume.setDisable(!sliderAlsaVolume.isDisabled());
        });

        btnAlsaVolumePlus.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            player.setVolume("1+");
        });

        btnAlsaVolumeMinus.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            player.setVolume("1-");
        });


        btnConnect.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            String user = MyConfig.getInstance().getProperty("user");
            String host = MyConfig.getInstance().getProperty("host");
            String password = MyConfig.getInstance().getProperty("password");
            int port = 22;
            ssh.connect(user, host, port, password);
            player.setDefaultMPC();
            refreshConnectStatus();
            refresh();
        });

        btnDisconnect.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            ssh.disconnect();
            refreshConnectStatus();
        });

        btnSettings.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../views/settings.fxml"));
                Scene scene = new Scene(root, 225, 145);
                scene.getStylesheets().add(getClass().getResource("../views/settings.css").toExternalForm());
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UTILITY);
                stage.setTitle("Настройки");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        sliderAlsaVolume.setOnMouseReleased(event -> {
            String x = Integer.toString((int)sliderAlsaVolume.getValue());
            player.setVolume(x);
        });


        btnPrev.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {player.prev(); refreshSong2();});
        btnStop.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {player.stop(); refreshSong();});
        btnPlayPause.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {player.play(); refreshSong();});
        btnNext.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {player.next(); refreshSong2();});

        btnRandom.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {player.toggleRandom(); refreshSong();});
        btnRepeat.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {player.toggleRepeat(); refreshSong();});

    }

}
