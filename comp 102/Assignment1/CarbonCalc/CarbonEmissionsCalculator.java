// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2021T1, Assignment 1
 * Name: Elisha Jones
 * Username: Joneselis1
 * ID: 300573902
 */

import ecs100.*;

/** Program for calculating carbon emissions */

public class CarbonEmissionsCalculator{

    public static final double EMISSION_FACTOR_POWER = 0.0977;   // emissions factor of electricity per kWh
    public static final double EMISSION_FACTOR_WASTE = 0.299;    // emissions factor of food waste per kg

    public static final double ANNUAL_AVERAGE_POWER_EMISSION_NZ = 266.5;   //Annual average carbon emissions in NZ (kg) from electricity use
    public static final double ANNUAL_AVERAGE_FOOD_WASTE_EMISSION_NZ = 18.0; //Annual average carbon emissions in NZ (kg) from food waste

    /**
     * Calculates and prints carbon emissions
     */
    public void calculateEmissions(){
        /** asks user for inputs and sets as variables */
        double QFoodWaste = UI.askDouble("How much food did you waste this week in kgs?");
        double QElectricWaste = UI.askDouble("How much electricity did you use this month in kwh?");
        double QHouseSize = UI.askDouble("How many people are within your household?");
        /** calculates user inputs with a constant */
        /* User Waste */
        /* Rounding code found from https://stackoverflow.com/questions/11701399/round-up-to-2-decimal-places-in-java */
        double TPowerWaste = Math.round(QElectricWaste * EMISSION_FACTOR_POWER * 100.0) / 100.0;
        double TFoodWaste = Math.round(QFoodWaste * EMISSION_FACTOR_WASTE * 100.0) / 100.0;
        /* National waste */
        double CFoodWaste = Math.round(ANNUAL_AVERAGE_FOOD_WASTE_EMISSION_NZ / 51.1429 * EMISSION_FACTOR_POWER * 100.0) / 100.0;
        double CPowerWaste = Math.round(ANNUAL_AVERAGE_POWER_EMISSION_NZ / 12.0 * EMISSION_FACTOR_WASTE * 100.0) / 100.0;
        double CTotalWaste = Math.round(((ANNUAL_AVERAGE_FOOD_WASTE_EMISSION_NZ  * EMISSION_FACTOR_POWER) + (ANNUAL_AVERAGE_POWER_EMISSION_NZ * EMISSION_FACTOR_WASTE)) * 100.0) / 100.0;
        
        /* Calculates average amount of emissions per day */
        double AverageWastePD = Math.round(((TPowerWaste / 30.0) + (TFoodWaste / 7.0)) * 100.0) / 100.0;
        
        /* Percentage against */
        double PercentFood = Math.round((TFoodWaste / CFoodWaste) * 10000.0) / 100.0;
        double PercentPower = Math.round((TPowerWaste / CPowerWaste) * 10000.0) / 100.0;
        double PercentAverage = Math.round((AverageWastePD / (CTotalWaste / 7)) * 10000.0) / 100.0;
        
        /* Displays the amount of emissions after calculations applied */
        UI.println("Your CO2 emissions caused by Waste was " + TFoodWaste + "KG CO2-e for this week. Thats " + PercentFood + "% of the New Zealand average.");
        UI.println("Your CO2 emissions caused by Electricity was " + TPowerWaste + "KG CO2-e for this month. Thats " + PercentPower + "% of the New Zealand average.");
        UI.println("Your average daily CO2 emissions is " + AverageWastePD + "KG CO2-e");
    
    }

    public void setupGUI(){
        UI.initialise();
        UI.addButton("Calculate Emissions", this::calculateEmissions);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1);    // Expand the Text pane
    }

    public static void main(String[] args){
        CarbonEmissionsCalculator cec = new CarbonEmissionsCalculator();
        cec.setupGUI();
    }

}
