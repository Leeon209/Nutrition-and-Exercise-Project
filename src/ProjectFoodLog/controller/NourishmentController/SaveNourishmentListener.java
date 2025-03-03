package controller.NourishmentController;

import model.HealthProgram;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;

public class SaveNourishmentListener implements ActionListener {
    private HealthProgram program;


    public SaveNourishmentListener(HealthProgram program){
        this.program = program;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        program.saveNourishment();
    }

}
