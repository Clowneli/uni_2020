// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2021T1, Assignment 4
 * Name: Elisha Jones
 * Username: Joneselis1
 * ID: 300573902
 */

import ecs100.*;

/** PatternsDrawer
Draws six different repetitive patterns. */

public class PatternsDrawer{

    public static final double PATTERN_LEFT = 50.0;   // Left side of the pattern
    public static final double PATTERN_TOP = 50.0;    // Top of the pattern
    public static final double PATTERN_SIZE = 300.0;  // The size of the pattern on the window

    /** 
     * Draws a star pattern consisting of a circle containing black rays (separated by white regions)
     * Asks the user for the number of rays.
     */
    public void drawStar(){
        UI.clearGraphics();
        /* Sets variables */
        double num = UI.askInt("How many rays:");
        double dubNum = 2*num;
        double cirRatio = PATTERN_SIZE/360;
        double Angle = 360 / dubNum;
        //Runs the loop to draw all the rays and the circle
        UI.drawOval(PATTERN_LEFT, PATTERN_TOP, PATTERN_SIZE, PATTERN_SIZE);
        for (int i = 0; dubNum > i; i+=Math.PI*cirRatio){
            double Angle2 = Angle*i;
            double Left=PATTERN_LEFT;
            double Top=PATTERN_TOP;
            UI.fillArc(Left, Top, PATTERN_SIZE, PATTERN_SIZE, Angle2, Angle);
        }
    }

    /** Draw a checkered board with alternating black and white squares
     *    Asks the user for the number of squares on each side
     *
     * CORE
     */
    public void drawDraughtsBoard(){
        UI.clearGraphics();
        /* sets the variables */
        int num = UI.askInt("How many rows:");
        double size = PATTERN_SIZE / num;
        UI.drawRect(PATTERN_LEFT,PATTERN_TOP,PATTERN_SIZE,PATTERN_SIZE);
        /* Runs loop for the odd rows */
        for (int n = 0; n < num; n+=2){
            for (int i = 0; i < num; i+=2){
                UI.fillRect(PATTERN_LEFT + i*size,PATTERN_TOP + n*size,size,size);
            }
        }
        /* Runs loop for the even rows */
        for (int n = 1; n < num; n+=2){
            for (int i = 1; i < num; i+=2){
                UI.fillRect(PATTERN_LEFT + i*size,PATTERN_TOP + n*size,size,size);
            }
        }
    }

    /** TriGrid
     * a triangular grid of squares that makes dark circles appear 
     * in the intersections when you look at it.
     *
     * COMPLETION
     */
    public void drawTriGrid(){
        UI.clearGraphics();
        /* Sets variable */
        int num = UI.askInt("How many rows:");
        int collums = num;
        double size = PATTERN_SIZE / num;
        /* runs loop to draw pattern */
        for (int i=0; num > i; i++){
            for (int n=0; collums > n; n++){
                UI.fillRect(PATTERN_LEFT+1+n*size,PATTERN_TOP+1+i*size,size-1,size-1);
            }
            collums -= 1;
        }
    }


    public void setupGUI(){
        UI.initialise();
        UI.addButton("Clear",UI::clearPanes);
        UI.addButton("Core: star", this::drawStar);
        UI.addButton("Core: draughts", this::drawDraughtsBoard);
        UI.addButton("Completion: TriGrid", this::drawTriGrid);
        //UI.addButton("Challenge: Concentric", this::drawConcentricBoard);
        //UI.addButton("Challenge: Hexagon", this::drawHexagonalBoard);
        //UI.addButton("Challenge Spiral", this::drawSpiralBoard);
        UI.addButton("Quit",UI::quit);
    }   

    public static void main(String[] arguments){
        PatternsDrawer pd = new PatternsDrawer();
        pd.setupGUI();
    }

}

