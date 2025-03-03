package controller.ExerciseController;

import model.HealthProgram;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;


public class DeleteExerciseListener implements ActionListener {
    private HealthProgram program;
    private String name;
    

    public DeleteExerciseListener(HealthProgram program, String name){
        this.program = program;
        this.name = name;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        program.deleteExercise(name);
    }

}