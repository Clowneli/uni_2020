// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2021T2, Assignment 2
 * Name: Elisha Jones
 * Username: Joneselis1
 * ID: 300573902
 */

import ecs100.*;
import java.util.*;
import java.awt.*;
import java.util.List;
import javax.swing.*;

/** Pencil   */
public class Pencil{
    private double lastX,lastY;
    
    //Creates colour and width fields
    private Color strokeCol = Color.black;
    private double lineWidth = 1;

    //Creates stacks for the arrayLists of historys.
    private Stack<ArrayList<Strokes>> strokeHistory = new Stack<>();
    private Stack<ArrayList<Strokes>> redoHistory = new Stack<>();
    //ArrayList for temporarily holding each stroke
    private ArrayList<Strokes> tempArray = new ArrayList<>();

    /**
     * Setup the GUI
     */
    public void setupGUI(){
        UI.setMouseMotionListener(this::doMouse);
        UI.addButton("Quit", UI::quit);
        //Colour button
        UI.addButton("Change Colour",() ->{ strokeCol = JColorChooser.showDialog(null,"Choose colour", this.strokeCol); });
        //Stroke width slider
        UI.addSlider("Line Width",1.0,25.0,lineWidth,(w)->{ lineWidth = w; });
        UI.addButton("Undo", this::undo); //undo button
        UI.addButton("Redo", this::redo); //redo button
        UI.setLineWidth(3);
        UI.setDivider(0.0);
    }
    /**
     * undo method
     */
    public void undo(){
        //check if stroke history is not empty
        if(!(strokeHistory.isEmpty())){
            //adds lasts stroke to the redo history
            redoHistory.push(strokeHistory.peek());
            
            //pops the last stroke in history
            strokeHistory.pop();
            redraw();
        }else UI.printMessage("Nothing to Undo");
        
    }
    /**
     * redo method
     */
    public void redo(){
        //check if the histoy is not empty
        if(!redoHistory.isEmpty()) {
            //adds last stroke in redo history to stroke history
            strokeHistory.push(redoHistory.peek());
            //pops the last stroke in the redo history
            redoHistory.pop();
            redraw();
        }else UI.printMessage("Nothing to Redo");
    }
    
    /**
     * redraw method for after each undo/redo
     */
    public void redraw() {
        //Clears the graphics pane
        UI.clearGraphics();
        //loop for redrawing all the strokes
        for(ArrayList<Strokes> strokes : strokeHistory){
            for(Strokes stroke: strokes){
                //Sets the colour and the width from the values above
                stroke.draw();
                }
        }
    }
    
    /**
     * Respond to mouse events
     */
    public void doMouse(String action, double x, double y) {
        if (action.equals("pressed")){
            lastX = x;
            lastY = y;
            UI.setColor(strokeCol);
        }
        else if (action.equals("dragged")){
            UI.setLineWidth(lineWidth);
            UI.drawLine(lastX, lastY, x, y);
            
            //adds stroke to list
            tempArray.add(new Strokes(lastX,lastY,x,y,lineWidth,strokeCol));
            
            lastX = x;
            lastY = y;
            
        }
        else if (action.equals("released")){
            UI.setLineWidth(lineWidth);
            UI.drawLine(lastX, lastY, x, y);
            
            //adds stroke to list
            tempArray.add(new Strokes(lastX,lastY,x,y,lineWidth,strokeCol));
            
            strokeHistory.push(new ArrayList<>(tempArray));
            
            //clears redo and the temp storage array
            tempArray.clear();
            redoHistory.clear();
        }
    }

    public static void main(String[] arguments){
        new Pencil().setupGUI();
    }

}

/**
 * Class for each individual stroke
 */
class Strokes {
    //initialises the x and y coordinates, width and colour
    private final double x1,y1,x2,y2,width;
    private final Color color;

    public Strokes(double x1, double y1, double x2, double y2,double width, Color color){
       //Sets values
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
        this.width=width;
        this.color = color;
    }

    //Method for drawing a line (used in undo/redo).
    public void draw(){
        UI.setColor(color);
        UI.setLineWidth(width);
        UI.drawLine(x1,y1,x2,y2);
            }
}
