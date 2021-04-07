package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Cell {

    public int i;
    public int j;
    public static double width;
    public static double height;
    public boolean[] walls;
    public boolean visited = false;
    public List<Cell> neighbors;
    public static GraphicsContext graphicsContext;

    public Cell(int i, int j) {
        this.i = i;
        this.j = j;
        walls = new boolean[]{true, true, true, true};
        neighbors = new ArrayList<>();
    }

    public Cell searchForNeighbor() {
        if(neighbors.size() > 0)
        {
            int randomNeighbor = (int) ((Math.random() * 100) % neighbors.size() - 1);
            neighbors.remove(randomNeighbor);
            return neighbors.get(randomNeighbor);

        }
        else return null;

    }


    ///draw a cell
    public void show() {
        double starty = this.i * height;
        double startx = this.j * width;

        graphicsContext.setStroke(Color.WHITE);
        if (walls[0])
            graphicsContext.strokeLine(startx, starty, startx + width, starty);
        if (walls[1])
            graphicsContext.strokeLine(startx + width, starty, startx + width, starty + height);
        if (walls[2])
            graphicsContext.strokeLine(startx, starty + height, startx + width, starty + height);
        if (walls[3])
            graphicsContext.strokeLine(startx, starty, startx, starty + height);
        if (this.visited) {
            graphicsContext.setFill(Color.RED);
            graphicsContext.fillRect(startx, starty, width, height);
        }

    }


}
