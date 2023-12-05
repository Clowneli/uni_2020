// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2021T2, Assignment 2
 * Name: Elisha Jones
 * Username: joneselis1
 * ID: 300573902
 */

import ecs100.*;
import java.util.*;
import java.util.Map.Entry;
import java.io.*;
import java.nio.file.*;

/**
 * WellingtonTrains
 * A program to answer queries about Wellington train lines and timetables for
 *  the train services on those train lines.
 *
 * See the assignment page for a description of the program and what you have to do.
 */

public class WellingtonTrains{
    //Fields to store the collections of Stations and Lines
    /*# YOUR CODE HERE */
    private Map<Station,TrainLine> stations = new TreeMap<Station,TrainLine>();
    private List<Station> stationsList = new ArrayList<Station>();
    private Collection<TrainLine> linesList = new ArrayList<TrainLine>();
    
    // Fields for the suggested GUI.
    private String stationName;        // station to get info about, or to start journey from
    private String lineName;           // train line to get info about.
    private String destinationName;
    private int startTime = 0;         // time for enquiring about

    /**
     * main method:  load the data and set up the user interface
     */
    public static void main(String[] args){
        WellingtonTrains wel = new WellingtonTrains();
        wel.loadData();   // load all the data
        wel.setupGUI();   // set up the interface
    }

    /**
     * Load data files
     */
    public void loadData(){
        loadStationData();
        UI.println("Loaded Stations");
        loadTrainLineData();
        UI.println("Loaded Train Lines");
        // The following is only needed for the Completion and Challenge
        //loadTrainServicesData();
        //UI.println("Loaded Train Services");
    }

    /**
     * User interface has buttons for the queries and text fields to enter stations and train line
     * You will need to implement the methods here.
     */
    public void setupGUI(){
        UI.addButton("All Stations",        this::listAllStations);
        UI.addButton("Stations by name",    this::listStationsByName);
        UI.addButton("All Lines",           this::listAllTrainLines);
        UI.addTextField("Station",          (String name) -> {this.stationName=name;});
        UI.addTextField("Train Line",       (String name) -> {this.lineName=name;});
        UI.addTextField("Destination",      (String name) -> {this.destinationName=name;});
        //UI.addTextField("Time (24hr)",      (String time) ->
        //    {try{this.startTime=Integer.parseInt(time);}catch(Exception e){UI.println("Enter four digits");}});
        UI.addButton("Lines of Station",    () -> {listLinesOfStation(this.stationName);});
        UI.addButton("Stations on Line",    () -> {listStationsOnLine(this.lineName);});
        UI.addButton("Stations connected?", () -> {checkConnected(this.stationName, this.destinationName);});
        //UI.addButton("Next Services",       () -> {findNextServices(this.stationName, this.startTime);});
        //UI.addButton("Find Trip",           () -> {findTrip(this.stationName, this.destinationName, this.startTime);});
        
        UI.addButton("Quit", UI::quit);
        UI.setMouseListener(this::doMouse);

        UI.setWindowSize(900, 400);
        UI.setDivider(0.2);
        // this is just to remind you to start the program using main!
        if (stationsList.isEmpty()){
            UI.setFontSize(36);
            UI.drawString("Start the program from main", 2, 36);
            UI.drawString("in order to load the data", 2, 80);
            UI.sleep(2000);
            UI.quit();
        }
        else {
            UI.drawImage("data/geographic-map.png", 0, 0);
            UI.drawString("Click to list closest stations", 2, 12);
        }
    }

    public void doMouse(String action, double x, double y){
        if (action.equals("released")){
            /*# YOUR CODE HERE */
            UI.clearText();
            UI.println("10 closest stations:");
            for(Station station:stationsList){
                station.setVector(x,y,station.getXCoord(),station.getYCoord());
            }
            //sets the comparator for the vectors and sorts
            Comparator<Station> compareVectors = (Station s1,Station s2) -> 
                {if (s1.getVector() < s2.getVector()){return -1;}
                else if (s1.getVector() > s2.getVector()){return 1;}
                else {return 0;}};
            Collections.sort(stationsList,compareVectors);
            
            //prints closest 10 to click
            for(int i=0;i<10;i++){
                UI.println(stationsList.get(i).toString());
            }
        }
    }

    // Methods for loading data and answering queries
    
    /*# YOUR CODE HERE */
    
    /**
     * loads the stations
     */
    public void loadStationData(){
        //initilases the scanner
        try{
            Scanner sc = new Scanner(Path.of("data/stations.data"));
            List<String> len = Files.readAllLines(Path.of("data/stations.data"));
            //for loop to read stations.data file and create each station.
            for (int i=0; i < len.size(); i++){
                String name = sc.next();
                int zone = sc.nextInt();
                double x = sc.nextDouble();
                double y = sc.nextDouble();
                sc.nextLine();
                stationsList.add(new Station(name,zone,x,y));
            }
        }catch(IOException e){UI.println("File reading faled");}
    }
    /**
     * loads the train line data
     * allocates the trains to a line
     */
    public void loadTrainLineData(){
        //initilases the scanner
        try{
            Scanner sc = new Scanner(Path.of("data/train-lines.data"));
            List<String> len = Files.readAllLines(Path.of("data/train-lines.data"));
            //for loop to read train-lines.data file and create each line.
            for (int i=0; i < len.size(); i++){
                String name = sc.next();
                sc.nextLine();
                linesList.add(new TrainLine(name));
            }
            //loop for adding each station to each line
            for(TrainLine line: linesList){
                try{
                    sc = new Scanner(Path.of("data/"+line.getName()+"-stations.data"));
                    len = Files.readAllLines(Path.of("data/"+line.getName()+"-stations.data"));
                    //Runs list of staions for scanner
                    for (int i=0; i < len.size(); i++){
                        String name = sc.next();
                        sc.nextLine();
                        //loop for adding the station for the line and vise versa if the staion is pressent in the ""-staions.data file
                        for(Station station: stationsList){
                            if(station.getName().contains(name)){
                                line.addStation(station);
                                station.addTrainLine(line);
                            }
                        }
                    }
                }catch(IOException e){UI.println("File reading failed");}  
            }
        } catch(IOException e){UI.println("File reading failed");}  
    }
    /**
     * loads the service data.
     *
    void loadTrainServicesData(){
        //loop for adding each station to each line
            for(TrainLine line: linesList){
                try{
                    Scanner sc = new Scanner(Path.of("data/"+line.getName()+"-stations.data"));
                    List<String> len = Files.readAllLines(Path.of("data/"+line.getName()+"-service.data"));
                    for(int i=0;i<len.size();i++){
                        
                        while(sc.hasNextInt()){
                            
                        }
                    }
            } catch(IOException e){UI.println("File reading failed");}   
        }
    }
    
    /**
     * list all staions method
     */
    void listAllStations(){
        UI.clearText();
        UI.println("All stations:");
        for(Station station:stationsList){
            UI.println(station.toString());
        }
    }
    /**
     * list all staions alphabetically method
     */
    void listStationsByName(){
        UI.clearText();
        UI.println("All stations ordered alphabetically:");
        Collections.sort(stationsList);
        for(Station station:stationsList){
            UI.println(station.toString());
        }
    }
    /**
     * list all lines method
     */
    void listAllTrainLines(){
        UI.clearText();
        UI.println("All train lines:");
        for(TrainLine line:linesList){
            UI.println(line.toString());
        }
    }
    /**
     * list lines of station method
     */
    void listLinesOfStation(String stationName){
        UI.clearText();
        UI.println("lines with station, "+stationName+" on it:");
        for(TrainLine line:linesList){
            for(Station station:line.getStations()){
                if(station.getName().contains(stationName)){
                    UI.println(line.toString());
                }
            }
        }
    }
    /** 
     * list staions on line method
     */
    void listStationsOnLine(String lineName){
        UI.clearText();
        UI.println("List of stations on line "+lineName+":");
        for(Station station:stationsList){
            for(TrainLine line:station.getTrainLines()){
                if(line.getName().contains(lineName)){
                    UI.println(station.toString());
                }
            }
        }
    }
    /**
     * What line gets to the destination method
     */
    void checkConnected(String name, String dest){
        UI.clearText();
        UI.println("Connected line:");
        for(TrainLine line:linesList){
            boolean isOn = false;
            for(Station station:line.getStations()){
                if(station.getName().contains(name)){isOn = true;}
                if((isOn) &&
                    (station.getName().contains(dest))){
                    UI.println(line.toString());
                }
            }
        }
    }
}
