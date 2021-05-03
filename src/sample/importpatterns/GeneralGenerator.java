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

public class GeneralGenerator extends MazeGeneratorRunnable {
    private Maze maze;
    public JSONObject jsonObject;

    public GeneralGenerator(JSONObject jsonObject) {
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
            removeWalls(curentCell, nextCell);


            if (reminder != null) {

                if (!reminder.equals(curentCell)) {
                    reminder.visited = true;
                    reminder.show();
                    System.out.println(reminder.i + "," + reminder.j);
                }
            }

            nextCell.drawHead();
            curentCell.visited = true;
            curentCell.show();
            reminder = nextCell;

        }
        reminder.visited=true;
        reminder.show();


//        Cell currentCell = cells[0][0];
//        Stack<Cell> stack = new Stack<>();
//        stack.push(currentCell);
//        while (stack.size() > 0 && keepRunning) {
//            currentCell = stack.peek();
//            currentCell.visited = true;
//            Cell next = currentCell.searchForNeighbor(cells);
//            if (next != null) {
//
//                removeWalls(currentCell, next);
//                pereche = new JSONObject();
//                pereche.put("first", currentCell.i + "," + currentCell.j);
//                perechi.add(pereche);
//                pereche.put("second", next.i + "," + next.j);
//                perechi.add(pereche);
//                next.drawHead();
//                stack.push(next);
//            } else stack.pop();
//            currentCell.show();
//        }
    }
}
