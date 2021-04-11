package sample.mazegenerators;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Maze {
    final private Cell[][] cells;
    final private int numberOfRows;
    final private int numberOfColumns;
    final private Canvas canvas;
    final private GraphicsContext graphicsContext;
    final private Color color;
    public List<Wall> edges = new ArrayList<>();
    public int[] father;

    public Cell[][] getCells() {
        return cells;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getNumberOfCell(int i, int j) {
        return i * numberOfColumns + j + 1;
    }

    public int findRoot(Cell cell) {
        int numberOfCell = getNumberOfCell(cell.i, cell.j);
        while (father[numberOfCell] != 0)
            numberOfCell = father[numberOfCell];
        return numberOfCell;
    }

    public void union(Cell first, Cell second) {

        father[findRoot(first)] = findRoot(second);
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
        this.father = new int[numberOfColumns * numberOfRows + 1];

        //Cells must be initialized...
        this.initializeCells();
    }

    public void instantiateWalls() {
        for (int i = 0; i < numberOfRows; i++)
            for (int j = 0; j < numberOfColumns; j++) {
                if (i != numberOfRows - 1) {
                    if (j != numberOfColumns - 1) {
                        Wall wall;
                        wall = new Wall(cells[i][j], cells[i][j + 1]);
                        edges.add(wall);
                        wall = new Wall(cells[i][j], cells[i + 1][j]);
                        edges.add(wall);
                    } else {
                        Wall wall;
                        wall = new Wall(cells[i][j], cells[i + 1][j]);
                        edges.add(wall);
                    }
                } else {
                    if (j != numberOfColumns - 1) {
                        Wall wall;
                        wall = new Wall(cells[i][j], cells[i][j + 1]);
                        edges.add(wall);
                    }
                }
            }
    }

    public void initializeCells() {
        this.instantiateCells();
        this.initializeStaticMembers();
        this.instantiateWalls();
    }

    private void instantiateCells() {
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    private void initializeStaticMembers() {
        Cell.width = canvas.getWidth() / numberOfColumns;
        Cell.height = canvas.getHeight() / numberOfRows;
        Cell.graphicsContext = graphicsContext;
        Cell.color = color;
        Cell.numberOfRow = numberOfRows;
        Cell.numberOfColumns = numberOfColumns;
    }


}




