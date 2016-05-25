package org.nda.javafx.piradioplayer2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.nda.javafx.piradioplayer2.config.MyConfig;
import org.nda.javafx.piradioplayer2.utils.ErrorHandler;
import org.nda.javafx.piradioplayer2.utils.License;

import javax.swing.text.html.parser.Parser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
       // FXMLLoader loader = new FXMLLoader();
        if (!License.getInstance().checkConnection()){
            new ErrorHandler(new Exception("Отсутствует подключение к интернету"), null);
            try {
                Thread.sleep(2000);
                System.exit(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (License.getInstance().checkLicense()){
            MyConfig myConfig = MyConfig.getInstance("config.txt");
            Parent root = null;
            try {
                root = FXMLLoader.load(Main.class.getResource("views/test.fxml"));
            } catch (IOException e) {
                new ErrorHandler(e, null);
            }
            primaryStage.setTitle("PiRadioPlayer 2");
            Scene mainScene = new Scene(root, 739, 450);
            mainScene.getStylesheets().add(getClass().getResource("views/MainForm.css").toExternalForm());
            primaryStage.setResizable(false);
            primaryStage.setScene(mainScene);
            primaryStage.show();
        }else{
            new ErrorHandler(new Exception("License not found"), null);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

}
