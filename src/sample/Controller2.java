package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller2 implements Initializable {

    @FXML
    ChoiceBox<Integer> rows;

    @FXML
    ChoiceBox<Integer> columns;

    @FXML
    ColorPicker colorPicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rows.getItems().setAll(5,10,25);
        rows.setValue(5);
        columns.getItems().setAll(5,10,25);
        columns.setValue(5);
    }

    @FXML
    private Pane rootPane;

    public void onMouseClicked(MouseEvent mouseEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("output.fxml"));
        root.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());

        Stage outputStage = new Stage();
        outputStage.setTitle("Output Maze");

        Scene scene = new Scene(root, 792, 649);
        outputStage.setScene(scene);

        MazeController maze = new MazeController(scene,colorPicker.getValue() , rows.getValue(), columns.getValue());
        maze.keepRunning = true;
        new Thread() {
            public void run() {
                maze.setBackgroundColor();
                maze.initializeCells();


            }
        }.start();

        outputStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                maze.keepRunning = false;
                System.out.println(outputStage);
            }
        });

        outputStage.show();
    }
}
