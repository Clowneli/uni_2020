/* Code for COMP103 - 2021T2, Assignment 6
 * Name: Eisha Jones
 * Username: joneselis1
 * ID: 300573902
 */

import java.util.*;

/**
 * Implements a generic general tree node where the type of the
 *  item in each node is specified by the type variable MazeNode
 *  Used to help traverse the graph.
 *  The neighbours of a node are ordered.
 *
 *  Taken from the previous assignments and edited to work with this one.
 */
public class MazeNode <MazeCell> implements Iterable <MazeNode<MazeCell>>{

    private final MazeCell cell; //the cell
    private Set<MazeNode<MazeCell>> neighbours; //the neighbouring cells
    private MazeNode<MazeCell> previous; //the node accessed before this
    private int neighbour;
    private int recordNeighbour;

    /** Constructor */
    public MazeNode(MazeCell C, MazeNode<MazeCell> parent){
        this.cell = C;
        this.previous = parent;
        this.neighbours = new HashSet<>();
    }

    /** Getter methods */
    public MazeCell getCell(){ return cell; }
    public MazeNode<MazeCell> getParent(){ return previous; }
    public int getNeighbour(){return neighbour;}
    public int getRecordNeighbour(){return recordNeighbour;}
    public int numberOfChildren(){return neighbours.size();}

    public void addNeighbours(MazeNode<MazeCell> node){
        neighbours.add(node);
        recordNeighbour ++;
    }

    public void setNeighbour(int neighbour){
        this.neighbour = neighbour;
    }

    /** Remove child at position i */
    public void removeChild(MazeNode<MazeCell> neighbour){neighbours.remove(neighbour);}

    /**
     * Enables foreach:
     *      for (GTNode<E> child : node){..do something to each child..}
     * to loop through the children of a node
     */
    public Iterator<MazeNode<MazeCell>> iterator(){
        return neighbours.iterator();
    }

}
