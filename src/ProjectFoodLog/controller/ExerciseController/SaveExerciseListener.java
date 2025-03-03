package controller.ExerciseController;

import model.HealthProgram;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;


public class SaveExerciseListener implements ActionListener {
    private HealthProgram program;
    

    public SaveExerciseListener(HealthProgram program){
        this.program = program;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        program.saveExercises();
    }

}