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
    private String text;                                            //string for user text input
    private double shapeIndex;                                      //double for current tool selection
    private double sizeX = 150;                                     //initialise x size
    private double sizeY = 150;                                     //initialise y size
    private double startX;                                          //double for mouse x coordinates
    private double startY;                                          //double for mouse y coordinates
    private Color currentColor = Color.black;                       //initialise draw colour
    private boolean erase = false;                                  //initialise erase mode
    
    /**  
     * button initialiser
    */
    public DrawShape(){
        UI.initialise();
        UI.addButton("Quit", UI::quit);                             //quit button
        UI.addButton("Cycle Stamp Tool", this::changeShape);        //cycle stamps button
        UI.addButton("Toggle Erase", this::eraseToggle);            //toggle erase mode button
        UI.addTextField("Enter Text", this::setText);               //user text input for text stamp
        UI.addSlider("X Size", 1, 300, 150, this::setX);            //x size slider user input
        UI.addSlider("Y Size", 1, 300, 150, this::setY);            //y size slider user input
        UI.addSlider("Line Width", 1, 100, 50, this::setWidth);     //line width slider user input
        UI.addSlider("Font Size", 1, 100, 50, this::setFontSize);   //font size slider user input
        UI.addButton("Select Colour", this::selectColour);          //colour selection input
        UI.addButton("Clear Canvas", this::erase);                  //clear canvas completely
        UI.setLineWidth(50);                                        //initialise line width
        UI.setFontSize(10);                                         //initialise font size
    }

    /**
     * Draw shapes when clicked
     */
    public void doMouse(String action, double x, double y)
    {
        if (action.equals("clicked") && (shapeIndex == 0))              //if user clicks and has oval tool selected
        {
            UI.fillOval(x - (sizeX/2), y - (sizeY/2), sizeX, sizeY);    //draw oval with centre at user mouse position
        }
        
        else if (action.equals("pressed") && (shapeIndex == 1)) //obtain starting coordinates for click and hold for line tool
        {   
            startX = x;
            startY = y;
        }
        else if (action.equals("released") && (shapeIndex == 1)) { //draw line to where user releases for line tool
            UI.drawLine(startX, startY, x, y);
        }
        
        else if (action.equals("pressed") && (shapeIndex == 2)) { //obtain starting coordinates for click and hold for quadrilateral tool
            startX = x;
            startY = y;
        }
         else if (action.equals("released") && (shapeIndex == 2) && (erase == false)) { //if user releases, has quadrilateral mode selected and erase mode inactive
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
         else if (action.equals("released") && (shapeIndex == 2) && (erase == true)) {  //if user releases, has quadrilateral mode selected and erase mode active
            if(startX < x && startY < y){
                UI.eraseRect(this.startX, this.startY, (x - startX), (y - startY));  // Top left to bottom right
           
            } else if(startX > x && startY > y) {
                UI.eraseRect(x, y, (startX - x), (startY - y));                      // Bottom right to top left
               
            } else if(startX > x && startY < y) {
                UI.eraseRect(x, startY, (startX - x), (y - startY));                 // Top right to bottom left
               
            } else if(startX < x && startY > y) {
                UI.eraseRect(startX, y, (x - startX), (startY - y));                 // Bottom left to top right
               
            }
           
        }
        else if (action.equals("clicked") && (shapeIndex == 3)) {   //if user clicks and has text mode selected
            UI.drawString(text, x, y);                      //stamp text
        }
        
    }
   
    /**
     * method for changing oval colour randomly
     */
    public void selectColour()
    {
        this.currentColor = JColorChooser.showDialog(null, "Choose Color", this.currentColor);
        UI.setColor(this.currentColor);                     //change stamp colour
        UI.println("Changed colour");
    }
    
    /**
     * set user input text to variable
     */
    public void setText(String input)
    {
        text = input;                                       //set user text input to variable
    }
    
    /**
     * set user slider input for font size
     */
    public void setFontSize(double size)
    {
        UI.setFontSize(size);                               //change font size
        UI.println("Set font size to " + size);
    }
    
    /**
     * erase entire graphics pane
     */
    public void erase()
    {
        UI.clearGraphics();                                 //erase entire graphics pane
        UI.println("*Erased Graphics Pane*");
    }
    
    /**
     * toggle erase mode on quadrilateral tool
     */
    public void eraseToggle()
    {   
        if (erase){                                         //if erase mode is on, toggle off
            erase = false;
            UI.println("Set quadrilateral tool to draw");
        }
        
        else if (!(erase)){                                 // if erase mode is off, toggle on
            erase = true;
            UI.println("Set quadrilateral tool to erase");
        }
    }
    
    /**
     * cycle between stamp shapes
     */
    public double changeShape()
    {
        shapeIndex ++;                                      //cycle through stamp modes
        if (shapeIndex > 3){                                //loop through the 4 stamp modes
            shapeIndex = 0;
        }
        if (shapeIndex == 0){                               //oval tool
            UI.println("Set draw tool to oval.");
        }
        if (shapeIndex == 1){                               //line tool
            UI.println("Set draw tool to line.");
        }
        if (shapeIndex == 2){                               //quadrilateral tool
            UI.println("Set draw tool to quadrilateral.");
        }
        if (shapeIndex == 3){                               //text tool
            UI.println("Set draw tool to text.");
        }
        return shapeIndex;                                  //return current mode
    }
    
    /**
     * set user slider input for x size
     */
    public void setX(double size)
    {
        sizeX = size;
        UI.println("Set size X to: " + size);
    }
    
    /**
     * set user slider input for y size
     */
    public void setY(double size)
    {
        sizeY = size;
        UI.println("Set size Y to: " + size);
    }
    
    /**
     * set user slider input for line width
     */
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