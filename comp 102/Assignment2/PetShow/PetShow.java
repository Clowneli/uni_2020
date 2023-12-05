// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2021T1, Assignment 2
 * Name: Elisha Jones
 * Username: Joneselis1
 * ID: 300573902
 */

import ecs100.*;

/** Program to create simple animated animal character using the
 *  Animal class.  
 */

public class PetShow{

    /** animate creates two or several animals on the window.
     *  Then animates them according to a fixed script by calling a series
     *  of methods on the animals.
     *  
     *  CORE
     */
    public void animate(){
        /*# YOUR CODE HERE */
        Animal a1 = new Animal("dog", "Jim", 50, 350);
        a1.introduce("Hello");
        a1.speak("Woof! Woof!");
        a1.goRight(50);
        a1.goLeft(100);
        a1.goRight(50);
        a1.speak("Woof! Woof!");
        a1.speak("I am looking for a friend!");
        Animal a2 = new Animal("snake", "Simon", 800, 350);
        a2.goLeft(300);
        a1.think("Maybe he will be my friend");
        a1.goRight(150);
        a1.speak("Hello! What is your name?");
        a2.introduce("");
        a1.speak("Will you be my friend?");
        a1.jump(20);
        a1.jump(20);
        a2.speak("No.");
        a2.goRight(300);
        a1.think("That snake was mean D:");
        a1.think("Maybe someone else will be my friend!");
        Animal a3 = new Animal("turtle", "Tommy", 800, 350);
        a3.goLeft(300);
        a1.think("Maybe he will be my friend");
        a1.goRight(150);
        a1.speak("Hello! What is your name?");
        a3.introduce("Hello!");
        a1.speak("Will you be my friend?");
        a1.jump(20);
        a1.jump(20);
        a3.speak("sure.");
        a1.goLeft(600);
        a3.goLeft(800);
    }

    /** threeAnimalsRoutine creates three animals on the window.
     *  Then makes each animal do the same routine in turn.
     *  You should define a routine method, and threeAnimalsRoutine
     *   should call the routine method three times, to make
     *   each of the three animals perform the routine in turn.
     *   
     *   COMPLETION
     */
    public void threeAnimalsRoutine(){
        /*# YOUR CODE HERE */
        Animal a1 = new Animal ("tiger", "Timmy", 100, 300);
        Animal a2 = new Animal ("grasshopper", "Garry", 300, 100);
        Animal a3 = new Animal ("dinosaur", "Desmond", 500, 300);
        this.routine(a1);
        this.routine(a2);
        this.routine(a3);       
    }

    /** makes the animal character do a little routine
     */
    public void routine(Animal a){
        /*# YOUR CODE HERE */
        a.introduce("Hello");
        a.goRight(50);
        a.goLeft(100);
        a.goRight(50);
        a.jump(50);
        a.jump(50);
        a.speak("Goodbye!");
        a.goLeft(1000);
    }

    /** Make buttons to let the user run the methods */
    public void setupGUI(){
        UI.initialise();
        UI.addButton("Clear", UI::clearGraphics );
        UI.addButton("Animate", this::animate );
        UI.addButton("Three", this::threeAnimalsRoutine );
        UI.addButton("Quit", UI::quit );
        UI.setDivider(0);       // Expand the graphics area
    }

    public static void main(String[] args){
        PetShow ps = new PetShow();
        ps.setupGUI();
    }
}

