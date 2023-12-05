// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2021T2, Assignment 6
 * Name: Elisha Jones
 * Username: Joneselis1
 * ID: 300573902
 */

import ecs100.*;
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class BusNetworks {

    /** Map of towns, indexed by their names */
    private Map<String,Town> busNetwork = new HashMap<String,Town>();

    /** CORE
     * Loads a network of towns from a file.
     * Constructs a Set of Town objects in the busNetwork field
     * Each town has a name and a set of neighbouring towns
     * First line of file contains the names of all the towns.
     * Remaining lines have pairs of names of towns that are connected.
     */
    public void loadNetwork(String filename) {
        try {
            //sets the staring vars
            busNetwork.clear();
            UI.clearText();
            List<String> lines = Files.readAllLines(Path.of(filename));
            String firstLine = lines.remove(0);
            /*# YOUR CODE HERE */
            //for loop of each line
            for (String line:lines){
                //starts scanner and begins scan of network
                Scanner sc = new Scanner(line);
                String city1 = sc.next();
                String city2 = sc.next();
                //checks the keys and add town to key if there is no town in key
                if(!busNetwork.containsKey(city1)){
                   busNetwork.put(city1,new Town(city1)); 
                }
                if(!busNetwork.containsKey(city2)){
                   busNetwork.put(city2,new Town(city2)); 
                }
                //sets the towns neighbours.
                busNetwork.get(city1).addNeighbour(busNetwork.get(city2));
                busNetwork.get(city2).addNeighbour(busNetwork.get(city1));
            }
            
            UI.println("Loaded " + busNetwork.size() + " towns:");

        } catch (IOException e) {throw new RuntimeException("Loading data.txt failed" + e);}
    }

    /**  CORE
     * Print all the towns and their neighbours:
     * Each line starts with the name of the town, followed by
     *  the names of all its immediate neighbours,
     */
    public void printNetwork() {
        UI.println("The current network: \n====================");
        /*# YOUR CODE HERE */
        //For loop of the networks
        for (Map.Entry<String, Town> entry : busNetwork.entrySet()) {
            //sets values for testing
            String key = entry.getKey();
            Town value = entry.getValue();
            //sets and prints the network
            List<String> neighbours = new ArrayList<String>();
            for(Town town: value.getNeighbours()){
                neighbours.add(town.getName());
            }
            
            UI.println(key + " -> " + neighbours);
        }
    }

    /** COMPLETION
     * Return a set of all the nodes that are connected to the given node.
     * Traverse the network from this node in the standard way, using a
     * visited set, and then return the visited set
     */
    public Set<Town> findAllConnected(Town town,Set<Town> visited) {
        /*# YOUR CODE HERE */
        
        visited.add(town);
        //runs through the list and wont add to the connected if already in list
        for(Town toAdd : town.getNeighbours()){
            if(!visited.contains(toAdd)){
                findAllConnected(toAdd,visited);
            }
        }
        return visited;
    }

    /**  COMPLETION
     * Print all the towns that are reachable through the network from
     * the town with the given name.
     * Note, do not include the town itself in the list.
     */
    public void printReachable(String name){
        Town town = busNetwork.get(name);
        if (town==null){
            UI.println(name+" is not a recognised town");
        }
        else {
            UI.println("\nFrom "+town.getName()+" you can get to:");
            /*# YOUR CODE HERE */
            //finds the lists and prints the lists
            Set<Town> visited = findAllConnected(town, new HashSet<Town>());
            for(Town towns:visited){
                UI.println(towns.getName());
            }
        }

    }

    /**  COMPLETION
     * Print all the connected sets of towns in the busNetwork
     * Each line of the output should be the names of the towns in a connected set
     * Works through busNetwork, using findAllConnected on each town that hasn't
     * yet been printed out.
     */
    public void printConnectedGroups() {
        UI.println("Groups of Connected Towns: \n================");
        int groupNum = 1;
        /*# YOUR CODE HERE */
        //Checks if the lists are connected and adds to list if they are
        Set<Set<Town>> visited = new HashSet<Set<Town>>();
        for (Map.Entry<String, Town> entry : busNetwork.entrySet()) {
            Town town = entry.getValue();
            if(!visited.contains(town)){
                visited.add(findAllConnected(town, new HashSet<Town>()));
                groupNum++;
            }
        }
        //prints the sets of connected towns
        for (Set<Town> towns : visited){
            UI.println(towns);
        }
    }

    /**
     * Set up the GUI (buttons and mouse)
     */
    public void setupGUI() {
        UI.addButton("Load", ()->{loadNetwork(UIFileChooser.open());});
        UI.addButton("Print Network", this::printNetwork);
        UI.addTextField("Reachable from", this::printReachable);
        UI.addButton("All Connected Groups", this::printConnectedGroups);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setWindowSize(1100, 500);
        UI.setDivider(1.0);
        loadNetwork("data-small.txt");
    }

    // Main
    public static void main(String[] arguments) {
        BusNetworks bnw = new BusNetworks();
        bnw.setupGUI();
    }

}
