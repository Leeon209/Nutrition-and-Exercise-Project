package controller.NourishmentController;

import model.HealthProgram;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.util.List;

public class UpdateRecipeListener implements ActionListener {
    private HealthProgram program;
    private String recipeName;
    private List<String> ingredients;
    private List<Float> quantities;

    public UpdateRecipeListener(HealthProgram program, String recipeName, List<String> ingredients, List<Float> quantities){
        this.program = program;
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.quantities = quantities;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        program.createRecipe(recipeName, ingredients, quantities);
    }

}
