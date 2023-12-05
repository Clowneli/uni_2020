// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2020T2, Assignment 5
 * Name: Chloe Crowe
 * Username: crowechlo
 * ID: 300539785
 */

import ecs100.UI;

import java.awt.*;
import java.util.*;
import java.util.List;

///button to make, slider for size, slider for speed, delay -> field.

/**
 * Search for a path to the goal in a maze.
 * The maze consists of a graph of MazeCells:
 * Each cell has a collection of neighbouring cells.
 * Each cell can be "visited" and it will remember that it has been visited
 * A MazeCell is Iterable, so that you can iterate through its neighbour cells with
 * for(MazeCell neighbour : cell){....
 * <p>
 * The maze has a goal cell (shown in green, two thirds towards the bottom right corner)
 * The maze.getGoal() method returns the goal cell of the maze.
 * The user can click on a cell, and the program will search for a path
 * from that cell to the goal.
 * <p>
 * Every cell that is looked at during the search is coloured  yellow, and then,
 * if the cell turns out to be on a dead end, it is coloured red.
 */

public class MazeSearch {


    private Maze maze;
    private String search = "first";   // "first", "all", or "shortest"
    private int pathCount = 0;
    private boolean stopNow = false;
    public MazeCell startingCell; //Gets where the user pressed
    public MazeNode<MazeCell> firstCell; //Gets the first cell

    // Keeps track of all the full paths.
    public List<List<MazeCell>> fullPaths = new ArrayList<>();


    /**
     * CORE
     * Search for a path from a cell to the goal.
     * Return true if we got to the goal via this cell (and don't
     * search for any more paths).
     * Return false if there is not a path via this cell.
     * <p>
     * If the cell is the goal, then we have found a path - return true.
     * If the cell is already visited, then abandon this path - return false.
     * Otherwise,
     * Mark the cell as visited, and colour it yellow [and pause: UI.sleep(delay);]
     * Recursively try exploring from the cell's neighbouring cells, returning true
     * if a neighbour leads to the goal
     * If no neighbour leads to a goal,
     * colour the cell red (to signal failure)
     * abandon the path - return false.
     */
    public boolean exploreFromCell(MazeCell cell, MazeNode<MazeCell> parentCell) {
        startingCell.draw(Color.green);
        if (cell == maze.getGoal()) {
            cell.draw(Color.blue);   // to indicate finding the goal
            stopNow = true; // If its met its gol
            return true;
        } else if (!cell.isVisited() && !stopNow) {
            //Adds cell as a child cell
            MazeNode<MazeCell> childCell = new MazeNode<>(cell, parentCell);
            //Adds the child cell to its parent.
            parentCell.addChild(childCell);

            cell.visit();
            cell.draw(Color.yellow);
            UI.sleep(delay);

            int neighbours = 0;
            //Gets the number of neighbours.
            for (MazeCell c : cell) {
                neighbours++;
            }

            //Sets the childs number of neighbours.
            childCell.setNeighbour(neighbours);

            for (MazeCell neighbour : cell) {
                if (neighbours == 1 && cell != startingCell) {
                    //If theres only 1 neighbour and its not the starting point.
                    cell.draw(Color.red);
                    //Blocks the path from the cell.
                    blockPaths(childCell);
                    return false;
                } else {
                    exploreFromCell(neighbour, childCell);
                }
            }
        }
        return false;
    }

    //Blocks the cell going along the path.
    public void blockPaths(MazeNode<MazeCell> childCell) {
        if (childCell.numberOfChildren() == 0 && childCell.getNeighbour() - 1 == childCell.getRecordChild()) {
            //Gets the child cell and marks it red.
            childCell.getCell().draw(Color.red);
            //Gets the parent cell and removes the child.
            MazeNode<MazeCell> parentCell = childCell.getParent();
            parentCell.removeChild(childCell);
            blockPaths(parentCell);
        }
    }

    /**
     * COMPLETION
     * Search for all paths from a cell,
     * If we reach the goal, then we have found a complete path,
     * so pause for 1000 milliseconds
     * Otherwise,
     * visit the cell, and colour it yellow [and pause: UI.sleep(delay);]
     * Recursively explore from the cell's neighbours,
     * unvisit the cell and colour it white.
     */

    public void exploreFromCellAll(MazeCell cell, List<MazeCell> thePath) {
        if (stopNow) {
            return;
        }    // exit if user clicked the stop now button

        startingCell.draw(Color.green);

        if (cell == maze.getGoal()) {
            cell.draw(Color.blue);   // to indicate finding the goal
            UI.sleep(1000);
        } else if (!cell.isVisited() && !thePath.contains(cell) && !stopNow) {
            cell.visit();
            //Adds the cell to the path and colours it yellow.
            thePath.add(cell);
            cell.draw(Color.yellow);
            UI.sleep(delay);
            cell.unvisit();
            cell.draw(Color.white);

            //Gets the number of neighbours the cell has.
            int neighbours = 0;
            for (MazeCell c : cell) {
                neighbours++;
            }

            //For all the cells neighbours.
            for (MazeCell neighbour : cell) {
                //If theres only one neighbour and its not the starting cell, break.
                if (neighbours == 1 && cell != startingCell) {
                    break;
                } else {
                    exploreFromCellAll(neighbour, thePath);
                }
            }
        }
        for(MazeCell c : thePath){
            c.draw(Color.yellow);
        }

    }

    /**
     * CHALLENGE
     * Search for shortest path from a cell,
     * Use Breadth first search.
     */
    public void exploreFromCellShortest(MazeCell start) {
        fullPaths.clear();
        startingCell.draw(Color.green);
        if (stopNow) {
            return;
        }    // exit if user clicked the stop now button
        find(start, new ArrayList<>()); //Calls find
        List<MazeCell> shortPath = fullPaths.get(0); //Gets the first path in the full path list.
        pathCount = shortPath.size(); // Gets the size of the path

        for (List<MazeCell> p : fullPaths) {
            //If the size of the path is smaller than the path count. reset the shortest path.
            if (p.size() < pathCount) {
                pathCount = p.size();
                shortPath = p;
            }
        }
        for (MazeCell cell : shortPath) {
            //For all the shortest paths sleep and draw yellow.
            UI.sleep(delay);
            cell.draw(Color.yellow);
            //If it meets the goal turn blue, set the starting cell to green.
            if (cell == maze.getGoal()) {
                cell.draw(Color.blue);
            } else if (cell == startingCell) {
                cell.draw(Color.green);
            }
        }
    }

    public void find(MazeCell cell, List<MazeCell> path) {

        if (cell == maze.getGoal()) {
            //Adds the cell to the path
            path.add(cell);
            //Add the path to the full path list.
            fullPaths.add(path);
        } else if (!path.contains(cell)) {
            //If the path doesn't contain the cell add it to the path.
            path.add(cell);

            //Counts the number of neighbours.
            int neighbours = 0;
            for (MazeCell c : cell) {
                neighbours++;
            }

            //For all the cells neighbours. if theres more than 1 neighbour or if its the starting cell
            for (MazeCell neighbour : cell) {
                if (neighbours > 1 || cell == startingCell) {
                    //Find the updated path with the neighbour.
                    List<MazeCell> updatedPath = new ArrayList<>(path);
                    find(neighbour, updatedPath);
                }
            }
        }
    }


    //=================================================

    // fields for gui.
    private int delay = 20;
    private int size = 10;

    /**
     * Set up the interface
     */
    public void setupGui() {
        UI.addButton("New Maze", this::makeMaze);
        UI.addSlider("Maze Size", 4, 40, 10, (double v) -> { size = (int) v; });
        UI.setMouseListener(this::doMouse);
        UI.addButton("First path", () -> { search = "first"; });
        UI.addButton("All paths", () -> { search = "all"; });
        UI.addButton("Shortest path", () -> { search = "shortest"; });
        UI.addButton("Stop", () -> { stopNow = true; });
        UI.addSlider("Speed", 1, 101, 80, (double v) -> { delay = (int) (100 - v); });
        UI.addButton("Quit", UI::quit);
        UI.setDivider(0.);
    }

    /**
     * Creates a new maze and draws it .
     */
    public void makeMaze() {
        maze = new Maze(size);
        maze.draw();
    }

    /**
     * Clicking the mouse on a cell should make the program
     * search for a path from the clicked cell to the goal.
     */
    public void doMouse(String action, double x, double y) {
        if (action.equals("released")) {
            stopNow = false;
            maze.reset();
            maze.draw();
            pathCount = 0; //Reset the count
            fullPaths.clear(); // Clear the paths
            MazeCell start = maze.getCellAt(x, y);
            startingCell = start; //Set the starting cell
            firstCell = new MazeNode<>(startingCell, null); //Set the starting cell

            if (search == "first") {
                exploreFromCell(start, firstCell); //from the start and the first cell
            } else if (search == "all") {
                stopNow = false;
                exploreFromCellAll(start, new ArrayList<>());
            } else if (search == "shortest") {
                exploreFromCellShortest(start);
            }
        }
    }

    public static void main(String[] args) {
        MazeSearch ms = new MazeSearch();
        ms.setupGui();
        ms.makeMaze();
    }
}