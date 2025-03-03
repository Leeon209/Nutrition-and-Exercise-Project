package controller.LogController;

import model.HealthProgram;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateLogListener implements ActionListener {
    private HealthProgram program;
    private int year;
    private int month;
    private int day;
    private float weight;
    private float desiredCalories;
    private List<String> foodNames;
    private List<Float> quantities;
    private List<String> exercises;
    private List<Double> minutes;

    public CreateLogListener(HealthProgram program, int year, int month, int day, float weight, float desiredCalories,
            List<String> foodNames, List<Float> quantities, List<String> exercises, List<Double> minutes) {
        this.program = program;
        this.year = year;
        this.month = month;
        this.day = day;
        this.weight = weight;
        this.desiredCalories = desiredCalories;
        this.foodNames = foodNames;
        this.quantities = quantities;
        this.exercises = exercises;
        this.minutes = minutes;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        program.createLog(year, month, day, weight, desiredCalories, foodNames, quantities, exercises, minutes);
    }

}
