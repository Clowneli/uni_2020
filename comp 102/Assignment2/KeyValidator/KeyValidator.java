// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2021T1, Assignment 2
 * Name: Elisha Jones
 * Username: Joneselis1
 * ID: 300573902
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;

/**
 * Key:
 * Core:       Method must report whether the key is valid, or
 *             report that it is invalid and give one reason why it is invalid.
 *             To be valid, the key must
 *             - be at least 8 characters and at most 16 characters long,
 *             - not end with the special characters '#' or '$',
 *             - not have a hyphen ('-') character anywhere
 *            
 * Completion: Method should either report that the key is valid, or
 *             report that it is invalid and list ALL the reasons that it is invalid.
 *             To be valid, the key must
 *             - satisfy all of the conditions above AND
 *             - have at least one Upper case character and at least one Lower case character,
 *             - not start with the same character as the first character of the user's name
 *             - contain either a '#' or a '$', but not both.
 * Challenge:  Same as completion, except that to be valid, the key must
 *             - satisfy all of the conditions above AND
 *             - have a mix of numbers and letters
 *             - not contain any "suffix" of the user's name of 2 characters or more in any case.
 *               (eg if name is Peter, it does not contain "er", or "eR" or "ter", or "eTer" or "ETER", or...)
 *
 * Hint.  Look at the documentation in the String class.
 * You will definitely find the length(), endsWith(...), and contains(...) methods to be helpful
 */

public class KeyValidator {

    /**
     * Asks user for key word and then checks if it is a valid key word.
     */
    public void doCore(){
        UI.clearText();
        String key = UI.askString("Key:   ");
        UI.println();
        this.validateKeyCore(key);
    }

    /** CORE
     * Report "Valid" or "Invalid: ...reason...."
     */

    public void validateKeyCore(String key){
        if (key.length() < 8 || key.length() > 16){
            UI.println("Key length is invalid. Please select a key between 8 and 16 characters long.");
        }
        else if (key.endsWith("&") || key.endsWith("#")) {
            UI.println("Key cannot end with & or #."); 
        }
        else if (key.contains("-")) {
            UI.println("Key cannot contain a hyphen.");
        }
        else{
            UI.println("Key is valid.");
        }
    }

    /**
     * Asks user for key word and the name and then checks if it is a valid key word.
     */
    public void doCompletion(){
        UI.clearText();
        String key = UI.askString("Key:   ");
        String name = UI.askString("Your name:   ");
        UI.println();
        this.validateKeyCompletion(key, name);
    }

    /** COMPLETION
     * Report that the key is valid or report ALL the rules that the key failed.
     */
        
    /* Works by checking each indivdual character within the key.
    Checks if it is a lower case or uppercase character. 
    string must have 1 of each to output true */
    private static boolean checkString(String str) {
        char ch;
        boolean capitalCheck = false;
        boolean lowerCaseCheck = false;
        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if (Character.isUpperCase(ch)) {
                capitalCheck = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseCheck = true;
            }
            if(capitalCheck && lowerCaseCheck)   
                return true;
        }
        return false;
    }
    public void validateKeyCompletion(String key, String name){
        boolean error = true; // 
        int X = 0;
        ArrayList<String> listErrors = new ArrayList<String>(); 
        // Checks key length
        if (key.length() < 8 || key.length() > 16){
            error = false;
            listErrors.add("Key length is invalid, please have a key length between 8 and 16.");
        }
        // Checks if key ends with # or &
        if (key.endsWith("&") || key.endsWith("#")) {
            error = false;
            listErrors.add("Key Cannot end with & or #.");
        }
        // Checks if key contains a hyphen
        if (key.contains("-")) {
            error = false;
            listErrors.add("Key cannot contain a hyphen.");
        }
        // Checks if key has a capital and lowercase letter
        if (!(checkString(key))){
            error = false;
            listErrors.add("Key must contain an uppercase and a lowercase character.");
        }
        // Checks if key starts with same letter as users name
        if (Character.toLowerCase(key.charAt(0)) == Character.toLowerCase(name.charAt(0))){
            error = false;  
            listErrors.add("Key cannot start with the starting letter of your name.");
        }
        // Checks if key has an & or a # but not both
        if (!(((key.contains("&")) && !(key.contains("#"))) || (key.contains("#") && (!(key.contains("&")))))){
            error = false;
            listErrors.add("Key must contain an & or a # but not both.");
        }
        // Checks if valid and prints list of errors if its not.
        if (error == true){
            UI.println("Key is valid.");
        }
        else{
            UI.println("Key is invalid:");
            for (String s:listErrors){
                UI.println(s);
            }
        }
    }
    
    /** Challenge */
    public void doChallenge(){
        UI.clearText();
        UI.println("Sufix check not yet working.");
        String key = UI.askString("Key:   ");
        String name = UI.askString("Your name:   ");
        UI.println();
        this.validateKeyChallenge(key, name);
    }
    
    private static boolean checkStringChall(String str) {
        char ch;
        boolean capitalCheck = false;
        boolean lowerCaseCheck = false;
        boolean numberCheck = false;
        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if(Character.isDigit(ch)) {
                numberCheck = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalCheck = true;
            } 
            else if (Character.isLowerCase(ch)) {
                lowerCaseCheck = true;
            }
            if(numberCheck && capitalCheck && lowerCaseCheck)
                return true;
        }
        return false;        
    }
    
    public void validateKeyChallenge(String key, String name){
        boolean error = true;
        ArrayList<String> listErrors = new ArrayList<String>(); 
        
        if (key.length() < 8 || key.length() > 16){
            error = false;
            listErrors.add("Key length is invalid, please have a key length between 8 and 16.");
        }
        if (key.endsWith("&") || key.endsWith("#")) {
            error = false;
            listErrors.add("Key Cannot end with & or #.");
        }
        if (key.contains("-")) {
            error = false;
            listErrors.add("Key cannot contain a hyphen.");
        }
        if (!(checkStringChall(key))){
            error = false;
            listErrors.add("Key must contain an uppercase character, lowercase character and a number.");
        }
        if (Character.toLowerCase(key.charAt(0)) == Character.toLowerCase(name.charAt(0))){
            error = false;  
            listErrors.add("Key cannot start with the starting letter of your name.");
        }
        if (!(((key.contains("&")) && !(key.contains("#"))) || (key.contains("#") && (!(key.contains("&")))))){
            error = false;
            listErrors.add("Key must contain an & or a # but not both.");
        }
        if (error == true){
            UI.println("Key is valid.");
        }
        else{
            UI.println("Key is invalid:");
            for (String s:listErrors){
                UI.println(s);
            }
        }
    }
    public void setupGUI(){
        UI.initialise();
        UI.addButton("Clear", UI::clearText );
        UI.addButton("Validate Key Core", this::doCore );
        UI.addButton("Validate Key Completion", this::doCompletion );
        UI.addButton("Validate Key Challenge", this::doChallenge );
        UI.addButton("Quit", UI::quit );
        UI.setDivider(1);       // Expand the text area
    }

    public static void main(String[] args){
        KeyValidator kv = new KeyValidator();
        kv.setupGUI();
    }
}
