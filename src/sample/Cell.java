package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

public class Cell {

    public int i; 
    public int j; 
    public static double width; 
    public static double height;

    public static GraphicsContext graphicsContext;

    public Cell(int i, int j) {
        this.i = i;
        this.j = j ;
        
    }
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
         

    }


}
