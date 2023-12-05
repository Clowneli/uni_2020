// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2020T2, Assignment 5
 * Name: Chloe Crowe
 * Username: crowechlo
 * ID: 300539785
 */

import java.util.*;

/**
 * Implements a generic general tree node where the type of the
 *  item in each node is specified by the type variable MazeNode
 *  The children of a node are ordered.
 *  There are many ways of designing such a tree class;
 *  this is a very simple one which does not have many features.
 *
 *  Taken from the Calculator assignment and edited.
 */
public class MazeNode <MazeCell> implements Iterable <MazeNode<MazeCell>>{

    private final MazeCell cell;
    private Set<MazeNode<MazeCell>> children;
    private MazeNode<MazeCell> parent;
    private int neighbour;
    private int recordChild;

    /** Constructor for objects of class GTNode */
    public MazeNode(MazeCell C, MazeNode<MazeCell> parent){
        this.cell = C;
        this.parent = parent;
        this.children = new HashSet<>();
    }

    /** Get item from node */
    public MazeCell getCell(){ return cell; }

    /** Get item from node */
    public MazeNode<MazeCell> getParent(){ return parent; }

    public int getNeighbour(){return neighbour;}

    public int getRecordChild(){return recordChild;}

    /** Get number of children of node */
    public int numberOfChildren() {
        return children.size();
    }


    public void addChild(MazeNode<MazeCell> node){
        children.add(node);
        recordChild ++;
    }

    public void setNeighbour(int neighbour){
        this.neighbour = neighbour;
    }

    /** Remove child at position i */
    public void removeChild(MazeNode<MazeCell> child){ children.remove(child); }

    /**
     * Enables foreach:
     *      for (GTNode<E> child : node){..do something to each child..}
     * to loop through the children of a node
     */
    public Iterator<MazeNode<MazeCell>> iterator(){
        return children.iterator();
    }

}
