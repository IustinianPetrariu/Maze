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

        Parent root = FXMLLoader.load(getClass().getResource("output.fxml"));
        Scene scene = new Scene(root, 792, 649);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());


        primaryStage.setScene(scene);

        ////calculation that needs to be in a non-FX thread

        new Thread()
        {
            public void run()
            {
                MazeController mc = new MazeController(scene, 10,10);
                mc.setBackgroundColor();
                mc.initializeCells();
                mc.drawCells();
            }
        }.start();


        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
