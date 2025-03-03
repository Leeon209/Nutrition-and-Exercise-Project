package controller.LogController;

import model.HealthProgram;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;


public class GetLogListener implements ActionListener {
    private HealthProgram program;
    private int year;
    private int month;
    private int day;

    public GetLogListener(HealthProgram program, int year, int month, int day){
        this.program = program;
        this.year = year;
        this.month = month;
        this.day = day;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        program.getLog(year, month, day);
    }

}
