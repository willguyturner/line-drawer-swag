/**
 * Based on the ecs 100 template
 * Code for drawing shapes with mouse
 * Name: Will Turner
 * Date: 3/5/2021
 */
import ecs100.*;
import java.util.*;
import java.io.*;
import java.awt.Color;
import javax.swing.JColorChooser;


/**
 * draw a circle of random colours when clicked
 */
public class DrawShape{
    private double shapeIndex;
    private double sizeX;
    private double sizeY;
    private double startX;
    private double startY;

    /**  
     * Quit button initialiser
    */
    public DrawShape(){
        UI.initialise();
        UI.addButton("Quit", UI::quit);
        UI.addButton("Toggle Shape", this::changeShape);
        UI.addSlider("X Size", 1, 300, 150, this::setX);
        UI.addSlider("Y Size", 1, 300, 150, this::setY);
        UI.addSlider("Line Width", 1, 100, 50, this::setWidth);
        UI.addButton("Change Colour", this::randColour);
        UI.addButton("Clear", this::erase);
    }

    /**
     * Draw an oval when clicked
     */
    public void doMouse(String action, double x, double y)
    {
        if (action.equals("clicked") && (shapeIndex == 0))
        {
            UI.fillOval(x - (sizeX/2), y - (sizeY/2), sizeX, sizeY);
        }
        
        else if (action.equals("pressed") && (shapeIndex == 1)) 
        {   
            startX = x;
            startY = y;
        }
        else if (action.equals("released") && (shapeIndex == 1)) {
            UI.drawLine(startX, startY, x, y);
        }
        
        else if (action.equals("pressed") && (shapeIndex == 2)) {
            startX = x;
            startY = y;
        }
         else if (action.equals("released") && (shapeIndex == 2)) {
            if(startX < x && startY < y){
                UI.fillRect(this.startX, this.startY, (x - startX), (y - startY));  // Top left to bottom right
           
            } else if(startX > x && startY > y) {
                UI.fillRect(x, y, (startX - x), (startY - y));                      // Bottom right to top left
               
            } else if(startX > x && startY < y) {
                UI.fillRect(x, startY, (startX - x), (y - startY));                 // Top right to bottom left
               
            } else if(startX < x && startY > y) {
                UI.fillRect(startX, y, (x - startX), (startY - y));                 // Bottom left to top right
               
            }
        
        }
    }
   
    /**
     * method for chaning oval colour randomly
     */
    public void randColour()
    {
        Color col = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
        UI.setColor(col);
        UI.println("Changed colour to random.");
    }

    public void erase()
    {
        UI.clearGraphics();
        UI.println("*Erased Graphics Window*");
    }
    
    public double changeShape()
    {
        shapeIndex ++;
        if (shapeIndex > 2){
            shapeIndex = 0;
        }
        if (shapeIndex == 0){
            UI.println("Set draw tool to oval.");
        }
        if (shapeIndex == 1){
            UI.println("Set draw tool to line.");
        }
        if (shapeIndex == 2){
            UI.println("Set draw tool to polygon.");
        }
        return shapeIndex;
    }
    
    public void setX(double size)
    {
        sizeX = size;
        UI.println("Set size X to: " + size);
    }
    
    public void setY(double size)
    {
        sizeY = size;
        UI.println("Set size Y to: " + size);
    }
    
    public void setWidth(double size)
    {
        UI.setLineWidth(size);
        UI.println("Set line width to: " + size);
    }
    
    /**
     * main routine for class
     */
    public static void main(String[] args){
        DrawShape obj = new DrawShape();
        UI.setMouseListener(obj::doMouse);
    }

}