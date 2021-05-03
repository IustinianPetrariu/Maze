package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;


import java.io.FileReader;
import java.io.FileWriter;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Maze generator");

        Parent root = FXMLLoader.load(getClass().getResource("StartingWindow.fxml"));
        Scene scene = new Scene(root, 792, 649);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}


//        ///read from json
//        JSONParser parser = new JSONParser();
//        try {
//            Object obj = parser.parse(new FileReader("E:\\FII\\Semestrul IV\\Programare avansata\\Maze\\Maze\\maze.json"));
//            JSONObject jsonObject = (JSONObject) obj;
//            JSONArray pairs = (JSONArray) jsonObject.get("pairs");
//            var iterator = pairs.iterator();
//            while (iterator.hasNext()) {
//
//                JSONObject pereche = (JSONObject) iterator.next();
//                System.out.println(pereche.get("first"));
//
//            }
//
//
//            } catch(Exception exception){
//                exception.printStackTrace();
//            }


//     try {
//
//             JSONObject jsonObject = new JSONObject();
//             JSONArray perechi = new JSONArray();
//             JSONObject pereche = new JSONObject();
//             pereche.put("first", "1,2");
//             pereche.put("second", "1,3");
//
//             perechi.add(pereche);
//
//             pereche = new JSONObject();
//             pereche.put("first", "1,3");
//             pereche.put("second", "1,3");
//
//             perechi.add(pereche);
//
//
//             jsonObject.put("pairs", perechi);
//             //Write JSON file
//             FileWriter file = new FileWriter("E:\\FII\\Semestrul IV\\Programare avansata\\Maze\\Maze\\maze.json");
//             //We can write any JSONArray or JSONObject instance to the file
//             file.write(jsonObject.toJSONString());
//             file.flush();
//
//             } catch (Exception exception) {
//             exception.printStackTrace();
//             }