package sample.mazegenerators;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Maze {
    final private Cell[][] cells;
    final private int numberOfRows;
    final private int numberOfColumns;

    final private Canvas canvas;
    final private GraphicsContext graphicsContext;
    final private Color color;

    public Cell[][] getCells() {
        return cells;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public Color getColor() {
        return color;
    }

    public Maze(int numberOfRows, int numberOfColumns, Scene scene, Color color) {
        this.cells = new Cell[numberOfRows][numberOfColumns];
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;

        this.canvas = (Canvas) scene.lookup("#canvas");
        graphicsContext = canvas.getGraphicsContext2D();
        this.color = color;

        //Cells must be initialized...
        this.initializeCells();
    }


    public void initializeCells() {
        this.instantiateCells();
        this.initializeStaticMembers();
        this.initializeNeighbors();
    }

    private void instantiateCells() {
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    private void initializeStaticMembers() {
        Cell.width  = canvas.getWidth()  / numberOfColumns;
        Cell.height = canvas.getHeight() / numberOfRows;
        Cell.graphicsContext = graphicsContext;
        Cell.color = color;
    }

    private void initializeNeighbors() {
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
    }

}




