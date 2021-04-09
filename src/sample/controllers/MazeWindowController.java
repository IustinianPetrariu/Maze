package sample.controllers;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.mazegenerators.Cell;
import sample.Main;
import sample.mazegenerators.MazeGeneratorRunnable;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MazeWindowController implements Initializable {

    @FXML
    private Canvas canvas;
    @FXML
    private Pane pane;
    @FXML
    private Slider slider;

    private MazeGeneratorRunnable mazeGenerator;

    public void setMaze(MazeGeneratorRunnable mazeGenerator) {
        this.mazeGenerator = mazeGenerator;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Platform.runLater(() -> {
            MazeGeneratorRunnable.keepRunning = true;
            new Thread() {
                public void run() {
                    mazeGenerator.generateMaze();
                }
            }.start();
            Stage thisStage = (Stage) ( pane.getScene().getWindow() );

            Cell.delay = 220;

            thisStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    MazeGeneratorRunnable.keepRunning = false;
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("../SettingsWindow.fxml"));
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

    public void onDragDetected(MouseEvent mouseEvent) {
        Cell.delay = 221 - (int)slider.getValue();
        System.out.println(Cell.delay);
    }
}
