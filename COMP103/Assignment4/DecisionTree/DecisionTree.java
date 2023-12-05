// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2021T2, Assignment 4
 * Name: Elisha Jones 
 * Username: joneselis1
 * ID: 300573902
 */

/**
 * Implements a decision tree that asks a user yes/no questions to determine a decision.
 * Eg, asks about properties of an animal to determine the type of animal.
 * 
 * A decision tree is a tree in which all the internal nodes have a question, 
 * The answer to the question determines which way the program will
 *  proceed down the tree.  
 * All the leaf nodes have the decision (the kind of animal in the example tree).
 *
 * The decision tree may be a predermined decision tree, or it can be a "growing"
 * decision tree, where the user can add questions and decisions to the tree whenever
 * the tree gives a wrong answer.
 *
 * In the growing version, when the program guesses wrong, it asks the player
 * for another question that would help it in the future, and adds it (with the
 * correct answers) to the decision tree. 
 *
 */

import ecs100.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.awt.Color;

public class DecisionTree {

    public DTNode theTree;    // root of the decision tree;

    /**
     * Setup the GUI and make a sample tree
     */
    public static void main(String[] args){
        DecisionTree dt = new DecisionTree();
        dt.setupGUI();
        dt.loadTree("sample-animal-tree.txt");
    }

    /**
     * Set up the interface
     */
    public void setupGUI(){
        UI.addButton("Load Tree", ()->{loadTree(UIFileChooser.open("File with a Decision Tree"));});
        UI.addButton("Print Tree", this::printTree);
        UI.addButton("Run Tree", this::runTree);
        UI.addButton("Grow Tree", this::growTree);
        UI.addButton("Save Tree", this::saveTree);  // for completion
        // UI.addButton("Draw Tree", this::drawTree);  // for challenge
        UI.addButton("Reset", ()->{loadTree("sample-animal-tree.txt");});
        UI.addButton("Quit", UI::quit);
        UI.setDivider(0.5);
    }

    /**  
     * Print out the contents of the decision tree in the text pane.
     * The root node should be at the top, followed by its "yes" subtree,
     * and then its "no" subtree.
     * Needs a recursive "helper method" which is passed a node.
     * 
     * COMPLETION:
     * Each node should be indented by how deep it is in the tree.
     * The recursive "helper method" is passed a node and an indentation string.
     *  (The indentation string will be a string of space characters)
     */
    public void printTree(){
        UI.clearText();
        /*# YOUR CODE HERE */
        UI.println(theTree.getText());
        printTreePath(theTree, 0);
    }
    
    /**
     * helper method for printing the tree
     */
    public void printTreePath(DTNode node, int spacing){
        //Will only continue if not a leaf.
        if(!node.isAnswer()){
            //adds spaces based on branch number
            String space = "";
            for (int i = 0; i < spacing; i++){
                space+="    ";
            }
            //prints each tree node branch and then traverses down the tree
            UI.println(space+"Yes:" + node.getYes().getText());
            printTreePath(node.getYes(), spacing+1);
            UI.println(space+"No:" + node.getNo().getText());
            printTreePath(node.getNo(), spacing+1);
        }
    }

    /**
     * Run the tree by starting at the top (of theTree), and working
     * down the tree until it gets to a leaf node (a node with no children)
     * If the node is a leaf it prints the answer in the node
     * If the node is not a leaf node, then it asks the question in the node,
     * and depending on the answer, goes to the "yes" child or the "no" child.
     */
    public void runTree() {
        UI.clearText();
        /*# YOUR CODE HERE */
        runTreePath(theTree);
    }
    
    /**
     * helper method for running the tree
     */
    public void runTreePath(DTNode node){
        if(node.isAnswer()){
            //final answer
            UI.println("The answer is: " + node.getText());
        }
        else{
            //traverses the tree based on user inputs
            String YorN = UI.askString(node.getText() + "(Y/N)");
            //if yes
            if (YorN.contains("Y")){runTreePath(node.getYes());}
            //if no
            else if(YorN.contains("N")){runTreePath(node.getNo());}
            //if non-valid input
            else{ 
                UI.println("Not a Valid input, please put Y or N"); 
                runTreePath(node);
            }
        }
    }
    
    /**
     * Grow the tree by allowing the user to extend the tree.
     * Like runTree, it starts at the top (of theTree), and works its way down the tree
     *  until it finally gets to a leaf node. 
     * If the current node has a question, then it asks the question in the node,
     * and depending on the answer, goes to the "yes" child or the "no" child.
     * If the current node is a leaf it prints the decision, and asks if it is right.
     * If it was wrong, it
     *  - asks the user what the decision should have been,
     *  - asks for a question to distinguish the right decision from the wrong one
     *  - changes the text in the node to be the question
     *  - adds two new children (leaf nodes) to the node with the two decisions.
     */
    public void growTree () {
        UI.clearText();
        /*# YOUR CODE HERE */
        growTreePath(theTree);
    }
    
    /**
     * helper method for growing the tree
     */
    public void growTreePath(DTNode node){
        if(!node.isAnswer()){
            //Code for running through the tree
            String YorN = UI.askString(node.getText() + "(Y/N)");
            if (YorN.contains("Y")){growTreePath(node.getYes());}
            else if(YorN.contains("N")){growTreePath(node.getNo());}
            else{ 
                UI.println("Not a Valid input, please put Y or N"); 
                growTreePath(node);
            }
        }
        else{
            //code for printing the final branch of tree traversed.
            String guess = UI.askString("Is the answer:" + node.getText() + "(Y/N)");
            // If guess is correct
            if (guess.contains("Y")){UI.println("The answer is: "+node.getText());}
            // Adds a new animal if guess is wrong
            else if(guess.contains("N")){
                String newAnimal = UI.askString("What animal is it?");
                String difference = UI.askString("What can "+newAnimal+" do that "+node.getText() + " cant?");
                //Replaces the node with new question and children
                DTNode animal = new DTNode(newAnimal);
                DTNode temp = new DTNode(node.getText());
                node.setText(difference);
                node.setChildren(animal,temp);
            }
            else{ 
                UI.println("Not a Valid input, please put Y or N"); 
                growTreePath(node);
            }
        }
    }

    // You will need to define methods for the Completion and Challenge parts.
    
     public void saveTree(){
        //Gets the users file name
        String treeName = UI.askString("What is your trees name? ");
        String fileName = treeName+".txt";
        //Create a new file
        File file = new File(fileName);
        //Try and write the file and call print An
        try(PrintWriter print = new PrintWriter(file)){
            printAn(print,theTree);
            UI.println("Your file has saved!");
            //If theres an error print it
        }catch (FileNotFoundException e){
            //e.printStackTrace();
            UI.println(e);
        }
    }
    public void printAn(PrintWriter print, DTNode theTree){
        //If the tree isnt null
        if(theTree != null){
            //Get the answer and print it else get and print the question
            if(theTree.isAnswer()){
                print.println("Answer: " + theTree.getText());
            }
            else {
                print.println("Question: " + theTree.getText());
            }
            printAn(print,theTree.getYes());
            printAn(print, theTree.getNo());
        }
    }
    // Written for you

    /** 
     * Loads a decision tree from a file.
     * Each line starts with either "Question:" or "Answer:" and is followed by the text
     * Calls a recursive method to load the tree and return the root node,
     *  and assigns this node to theTree.
     */
    public void loadTree (String filename) { 
        if (!Files.exists(Path.of(filename))){
            UI.println("No such file: "+filename);
            return;
        }
        try{theTree = loadSubTree(new ArrayDeque<String>(Files.readAllLines(Path.of(filename))));}
        catch(IOException e){UI.println("File reading failed: " + e);}
    }

    /**
     * Loads a tree (or subtree) from a Scanner and returns the root.
     * The first line has the text for the root node of the tree (or subtree)
     * It should make the node, and 
     *   if the first line starts with "Question:", it loads two subtrees (yes, and no)
     *    from the scanner and add them as the  children of the node,
     * Finally, it should return the  node.
     */
    public DTNode loadSubTree(Queue<String> lines){
        Scanner line = new Scanner(lines.poll());
        String type = line.next();
        String text = line.nextLine().trim();
        DTNode node = new DTNode(text);
        if (type.equals("Question:")){
            DTNode yesCh = loadSubTree(lines);
            DTNode noCh = loadSubTree(lines);
            node.setChildren(yesCh, noCh);
        }
        return node;

    }



}
