package sample.mazegenerators;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.naming.NamingEnumeration;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrimGenerator extends MazeGeneratorRunnable implements MazeGenerator {
    private Maze maze;

    @Override
    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public Maze getMaze() {
        return maze;
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
        System.out.println(maze.edges.size());


        //maze.initializeCells();
        this.drawField(canvas, graphicsContext, color, numberOfRows, numberOfColumns);
        /// implement Prim's

        List<Cell> white = new ArrayList<>();
        ///choose starting point
        white.add(cells[numberOfRows / 2][numberOfColumns / 2]);

        ///iterate through list
        while (white.size() > 0 && keepRunning) {
            int randomIndex = Math.abs(MazeGeneratorRunnable.generator.nextInt()) % white.size();
            Cell chosen = white.get(randomIndex);

            chosen.visited = true;
            for (Cell neighborr : chosen.getNeighbors(cells)) {
                neighborr.show(Color.PINK);
            }

            Cell neighbor = chosen.searchForNeighbor(cells);

            if (neighbor != null) {
                neighbor.visited = true;
                for (Cell neighborr : neighbor.getNeighbors(cells)) {
                    neighborr.show(Color.PINK);
                }
                removeWalls(chosen, neighbor);
                Cell.color = Color.WHITE;
                chosen.show();
                neighbor.show();
                white.add(neighbor);
            } else
                white.remove(chosen);


        }

    }
}
