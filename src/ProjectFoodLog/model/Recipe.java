package model;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Recipe implements iNourishment {
    private String name;
    private Map<iNourishment, Float> ingredients;
    float totalFat = 0;
    float totalCarbs = 0;
    float totalProtein = 0;
    float totalSodium = 0;


    public Recipe(String name) {
        this.name = name;
        this.ingredients = new LinkedHashMap<>();
    }

    public void addIngredient(iNourishment ingredient, float quantity) {
        ingredients.put(ingredient, quantity);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getCalories() {
        float totalCalories = 0;
        for (Map.Entry<iNourishment,Float> entry : ingredients.entrySet()){
            Float ingredientCalories = entry.getKey().getCalories();
            Float quantity = entry.getValue();
            
            totalCalories += ingredientCalories*quantity;
        }
        
        return totalCalories;
    }

    @Override
    public float getFat() {
        float totalFat = 0;
        for (Map.Entry<iNourishment,Float> entry : ingredients.entrySet()){
            Float ingredientCalories = entry.getKey().getFat();
            Float quantity = entry.getValue();
            
            totalFat += ingredientCalories*quantity;
        }
        
        return totalFat;
    }

    @Override
    public float getCarbs() {
        float totalCarbs = 0;
        for (Map.Entry<iNourishment,Float> entry : ingredients.entrySet()){
            Float ingredientCalories = entry.getKey().getCarbs();
            Float quantity = entry.getValue();
            
            totalCarbs += ingredientCalories*quantity;
        }
        
        return totalCarbs;
    }

    @Override
    public float getProtein() {
        float totalProtein = 0;
        for (Map.Entry<iNourishment,Float> entry : ingredients.entrySet()){
            Float ingredientCalories = entry.getKey().getProtein();
            Float quantity = entry.getValue();
            
            totalProtein += ingredientCalories*quantity;
        }
        
        return totalProtein;
    }

    @Override
    public float getSodium() {
        float totalSodium = 0;
        for (Map.Entry<iNourishment,Float> entry : ingredients.entrySet()){
            Float ingredientCalories = entry.getKey().getSodium();
            Float quantity = entry.getValue();
            
            totalSodium += ingredientCalories*quantity;
        }
        
        return totalSodium;
    }

    public Set<iNourishment> getIngredients(){
        return ingredients.keySet();
    }

    public String toFormat(){
        String format = String.format("r,%s", this.name);
        for (Map.Entry<iNourishment,Float> entry : ingredients.entrySet()){
            String ingredientName = entry.getKey().getName();
            String quantity = entry.getValue().toString();
            
            format += String.format(",%s,%s", ingredientName, quantity);
        }
        return format;
    }

    @Override
    public String toString() {
        return String.format("Name: %s \n Ingredients: %s \n\n", name, ingredients);
    }

}
