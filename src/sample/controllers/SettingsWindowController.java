package sample.controllers;

import javafx.event.ActionEvent;
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
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sample.*;
import sample.importpatterns.ImportKruskal;
import sample.mazegenerators.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsWindowController implements Initializable {

    @FXML
    private ChoiceBox<Integer> rows;
    @FXML
    private ChoiceBox<Integer> columns;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Pane rootPane;

    public void ImportMaze(MouseEvent mouseEvent) {

        try {
            FileChooser fc = new FileChooser();

            fc.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Json Files", "*.json"),

                    new FileChooser.ExtensionFilter("BMP Files", "*.bmp"),
                    new FileChooser.ExtensionFilter("GIF Files", "*.gif"));

            File file = fc.showOpenDialog(null);
            String path = file.getPath();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../MazeWindow.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            root.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            Stage outputStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            outputStage.setTitle("Output Maze");
            Scene scene = new Scene(root, 1129, 829);
            outputStage.setScene(scene);
            Maze maze = new Maze(5, 5, scene, Color.RED);

            ///


            MazeGeneratorRunnable mazeGenerator = new ImportKruskal(jsonObject);
            mazeGenerator.getMaze(maze);

            MazeWindowController mazeWindowController = fxmlLoader.<MazeWindowController>getController();
            mazeWindowController.setMaze(mazeGenerator);

           ///


        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

    }


    private enum MazeGeneratorOption {
        DFS_IMPLEMENTATION,
        KRUSKAL_IMPLEMENTATION,
        PRIM_IMPLEMENTATION
    }

    ;

    MazeGeneratorOption generatorOption = MazeGeneratorOption.DFS_IMPLEMENTATION;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeChoiceBoxes();
    }

    private void initializeChoiceBoxes() {
        rows.getItems().setAll(5, 10, 25);
        rows.setValue(5);
        columns.getItems().setAll(5, 10, 25);
        columns.setValue(5);
    }

    public void generateMazeMouseClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../MazeWindow.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        root.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        Stage outputStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        outputStage.setTitle("Output Maze");
        Scene scene = new Scene(root, 1129, 829);
        outputStage.setScene(scene);

        Maze maze = new Maze(rows.getValue(), columns.getValue(), scene, colorPicker.getValue());
        MazeGeneratorRunnable mazeGenerator = this.getInstanceFromOption();
        mazeGenerator.getMaze(maze);

        MazeWindowController mazeWindowController = fxmlLoader.<MazeWindowController>getController();
        mazeWindowController.setMaze(mazeGenerator);
    }

    private MazeGeneratorRunnable getInstanceFromOption() {
        switch (generatorOption) {
            case DFS_IMPLEMENTATION -> {
                return new DFSGenerator();
            }
            case KRUSKAL_IMPLEMENTATION -> {
                return new KruskalGenerator();
            }
            case PRIM_IMPLEMENTATION -> {
                return new PrimGenerator();
            }
            default -> {
                return null;
            }
        }
    }

    public void dfsImplementationSelected(ActionEvent actionEvent) {
        generatorOption = MazeGeneratorOption.DFS_IMPLEMENTATION;
        System.out.println("DFS implementation selected..");
    }

    public void kruskalImplementationSelected(ActionEvent actionEvent) {
        generatorOption = MazeGeneratorOption.KRUSKAL_IMPLEMENTATION;
        System.out.println("Kruskal's implementation selected..");
    }

    public void primImplementationSelected(ActionEvent actionEvent) {
        generatorOption = MazeGeneratorOption.PRIM_IMPLEMENTATION;
        System.out.println("Prim's implementation selected..");
    }
}
