package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller2 implements Initializable {

    @FXML
    ChoiceBox<Integer> rows;

    @FXML
    ChoiceBox<Integer> columns;

    @FXML
    ColorPicker colorPicker;

    @FXML
    private Pane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rows.getItems().setAll(5,10,25);
        rows.setValue(5);
        columns.getItems().setAll(5,10,25);
        columns.setValue(5);
    }

    private void serialiaze(MazeController object) {
        try
        {
            String filename = "file.ser";
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(object);

            out.close();
            file.close();

            System.out.println("Object has been serialized");

        }

        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }


    public void onMouseClicked(MouseEvent mouseEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("output.fxml"));


        Parent root = (Parent) fxmlLoader.load();
        root.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());

        Stage outputStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        outputStage.setTitle("Output Maze");

        Scene scene = new Scene(root, 1129, 829);
        outputStage.setScene(scene);

        MazeController maze = new MazeController(scene,colorPicker.getValue() , rows.getValue(), columns.getValue());


        ControllerOutput controllerOutput =fxmlLoader.<ControllerOutput>getController();
        controllerOutput.getMaze(maze);


//        this.serialiaze(maze);


//        maze.keepRunning = true;
//        new Thread() {
//            public void run() {
//                maze.setBackgroundColor();
//                maze.initializeCells();
//
//
//            }
//        }.start();
//
//        outputStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent windowEvent) {
//                maze.keepRunning = false;
//                System.out.println(outputStage);
//            }
//        });
    }
}
