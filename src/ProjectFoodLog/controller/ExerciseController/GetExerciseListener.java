package controller.ExerciseController;

import model.HealthProgram;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;


public class GetExerciseListener implements ActionListener {
    private HealthProgram program;
    private String name;
    

    public GetExerciseListener(HealthProgram program, String name){
        this.program = program;
        this.name = name;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        program.getExercise(name);
    }

}