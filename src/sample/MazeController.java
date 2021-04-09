package sample;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class MazeController implements Serializable {
    private Scene scene;
    private int numberOfRows;
    private int numberOfColumns;
    private Cell currentCell;
    private Color color;
    private long dinamicWait;

    private GraphicsContext graphicsContext;
    private Canvas canvas;

    private Cell[][] cells;

    boolean keepRunning = true;

    public MazeController(Scene scene, Color color, int numberOfRows, int numberOfColumns) {
        this.scene = scene;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.color = color;
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

        if (color == Color.WHITE) {
            graphicsContext.setFill(Color.WHITE);
        }
        else {
            graphicsContext.setFill(Color.BLACK);
        }
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void drawGrid() {
        setBackgroundColor();

        double xOffset = canvas.getWidth() / numberOfColumns;
        double yOffset = canvas.getHeight() / numberOfRows;
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                double starty = i * yOffset;
                double startx = j * xOffset;

                if(color == Color.WHITE) {
                    graphicsContext.setStroke(Color.BLACK);

                }
                else {
                    graphicsContext.setStroke(Color.WHITE);
                }
                graphicsContext.strokeLine(startx, starty, startx + xOffset, starty);
                graphicsContext.strokeLine(startx, starty, startx, starty + yOffset);
                graphicsContext.strokeLine(startx, starty + yOffset, startx + xOffset, starty + yOffset);
                graphicsContext.strokeLine(startx + xOffset, starty, startx + xOffset, starty + yOffset);

                if(color == Color.WHITE) {
                    graphicsContext.setStroke(Color.BLACK);

                }
                else {
                    graphicsContext.setStroke(Color.WHITE);
                }
            }
        }
    }
    public void initializeCells() {
        if(keepRunning == false) {
            return;
        }

        double xOffset = canvas.getWidth() / numberOfColumns;
        double yOffset = canvas.getHeight() / numberOfRows;

        Cell.width = xOffset;
        Cell.height = yOffset;
        Cell.graphicsContext = graphicsContext;
        Cell.color = color;
        Cell.numberOfCells = numberOfRows * numberOfColumns;

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {

                if (i != 0)
                    cells[i][j].neighbors.add(cells[i - 1][j]);
                if (j != numberOfColumns - 1)
                    cells[i][j].neighbors.add(cells[i][j + 1]);
                if (i != numberOfRows - 1)
                    cells[i][j].neighbors.add(cells[i + 1][j]);
                if (j != 0)
                    cells[i][j].neighbors.add(cells[i][j - 1]);
            }
        }
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                double starty = i * yOffset;
                double startx = j * xOffset;
                if(color == Color.WHITE) {
                    graphicsContext.setStroke(Color.BLACK);
                }
                else {
                    graphicsContext.setStroke(Color.WHITE);
                }
                graphicsContext.strokeLine(startx, starty, startx + xOffset, starty);
                graphicsContext.strokeLine(startx, starty, startx, starty + yOffset);
                graphicsContext.strokeLine(startx, starty + yOffset, startx + xOffset, starty + yOffset);
                graphicsContext.strokeLine(startx + xOffset, starty, startx + xOffset, starty + yOffset);
            }
        }
        currentCell = cells[0][0];
        Stack<Cell> stack = new Stack<>();
        stack.push(currentCell);
        while (stack.size() > 0 && keepRunning) {
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


    public void drawCells() {
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                cells[i][j].show();
                try {
                    TimeUnit.SECONDS.sleep(1);
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Asd");
                }

            }
        }

        drawGrid();

        currentCell = cells[0][0];
        Stack<Cell> stack = new Stack<>();
        stack.push(currentCell);
        while (stack.size() > 0 && keepRunning) {
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


