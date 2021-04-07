package sample;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MazeController {
    private Scene scene;
    private int numberOfRows;
    private int numberOfColumns;

    private GraphicsContext graphicsContext;
    private Canvas canvas;

    private Cell[][] cells;
    private Timer timer;

    public MazeController(Scene scene, int numberOfRows, int numberOfColumns) {
        this.scene = scene;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.cells = new Cell[numberOfRows][numberOfColumns];
        this.timer = new Timer();

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
                final int I = i;
                final int J = j;
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        cells[I][J].show();
                    }
                };
                timer.schedule(task, 0);
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }
}

