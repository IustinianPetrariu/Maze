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
<<<<<<< Updated upstream
=======
    public static Color color;
    public boolean alreadyDraw;
>>>>>>> Stashed changes

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
<<<<<<< Updated upstream


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
=======
<<<<<<< Updated upstream
    ///draw a cell 
    public void show() 
    {  
        double starty = this.i * height ;
        double startx = this.j * width ;

        System.out.println(startx);
        System.out.println(starty);
        System.out.println(width);
        System.out.println(height);

        // graphicsContext.setFill(Color.WHITE);
        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.strokeLine(startx, starty, startx + width, starty);
        graphicsContext.strokeLine(startx, starty, startx, starty + height);
        graphicsContext.strokeLine(startx,starty+height, startx+width, starty+height);
        graphicsContext.strokeLine(startx + width, starty, startx + width, starty + height );
         
=======

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
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
>>>>>>> Stashed changes
>>>>>>> Stashed changes

    }


}
