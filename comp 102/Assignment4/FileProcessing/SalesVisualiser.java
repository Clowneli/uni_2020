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

/**
 * SalesVisualiser
 * Reads details of sales from a file and produces a bar graph of the data
 */

public class SalesVisualiser{

    // Constants for plotting the graph
    public static final double GRAPH_LEFT = 50;
    public static final double GRAPH_RIGHT = 650;
    public static final double GRAPH_BASE = 400;
    public static final double MONTH_WIDTH = (GRAPH_RIGHT-GRAPH_LEFT)/12;  // the width for each set of three bars
    public static final double BAR_WIDTH = 12;    // the width of each bar

    /**
     * Asks the user for the name of a file containing the details of sales
     *  reported by dealers over the last three years, and then produces a
     *  bar graph of the data, showing the sales for each month, with different
     *  color bars for each year.
     * Each line of a sales data file contains
     *    a year, a month, and a series of sales from the dealers
     *    There may be a different number of sales on each line.
     * For example:
     *   2018 01 21 15 32 12 2 7
     *   2018 02 5 18 12
     *   :
     *   2019 01 5 5 4 11
     *   2019 02 41 3
     *   :
     * There is no guarantee that the lines are in order of date
     * The total sales in any month will never be over 200.
     * 
     * The method should draw a bar graph with 12 sets of bars, one set for each month
     * Each set should have
     *  a red bar for the 2018 data,
     *  a green bar for the 2019 data, and
     *  a blue bar for the 2020 data
     * The height of the bar should be the total number of sales in that month
     * Hints:
     *   Use a Scanner for each line
     *   After getting the year and month from the Scanner, you will need a loop to add
     *       up all the sales on each line.
     *   Look carefully at the example file and the example output.
     */
    public void graphSales() {
        /*# YOUR CODE HERE */
        /* file select */
        String fileName = UIFileChooser.open("Choose file to search");
        /* draws base graph */
        UI.drawRect(GRAPH_LEFT,GRAPH_LEFT,(GRAPH_RIGHT-GRAPH_LEFT),GRAPH_BASE);
        UI.setColor(Color.black);
        for (int i = 0; (i > MONTH_WIDTH) ; i ++){
            UI.drawLine(GRAPH_LEFT+(i*MONTH_WIDTH)-2,GRAPH_BASE + GRAPH_LEFT - 15,GRAPH_LEFT+(i*MONTH_WIDTH)-2,GRAPH_BASE + GRAPH_LEFT + 15);
        }
        try {
            double scanTot = 0;
            List<String> allLines = Files.readAllLines(Path.of(fileName));
            /* scans for each variable */
            for (String line : allLines){
                Scanner scan = new Scanner (line);
                int dateYear = scan.nextInt();
                int dateMonth = scan.nextInt();
                double salesTotal = 0;
                /* totals the sales for each month */
                while (scan.hasNextInt()){
                    salesTotal += scan.nextDouble();
                }
                /* draws points for each year */
                if (dateYear == 2018){
                    UI.setColor(Color.red);
                    UI.fillRect(5+(MONTH_WIDTH*dateMonth),450-salesTotal,BAR_WIDTH,salesTotal);
                }
                if (dateYear == 2019){
                    UI.setColor(Color.green);
                    UI.fillRect(5+(dateMonth*MONTH_WIDTH)+BAR_WIDTH,450-salesTotal,BAR_WIDTH,salesTotal);
                }
                if (dateYear == 2020){
                    UI.setColor(Color.blue);
                    UI.fillRect(5+(MONTH_WIDTH*dateMonth)+(BAR_WIDTH*2),450-salesTotal,BAR_WIDTH,salesTotal);
                }
            }
        }catch (IOException e) { UI.println("File failure: " + e); }

    }

    /** set up the buttons */
    public void setupGUI(){
        UI.addButton("Clear", UI::clearPanes);
        UI.addButton("Sales", this::graphSales); 
        UI.addButton("quit", UI::quit);
        UI.setDivider(0.0);
    }

    public static void main(String[] arguments){
        SalesVisualiser sv = new SalesVisualiser();
        sv.setupGUI();
    }    
}
