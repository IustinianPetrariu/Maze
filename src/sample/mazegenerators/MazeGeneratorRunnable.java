package sample.mazegenerators;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.json.simple.JSONArray;

public abstract class MazeGeneratorRunnable implements MazeGenerator {
    public static boolean keepRunning = true;
    public static int seed;

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

    private void setBackgroundColor(Canvas canvas, GraphicsContext graphicsContext, Color color) {
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (color == Color.WHITE) {
            graphicsContext.setFill(Color.WHITE);
        } else {
            graphicsContext.setFill(Color.BLACK);
        }
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void drawGrid(Canvas canvas, GraphicsContext graphicsContext, Color color, int numberOfRows, int numberOfColumns) {
        double xOffset = canvas.getWidth() / numberOfColumns;
        double yOffset = canvas.getHeight() / numberOfRows;
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                double startY = i * yOffset;
                double startX = j * xOffset;

                if (color == Color.WHITE) {
                    graphicsContext.setStroke(Color.BLACK);

                } else {
                    graphicsContext.setStroke(Color.WHITE);
                }

                graphicsContext.strokeLine(startX, startY, startX + xOffset, startY);
                graphicsContext.strokeLine(startX, startY, startX, startY + yOffset);
                graphicsContext.strokeLine(startX, startY + yOffset, startX + xOffset, startY + yOffset);
                graphicsContext.strokeLine(startX + xOffset, startY, startX + xOffset, startY + yOffset);

                if (color == Color.WHITE) {
                    graphicsContext.setStroke(Color.BLACK);

                } else {
                    graphicsContext.setStroke(Color.WHITE);
                }
            }
        }
    }

    public void drawField(Canvas canvas, GraphicsContext graphicsContext, Color color, int numberOfRows, int numberOfColumns) {
        this.setBackgroundColor(canvas, graphicsContext, color);
        this.drawGrid(canvas, graphicsContext, color, numberOfRows, numberOfColumns);
    }
}
