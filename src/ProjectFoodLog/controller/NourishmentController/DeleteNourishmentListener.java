package controller.NourishmentController;

import model.HealthProgram;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;

public class DeleteNourishmentListener implements ActionListener {
    private HealthProgram program;
    private String foodName;


    public DeleteNourishmentListener(HealthProgram program, String foodName){
        this.program = program;
        this.foodName = foodName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        program.deleteNourishment(foodName);
    }

}
