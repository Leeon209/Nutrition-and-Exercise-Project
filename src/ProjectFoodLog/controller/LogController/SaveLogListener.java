package controller.LogController;

import model.HealthProgram;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;


public class SaveLogListener implements ActionListener {
    private HealthProgram program;
    

    public SaveLogListener(HealthProgram program){
        this.program = program;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        program.saveLogs();
    }

}
