package org.nda.javafx.piradioplayer2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.nda.javafx.piradioplayer2.config.MyConfig;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
       // FXMLLoader loader = new FXMLLoader();
        Parent root =  FXMLLoader.load(Main.class.getResource("views/test.fxml"));
        primaryStage.setTitle("PiRadioPlayer 2");
        Scene mainScene = new Scene(root, 739, 450);
        mainScene.getStylesheets().add(getClass().getResource("views/MainForm.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setScene(mainScene);

        MyConfig myConfig = MyConfig.getInstance("config.properties");
        primaryStage.show();

        //System.out.println(System.getProperty("user.dir"));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
