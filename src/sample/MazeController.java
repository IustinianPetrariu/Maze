package sample;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class MazeController {
    private Scene scene;
    private int numberOfRows;
    private int numberOfColumns;
    private Cell currentCell;
    private long dinamicWait;

    private GraphicsContext graphicsContext;
    private Canvas canvas;

    private Cell[][] cells;

    public MazeController(Scene scene, int numberOfRows, int numberOfColumns) {
        this.scene = scene;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.cells = new Cell[numberOfRows][numberOfColumns];
        canvas = (Canvas) scene.lookup("#canvas");
        graphicsContext = canvas.getGraphicsContext2D();
    }

    public void removeWalls(Cell current, Cell next) {
        if (current.j - next.j == -1) {
            current.walls[1] = false;
            next.walls[3] = false;
        }
        if (current.j - next.j == 1) {
            current.walls[3] = false;
            next.walls[1] = false;
        }
        if (current.i - next.i == -1) {
            current.walls[2] = false;
            next.walls[0] = false;
        }
        if (current.i - next.i == 1) {
            current.walls[0] = false;
            next.walls[2] = false;
        }
    }

    public void setBackgroundColor() {
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void drawGreed() {
        double xOffset = canvas.getWidth() / numberOfColumns;
        double yOffset = canvas.getHeight() / numberOfRows;
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                double starty = i * yOffset;
                double startx = j * xOffset;
                graphicsContext.setStroke(Color.WHITE);
                graphicsContext.strokeLine(startx, starty, startx + xOffset, starty);
                graphicsContext.strokeLine(startx, starty, startx, starty + yOffset);
                graphicsContext.strokeLine(startx, starty + yOffset, startx + xOffset, starty + yOffset);
                graphicsContext.strokeLine(startx + xOffset, starty, startx + xOffset, starty + yOffset);
            }
        }
    }

    public void initializeCells() {
        double xOffset = canvas.getWidth() / numberOfColumns;
        double yOffset = canvas.getHeight() / numberOfRows;

        Cell.width = xOffset;
        Cell.height = yOffset;
        Cell.graphicsContext = graphicsContext;

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }

        drawGreed();

        currentCell = cells[0][0];
        Stack<Cell> stack = new Stack<>();
        stack.push(currentCell);
        while (stack.size() > 0) {
            currentCell = stack.peek();
            currentCell.visited = true;
            Cell next = currentCell.searchForNeighbor(cells, numberOfRows, numberOfColumns);
            if (next != null) {
                removeWalls(currentCell, next);
                next.drawHead();
                stack.push(next);
            } else stack.pop();
            currentCell.show();
        }
    }

}


