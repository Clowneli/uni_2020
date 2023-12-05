// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2021T1, Assignment 4
 * Name: Elisha Jones
 * Username: Joneselis1
 * ID: 300573902
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class GraphPlotter {

    // Constants for plotting the graph
    public static final double GRAPH_LEFT = 50;
    public static final double GRAPH_RIGHT = 550;
    public static final double GRAPH_BASE = 400;

    /**
     * Plot a graph of a sequence of numbers read from a file using +'s for each point.
     * The origin of the graph should be at (GRAPH_LEFT, GRAPH_BASE)
     * The method should ask the user for the name of a file that contains only numbers
     * It should then plot the numbers:
     *  - Draw two axes
     *  - Plot each number as a small +      eg, to plot at (x,y),
     *       draw a line from (x-2,y) to (x+2,y) and a line from (x,y-2) to (x,y+2)
     *  - The x value of the first point should be at GRAPH_LEFT, and
     *    the last point should be at GRAPH_RIGHT.
     *  - (ie, the points should be separated by (GRAPH_RIGHT - GRAPH_LEFT)/(number of points - 1)
     * Hints:
     *   look at the model answers for the Temperature Analyser problem from assignment 3.
     */
    public void plotGraph() {
        /* asks user for file */
        String FilePoint = UIFileChooser.open("What file?");
        try{
            /* gets file */
            List<String> len = Files.readAllLines(Path.of(FilePoint));
            /* Draws graph */
            double X = (GRAPH_RIGHT - GRAPH_LEFT)/(len.size() - 1);
            for (String line : Files.readAllLines(Path.of(FilePoint))){
                Double Y = Double.parseDouble(line);
                UI.drawLine(GRAPH_LEFT + X-2, GRAPH_BASE - Y,GRAPH_LEFT + X +2, GRAPH_BASE - Y+2);
                UI.drawLine(GRAPH_LEFT + X, GRAPH_BASE - Y-2,GRAPH_LEFT + X, GRAPH_BASE - Y+2);
                X += (GRAPH_RIGHT - GRAPH_LEFT)/(len.size() - 1);
            }
            UI.drawRect(GRAPH_LEFT, GRAPH_LEFT, X, GRAPH_BASE);
        } catch (IOException e) { UI.println("File failure: " + e); }
}
    /** set up the buttons */
    public void setupGUI(){
        UI.addButton("Clear", UI::clearPanes);
        UI.addButton("Plot", this::plotGraph);
        UI.addButton("quit", UI::quit);
        UI.setDivider(0.0);
    }

    public static void main(String[] args){
        GraphPlotter gp = new GraphPlotter();
        gp.setupGUI();
    }
}

