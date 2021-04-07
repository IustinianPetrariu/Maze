package sample;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.concurrent.TimeUnit;

public class MazeController {
    private Scene scene;
    private int numberOfRows;
    private int numberOfColumns;

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

    public void setBackgroundColor() {
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
    }


    public void initializeCells() {
        double xOffset = canvas.getWidth()/numberOfColumns;
        double yOffset = canvas.getHeight()/numberOfRows;

        Cell.width = xOffset;
        Cell.height = yOffset;
        Cell.graphicsContext = graphicsContext;

        for(int i=0;i<numberOfRows;i++) {
            for(int j=0;j<numberOfColumns;j++) {
                cells[i][j] = new Cell(i,j);
            }
        }
    }

    public void drawCells() {
        for(int i=0;i<numberOfRows;i++) {
            for(int j=0;j<numberOfColumns;j++) {
                cells[i][j].show();
                try {
//                    TimeUnit.SECONDS.sleep(1);
                    TimeUnit.MILLISECONDS.sleep(500);
                }
                catch (InterruptedException e) {
                    System.out.println("Asd");
                }

            }
        }
    }
}

