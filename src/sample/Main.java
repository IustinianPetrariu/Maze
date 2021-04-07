package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Maze generator");

        Parent root = FXMLLoader.load(getClass().getResource("output.fxml"));


        Scene scene = new Scene(root, 792, 649);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());




        primaryStage.setScene(scene);
        primaryStage.show();

        MazeController mc = new MazeController(scene, 5,5);
        mc.setBackgroundColor();
        mc.initializeCells();
        mc.drawCells();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
