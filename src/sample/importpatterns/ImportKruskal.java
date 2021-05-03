package sample.importpatterns;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sample.mazegenerators.Cell;
import sample.mazegenerators.Maze;
import sample.mazegenerators.MazeGeneratorRunnable;

import java.util.Stack;

public class ImportKruskal extends MazeGeneratorRunnable {
    private Maze maze;
    public JSONObject jsonObject;

    public ImportKruskal(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public void getMaze(Maze maze) {
        this.maze = maze;

    }

    @Override
    public void generateMaze() {
        //getting Maze members
        Canvas canvas = maze.getCanvas();
        GraphicsContext graphicsContext = maze.getGraphicsContext();
        Color color = maze.getColor();
        int numberOfRows = maze.getNumberOfRows();
        int numberOfColumns = maze.getNumberOfColumns();
        Cell[][] cells = maze.getCells();
        // open json filees
        perechi = new JSONArray();
        JSONObject pereche;

        //maze.initializeCells();

        this.drawField(canvas, graphicsContext, color, numberOfRows, numberOfColumns);

        JSONArray pairs = (JSONArray) jsonObject.get("pairs");

        var iterator = pairs.iterator();
        Cell.color = Color.RED;
        Cell reminder = null;
        while (iterator.hasNext()) {

            pereche = (JSONObject) iterator.next();
            String first = (String) pereche.get("first");
            String second = (String) pereche.get("second");
            String[] current = first.split(",");
            String[] next = second.split(",");
            int current_i = Integer.parseInt(current[0]);
            int current_j = Integer.parseInt(current[1]);

            int next_i = Integer.parseInt(next[0]);
            int next_j = Integer.parseInt(next[1]);

            Cell curentCell = cells[current_i][current_j];
            Cell nextCell = cells[next_i][next_j];

            if (maze.findRoot(curentCell) != maze.findRoot(nextCell))
            {
                 maze.union(curentCell, nextCell);
                 removeWalls(curentCell, nextCell);
                 curentCell.visited= true;
                 nextCell.visited=true;
                 curentCell.show();
                 nextCell.show();

            }
        }
         for(int i = 0 ; i < maze.father.length; i++)
             System.out.println(maze.father[i]);


    }
}
