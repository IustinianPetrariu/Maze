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
import javafx.stage.Stage;
import sample.*;
import sample.mazegenerators.*;

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


    private enum MazeGeneratorOption {
        DFS_IMPLEMENTATION,
        KRUSKAL_IMPLEMENTATION,
        PRIM_IMPLEMENTATION
    };

    MazeGeneratorOption generatorOption = MazeGeneratorOption.DFS_IMPLEMENTATION;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeChoiceBoxes();
    }

    private void initializeChoiceBoxes() {
        rows.getItems().setAll(5,10,25);
        rows.setValue(5);
        columns.getItems().setAll(5,10,25);
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

        MazeWindowController mazeWindowController =fxmlLoader.<MazeWindowController>getController();
        mazeWindowController.setMaze(mazeGenerator);
    }

    private MazeGeneratorRunnable getInstanceFromOption() {
        switch (generatorOption) {
            case DFS_IMPLEMENTATION ->      {return new DFSGenerator();}
            case KRUSKAL_IMPLEMENTATION ->  {return new KruskalGenerator();}
            case PRIM_IMPLEMENTATION ->     {return new PrimGenerator();}
            default ->                      {return null;}
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
