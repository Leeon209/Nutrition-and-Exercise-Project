package model;

public class Exercise implements iExercise {
    private String name;
    private double caloriesPerHour;

    public Exercise(String name, double caloriesPerHour) {
        this.name = name;
        this.caloriesPerHour = caloriesPerHour;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getCaloriesPerHour() {
        return caloriesPerHour;
    }

    public void setCaloriesPerHour(double caloriesPerHour) {
        this.caloriesPerHour = caloriesPerHour;
    }

    // calculates calories burned through weight and minutes of exercise
    public double calculateCaloriesBurned(double weight, double minutes) {
        double calories = caloriesPerHour * (weight / 100.0) * (minutes / 60.0);
        return Math.round(calories * 100.0) / 100.0; // rounded result
    }

    // Leeon adding this for UserData.java
    public String toFormat() {
        return String.format("e,%s,%.2f", name, caloriesPerHour);
    }

    public String toString() {
        return name + " burns " + caloriesPerHour + " calories per hour";
    }
}