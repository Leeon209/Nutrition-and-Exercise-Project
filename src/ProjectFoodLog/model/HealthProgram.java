package model;

import java.util.Observable;
import java.util.List;

@SuppressWarnings("deprecation")
public class HealthProgram {
    // protected UserData userData;
    protected ExerciseManager exerciseManager;
    protected LogManager logManager;
    protected NourishmentManager nourishmentManager;

    public HealthProgram() {
        super();
        // userData = new UserData();
        // userData.loadNourishment("src/ProjectFoodLog/csv/foods.csv");
        // userData.loadLogs("src/ProjectFoodLog/csv/log.csv");
        nourishmentManager = new NourishmentManager();
        nourishmentManager.loadNourishment("src/ProjectFoodLog/csv/foods.csv");
        exerciseManager = new ExerciseManager();
        exerciseManager.loadExercise("src/ProjectFoodLog/csv/exercise.csv");
        logManager = new LogManager("src/ProjectFoodLog/csv/log.csv", nourishmentManager, exerciseManager);
        logManager.loadLogs();
        // userData.saveLogs("src/ProjectFoodLog/csv/log.csv");
    }

    /**
     * Gets a log given the index
     * 
     * @param Date
     * @return Log
     */
    public Log getLog(int year, int month, int day) {
        // Log log = userData.getLog(year, month, day);
        Log log = logManager.getLog(year, month, day);
        return log;
    }

    /**
     * Creates a log with given information.
     * 
     * @param Date
     * @return Log
     */
    public void createLog(int year, int month, int day, float weight, float desiredCalories, List<String> foodNames,
            List<Float> quantity, List<String> exercises, List<Double> minutes) {
        // Log log = userData.createLog(year, month, day);
        Log log = logManager.createLog(year, month, day);
        log.setDesiredCalories(desiredCalories);
        log.setWeight(weight);
        for (int i = 0; i < foodNames.size(); i++) {
            // iNourishment food = userData.getNourishment(foodNames.get(i).toUpperCase());
            iNourishment food = nourishmentManager.getNourishment(foodNames.get(i).toUpperCase());
            log.addNourishment(food, quantity.get(i));
        }

        for (int i = 0; i < exercises.size(); i++) {
            Exercise exercise = exerciseManager.getExercise(exercises.get(i));
            log.addExercise(exercise.getName(), quantity.get(i));
        }
    }

    /**
     * Sets a log given the index and fields to set.
     * 
     * @param Date
     * @return Log
     */
    public void updateLog(int year, int month, int day, float weight, float desiredCalories, List<String> foodNames,
            List<Float> quantities, List<String> exerciseNames, List<Double> minutes) {
        // Log log = userData.updateLog(year, month, day, weight, desiredCalories,
        // foodNames, quantities);
        Log log = logManager.updateLog(year, month, day, weight, desiredCalories, foodNames, quantities, exerciseNames,
                minutes);
    }

    /**
     * Deletes a log given it's index.
     * 
     * @param Date
     * @return Log
     */
    public void deleteLog(int year, int month, int day) {
        // userData.deleteLog(year, month, day);
        logManager.deleteLog(year, month, day);
    }

    /**
     * Gets a Nourishment item given the name
     * 
     * @param name A string name of the nourishment item
     * @return iNourishment item
     */
    public iNourishment getNourishment(String foodName) {
        // iNourishment nourishment = userData.getNourishment(foodName);
        iNourishment nourishment = nourishmentManager.getNourishment(foodName);
        return nourishment;
    }

    /**
     * Creates a Nourishment item given the name
     * 
     * @param name A string name of the nourishment item
     * @return iNourishment item
     */
    public void createFood(String foodName, float calories, float fat, float carbs, float protein, float sodium) {
        iNourishment nourishment = new Food(foodName, calories, fat, carbs, protein, sodium);
        // userData.addNourishment(nourishment);
        nourishmentManager.addNourishment(nourishment);
    }

    /**
     * Sets a nourishment item given the name
     * 
     * @param name A string name of the nourishment item
     * @return iNourishment item
     */
    public void updateFood(String foodName, float calories, float fat, float carbs, float protein, float sodium) {
        iNourishment newfood = new Food(foodName, calories, fat, carbs, protein, sodium);
        // iNourishment nourishment = userData.updateNourishment(foodName, newfood);
        iNourishment nourishment = nourishmentManager.updateNourishment(foodName, newfood);
    }

    /**
     * Creates a Nourishment item given the name
     * 
     * @param name A string name of the nourishment item
     * @return iNourishment item
     */
    public iNourishment createRecipe(String foodName, List<String> ingredients, List<Float> quantities) {
        Recipe nourishment = new Recipe(foodName);
        for (int i = 0; i < ingredients.size(); i++) {
            // iNourishment ingredient = userData.getNourishment(ingredients.get(i));
            iNourishment ingredient = nourishmentManager.getNourishment(ingredients.get(i));
            nourishment.addIngredient(ingredient, quantities.get(i));
        }
        // userData.addNourishment(nourishment);
        nourishmentManager.addNourishment(nourishment);
        return nourishment;
    }

    /**
     * Sets a nourishment item given the name
     * 
     * @param name A string name of the nourishment item
     * @return iNourishment item
     */
    public void updateRecipe(String foodName, List<String> ingredients, List<Float> quantities) {
        // userData.deleteNourishment(foodName);
        nourishmentManager.deleteNourishment(foodName);
        iNourishment nourishment = createRecipe(foodName, ingredients, quantities);
        // userData.addNourishment(nourishment);
        nourishmentManager.addNourishment(nourishment);
    }

    /**
     * Deletes a nourishment item given the name
     * 
     * @param name A string name of the nourishment item
     * @return void
     */
    public void deleteNourishment(String foodName) {
        // userData.deleteNourishment(foodName);
        nourishmentManager.deleteNourishment(foodName);
    }

    /**
     * Creates an exercise and then adds it to the Exercise manager's collection
     * 
     * @param name            A string name of the Exercise
     * @param caloriesPerHour A double number of the calories burned per hour
     * @return void
     */
    public void createExercise(String name, double caloriesPerHour) {
        exerciseManager.createExercise(name, caloriesPerHour);
    }

    /**
     * Deletes an exercise and removes it from the Exercise manager's collection
     * 
     * @param name A string name of the Exercise
     * @return void
     */
    public void deleteExercise(String name) {
        exerciseManager.deleteExercise(name);
    }

    /**
     * Returns an exercise
     * 
     * @param name            A string name of the Exercise
     * @param caloriesPerHour A double number of the calories burned per hour
     * @return Exercise
     */
    public Exercise getExercise(String name) {
        return exerciseManager.getExercise(name);
    }

    /**
     * Updates an exercise and then adds it to the Exercise manager's collection
     * 
     * @param name            A string name of the Exercise
     * @param caloriesPerHour A double number of the calories burned per hour
     * @return void
     */
    public void updateExercise(String name, double caloriesPerHour) {
        exerciseManager.updateExercise(name, caloriesPerHour);
    }

    public void saveExercises() {
        System.out.println("program save");
        exerciseManager.saveExercise("src/ProjectFoodLog/csv/exercise.csv");
    }

    public void saveNourishment() {
        // userData.saveNourishment("src/ProjectFoodLog/csv/foods.csv");
        nourishmentManager.saveNourishment("src/ProjectFoodLog/csv/foods.csv");
    }

    public void saveLogs() {
        // userData.saveLogs("src/ProjectFoodLog/csv/log.csv");
        logManager.saveLogs();
    }

    public LogManager getLogManager(){
        return logManager;
    }
    public NourishmentManager getNourishmentManager(){
        return nourishmentManager;
    }
    public ExerciseManager getExerciseManager(){
        return exerciseManager;
    }

}