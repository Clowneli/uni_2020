// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2021T1, Assignment 4
 * Name: Elisha Jones 
 * Username: Joneselis1
 * ID: 300573902
 */

import ecs100.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class WordSearcher {

    /**
     * Asks the user for a pattern and then finds and prints out (one per line)
     *     all the words in a dictionary that contain that pattern.
     * At the end, it prints out how many words in the dictionary contained
     *    the pattern.
     * It should print the words as it finds them, but should stop printing
     *    after it has found 100 of them
     * The dictionary is in the file dictionary.txt, and has one word per line.
     */
    public void searchPattern(){
        /* asks for user inputs */
        String fileName = UIFileChooser.open("Choose file to search");
        String Pattern = UI.askString("Pattern to search for");
        try {
            /* Surches file for phrase */
            List<String> allLines = Files.readAllLines(Path.of(fileName));
            int TotalNumber = 0;
                for (String line : allLines){
                    if (line.contains(Pattern)){
                        if (TotalNumber <= 100){
                            UI.println(line);
                        }
                        TotalNumber++;
                    }
            }
            UI.println(TotalNumber + " Matches for pattern: " + Pattern);
        } catch (IOException e) { UI.println("File failure: " + e); }
    }

    /** set up the buttons */
    public void setupGUI(){
        UI.addButton("Clear", UI::clearPanes);
        UI.addButton("Search", this::searchPattern);
        UI.addButton("quit", UI::quit);
        UI.setDivider(1.0);
    }

    public static void main(String[] args){
        WordSearcher ws = new WordSearcher();
        ws.setupGUI();
    }
}

