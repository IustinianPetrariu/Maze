package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Maze generator");
        Stage secondStage = new Stage();
        Parent root1 = FXMLLoader.load(getClass().getResource("output.fxml"));
        Scene scene1 = new Scene(root1, 792, 649);
        scene1.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        secondStage.setScene(scene1);

        Parent root = FXMLLoader.load(getClass().getResource("output.fxml"));
        Scene scene = new Scene(root, 792, 649);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);

        ////calculation that needs to be in a non-FX thread
        new Thread() {
            public void run() {
                MazeController maze = new MazeController(scene, 5, 5);
                maze.setBackgroundColor();
                maze.initializeCells();
            }
        }.start();
        primaryStage.show();

        new Thread() {
            public void run() {
                MazeController maze = new MazeController(scene1, 5, 5);
                maze.setBackgroundColor();
                maze.initializeCells();
            }
        }.start();
        secondStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
