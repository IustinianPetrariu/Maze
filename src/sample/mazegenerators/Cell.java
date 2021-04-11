package sample.mazegenerators;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Cell {

    public int i;
    public int j;

    public boolean alreadyDrawn;
    public boolean[] walls;
    public boolean visited = false;
    public List<Cell> neighbors;

    public static double width;
    public static double height;
    public static GraphicsContext graphicsContext;
    public static Color color;
    public static long delay;
    public static int numberOfRow;
    public static int numberOfColumns;


    public Cell(int i, int j) {
        this.i = i;
        this.j = j;
        walls = new boolean[]{true, true, true, true};
        neighbors = new ArrayList<>();
    }

    public Cell searchForNeighbor(Cell[][] cells, int numberOfRows, int numberOfColumns) {
        int i = this.i;
        int j = this.j;

        neighbors.clear();

        if (i != 0 && !cells[i - 1][j].visited)
            neighbors.add(cells[i - 1][j]);
        if (j != numberOfColumns - 1 && !cells[i][j + 1].visited)
            neighbors.add(cells[i][j + 1]);
        if (i != numberOfRows - 1 && !cells[i + 1][j].visited)
            neighbors.add(cells[i + 1][j]);
        if (j != 0 && !cells[i][j - 1].visited)
            neighbors.add(cells[i][j - 1]);
        if (neighbors.size() > 0) {
            int randomIndex = (int) (Math.random() * 100) % neighbors.size();
            return neighbors.get(randomIndex);
        } else return null;
    }

    public void drawHead() {
        double startY = this.i * height;
        double startX = this.j * width;
        if (color == Color.YELLOW) {
            graphicsContext.setFill(Color.RED);
        } else {
            graphicsContext.setFill(Color.YELLOW);
        }
        graphicsContext.fillRect(startX, startY, width, height);
    }

    public void show() {
        double startY = this.i * height;
        double startX = this.j * width;

        if (this.visited) {
            graphicsContext.setFill(color);
            graphicsContext.fillRect(startX, startY, width, height);
        }

        if (color == Color.WHITE) {
            graphicsContext.setStroke(Color.BLACK);
        } else {
            graphicsContext.setStroke(Color.WHITE);
        }

        graphicsContext.setLineWidth(5);
        if (walls[0])
            graphicsContext.strokeLine(startX, startY, startX + width, startY);

        if (walls[1])
            graphicsContext.strokeLine(startX + width, startY, startX + width, startY + height);

        if (walls[2])
            graphicsContext.strokeLine(startX, startY + height, startX + width, startY + height);

        if (walls[3])
            graphicsContext.strokeLine(startX, startY, startX, startY + height);

        if (graphicsContext.getStroke() == Color.WHITE) {
            graphicsContext.setStroke(Color.BLACK);
        } else {
            graphicsContext.setStroke(Color.WHITE);
        }

        try {
            TimeUnit.MILLISECONDS.sleep(Cell.delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
