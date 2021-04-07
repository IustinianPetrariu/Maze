package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Controller1 {

    @FXML
    Pane rootPane;

    public void onMouseClicked(MouseEvent mouseEvent) throws IOException {
        Pane root = FXMLLoader.load(getClass().getResource("window2.fxml"));
        root.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());

        rootPane.getChildren().setAll(root);
    }
}
