package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

public class StartingWindowController {

    @FXML
    Pane rootPane;

    public void getNextScene(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../SettingsWindow.fxml"));
        root.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void exitButtonClicked(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
