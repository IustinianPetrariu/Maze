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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sample.*;
import sample.mazegenerators.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsWindowController implements Initializable {

    @FXML
    public TextField seed;
    @FXML
    private ChoiceBox<Integer> rows;
    @FXML
    private ChoiceBox<Integer> columns;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Pane rootPane;

    public void importMazeMouseClick(MouseEvent mouseEvent) {
        try {
            FileChooser fc = new FileChooser();

            fc.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Json Files", "*.json"),
                    new FileChooser.ExtensionFilter("BMP Files", "*.txt"));

            File file = fc.showOpenDialog(null);
            String path = file.getPath();

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) obj;

            //getting parameters
            generatorOption = fromStringToOption((String) jsonObject.get("type"));
            Color color = Color.web( (String) jsonObject.get("color") );
            int rows = Integer.parseInt((String) jsonObject.get("rows"));
            int columns = Integer.parseInt((String) jsonObject.get("columns"));
            int seed = Integer.parseInt((String) jsonObject.get("seed"));
            ///

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../MazeWindow.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            root.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            Stage outputStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            outputStage.setTitle("Output Maze");
            Scene scene = new Scene(root, 635, 511);
            outputStage.setScene(scene);

            Maze maze = new Maze(rows, columns, scene, color);
            MazeGeneratorRunnable mazeGenerator = this.getInstanceFromOption();
            mazeGenerator.setMaze(maze);

            MazeWindowController mazeWindowController = fxmlLoader.<MazeWindowController>getController();
            mazeWindowController.setMaze(mazeGenerator);
            mazeWindowController.setSeed(seed);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    private MazeGeneratorOption fromStringToOption(String type) {
        switch (type) {
            case "DFS_IMPLEMENTATION"     -> {return MazeGeneratorOption.DFS_IMPLEMENTATION;}
            case "KRUSKAL_IMPLEMENTATION" -> {return MazeGeneratorOption.KRUSKAL_IMPLEMENTATION;}
            case "PRIM_IMPLEMENTATION"    -> {return MazeGeneratorOption.PRIM_IMPLEMENTATION;}
        }
        return null;
    }

    public void generateMazeMouseClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../MazeWindow.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        root.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        Stage outputStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        outputStage.setTitle("Output Maze");
        Scene scene = new Scene(root, 635, 511);
        outputStage.setScene(scene);

        Maze maze = new Maze(rows.getValue(), columns.getValue(), scene, colorPicker.getValue());
        MazeGeneratorRunnable mazeGenerator = this.getInstanceFromOption();
        mazeGenerator.setMaze(maze);

        MazeWindowController mazeWindowController = fxmlLoader.<MazeWindowController>getController();
        mazeWindowController.setMaze(mazeGenerator);
        try {
            mazeWindowController.setSeed(Integer.parseInt(seed.getText()));
        }
        catch (NumberFormatException exception) {
            mazeWindowController.setSeed((int)(Math.random() * 100));
        }
    }

    private enum MazeGeneratorOption {
        DFS_IMPLEMENTATION,
        KRUSKAL_IMPLEMENTATION,
        PRIM_IMPLEMENTATION
    }

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
