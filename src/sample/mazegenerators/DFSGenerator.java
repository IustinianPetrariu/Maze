package sample.mazegenerators;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Stack;

public class DFSGenerator extends MazeGeneratorRunnable implements MazeGenerator {
    private Maze maze;

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
        JSONObject pereche ;

        //maze.initializeCells();

        this.drawField(canvas, graphicsContext, color, numberOfRows, numberOfColumns);

        Cell currentCell = cells[0][0];
        Stack<Cell> stack = new Stack<>();
        stack.push(currentCell);
        while (stack.size() > 0 && keepRunning) {
            currentCell = stack.peek();
            currentCell.visited = true;
            Cell next = currentCell.searchForNeighbor(cells);
            if (next != null) {

                removeWalls(currentCell, next);
                pereche = new JSONObject();
                pereche.put("first", currentCell.i + "," + currentCell.j);
//                pereche = new JSONObject();
                pereche.put("second", next.i + "," + next.j);
                perechi.add(pereche);
                next.drawHead();
                stack.push(next);
            } else stack.pop();
            currentCell.show();
        }
    }
}
