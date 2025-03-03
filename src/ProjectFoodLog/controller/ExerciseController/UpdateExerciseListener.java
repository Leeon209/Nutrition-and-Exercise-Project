package controller.ExerciseController;

import model.HealthProgram;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;


public class UpdateExerciseListener implements ActionListener {
    private HealthProgram program;
    private String name;
    private double caloriesPerHour;
    

    public UpdateExerciseListener(HealthProgram program, String name, double caloriesPerHour){
        this.program = program;
        this.name = name;
        this.caloriesPerHour = caloriesPerHour;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        program.updateExercise(name, caloriesPerHour);
    }

}