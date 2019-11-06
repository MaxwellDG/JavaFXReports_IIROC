package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    private int screen_width = 500;
    private int screen_height = 650;

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("IIROC Report Creation");
        primaryStage.setScene(new Scene(root, screen_width, screen_height));
        primaryStage.show();
        primaryStage.setResizable(true);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
