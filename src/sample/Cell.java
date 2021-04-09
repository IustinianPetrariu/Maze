package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Cell implements Serializable {

    public int i;
    public int j;
    public static double width;
    public static double height;
    public boolean[] walls;
    public boolean visited = false;
    public List<Cell> neighbors;

    public static GraphicsContext graphicsContext;
    public static Color color;
    public static int numberOfCells;
    public boolean alreadyDraw;

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
            // System.out.print(randomIndex + " " + neighbors.size());
            // System.out.println();

            return neighbors.get(randomIndex);
        } else return null;
    }

    public void drawHead() {
        double starty = this.i * height;
        double startx = this.j * width;
        graphicsContext.setFill(Color.YELLOW);
        graphicsContext.fillRect(startx, starty, width, height);
    }

    ///draw a cell
    public void show() {
        double starty = this.i * height;
        double startx = this.j * width;

        if (this.visited) {
            graphicsContext.setFill(color);
            graphicsContext.fillRect(startx, starty, width, height);
        }

        if(color == Color.WHITE) {
            graphicsContext.setStroke(Color.BLACK);
        }
        else {
            graphicsContext.setStroke(Color.WHITE);
        }

        graphicsContext.setLineWidth(5);
        if (walls[0])
            graphicsContext.strokeLine(startx, starty, startx + width, starty);

        if (walls[1])
            graphicsContext.strokeLine(startx + width, starty, startx + width, starty + height);

        if (walls[2])
            graphicsContext.strokeLine(startx, starty + height, startx + width, starty + height);

        if (walls[3])
            graphicsContext.strokeLine(startx, starty, startx, starty + height);

        if(graphicsContext.getStroke() == Color.WHITE) {
            graphicsContext.setStroke(Color.BLACK);

        }
        else {
            graphicsContext.setStroke(Color.WHITE);
        }

        try {
            long time;
            switch (numberOfCells) {
                case 25:
                    time = 200;
                    break;
                case 625:
                    time = 25;
                default:
                    time = 100;
            }
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
