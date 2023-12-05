// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2021T1, Assignment 5
 * Name: Elisha Jones
 * Username: Joneselis1
 * ID: 300573902
 */

import ecs100.*;
import java.util.*;
import java.nio.file.*;
import java.io.*;
import java.awt.Color;
import java.util.concurrent.TimeUnit;

/**
 * WeatherReporter
 * Analyses weather data from files of weather-station measurements.
 *
 * The weather data files consist of a set of measurements from weather stations around
 * New Zealand at a series of date/time stamps.
 * For each date/time, the file has:
 *  A line with the date and time (four integers for day, month, year, and time)
 *   eg "24 01 2021 1900"  for 24 Jan 2021 at 19:00 
 *  A line with the number of weather-stations for that date/time 
 *  Followed by a line of data for each weather station:
 *   - name: one token, eg "Cape-Reinga"
 *   - (x, y) coordinates on the map: two numbers, eg   186 38
 *   - four numbers for temperature, dew-point, suface-pressure, and sea-level-pressure
 * Some of the data files (eg hot-weather.txt, and cold-weather.txt) have data for just one date/time.
 * The weather-all.txt has data for lots of times. The date/times are all in order.
 * You should look at the files before trying to complete the methods below.
 *
 * Note, the data files were extracted from MetOffice weather data from 24-26 January 2021
 */

public class WeatherReporter{

    public static final double DIAM = 10;       // The diameter of the temperature circles.    
    public static final double LEFT_TEXT = 10;  // The left of the date text
    public static final double TOP_TEXT = 50;   // The top of the date text

    /**   CORE
     * Plots the temperatures for one date/time from a file on a map of NZ
     * Asks for the name of the file and opens a Scanner
     * It is good design to call plotSnapshot, passing the Scanner as an argument.
     */
    public void plotTemperatures(){
        /* asks user for file */
        String FilePoint = UIFileChooser.open("What file? Please not it will not correctly process weather-all.txt");
        try{
            /* gets file */
            List<String> len = Files.readAllLines(Path.of(FilePoint));
            Scanner scan = new Scanner(Path.of(FilePoint));
            int pause = 0;
            // calls method
            plotSnapshot(scan,len,pause);
        } catch (IOException e) { UI.println("File failure: " + e); }  
    }


    /**
     * CORE:
     *  Plot the temperatures for the next snapshot in the file by drawing
     *   a filled coloured circle (size DIAM) at each weather-station location.
     *  The colour of the circle should indicate the temperature.
     *
     *  The method should
     *   - read the date/time and draw the date/time at the top-left of the map.
     *   - read the number of stations, then
     *   - for each station,
     *     - read the name, coordinates, and data, and
     *     - plot the temperature for that station. 
     *   (Hint: You will find the getTemperatureColor(...) method useful.)
     *
     *  COMPLETION:
     *  Also finds the highest and lowest temperatures at that time, and
     *  plots them with a larger circle.
     *  (Hint: If more than one station has the highest (or coolest) temperature,
     *         you only need to draw a larger circle for one of them.
     */     
    public void plotSnapshot(Scanner scan, List len, int pause){
        UI.drawImage("map-new-zealand.gif", 0, 0);
        // Core method
        if (pause == 0){
            String date = scan.nextLine();
            // states date and time and other variables
            UI.drawString(date, LEFT_TEXT, TOP_TEXT);
            scan.nextLine();
            double max = 0;
            double min = 1000;
            double maxX = 0;
            double maxY = 0;
            double minX = 0;
            double minY = 0;
            /* Draws graph and collects city info */
            for (int i = 0; i < len.size()-2; i++){
                String city = scan.next();
                double x = scan.nextDouble();
                double y = scan.nextDouble(); 
                double temp = scan.nextDouble();
                UI.setColor(getTemperatureColor(temp));
                UI.fillOval(x,y,DIAM,DIAM);
                scan.nextLine();
                try
                {
                    Thread.sleep(pause);
                }
                    catch(InterruptedException ex){Thread.currentThread().interrupt();
                }
                if (temp > max){
                    max = temp;
                    maxX = x;
                    maxY = y;
                }
                if (temp < min){
                    min = temp;
                    minX = x;
                    minY = y;
                }
                if(scan.hasNextDouble() || !(scan.hasNextLine())){
                    UI.setColor(getTemperatureColor(max));
                    UI.fillOval(maxX,maxY,2*DIAM,2*DIAM);
                    UI.setColor(getTemperatureColor(min));
                    UI.fillOval(minX,minY,2*DIAM,2*DIAM);
                }
            }
        }
        // completion method
        if (pause == 500){
            //iniates variables
            int header = 0;
            boolean notFirstRun = false;
            double max = 0;
            double min = 1000;
            double maxX = 0;
            double maxY = 0;
            double minX = 0;
            double minY = 0;
            /* Draws graph */
            for (int i = 0; i < len.size()-header; i++){
                // detects if the next line is the start of the snapshot
                if(scan.hasNextDouble()){
                    // draws the max and min bubbles
                    if(notFirstRun){
                        UI.setColor(getTemperatureColor(max));
                        UI.fillOval(maxX,maxY,2*DIAM,2*DIAM);
                        UI.setColor(getTemperatureColor(min));
                        UI.fillOval(minX,minY,2*DIAM,2*DIAM);
                        maxX = 0;
                        maxY = 0;
                        minX = 0;
                        minY = 0;
                        max = 0;
                        min = 1000;
                    }
                    // sets the pause for each snapshot
                    try{
                        Thread.sleep(pause);
                    }
                        catch(InterruptedException ex){Thread.currentThread().interrupt();
                    }
                    //draws each cities bubble
                    UI.drawImage("map-new-zealand.gif", 0, 0);
                    UI.setColor(Color.white);
                    UI.fillRect(LEFT_TEXT, TOP_TEXT-20,150,20);
                    String date = scan.nextLine();
                    UI.setColor(Color.black);
                    UI.drawString(date, LEFT_TEXT, TOP_TEXT);
                    scan.nextLine();
                    header += 2;
                }
                //calculates the max and min temprature values
                String city = scan.next();
                double x = scan.nextDouble();
                double y = scan.nextDouble(); 
                double temp = scan.nextDouble();
                if (temp > max){
                    max = temp;
                    maxX = x;
                    maxY = y;
                }
                if (temp < min){
                    min = temp;
                    minX = x;
                    minY = y;
                }
                //draws the cities and determines colour of bubble
                UI.setColor(getTemperatureColor(temp));
                UI.fillOval(x,y,DIAM,DIAM);
                scan.nextLine();
                notFirstRun = true;
                //draws min/max bubbles
                if(scan.hasNextDouble() || !(scan.hasNextLine())){
                    UI.setColor(getTemperatureColor(max));
                    UI.fillOval(maxX,maxY,2*DIAM,2*DIAM);
                    UI.setColor(getTemperatureColor(min));
                    UI.fillOval(minX,minY,2*DIAM,2*DIAM);
                }
            }
            UI.println("Animation completed");
        }
    }

    /**   COMPLETION
     * Displays an animated view of the temperatures over all
     * the times in a weather data files, plotting the temperatures
     * for the first date/time, as in the core, pausing for half a second,
     * then plotting the temperatures for the second date/time, and
     * repeating until all the data in the file has been shown.
     * 
     * (Hint, use the plotSnapshot(...) method that you used in the core)
     */
    public void animateTemperatures(){
    /* asks user for file */
        String FilePoint = UIFileChooser.open("What file?");
        try{
            /* gets file */
            List<String> len = Files.readAllLines(Path.of(FilePoint));
            Scanner scan = new Scanner(Path.of(FilePoint));
            int pause = 500;
            plotSnapshot(scan,len,pause);
        } catch (IOException e) { UI.println("File failure: " + e); } 
    }
    

    /**   COMPLETION
     * Prints a table of all the weather data from a single station, one line for each day/time.
     * Asks for the name of the station.
     * Prints a header line
     * Then for each line of data for that station in the weather-all.txt file, it prints 
     * a line with the date/time, temperature, dew-point, surface-pressure, and  sealevel-pressure
     * If there are no entries for that station, it will print a message saying "Station not found".
     * Hint, the \t in a String is the tab character, which helps to make the table line up.
     */
    public void reportStation(){
        //gets station shapshot file
        String stationName = UI.askString("Name of a station: ").strip();
        String FilePoint = UIFileChooser.open("What file?");
        //outputs table start
        UI.printf("Report for %s: \n", stationName);
        UI.println("Date   \t   Time\ttemp \tdew \tkPa \tsea kPa"); 
        //initialise variable
        int header = 0;
        boolean notFirstRun = false;
        List<String> len = Collections.<String>emptyList();
        Scanner scan = new Scanner("test");
        //creates the scanner
        try{
            /* gets file */
            len = Files.readAllLines(Path.of(FilePoint));
            scan = new Scanner(Path.of(FilePoint));
        } catch (IOException e) { UI.println("File failure: " + e); } 
        /* Sets more variables */
        String date = "";
        String time = "";
        boolean isStation = false;
        //detection program for each city
        for (int i = 0; i < len.size()-header; i++){
            if(scan.hasNextDouble()){
                date = scan.nextLine();
                scan.nextLine();
                header += 2;
            }
            //sets each variable for graph table
            String city = scan.next().strip();
            scan.nextDouble();
            scan.nextDouble(); 
            double temp = scan.nextDouble();
            double dew = scan.nextDouble();
            double kPa = scan.nextDouble();
            double CkPa = scan.nextDouble();
            scan.nextLine();
            notFirstRun = true;
            //draws table output if it the correct city
            if (city.toLowerCase().contains(stationName.toLowerCase())){
                UI.println(date+"\t"+temp+" \t"+dew+" \t"+kPa+" \t"+CkPa);
                isStation = true; //tells if station exists or not
            }
        }
        //prints statement if not station is found based on users input
        if (!(isStation)){
            UI.println("Station not found.");
        }
    }

    /** Returns a color representing that temperature
     *  The colors are increasingly blue below 15 degrees, and
     *  increasingly red above 15 degrees.
     */
    public Color getTemperatureColor(double temp){
        double max = 37, min = -5, mid = (max+min)/2;
        if (temp < min || temp > max){
            return Color.white;
        }
        else if (temp <= mid){ //blue range: hues from .7 to .5
            double tempFracOfRange = (temp-min)/(mid-min);
            double hue = 0.7 -  tempFracOfRange*(0.7-0.5); 
            return Color.getHSBColor((float)hue, 1.0F, 1.0F);
        }
        else { //red range: .15 to 0.0
            double tempFracOfRange = (temp-mid)/(max-mid);
            double hue = 0.15 -  tempFracOfRange*(0.15-0.0); 
            return Color.getHSBColor((float)hue, 1.0F, 1.0F);
        }
    }

    public void setupGUI(){
        UI.initialise();
        UI.addButton("Plot temperature", this::plotTemperatures);
        UI.addButton("Animate temperature", this::animateTemperatures);
        UI.addButton("Report",  this::reportStation);
        UI.addButton("Quit", UI::quit);
        UI.setWindowSize(800,750);
        UI.setFontSize(18);
    }

    public static void main(String[] arguments){
        WeatherReporter obj = new WeatherReporter();
        obj.setupGUI();
    }    

}
