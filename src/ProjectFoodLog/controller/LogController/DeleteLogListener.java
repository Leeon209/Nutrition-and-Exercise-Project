package controller.LogController;

import model.HealthProgram;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;


public class DeleteLogListener implements ActionListener {
    private HealthProgram program;
    private int year;
    private int month;
    private int day;
    public DeleteLogListener(HealthProgram program, int year, int month, int day){
        this.program = program;
        this.year = year;
        this.month = month;
        this.day = day;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        program.deleteLog(year, month, day);
    }

}
