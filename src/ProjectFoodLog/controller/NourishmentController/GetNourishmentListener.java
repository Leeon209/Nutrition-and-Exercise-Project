package controller.NourishmentController;

import model.HealthProgram;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;

public class GetNourishmentListener implements ActionListener {
    private HealthProgram program;
    private String foodName;


    public GetNourishmentListener(HealthProgram program, String foodName){
        this.program = program;
        this.foodName = foodName;
        System.out.println("Nourishment Listener created");
        System.out.println("Text: " + foodName);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        program.getNourishment(foodName);
    }

}
