package sample.mazegenerators;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class KruskalGenerator extends MazeGeneratorRunnable implements MazeGenerator {
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

        while (maze.edges.size() > 0 && keepRunning) {
            int randomIndex = (int) (seed * 1000) % maze.edges.size();
            System.out.println(randomIndex);
            Wall wall = maze.edges.get(randomIndex);
            if (maze.findRoot(wall.first) != maze.findRoot(wall.second)) {

                maze.union(wall.first, wall.second);
                this.removeWalls(wall.first, wall.second);
                if (wall.second.i == wall.first.i + 1) {
                    double startY = wall.first.i * Cell.height;
                    double startX = wall.first.j * Cell.width;
                    graphicsContext.setStroke(Color.YELLOW);
                    graphicsContext.strokeLine(startX, startY + Cell.height, startX + Cell.width, startY + Cell.height);
                }
                if (wall.second.j == wall.first.j + 1) {

                    double startY = wall.first.i * Cell.height;
                    double startX = wall.first.j * Cell.width;
                    graphicsContext.setStroke(Color.YELLOW);
                    graphicsContext.strokeLine(startX + Cell.width, startY, startX + Cell.width, startY + Cell.height);

                }
                try {
                    Thread.sleep(Cell.delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                wall.first.visited = true;
                wall.second.visited = true;
                wall.first.show();
                wall.second.show();
                maze.edges.remove(randomIndex);

            } else {
                maze.edges.remove(randomIndex);
            }
        }

    }
}
