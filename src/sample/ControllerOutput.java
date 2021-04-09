package sample;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerOutput implements Initializable {

    @FXML
    private Canvas canvas;

    @FXML
    private Pane pane;

    private MazeController maze;

    private MazeController deserialize() {
        MazeController object = null;
        String filename = "file.ser";
        // Deserialization
        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            object = (MazeController) in.readObject();

            in.close();
            file.close();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }

        return object;
    }

    public void getMaze(MazeController maze) {
        this.maze = maze;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
//        MazeController maze = deserialize();
        Platform.runLater(() -> {
            System.out.println(maze);
            maze.keepRunning = true;
            new Thread() {
                public void run() {
                    System.out.println("Thread" + this);
                    maze.setBackgroundColor();
                    maze.initializeCells();
                }
            }.start();

            Stage thisStage = (Stage) ( pane.getScene().getWindow() );
            thisStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    maze.keepRunning = false;
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("window2.fxml"));
                        root.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
                        Stage outputStage = new Stage();

                        Scene scene = new Scene(root);

                        outputStage.setScene(scene);

                        outputStage.show();
                    }
                    catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
        });


    }

    public void saveButtonClicked(MouseEvent mouseEvent) {
        FileChooser fc = new FileChooser();

        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                new FileChooser.ExtensionFilter("BMP Files", "*.bmp"),
                new FileChooser.ExtensionFilter("GIF Files", "*.gif"));

        fc.setInitialFileName("img.png");

        File file = fc.showSaveDialog(null);

        try {
            Image snapshot = canvas.snapshot(null, null);
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
