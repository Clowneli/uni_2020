// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2021T2, Assignment 5
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
 * Calculator for Cambridge-Polish Notation expressions
 * (see the description in the assignment page)
 * User can type in an expression (in CPN) and the program
 * will compute and print out the value of the expression.
 * The template provides the method to read an expression and turn it into a tree.
 * You have to write the method to evaluate an expression tree.
 *  and also check and report certain kinds of invalid expressions
 */

public class CPNCalculator{

    /**
     * Setup GUI then run the calculator
     */
    public static void main(String[] args){
        CPNCalculator calc = new CPNCalculator();
        calc.setupGUI();
        calc.runCalculator();
    }

    /** Setup the gui */
    public void setupGUI(){
        UI.addButton("Clear", UI::clearText); 
        UI.addButton("Quit", UI::quit); 
        UI.setDivider(1.0);
    }

    /**
     * Run the calculator:
     * loop forever:  (a REPL - Read Eval Print Loop)
     *  - read an expression,
     *  - evaluate the expression,
     *  - print out the value
     * Invalid expressions could cause errors when reading or evaluating
     * The try-catch prevents these errors from crashing the program - 
     *  the error is caught, and a message printed, then the loop continues.
     */
    public void runCalculator(){
        UI.println("Enter expressions in pre-order format with spaces");
        UI.println("eg   ( * ( + 4 5 8 3 -10 ) 7 ( / 6 4 ) 18 )");
        while (true){
            UI.println();
            try {
                GTNode<ExpElem> expr = readExpr();
                double value = evaluate(expr);
                UI.println(" -> " + value);
            }catch(Exception e){UI.println("Something went wrong! "+e);}
        }
    }

    /**
     * Evaluate an expression and return the value
     * Returns Double.NaN if the expression is invalid in some way.
     * If the node is a number
     *  => just return the value of the number
     * or it is a named constant
     *  => return the appropriate value
     * or it is an operator node with children
     *  => evaluate all the children and then apply the operator.
     */
    public double evaluate(GTNode<ExpElem> expr){
        if (expr==null){
            return Double.NaN;
        }
        /*# YOUR CODE HERE */
        
        //Core expressions
        
        if (expr.getItem().operator.equals("#")){
            return expr.getItem().value;
        }
        
        //initalises value
        double value = 0;
        
        // If the program should add
        if(expr.getItem().operator.equals("+")){
            //sets to 0
            value = 0;
            for (GTNode<ExpElem> child : expr){
                value += evaluate(child);
            }
        }
        //if the program should subtract
        else if(expr.getItem().operator.equals("-")){
            value = 2 * evaluate(expr.getChild(0));
            for (GTNode<ExpElem> child : expr){
                value -= evaluate(child);
            }
        }
        //if the program should multiply
        else if(expr.getItem().operator.equals("*")){            
            value = 1;
            for (GTNode<ExpElem> child : expr){
                value *= evaluate(child);
            }
        }
        //if the program should divide
        else if(expr.getItem().operator.equals("/")){
            value = Math.pow(evaluate(expr.getChild(0)), 2);
            for (GTNode<ExpElem> child : expr){
                value /= evaluate(child);
            }
        }
        //if the next value is PI
        else if(expr.getItem().operator.equals("PI")){
            value = Math.PI;
        }
        //if the next value is E
        else if(expr.getItem().operator.equals("E")){
            value = Math.E;
        }
        
        //Comp Expressions
        
        //If the program should profrom A^B
        else if(expr.getItem().operator.equals("^")){
            value = Math.pow(evaluate(expr.getChild(0)),
                            evaluate(expr.getChild(1)));
        }
        //If the program should profrom square root of A
        else if(expr.getItem().operator.equals("sqrt")){
            value = Math.sqrt(evaluate(expr.getChild(0)));
        }
        //If the program is a log expressions
        else if(expr.getItem().operator.equals("log")){
            if(expr.numberOfChildren()==1){
                value = Math.log10(evaluate(expr.getChild(0)));
            }
            else if(expr.numberOfChildren()==2){
                value = Math.log(evaluate(expr.getChild(0))) / Math.log(evaluate(expr.getChild(1)));
            }
            else {
                UI.println("Invalid opertator for Log. Requires 1 or 2 values.");
                return Double.NaN;
            }
        }
        //If the program is a ln expressions
        else if(expr.getItem().operator.equals("ln")){
            value = Math.log(evaluate(expr.getChild(0)));
        }
        //If the program should sin an expressions
        else if(expr.getItem().operator.equals("sin")){
            value = Math.sin(evaluate(expr.getChild(0)));
        }
        //If the program should cos an expressions
        else if(expr.getItem().operator.equals("cos")){
            value = Math.cos(evaluate(expr.getChild(0)));
        }
        //If the program should tan an expressions
        else if(expr.getItem().operator.equals("tan")){
            value = Math.tan(evaluate(expr.getChild(0)));
        }
        //If the program should calculate distance
        else if(expr.getItem().operator.equals("dist")){
            if (expr.numberOfChildren() == 4){
                double x = Math.abs(evaluate(expr.getChild(2)) - evaluate(expr.getChild(0)));
                double y = Math.abs(evaluate(expr.getChild(3)) - evaluate(expr.getChild(1)));
                
                value = Math.hypot(x, y);
            }
            else if (expr.numberOfChildren() == 6){
                double x = Math.pow(Math.abs(evaluate(expr.getChild(3)) - evaluate(expr.getChild(0))),2);
                double y = Math.pow(Math.abs(evaluate(expr.getChild(4)) - evaluate(expr.getChild(1))),2);
                double z = Math.pow(Math.abs(evaluate(expr.getChild(5)) - evaluate(expr.getChild(2))),2);
                
                value = Math.sqrt(x + y + z);
            }
            else {
                UI.println("Invalid entry for dist. Try (x1,y1,x2,y2) or (x1,y1,z1,x2,y2,z2).");
                return Double.NaN;
            }
        }
        //If the program should average an expressions
        else if(expr.getItem().operator.equals("avg")){
            value = 0;
            int count = 0;
            for (GTNode<ExpElem> child : expr){
                value += evaluate(child);
                count++;
            }
            value /= count;
        }
        else {
            UI.println("Operator: " + expr.getItem().operator +" is invalid!");
            return Double.NaN;
        }
        
        //returns value
        return value;
    }

    /** 
     * Reads an expression from the user and constructs the tree.
     */ 
    public GTNode<ExpElem> readExpr(){
        String expr = UI.askString("expr:");
        return readExpr(new Scanner(expr));   // the recursive reading method
    }

    /**
     * Recursive helper method.
     * Uses the hasNext(String pattern) method for the Scanner to peek at next token
     */
    public GTNode<ExpElem> readExpr(Scanner sc){
        if (sc.hasNextDouble()) {                     // next token is a number: return a new node
            return new GTNode<ExpElem>(new ExpElem(sc.nextDouble()));
        }
        else if (sc.hasNext("\\(")) {                 // next token is an opening bracket
            sc.next();                                // read and throw away the opening '('
            ExpElem opElem = new ExpElem(sc.next());  // read the operator
            GTNode<ExpElem> node = new GTNode<ExpElem>(opElem);  // make the node, with the operator in it.
            int count = 0;
            while (! sc.hasNext("\\)")){              // loop until the closing ')'
                GTNode<ExpElem> child = readExpr(sc); // read each operand/argument
                node.addChild(child);                 // and add as a child of the node
                count++;
                if(!sc.hasNext()){
                    UI.println("Missing closing bracket");
                    return null;
                }
            }
            sc.next();                                // read and throw away the closing ')'
            if (count < 1){UI.println("Missing statement in bracket");return null;}
            return node;
        }
        else {                                        // next token must be a named constant (PI or E)
                                                      // make a token with the name as the "operator"
            return new GTNode<ExpElem>(new ExpElem(sc.next()));
        }
    }

}

