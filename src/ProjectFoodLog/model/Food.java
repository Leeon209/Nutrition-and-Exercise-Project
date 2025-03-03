package model;

public class Food implements iNourishment {
    private String name;
    private float calories;
    private float fat;
    private float carbs;
    private float protein;
    private float sodium;

    public Food(String name, float calories, float fat, float carbs, float protein, float sodium) {
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
        this.sodium = sodium;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getCalories() {
        return calories;
    }

    @Override
    public float getFat() {
        return fat;
    }

    @Override
    public float getCarbs() {
        return carbs;
    }

    @Override
    public float getProtein() {
        return protein;
    }

    @Override
    public float getSodium() {
        return sodium;
    }

    public String toFormat(){
        return String.format("b,%s,%.2f,%.2f,%.2f,%.2f,%.2f", name, calories, fat, carbs, protein, sodium);
    }

    @Override
    public String toString() {
        return String.format("Name: %s \n Calories: %.2f | Fat: %.2f grams| Carbohydrates: %.2f grams| Protein: %.2f grams| Sodium: %.2f grams", name, calories, fat, carbs, protein, sodium);
    }
}
