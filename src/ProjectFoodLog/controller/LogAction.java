package controller;

import java.util.List;

import model.HealthProgram;

public class LogAction {
    private HealthProgram program;

    public LogAction(HealthProgram program) {
        this.program = program;
    }

    /*
     * public void createLog(int year, int month, int day, float weight, float
     * desiredCalories, List<String> foodNames, List<Float> quantities){
     * program.createLog(year, month, day, weight, desiredCalories, foodNames,
     * quantities);
     * }
     */
    public void updateLog(int year, int month, int day, float weight, float desiredCalories, List<String> foodNames,
            List<Float> quantities, List<String> exerciseNames, List<Double> minutes) {
        program.updateLog(year, month, day, weight, desiredCalories, foodNames, quantities, exerciseNames, minutes);
    }

    public void viewLog(int year, int month, int day) {
        program.getLog(year, month, day);
    }

    public void deleteLog(int year, int month, int day) {
        program.deleteLog(year, month, day);
    }

    public void saveLogs() {
        program.saveLogs();
    }

}