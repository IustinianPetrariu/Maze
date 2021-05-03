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
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.simple.JSONObject;
import sample.mazegenerators.*;
import sample.Main;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileWriter;
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
    private int seed;

    public void setSeed(int seed) {
        this.seed = seed;
    }
    public void setMaze(MazeGeneratorRunnable mazeGenerator) {
        this.mazeGenerator = mazeGenerator;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            MazeGeneratorRunnable.keepRunning = true;
            MazeGeneratorRunnable.seed = seed;
            new Thread(() -> {
                mazeGenerator.generateMaze();
            }).start();
            Stage thisStage = (Stage) (pane.getScene().getWindow());

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
                    } catch (IOException e) {
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void onDragDetected(MouseEvent mouseEvent) {
        Cell.delay = 221 - (int) slider.getValue();
        System.out.println(Cell.delay);
    }

    public void exportJson(MouseEvent mouseEvent) {

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Json Files", "*.json"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fc.setInitialFileName("maze.json");
        File file = fc.showSaveDialog(null);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", getType(mazeGenerator));
        jsonObject.put("color", toHexString(mazeGenerator.getMaze().getColor()));
        jsonObject.put("rows", mazeGenerator.getMaze().getNumberOfRows());
        jsonObject.put("columns", mazeGenerator.getMaze().getNumberOfColumns());
        jsonObject.put("seed", seed);

        try {
            FileWriter fileSave = new FileWriter(file);

            fileSave.write(jsonObject.toJSONString());
            fileSave.flush();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static String toHexString(Color color) {
        int r = ((int) Math.round(color.getRed()     * 255)) << 24;
        int g = ((int) Math.round(color.getGreen()   * 255)) << 16;
        int b = ((int) Math.round(color.getBlue()    * 255)) << 8;
        int a = ((int) Math.round(color.getOpacity() * 255));
        return String.format("#%08X", (r + g + b + a));
    }

    private Object getType(MazeGeneratorRunnable mazeGenerator) {
        if (mazeGenerator instanceof DFSGenerator) {
            return "DFS_GENERATOR";
        }
        else if (mazeGenerator instanceof KruskalGenerator) {
            return "KRUSKAL_IMPLEMENTATION";
        }
        else if (mazeGenerator instanceof PrimGenerator) {
            return "PRIM_IMPLEMENTATION";
        }
        return null;
    }
}
