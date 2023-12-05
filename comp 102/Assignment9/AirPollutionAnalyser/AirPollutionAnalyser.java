// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2021T1, Assignment 9
 * Name: Elisha Jones
 * Username: joneselis1
 * ID: 300573902
 */

import ecs100.*;
import java.util.*;
import java.nio.file.*;
import java.io.*;
import java.awt.Color;

/**
 * AirPollutionAnalyser analyses hourly data about PM2.5 concentration of five
 * cities in China in October, November and December in 2015.
 * Each line of the file "pollution.txt" has information about the pollution
 * level and weather in a city at a particular hour and date.
 * Data is from https://archive.ics.uci.edu/ml/datasets.php.
 *
 * Core: two methods:
 *   loadData
 *      Loads the data from a file into a field containing an ArrayList of
 *      AirPollution objects.
 *      Hint: read all the lines from the file, and then take each line
 *            apart into the values to pass to the AirPollution constructor.
 *   findHazardousLevels
 *      Prints out all the records in the ArrayList that have a
 *      PM2.5 concentration 300 and over.
 *      Hint: see the methods in the AirPollution class, especially getPM and toString
 *
 * Completion: one method:
 *   findContrastingCities
 *      Compares each record in the list with every other record and finds
 *      every pair of cities that having a difference of PM2.5 concentration
 *      larger than 400, at the same hour on the same day.
 *      For each pair, it should print cityA, cityB, hour, date, difference
 *
 * Challenge: two methods
 *   findDailyAverage(String city)
 *      -Prints the average daily PM2.5 value for each day for the given city.
 *      -Finds the longest sequence of days there the average PM2.5 is always
 *       above 200 ("Very unhealthy").
 *      Hint: Use an array where the index corresponds to the day of the year.
 *   plotPollutionLevels
 *      Makes a line plot for the daily average PM2.5 concentration data of
 *      the five cities in the same figure. You may choose different colours
 *      to represent different cities.
 *      Hint: Make the findDailyAverage(String city) method return the array
 *      for a given city.
 */

public class AirPollutionAnalyser {

    private ArrayList<AirPollution> pollutions = new ArrayList<AirPollution>();
    
    /**
     * CORE
     *
     * Load data from the data file into the pollutions field:
     * clear the pollutions field.
     * Read lines from file
     * For each line, use Scanner to break up each line and make an AirPollution
     *  adding it to the pollutions field.
     */
    public void loadData() {String fname = UIFileChooser.open("what file?");
        try{
            fname = "pollution.txt";
            Scanner sc = new Scanner(Path.of(fname));
            List<String> len = Files.readAllLines(Path.of(fname));
            /*# YOUR CODE HERE */
            pollutions = new ArrayList<AirPollution>();
            for(int i = 0 ; i < len.size(); i++){
                String city = sc.next();
                String date = sc.next();
                int hour = sc.nextInt();
                double PM2 = sc.nextDouble();
                double humid = sc.nextDouble();
                double temp = sc.nextDouble();
                sc.nextLine();
                pollutions.add(new AirPollution(city,date,hour,PM2,humid,temp));
                //UI.println(pollutions.get(i).toString());
            } 
            UI.printf("Loaded %d pollution information into list\n", this.pollutions.size());
        } catch(IOException e){UI.println("File reading failed");}  
        UI.println("----------------------------");
    }

    /**
     * CORE
     *
     * Prints out all the records in the ArrayList that have a PM2.5 concentration
     * 300 and over
     */
    public void findHazardousLevels() {
        UI.clearText();
        UI.println("PM2.5 Concentration 300 and above:");
        /*# YOUR CODE HERE */
        
        for(AirPollution p1 : pollutions){
            if (p1.getPM() >= 300.00){
                    UI.println(p1.toString());
            }
        }
        UI.println("----------------------------");
    }

    /**
     * COMPLETION
     * 
     * Finds every pair of cities that have at the same hour on the same day 
     * a difference of PM2.5 concentration larger than 400.
     * You need to compare each record in the list with every other record
     * For each pair, it should print
     * cityA, cityB, hour, date, difference
     */
    public void findContrastingCities() {
        UI.clearText();
        UI.println("Pairs of cities whose PM2.5 differs by more than 400 at the same time");
        
            /*# YOUR CODE HERE */
            for(AirPollution p1 : pollutions){
                for(AirPollution p2 : pollutions){
                    if (((p1.diffPM(p2) > 400.00)) 
                    && (p1.getDate().equals(p2.getDate())) 
                    && (p1.getHour()==p2.getHour())
                    && (p1.getCity() != p2.getCity()))
                    {
                        UI.println(p1.getCity() + " " + p2.getCity() + " " + p1.getHour() + " " + p1.getDate() + " " + p1.diffPM(p2));
                    }
                }
            }
        UI.println("----------------------------");
    }
    
    /*public void findDailyAverage(String city){
        UI.clearText();
        ArrayList<AirPollution> pollutions2 = pollutions;
        double dailyAverage;
        UI.println("Daily averages for "+city);
        for(AirPollution p1 : pollutions){
            dailyAverage = 0;
            int count = 0;
            for(int i=0; i > pollutions2.size() ;i++){
                if (p1.getDate().equals(pollutions2.get(i).getDate())
                && (city.equals(pollutions2.get(i).getCity())))
                {
                    dailyAverage += pollutions2.get(i).getPM();
                    pollutions2.remove(pollutions2.get(i));
                    count++;
                }
            }
            dailyAverage = dailyAverage/count;
            UI.println("Daily PM2.5 average for "+city+" on "+p1.getDate()+" is: "+dailyAverage); 
        }
    }

    // ------------------ Set up the GUI (buttons) ------------------------
    /** Make buttons to let the user run the methods */
    public void setupGUI() {
        UI.initialise();
        UI.addButton("Load", this::loadData);
        UI.addButton("Hazardous Levels", this::findHazardousLevels);
        UI.addButton("Contrasting Cities", this::findContrastingCities);
        //UI.addTextField("Daily average for city:", this::findDailyAverage);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0); // text pane only
    }

    public static void main(String[] arguments) {
        AirPollutionAnalyser obj = new AirPollutionAnalyser();
        obj.setupGUI();
    }

}
