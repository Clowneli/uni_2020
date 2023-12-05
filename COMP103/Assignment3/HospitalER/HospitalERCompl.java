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
import java.io.*;

/**
 * Simulation of a Hospital ER
 * 
 * The hospital has a collection of Departments, including the ER department, each of which has
 *  and a treatment room.
 * 
 * When patients arrive at the hospital, they are immediately assessed by the
 *  triage team who determine the priority of the patient and (unrealistically) a sequence of treatments 
 *  that the patient will need.
 *
 * The simulation should move patients through the departments for each of the required treatments,
 * finally discharging patients when they have completed their final treatment.
 *
 *  READ THE ASSIGNMENT PAGE!
 */

public class HospitalERCompl{

    // Copy the code from HospitalERCore and then modify/extend to handle multiple departments

    // Fields for recording the patients waiting in the waiting room and being treated in the treatment room
    private static final int MAX_PATIENTS = 5;   // max number of patients currently being treated
    private Map<String,Department> department = new HashMap<String,Department>();
    // fields for the statistics
    /*# YOUR CODE HERE */
    private int numOfTreated = 0;
    private double averageWait = 0;
    private double totalWait = 0;
    
    private int priNumOfTreated = 0;
    private double priAverageWait = 0;
    private double priTotalWait = 0;

    // Fields for the simulation
    private boolean running = false;
    private int time = 0; // The simulated time - the current "tick"
    private int delay = 300;  // milliseconds of real time for each tick

    // fields controlling the probabilities.
    private int arrivalInterval = 5;   // new patient every 5 ticks, on average
    private double probPri1 = 0.1; // 10% priority 1 patients
    private double probPri2 = 0.2; // 20% priority 2 patients
    private Random random = new Random();  // The random number generator.

    /**
     * Construct a new HospitalERCompl object, setting up the GUI, and resetting
     */
    public static void main(String[] arguments){
        HospitalERCompl er = new HospitalERCompl();
        er.setupGUI();
        er.reset(false);   // initialise with an ordinary queue.
    }        

    /**
     * Set up the GUI: buttons to control simulation and sliders for setting parameters
     */
    public void setupGUI(){
        UI.addButton("Reset (Queue)", () -> {this.reset(false); });
        UI.addButton("Reset (Pri Queue)", () -> {this.reset(true);});
        UI.addButton("Start", ()->{if (!running){ run(); }});   //don't start if already running!
        UI.addButton("Pause & Report", ()->{running=false;});
        UI.addSlider("Speed", 1, 400, (401-delay),
            (double val)-> {delay = (int)(401-val);});
        UI.addSlider("Av arrival interval", 1, 50, arrivalInterval,
            (double val)-> {arrivalInterval = (int)val;});
        UI.addSlider("Prob of Pri 1", 1, 100, probPri1*100,
            (double val)-> {probPri1 = val/100;});
        UI.addSlider("Prob of Pri 2", 1, 100, probPri2*100,
            (double val)-> {probPri2 = Math.min(val/100,1-probPri1);});
        UI.addButton("Quit", UI::quit);
        UI.setWindowSize(1000,600);
        UI.setDivider(0.5);
    }

    /**
     * Reset the simulation:
     *  stop any running simulation,
     *  reset the waiting and treatment rooms
     *  reset the statistics.
     */
    public void reset(boolean usePriorityQueue){
        running=false;
        UI.sleep(2*delay);  // to make sure that any running simulation has stopped
        time = 0;           // set the "tick" to zero.

        // reset the waiting room, the treatment room, and the statistics.
        /*# YOUR CODE HERE */
        makeDepartments(usePriorityQueue);
        
        UI.clearGraphics();
        UI.clearText();
    }
    
    public void makeDepartments(boolean usePriorityQueue){
        //constructs the 5 departments
        department = new HashMap<String,Department>();
        
        department.put("ER",new Department("ER",7,usePriorityQueue));
        department.put("MRI",new Department("MRI",3,usePriorityQueue));
        department.put("Surgery",new Department("Surgery",3,usePriorityQueue));
        department.put("X-ray",new Department("X-ray",2,usePriorityQueue));
        department.put("Ultrasound",new Department("Ultrasound",1,usePriorityQueue));
    }
    
    /**
     * Main loop of the simulation
     */
    public void run(){
        //Currently treated patient lists.
        // used for statistic
        if (running) { return; } // don't start simulation if already running one!
        HashSet<Patient> discharge = new HashSet<Patient>();
        running = true;
        while (running){         // each time step, check whether the simulation should pause.
    
            // Hint: if you are stepping through a set, you can't remove
            //   items from the set inside the loop!
            //   If you need to remove items, you can add the items to a
            //   temporary list, and after the loop is done, remove all 
            //   the items on the temporary list from the set.
    
            /*# YOUR CODE HERE */
                
            // Goes bettween all existing patients and moves them or advances treatments accordingly
            // Dispatches patients and records there stats if completed treatments
            HashSet<Patient> toMove = new HashSet<Patient>();
            for (Department dep : department.values()) {
                HashSet<Patient> toRemove = new HashSet<Patient>();
                for (Patient patient : dep.getTreatmentRoom()) {
                    if (patient.noMoreTreatments()) {
                        discharge.add(patient);
                    }
                    else{
                        if (!patient.completedCurrentTreatment()) {
                            patient.advanceTreatmentByTick();
                        } 
                        else if(patient.completedCurrentTreatment()){
                            patient.incrementTreatmentNumber();
                            toMove.add(patient);
                            toRemove.add(patient);
                        }
                    }
                }
                HashSet<Patient> toTreat = new HashSet<Patient>();
                for (Patient patient : dep.getWaitingRoom()) {
                    if(dep.treatmentNotFull()){
                        toTreat.add(patient);
                        dep.getTreatmentRoom().add(patient);
                    }
                }
                dep.getWaitingRoom().removeAll(toTreat);
                dep.getTreatmentRoom().removeAll(toRemove);
            }
            //moves each person to the next waiting room after completing a treatment
            for(Patient patient: toMove){
                if (patient.noMoreTreatments()) {
                    discharge.add(patient);
                }
                else {department.get(patient.getCurrentTreatment()).addPatient(patient);}
            }
            
            // adds a tick to waiting time of all in waiting rooms
            for (Department dep: department.values()){
                for (Patient patient: dep.getWaitingRoom()){patient.waitForATick();}
            }
            
            //removes all the patients that have finished their treatment.
            for (Patient patient: discharge) {
                
                //Adds the total wait times together
                if (patient.getPriority() == 1){
                    priNumOfTreated ++;
                    priTotalWait += patient.getWaitingTime();
                }
                numOfTreated++;
                totalWait += patient.getWaitingTime();
                //For the departments.
                for (Department dep : department.values()) {
                    dep.dischargePatient(patient);
                }
            }
            
            // Get any new patient that has arrived and add them to the waiting room
            if (time==1 || Math.random()<1.0/arrivalInterval){
                Patient newPatient = new Patient(time, randomPriority());
                UI.println(time+ ": Arrived: "+newPatient);
                department.get(newPatient.getCurrentTreatment()).getWaitingRoom().offer(newPatient);
            }
            
            redraw();
            UI.sleep(delay);
        }
        // paused, so report current statistics
        reportStatistics();
    }


    // Additional methods used by run() (You can define more of your own)

    /**
     * Report summary statistics about all the patients that have been discharged.
     * (Doesn't include information about the patients currently waiting or being treated)
     * The run method should have been recording various statistics during the simulation.
     */
    public void reportStatistics(){
        /*# YOUR CODE HERE */
        averageWait = (totalWait / numOfTreated);
        priAverageWait = (priTotalWait / priNumOfTreated);
        
        UI.println("Hostpital Statistics:");
        
        UI.println("Number of treated patients:" + numOfTreated);
        UI.println("Average time spent in waiting room:" + String.format("%.2f", averageWait));
        
        UI.println("Number of treated priority 1 patients:" + priNumOfTreated);
        UI.println("Average time of priority 1 patients spent in waiting room:" + String.format("%.2f", priAverageWait));
    
        for (Department dep : department.values()){
            for(Patient p: dep.getTreatmentRoom()){
                UI.println(p.toString());
            }
        }
    }


    // HELPER METHODS FOR THE SIMULATION AND VISUALISATION
    /**
     * Redraws all the departments
     */
    public void redraw(){
        UI.clearGraphics();
        UI.setFontSize(14);
        UI.drawString("Treating Patients", 5, 15);
        UI.drawString("Waiting Queues", 200, 15);
        UI.drawLine(0,32,400, 32);

        // Draw the treatment rooms and the waiting rooms:
        int y = 100;
        for (Map.Entry<String, Department> dep : department.entrySet()){
            dep.getValue().redraw(y);
            y+=50;
        }
        
    }

    /** 
     * Returns a random priority 1 - 3
     * Probability of a priority 1 patient should be probPri1
     * Probability of a priority 2 patient should be probPri2
     * Probability of a priority 3 patient should be (1-probPri1-probPri2)
     */
    private int randomPriority(){
        double rnd = random.nextDouble();
        if (rnd < probPri1) {return 1;}
        if (rnd < (probPri1 + probPri2) ) {return 2;}
        return 3;
    }

}
