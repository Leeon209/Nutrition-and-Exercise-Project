package controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.HealthProgram;
import model.iNourishment;

public class FoodAction {
    private HealthProgram program;

    public FoodAction(HealthProgram program){
        this.program = program;
    }

    public void createFood(String foodName, float calories, float fat, float carbs, float protein, float sodium){
        program.createFood(foodName, calories, fat, carbs, protein, sodium);
    }
    public void updateFood(String newFoodName, float calories, float fat, float carbs, float protein, float sodium){
        program.updateFood(newFoodName, calories, fat, carbs, protein, sodium);
    }

    /**
     * Retrieves the nutritional data of specified food
     * Takes a food name and gets corresponding f,c,p values
     * and returns a map containing the nutritional information such as fat, carbs, and protein.
     * 
     * @param foodName Name of the food item to view.
     * @return A map containing the nutritional data of the food item fat, protein, and carbs keys (will return null if doesn't exist)
     *        
     */
    public Map<String, Integer> viewFood(String foodName) {
        iNourishment nourishment = program.getNourishment(foodName);
        if (nourishment != null) {
            Map<String, Integer> nutritionData = new HashMap<>();
            nutritionData.put("fat", (int) nourishment.getFat());
            nutritionData.put("carbs", (int) nourishment.getCarbs());
            nutritionData.put("protein", (int) nourishment.getProtein());
            return nutritionData;
        }
        return null;
    }

    public void createRecipe(String recipeName, List<String> ingredients, List<Float> quantities){
        program.createRecipe(recipeName, ingredients, quantities);
    }
    public void updateRecipe(String recipeName, List<String> ingredients, List<Float> quantities){
        program.updateRecipe(recipeName, ingredients, quantities);
    }
    public void deleteNourishmentItem(String name){
        program.deleteNourishment(name);
    }

    public void saveNourishment(){
        program.saveNourishment();
    }
    
}
