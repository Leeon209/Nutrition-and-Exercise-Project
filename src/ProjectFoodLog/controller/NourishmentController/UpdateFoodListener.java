package controller.NourishmentController;

import model.HealthProgram;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;

public class UpdateFoodListener implements ActionListener {
    private HealthProgram program;
    private String foodName;
    private float calories;
    private float fat;
    private float carbs;
    private float protein;
    private float sodium;

    public UpdateFoodListener(HealthProgram program, String foodName, float calories, float fat, float carbs, float protein, float sodium){
        this.program = program;
        this.foodName = foodName;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
        this.sodium = sodium;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        program.createFood(foodName, calories, fat, carbs, protein, sodium);
    }

}
