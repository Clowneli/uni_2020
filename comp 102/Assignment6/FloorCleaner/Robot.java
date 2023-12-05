// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2021T1, Assignment 6
 * Name: Elisha Jones
 * Username: Joneselis1
 * ID: 300573902
 */

import ecs100.*;
import java.awt.Color;

/** The robot is a circular vacuum cleaner than runs around
 * a floor, erasing any "dirt".
 * The parameters of the constructor should include the initial position,
 * and possibly its size and color.
 * It has methods to make it step and change direction:
 *  step() makes it go forward one step in its current direction.
 *  changeDirection() makes it go backward one step, and then turn to a new
 *     (random) direction.
 * It has methods to report its current position (x and y) with the
 *     getX() and getY() methods.
 * It has methods to erase and draw itself
 *  draw() will make it draw itself,
 *  erase() will make it erase itself (cleaning the floor under it also!)
 *
 * Hint: if the the current direction of the robot is d (expressed in
 *  degrees clockwise from East), then it should step
 *     cos(d * pi/180) in the horizontal direction, and
 *     sin(d * pi/180) in the vertical direction.
 * Hint: see the Math class documentation!
 */

public class Robot{

    // Fields to store the state of the robot.
    /*# YOUR CODE HERE */
    private double botX;
    private double botY;
    private double d;
    private Color botCol;
    
    // bot dimensions
    private double DIAM;
    
    
    // vars for calculating direction and step
    private double stepX;
    private double stepY;
    private double LeftWall=FloorCleaner.LEFT;
    private double TopWall=FloorCleaner.TOP;
    private double BottomWall=FloorCleaner.BOT;
    private double RightWall=FloorCleaner.RIGHT;
    
    /** Construct a new Robot object.
     *  set its direction to a random direction, 0..360
     */
    public Robot(double diam, double xpos, double ypos, Color color){
        /*# YOUR CODE HERE */
        this.DIAM = diam;
        this.botX = xpos;
        this.botY = ypos;
        this.botCol = color;
        this.d = Math.random() * 360;
        this.draw();
    }
    /** colision tester */
    public boolean checkColFor2(Robot sweep2){
        if (((sweep2.botX >= botX)&&(sweep2.botX <= botX+(DIAM)))&&((sweep2.botY >= botY)&&(sweep2.botY <= botX+(DIAM)))){;
            return true;
        }
        else{return false;}
    }
    public boolean checkColFor1(Robot sweep1){
        if (((sweep1.botX >= botX)&&(sweep1.botX <= botX+(DIAM)))&&((sweep1.botY <= botY+(DIAM)&&(sweep1.botY >= botX)))){;
            return true;
        }
        else{return false;}
    }
    /** Step one unit in the current direction (but don't redraw) */
    public void step(double xpos, double ypos, double direc){
        /*# YOUR CODE HERE */
        /*Hint: if the the current direction of the robot is d (expressed in
        * degrees clockwise from East), then it should step
        * cos(d * pi/180) in the horizontal direction, and
        * sin(d * pi/180) in the vertical direction.
        * Hint: see the Math class documentation!*/
        if(d > 0 && d <= 90){
            stepX = Math.cos(d * Math.PI / 180);
            stepY = Math.sin(d * Math.PI / 180);
        }
        else if(d > 90 && d <= 180){
            stepX = -1*Math.sin(d * Math.PI / 180);
            stepY = Math.cos(d * Math.PI / 180);
        }
        else if(d > 180 && d <= 270){
            stepX = -1*Math.sin(d * Math.PI / 180);
            stepY = -1*Math.cos(d * Math.PI / 180);
        }
        else if(d > 270 && d <= 360){
            stepX = Math.sin(d * Math.PI / 180);
            stepY = -1*Math.cos(d * Math.PI / 180);
        }
        else{UI.println("ERROR!");}
        this.botX += stepX;
        this.botY += stepY;
        if(this.botX < LeftWall || this.botX > RightWall || botY < TopWall || botY > BottomWall){
                changeDirection();
        }
        //UI.println(d);
        //UI.println(botY);
        //UI.println(botX);
    }

    /** changeDirection: move backwards one unit and change direction randomly */
    public void changeDirection(){
        /*# YOUR CODE HERE */
        this.erase();
        if(d > 0 && d <= 90){
            stepX = Math.cos(d * Math.PI / 180);
            stepY = Math.sin(d * Math.PI / 180);
        }
        else if(d > 90 && d <= 180){
            stepX = -1*Math.sin(d * Math.PI / 180);
            stepY = Math.cos(d * Math.PI / 180);
        }
        else if(d > 180 && d <= 270){
            stepX = -1*Math.sin(d * Math.PI / 180);
            stepY = -1*Math.cos(d * Math.PI / 180);
        }
        else if(d > 270 && d <= 360){
            stepX = Math.sin(d * Math.PI / 180);
            stepY = -1*Math.cos(d * Math.PI / 180);
        }
        else{UI.println("ERROR!");}
        this.botX -= 2*stepX;
        this.botY -= 2*stepY;
        this.draw();
        this.d = Math.random() * 360;
    }

    /** Erase the robot */
    public void erase(){
        /*# YOUR CODE HERE */
        UI.eraseOval(this.botX, this.botY, this.DIAM, this.DIAM);

    }

    /** Draw the robot */
    public void draw(){
        /*# YOUR CODE HERE */
        double dotX = 0;
        double dotY = 0;
        UI.setColor(botCol);
        UI.fillOval(this.botX, this.botY, this.DIAM, this.DIAM);
        UI.setColor(Color.black);
        UI.drawOval(this.botX, this.botY, this.DIAM, this.DIAM);
        if(d > 0 && d <= 90){
            dotX = Math.cos(d * Math.PI / 180);
            dotY = Math.sin(d * Math.PI / 180);
        }
        else if(d > 90 && d <= 180){
            dotX = -1*(Math.sin(d * Math.PI / 180));
            dotY = Math.cos(d * Math.PI / 180);
        }
        else if(d > 180 && d <= 270){
            dotX = -1*(Math.sin(d * Math.PI / 180));
            dotY = -1*(Math.cos(d * Math.PI / 180));
        }
        else if(d > 270 && d <= 360){
            dotX = Math.sin(d * Math.PI / 180) ;
            dotY = -1*(Math.cos(d * Math.PI / 180));
        }
        dotX *= 20;
        dotY *= 20;
        UI.fillOval(this.botX+dotX+DIAM/2, this.botY+dotY+DIAM/2, this.DIAM/10, this.DIAM/10);
    }
}
