// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2021T1, Assignment 1
 * Name: Elisha Jones
 * Username: Joneselis1
 * ID: 300573902
 */

import ecs100.*;
import java.awt.Color;

/**
 * Draws various flags
 *
 * You can find lots of flag details (including the correct dimensions and colours)
 * from  http://www.crwflags.com/fotw/flags/    
 */

public class FlagDrawer{

    public static final double LEFT = 100;  // the left side of the flags
    public static final double TOP = 50;    // the top of the flags

    /**   CORE
     * Draw the flag of Belgium.
     * The flag has three vertical stripes;
     * The left is black, the middle is yellow, and the right is red.
     * The flag is 13/15 as high as it is wide (ratio 13:15).
     */
    public void drawBelgiumFlag(){
        UI.clearGraphics();
        UI.println("Belgium Flag");
        /* sets variables */
        double width = UI.askDouble("How wide: ");
        double height = width / (15.0/13.0);
        width = width / 3;
        /* draws the flag */
        UI.setColor(Color.black);
        UI.fillRect(0,0,width,height);
        UI.setColor(Color.yellow);
        UI.fillRect(width,0,width,height);
        UI.setColor(Color.red);
        UI.fillRect(width + width,0,width,height);

    }

    /**   CORE
     *  The Red Cross flag consists of a white square with a red cross in the center
     *  The cross can be drawn as a horizontal rectangle and a vertical rectangle.
     */
    public void drawRedCrossFlag() {
        UI.println("Red Cross Flag: ");
        UI.clearGraphics();
        /* sets variable */
        double size = UI.askDouble("How wide: ");
        double cshort = size / 5.0; // 1 5th of the size
        double clong = cshort * 3; // 3 5ths of the size
        /* Draws the flag */
        UI.setColor(Color.black);
        UI.drawRect(0,0,size,size);
        UI.setColor(Color.red);
        UI.fillRect(cshort + cshort, cshort, cshort, clong);
        UI.fillRect(cshort, cshort + cshort, clong, cshort);

    }

    /**   COMPLETION
     *  Pacman
     *  A red pacman facing up on a black background chasing yellow, green, and blue dots.
     *  
     */
    public  void drawPacman() {
        UI.clearGraphics();        
        UI.println("Pacman Flag");
        /* sets variables */
        double width = UI.askDouble("How wide: ");
        double height = width * (18.0/13.0);
        /* draws the flag */
        UI.setColor(Color.black);
        UI.fillRect(0,0,width,height);
        UI.setColor(Color.red);
        UI.fillOval(width / 4, 8.5/15.0 * height, width / 2.0, width / 2.0);
        UI.setColor(Color.black);
        // setting paramiters for the mouth of the pacman
        double y[] = {5.0 / 10.0 * height, 5.0 / 10.0 * height, 7.5 / 10.0 * height};
        double x[] = {6.0 / 15.0 * width,width - (6.0 / 15.0 * width),width / 2.0};
        int n = 3;
        UI.fillPolygon(x,y,n);
        UI.setColor(Color.yellow);
        UI.fillOval(width * 0.4,height * (5.75/15.0),width / 5.0, width / 5.0);
        UI.setColor(Color.green);
        UI.fillOval(width * 0.4,height * (3.25/15.0),width / 5.0, width / 5.0);
        UI.setColor(Color.blue.darker());
        UI.fillOval(width * 0.4,height * (0.75/15.0),width / 5.0, width / 5.0);

    }

    /**   COMPLETION
     * Draw the flag of Greenland.
     * The top half of the flag is white, and the bottom half is red.
     * There is a circle in the middle (off-set to left)  which is
     * also half white/red but on the opposite sides.
     * The flag is 2/3 as high as it is wide (ratio 2:3).
     */
    public void drawGreenlandFlag() {
        /* arcs learned from https://way2java.com/awt-graphics/drawing-arcs/ */
        UI.clearGraphics();
        UI.println("Greenland Flag");
        /* sets Variables */
        double width = UI.askDouble("How wide: ");
        double height = width * (2.0/3.0);
        /* draws the flag */
        UI.setColor(Color.gray);
        UI.drawRect(0,0,width,height);
        UI.setColor(Color.red);
        UI.fillArc(width * 0.2,height * 0.255,width * 0.4,height * 0.5,360,180);
        UI.fillRect(0,height / 2.0,width,height / 2);
        UI.setColor(Color.white);
        UI.fillArc(width * 0.2,height * 0.255,width * 0.4,height * 0.5,180,180);

    }

    /**   CHALLENGE
     * Draw the Koru Flag.
     * It was one of the new flag designs for the 2016 referendum,
     * designed by Sven Baker from Wellington
     * The flag is 1/2 as high as it is wide (ratio 1:2).
     */
    public void drawKoruFlag() {
        UI.clearGraphics();
        UI.println("Koru Flag");
        /* sets the variables */
        double width = UI.askDouble("How wide: ");
        double height = 0.5 * width;
        /* Draws the flag */
        /* arcs learned from https://way2java.com/awt-graphics/drawing-arcs/ */
        UI.setColor(Color.red);
        UI.fillRect(0, 0, width * 0.45, height);
        UI.setColor(Color.blue.darker());
        UI.fillRect(width * 0.45, 0, width * 0.55, height);
        UI.setColor(Color.white);
        UI.fillOval(width / 6.1,0,width / 2.0,width / 2.0);
        UI.setColor(Color.blue.darker());
        UI.fillOval(7.0/30.0 * width,height / 10.0, 3.75/10.0 * width,3.75/10.0 * width);
        UI.fillArc(8.0/15.0 * width,4.0/10.0 * height,width * 0.45,height * 0.4,180,180);
        UI.setColor(Color.white);
        UI.fillOval(5.45/10.0 * width,6.5 / 15.0 * height,height / 4.0,height / 4.0);

    }

    /**   CHALLENGE
     * Draw the flag of Samoa,
     * The flag is 1/2 as high as it is wide (ratio 1:2).
     * A red field with the blue rectangle on the top left quadrant bearing the Southern Cross
     *    of four white larger five-pointed stars and the smaller star in the center.
     * A full marks solution will have a method for drawing a 5 pointed star,
     * and call that method for each of the stars
     */
    public void drawSamoaFlag() {
        UI.clearGraphics();        
        UI.println("Samoa Flag");
        UI.println("Flag not finished");
        //double width = UI.askDouble("How wide: ");
        /*# YOUR CODE HERE */

    }

    public void setupGUI(){
        UI.addButton("Clear", UI::clearPanes);
        UI.addButton("Core: Flag of Belgium", this::drawBelgiumFlag);
        UI.addButton("Core: Red Cross Flag",  this::drawRedCrossFlag);
        // COMPLETION
        UI.addButton("Completion: Pacman Flag", this::drawPacman);
        UI.addButton("Completion: Flag of Greenland", this::drawGreenlandFlag);
        // CHALLENGE
        UI.addButton("Challenge: Koru flag", this::drawKoruFlag);
        UI.addButton("Challenge: Flag of Samoa", this::drawSamoaFlag);
        UI.addButton("Quit", UI::quit);

        UI.setDivider(0.3);
    }

    public static void main(String[] arguments){
        FlagDrawer fd = new FlagDrawer();
        fd.setupGUI();
    }

}
