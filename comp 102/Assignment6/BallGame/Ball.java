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
import java.util.*;

/** Ball represents a ball that is launched by the mouse towards a direction.
 *    Each time the step() method is called, it will take one step.  
 * For the Completion part, gravity should act on the ball by reducing its vertical speed.
 */

public class Ball{

    // Constants for all balls: size, position of the ground
    public static final double DIAM = 16;  // diameter of the balls.
    public static final double GROUND = BallGame.GROUND;
    public static final double RIGHT_END = BallGame.RIGHT_END;

    // Fields to store state of the ball:
    // x position, height above ground, stepX, stepY, colour
    //   The ball should initially be not moving at all. (step should be 0)
    /*# YOUR CODE HERE */
    private double xpos;
    private double height;
    private double stepX;
    private double stepY;
    private double ypos;
    private double diam;
    private double gravity = 0;
    Color randomColour;

    // Constructor
    /** Construct a new Ball object.
     *    Parameters are the initial position (x and the height above the ground),
     *    Stores the parameters into fields 
     *    and initialises the colour to a random colour
     *  SHOULD NOT DRAW THE BALL!
     */
    public Ball(double x, double h){
        this.xpos = x;
        this.height = h;
        this.diam = DIAM;
        this.stepX = 0;
        this.stepY = 0;
        Random rand = new Random();
        this.randomColour = new Color(rand.nextInt(0xFFFFFF));
        /*int red = (int)Math.round(Math.random()*256);
        int green = (int)Math.round(Math.random()*256);
        int blue = (int)Math.round(Math.random()*256);
        
        Color randomColour = new Color(red,green,blue);*/
        
    }
            //REMEMBER: SET STATIC FINAL FOR DIAM FOR EACH BALL!!
    // Methods
    /**
     * Draw the ball on the Graphics Pane in its current position
     * (unless it is past the RIGHT_END )
     */
    public void draw(){
        /*# YOUR CODE HERE */
        UI.setColor(randomColour);
        UI.fillOval(xpos - (DIAM/2),GROUND - diam - height - gravity,diam,diam);
        UI.setColor(Color.black);
        UI.drawOval(xpos - (DIAM/2),GROUND - diam - height - gravity,diam,diam);
        
        
    }

    /**
     * Move the ball one step (DO NOT REDRAW IT)
     * Core:
     *    Change its height and x position using the vertical and horizonal steps
     * Completion:
     *    Reduce its vertical speed each step (due to gravity), 
     *    If it would go below ground, then change its y position to ground level and
     *      set the  vertical speed back to 0.
     */
    public void step(){
        /*# YOUR CODE HERE */
        this.xpos += stepX;
        if (this.height >= 0){
            this.gravity += 0.2;
        }
        else{
            this.stepY = 0;
            this.gravity = 0;
        }
        this.height += stepY - gravity;
    }

    /**
     * Set the horizontal speed of the ball: how much it moves to the right in each step.
     * (negative if ball going to the left).
     */
    public void setXSpeed(double xSpeed){
        /*# YOUR CODE HERE */
        this.stepX = xSpeed;

    }

    /**
     * Set the vertical speed of the ball: how much it moves up in each step
     * (negative if ball going down).
     */
    public void setYSpeed(double ySpeed){
        /*# YOUR CODE HERE */
        this.stepY = ySpeed;
    }

    /**
     * Return the height of the ball above the ground
     */
    public double getHeight(){
        /*# YOUR CODE HERE */
        return height;
    }

    /**
     * Return the horizontal position of the ball
     */
    public double getX(){
        /*# YOUR CODE HERE */
        return xpos;
    }


}
