// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2021T1, Assignment 8
 * Name: Elisha Jones
 * Username: Joneselis1
 * ID: 300573902
 */

import ecs100.*;
import java.util.*;
import java.awt.Color;

/** Program for a simple game in which the player has to blow up balloons
 *   on the screen.
 *  The game starts with a collection of randomly placed small balloons
 *    (coloured circles) on the graphics pane.
 *  The player then clicks on balloons to blow them up by a small amount
 *   (randomly increases the radius between 4 and 10 pixels).
 *  If an expanded balloon touches another balloon, then they both "burst" and go grey.
 *  The goal is to get the largest score. The score is the total of the
 *   sizes (areas) of all the active balloons, minus the total size of all
 *   the burst balloons.
 *  At each step, the current score is recalculated and displayed,
 *   along with the highest score that the player has achieved so far.
 *  At any time, the player may choose to stop and "lock in" their score.
 *
 *  The BalloonGame class has a field containing an Arraylist of Balloon objects
 *   to represent the current set of Balloons on the screen.
 *  It has a field to hold the highest score.
 *
 *  The New Game button should start a new game.
 *  The Lock Score button should finish the current game, updating the highest score
 *
 *  Clicking (ie, releasing) the mouse on the graphics pane is the main "action"
 *  of the game. The action should do the following
 *    Find out if the mouse was clicked on top of any balloon.
 *    If so,
 *      Make the balloon a bit larger and redraw it.
 *      Check whether the balloon is touching any other balloon.
 *      If so
 *         burst the two balloons (which will make them go grey)
 *         redraw the burst Balloons
 *      Recalculate and redisplay the score
 *   If all the balloons are gone, the game is over.
 *    
 *   To start a game, the program should
 *       Clear the graphics pane
 *       Initialise the score information
 *       Make a new list of Balloons at random positions
 *       Print a message 
 *
 *   If the game is over, the program should
 *      Update the highest score if the current score is better,
 *      Print a message reporting the scores,
 *     
 *   There are lots of ways of designing the program. It is not a good idea
 *   to try to put everything into one big method.
 *        
 *  Note that the Balloon class is written for you. Make sure that you know
 *   all its methods - no marks for redoing code that is given to you.
 *    
 */
public class BalloonGame {
    private static final int MAX_BALLOONS = 20;
    
    private ArrayList <Balloon> balloons = new ArrayList<Balloon>(); // The list of balloons
    // (initially empty)

    // Fields
    /*# YOUR CODE HERE */
    private int score = 0;
    private int highScore = 0;
    private Balloon selected;
    private boolean isClicked = false;
    private boolean gameLock = true;
    private int numBalloons = MAX_BALLOONS;

    public void setupGUI(){
        UI.setWindowSize(600,600);
        /*# YOUR CODE HERE */
        UI.setMouseListener(this::doMouse);
        UI.addButton("Restart game", this::restartGame);
        UI.addButton("Lock Score", this::LockScore);
        UI.addSlider("How many pairs of balloons",1,10, this::NumBalloons);
        UI.setDivider(0.0);
        UI.printMessage("Score: "+score+"      Highscore: "+highScore);
    }   

    /** Start the game:
     *  Clear the graphics pane
     *  Initialise the score information 
     *  Make a new set of Balloons at random positions
     */
    public void restartGame(){
        /*# YOUR CODE HERE */
        UI.clearGraphics();
        gameLock = false;
        balloons = new ArrayList<Balloon>();
        this.score = 0;
        for (int i = 0; i < numBalloons; i++){
            Balloon bl = new Balloon(Math.random()*550 + 50,Math.random()*550 + 50);
            this.balloons.add(bl);
            bl.draw();
        }
        this.calculateScore();
    }
    
    public void NumBalloons(double n){
        int t = (int) Math.round(n);
        numBalloons = 2*t;
    }
    
    /** lock score button */
    public void LockScore(){
        if (this.gameLock == true){
            UI.printMessage("GAME OVER!      Score: "+score+"      Highscore: "+highScore);
        }
        this.gameLock = true;
        this.endGame();
    }
    /**
     * Main game action:
     *    Find the balloon at (x,y) if any,
     *  Expand it 
     *  Check whether it is touching another balloon,
     *  If so, burst both balloons.
     *  Redraw the balloon (and the other balloon if it was touching)
     *  Calculate and Report the score. (Hint: use UI.printMessage(...) to report)
     *  If there are no active balloons left, end the game.
     */
    public void  doMouse(String action, double x, double y){
        /*# YOUR CODE HERE */
        if (action.equals("clicked") && !gameLock){
            for (Balloon balloon: this.balloons){
                if (balloon.on(x,y)){
                    this.selected = balloon;
                    balloon.expand();
                    balloon.draw();
                    this.isClicked = !isClicked;
                    
                    if (this.findTouching(selected) != null){
                        selected.burst();
                        
                    }
                }
                
            }
            this.calculateScore();
            this.isClicked = !isClicked;
            if (!allBalloonsBurst()){
                this.gameLock = !gameLock;
            }
            this.endGame();
        }
    }

    public Balloon findTouching(Balloon balloon){
        for (Balloon balloonTouch: this.balloons){
            if (this.selected.isTouching(balloonTouch) && (this.selected != balloonTouch) && (balloonTouch.isActive())){
                balloonTouch.burst();
                return balloonTouch;
            }
        }
        return null;
    }
    //for finding another active balloon touching the given one.

    public int calculateScore(){
        for (Balloon balloon: this.balloons){
            if (balloon.isActive()){
                this.score += balloon.size();
            }
            else if (!balloon.isActive()){
                this.score -= balloon.size();
            }
        }
        UI.printMessage("Score: "+score+"      Highscore: "+highScore);
        return score;
    }
    //for calculating the current score

    
    public boolean allBalloonsBurst(){
        for (Balloon balloon: this.balloons){
            if (balloon.isActive()){
                return true;
            }
        }
        gameLock = !gameLock;
        return false;
    }
    //       to find out if all the balloons have been burst.
    
    public void endGame(){
        if(gameLock){
            if(this.score > this.highScore){
                this.highScore = this.score;
            }
            UI.printMessage("GAME OVER!      Score: "+score+"      Highscore: "+highScore);
        }
    }
    //        to update the highestScore and print a message

    /*# YOUR CODE HERE */

    public static void main(String[] arguments){
        BalloonGame bg = new BalloonGame();
        bg.setupGUI();
    }

}
