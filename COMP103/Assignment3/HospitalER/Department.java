// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2021T2, Assignment 3
 * Name: Elisha Jones
 * Username: Joneselis1
 * ID: 300573902
 */

import ecs100.*;
import java.util.*;

/**
 * A treatment Department (Surgery, X-ray room,  ER, Ultrasound, etc)
 * Each department will need
 * - A name,
 * - A maximum number of patients that can be treated at the same time
 * - A Set of Patients that are currently being treated
 * - A Queue of Patients waiting to be treated.
 *    (ordinary queue, or priority queue, depending on argument to constructor)
 */

public class Department{
    
    private String name;
    private int maxPatients;

    /*# YOUR CODE HERE */
    private Queue<Patient> waitingRoom = new ArrayDeque<Patient>();
    private Set<Patient> treatmentRoom = new HashSet<Patient>();
    
    /**
     * constructor
     * need a name, max number of patients and a priority
     */
    public Department(String depName, int maxCap, boolean isPriority){
        name = depName;
        maxPatients = maxCap;
        if (isPriority){waitingRoom = new PriorityQueue<Patient>();}
        else{waitingRoom = new ArrayDeque<Patient>();}
        treatmentRoom = new HashSet<Patient>();
    }
    
    /**
     * adds the patient to the departments waiting room
     */
    public void addPatient(Patient p) {
        waitingRoom.offer(p);
    }
    
    public boolean treatmentNotFull(){
        if (treatmentRoom.size() == maxPatients){
            return false;
        }
        return true;
    }
    
    /**
     * returns the treatment / department
     */
    public Set<Patient> getTreatmentRoom() {
        return treatmentRoom;
    }
    
    /**
     * returns the waiting room
     */
    public Queue<Patient> getWaitingRoom() {
        return waitingRoom;
    }
    
    /**
     * discarges a patient
     */
    public void dischargePatient(Patient p) {
        treatmentRoom.remove(p);
    }

    /**
     * Moves a patient to the departments waiting room
     */
    public void movePatientToTreatment() {
        List<Patient> movedPatients = new ArrayList<>();
        if (waitingRoom.size() > 0) {
            for (Patient p : waitingRoom) {
                if (treatmentRoom.size() < maxPatients) {
                    treatmentRoom.add(p);
                    movedPatients.add(p);
                } else {
                    p.waitForATick();
                }
            }
        }
        waitingRoom.removeAll(movedPatients);
        movedPatients.clear();
    }
    /**
     * Draw the department: the patients being treated and the patients waiting
     * You may need to change the names if your fields had different names
     */
    public void redraw(double y){
        UI.setFontSize(14);
        UI.drawString(name, 0, y-35);
        double x = 10;
        UI.drawRect(x-5, y-30, maxPatients*10, 30);  // box to show max number of patients
        for(Patient p : treatmentRoom){
            p.redraw(x, y);
            x += 10;
        }
        x = 200;
        for(Patient p : waitingRoom){
            p.redraw(x, y);
            x += 10;
        }
        UI.drawLine(0,y+2,400, y+2);
    }

}
