package model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Log {
    int year;
    int month;
    int day;
    float weight = 150;
    float desiredCalories = 2000;
    Map<iNourishment, Float> foodItems = new LinkedHashMap<>();
    Map<String, Float> exerciseItems = new LinkedHashMap<>();

    /**
     * 
     * @param year
     * @param month
     * @param day
     * @param weight
     * @param calories
     *                 Basic log constructor, for if no Nourishment items are being
     *                 immediately added
     */

    public Log(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setDesiredCalories(float desiredCalories) {
        this.desiredCalories = desiredCalories;
    }

    public void addNourishment(iNourishment nourishment, Float quantitiy) {
        foodItems.put(nourishment, quantitiy);
    }

    public void addExercise(String exerciseName, double minutes) {
        exerciseItems.put(exerciseName, (float) minutes);
    }

    public Map<String, Float> getExerciseItems() {
        return exerciseItems;
    }

    public Map<iNourishment, Float> getFoodItems() {
        return foodItems;
    }

    public float getWeight() {
        return weight;
    }

    public float getDesiredCalories() {
        return desiredCalories;
    }

    public int getTotalFat() {
        int totalFat = 0;
        for (Map.Entry<iNourishment, Float> entry : foodItems.entrySet()) {
            iNourishment nourishment = entry.getKey();
            if (nourishment != null) {
                totalFat += nourishment.getFat() * entry.getValue();
            }
        }
        return totalFat;
    }

    public int getTotalCarbs() {
        int totalCarbs = 0;
        for (Map.Entry<iNourishment, Float> entry : foodItems.entrySet()) {
            iNourishment nourishment = entry.getKey();
            if (nourishment != null) {
                totalCarbs += nourishment.getCarbs() * entry.getValue();
            }
        }
        return totalCarbs;
    }

    public int getTotalProtein() {
        int totalProtein = 0;
        for (Map.Entry<iNourishment, Float> entry : foodItems.entrySet()) {
            iNourishment nourishment = entry.getKey();
            if (nourishment != null) {
                totalProtein += nourishment.getProtein() * entry.getValue();
            }
        }
        return totalProtein;
    }

    public int getTotalSodium() {
        int totalSodium = 0;
        for (Map.Entry<iNourishment, Float> entry : foodItems.entrySet()) {
            iNourishment nourishment = entry.getKey();
            if (nourishment != null) {
                totalSodium += nourishment.getSodium() * entry.getValue();
            }
        }
        return totalSodium;
    }

    public int getTotalCalories() {
        int totalCalories = 0;
        for (Map.Entry<iNourishment, Float> entry : foodItems.entrySet()) {
            iNourishment nourishment = entry.getKey();
            if (nourishment != null) {
                totalCalories += nourishment.getCalories() * entry.getValue();
            }
        }
        return totalCalories;
    }

    public String toFormat() {
        String format = String.format("%d,%d,%d,w,%.1f,", year, month, day, weight);
        format += String.format("\n%d,%d,%d,c,%.0f,", year, month, day, desiredCalories);

        for (Map.Entry<iNourishment, Float> entry : foodItems.entrySet()) {
            iNourishment nourishment = entry.getKey();
            if (nourishment == null) {
                break;
            }
            String logName = entry.getKey().getName();
            String quantity = entry.getValue().toString();

            format += String.format("\n%d,%d,%d,f,%s,%s", year, month, day, logName, quantity);
        }

        for (Map.Entry<String, Float> entry : exerciseItems.entrySet()) {
            String exerciseName = entry.getKey();
            String minutes = entry.getValue().toString();

            format += String.format("\n%d,%d,%d,e,%s,%s", year, month, day, exerciseName, minutes);
        }

        return format;
    }

    @Override
    public String toString() {
        String food = "";
        for (iNourishment iNourishment : foodItems.keySet()) {
            if (iNourishment != null) {
                food += " " + iNourishment.getName() + " ";
            }
        }

        String exercises = "";
        for (String exercise : exerciseItems.keySet()) {
            exercises += " " + exercise + " ";
        }

        return String.format("%d-%d-%d | Weight: %.2f | Desired Calories: %.2f | Food Items: %s | Exercises: %s", year,
                month, day, weight, desiredCalories, food, exercises);
    }
}
