package controller.LogController;

import model.HealthProgram;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UpdateLogListener implements ActionListener {
    private HealthProgram program;
    private int year;
    private int month;
    private int day;
    private float weight;
    private float desiredCalories;
    private List<String> foodNames;
    private List<Float> quantities;
    private List<String> exerciseNames;
    private List<Double> minutes;

    public UpdateLogListener(HealthProgram program, int year, int month, int day, float weight, float desiredCalories,
            List<String> foodNames, List<Float> quantities, List<String> exerciseNames, List<Double> minutes) {
        this.program = program;
        this.year = year;
        this.month = month;
        this.day = day;
        this.weight = weight;
        this.desiredCalories = desiredCalories;
        this.foodNames = foodNames;
        this.quantities = quantities;
        this.exerciseNames = exerciseNames;
        this.minutes = minutes;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        program.updateLog(year, month, day, weight, desiredCalories, foodNames, quantities, exerciseNames, minutes);
    }

}
