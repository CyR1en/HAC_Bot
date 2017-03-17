package us.cyrien.hacbot;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import us.cyrien.hacbot.main.HAC;


public class Launcher extends Application {
    @FXML
    private TextArea consoleArea;

    public static HAC bot;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GUI/Interface.fxml"));
        primaryStage.getIcons().add(new Image("logo.png"));
        primaryStage.setMinWidth(730);
        primaryStage.setMinHeight(650);
        primaryStage.setTitle("HAC Bot");
        primaryStage.setScene(new Scene(root, 600, 700));
        primaryStage.show();
        bot = new HAC();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
