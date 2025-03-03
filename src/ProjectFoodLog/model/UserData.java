package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserData {
    // This collection is a map with the key of a food name and the value of an
    // array of all food items that make up the nourishment
    // This allows for all Nourishment items (foods and recipes) to be saved in one
    // collection with references to each single food object.
    // Ex. Recipe: Key: CheeseBurger / Value: [Bun, Cheese, Burger, Lettuce]
    // Ex. Food: Key: Cheese / Value: [Cheese]
    private Map<String, iNourishment> nourishmentCollection = new LinkedHashMap<>();
    private Map<String, Log> logCollection = new LinkedHashMap<>();
    private Map<String, Exercise> exerciseCollection = new LinkedHashMap<>();

    // UserData is a singleton and only one should exist in any given moment
    // All data with the exception of nourishmentCollection and logCollection should be stored in each daily log object
    public UserData() {

    }

    /**
     * This method pulls the Nourishment object from the nourishmentCollection map
     * @param name : The name of the Nourishment object
     * @return iNourishment object requested
     */
    public iNourishment getNourishment(String name) {
        System.out.println("[SUCCESS] Retrieving food! \n\n");
        iNourishment nourishment = nourishmentCollection.get(name.toUpperCase());
        if (nourishment == null){
            System.out.println("[ERROR] Food not found! \n\n");
            return null;
        }
        return nourishment;
    }

    /**
     * This method should remove the Nourishment object being updated from the nourishmentCollection and 
     * replace it with a new Nourishment object
     * @param name : The name of the Nourishment item to update
     * @param newNourishment : The new Nourishment item to replace it
     */
    public iNourishment updateNourishment(String name, iNourishment newNourishment) {

        if (nourishmentCollection.get(name.toUpperCase()) != null){
            nourishmentCollection.remove(name.toUpperCase());
            nourishmentCollection.put(newNourishment.getName().toUpperCase(), newNourishment);

            System.out.println("[SUCCESS] Food updated! \n\n");

            return newNourishment;

        } else {
            System.out.println("[ERROR] Food not found! \n\n");
            return null;
        }

    }

    /**
     * This method adds a new Nourishment object to 'nourishmentCollection'
     * @param nourishmentItem : The nourishment object to be added.
     */
    public void addNourishment(iNourishment nourishmentItem) {
        iNourishment existingFood = nourishmentCollection.get(nourishmentItem.getName().toUpperCase());
        if (existingFood != null){
            System.out.println("[ERROR] Food already exists! \n\n");
        }

        System.out.println("[SUCCESS] Food added! \n\n");
        nourishmentCollection.put(nourishmentItem.getName().toUpperCase(), nourishmentItem);
    }

    /**
     * This method removes a Nourishment object from the foodCollection
     * @param name : The name of the Nourishment object to be removed
     */
    public void deleteNourishment(String name) {
        System.out.println("[SUCCESS] Food deleted! \n\n");
        nourishmentCollection.remove(name.toUpperCase());
    }

    /**
     * Takes in the date parameters and retrieves the log object
     * @param year - int 
     * @param month - int 
     * @param day - int
     * @return Log
     */
    public Log getLog(int year, int month, int day) {
        Log log = logCollection.get(String.format("%d-%d-%d", year, month, day));
        if (log != null){
            System.out.println("[SUCCESS] Log retrieved!");
            return log;
        } 

        System.out.println("[ERROR] Log does not exist!");
        return null;
    }

    public void getLogs(){
        for (Map.Entry<String,Log> log : logCollection.entrySet()) {
            System.out.println(log.getValue());
        }
    }

    /**
     * Creates a new log object and adds it to the 'logCollection' collection
     * [Overloaded Method for non-food construction]
     * @param year
     * @param month
     * @param day
     * @return
     */
    public Log createLog(int year, int month, int day) {
        String logID = String.format("%d-%d-%d", year, month, day);

        Log existingLog = logCollection.get(logID);
        if (existingLog != null){
            return existingLog;
        }

        Log newLog = new Log(year, month, day);
        logCollection.put(logID, newLog);

        return newLog;
    }

    /**
     * Updates a log by replacing it with a new log
     * @param year
     * @param month
     * @param day
     * @param weight
     * @param desiredCalories
     * @param foodNames
     */
    public Log updateLog(int year, int month, int day, float weight, float desiredCalories, List<String> foodNames, List<Float> quantities) {
        String logID = String.format("%d-%d-%d", year, month, day);

        Log existingLog = logCollection.get(logID);
        if (existingLog != null){
            Log newLog = createLog(year, month, day);
            newLog.setWeight(weight);
            newLog.setDesiredCalories(desiredCalories);

            for (int i=0; i<foodNames.size(); i++){
                iNourishment food = getNourishment(foodNames.get(i).toUpperCase());
                newLog.addNourishment(food, quantities.get(i));
            }

            logCollection.remove(logID);
            logCollection.put(logID, newLog);
            return newLog;

        } else {
            return existingLog;
        }

    }

    /**
     * Deletes a log given the date
     * @param year
     * @param month
     * @param day
     */
    public void deleteLog(int year, int month, int day){
        String logID = String.format("%d-%d-%d", year, month, day);

        Log existingLog = logCollection.get(logID);
        if (existingLog != null){
            logCollection.remove(logID);
        }
    }

    public void addExercise(Exercise exercise) {
        Exercise existingExercise = exerciseCollection.get(exercise.getName().toUpperCase());
        if (existingExercise != null) {
            System.out.println("[ERROR] Exercise already exists! \n\n");
        } else {
            exerciseCollection.put(exercise.getName().toUpperCase(), exercise);
            System.out.println("[SUCCESS] Exercise added! \n\n");
        }
    }

    public Exercise getExercise(String name) {
        Exercise exercise = exerciseCollection.get(name.toUpperCase());
        if (exercise == null) {
            System.out.println("[ERROR] Exercise not found! \n\n");
        }
        return exercise;
    }

    public void updateExercise(String name, float caloriesPerHour) {
        Exercise exercise = exerciseCollection.get(name.toUpperCase());
        if (exercise != null) {
            exercise.setCaloriesPerHour(caloriesPerHour);
            System.out.println("[SUCCESS] Exercise updated! \n\n");
        } else {
            System.out.println("[ERROR] Exercise not found! \n\n");
        }
    }

    public void deleteExercise(String name) {
        Exercise exercise = exerciseCollection.remove(name.toUpperCase());
        if (exercise != null) {
            System.out.println("[SUCCESS] Exercise deleted! \n\n");
        } else {
            System.out.println("[ERROR] Exercise not found! \n\n");
        }
    }

    public Map<String, iNourishment> getNourishmentCollection(){
        return nourishmentCollection;
    }
    public Map<String, Exercise> getExerciseCollection() {
        return exerciseCollection;
    }

    /**
     * Parses the given CSV files and constructs the nourishment collection
     * @param path
     */
    public void loadNourishment(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] tokens = line.split(",");

                //BASIC FOOD
                if (tokens[0].equals("b")){
                    Food basicFood = new Food(tokens[1].toUpperCase(), Float.parseFloat(tokens[2]), 
                    Float.parseFloat(tokens[3]), Float.parseFloat(tokens[4]), Float.parseFloat(tokens[5]), Float.parseFloat(tokens[6]));
                    nourishmentCollection.put(tokens[1].toUpperCase(), basicFood);
                // RECIPE 
                } else if (tokens[0].equals("r")){
                    Recipe newRecipe = new Recipe(tokens[1].toUpperCase());

                    for (int i=2; i<tokens.length; i+=2){
                        iNourishment food = nourishmentCollection.get(tokens[i].toUpperCase());
                        Float quantity = Float.parseFloat(tokens[i+1]);
                        newRecipe.addIngredient(food, quantity);
                    }
                    nourishmentCollection.put(tokens[1].toUpperCase(), newRecipe);
                }

             }
             getNourishmentCollection();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveNourishment(String path) {
        try {
       FileWriter fileWriter = new FileWriter(path);
       BufferedWriter writer = new BufferedWriter(fileWriter);
       for (String key : nourishmentCollection.keySet()) {
        iNourishment nourishment = nourishmentCollection.get(key);
        if(nourishment instanceof Food) {
            Food food = (Food) nourishment;
            writer.write(food.toFormat());
        }
        else if (nourishment instanceof Recipe) {
            Recipe recipe = (Recipe) nourishment;
            writer.write(recipe.toFormat());
        }
        writer.newLine();
       }
       writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses the given CSV files and constructs the log collection
     * @param path
     */
    public void loadLogs(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] tokens = line.split(",");

                int year = Integer.parseInt(tokens[0]);
                int month = Integer.parseInt(tokens[1]);
                int day = Integer.parseInt(tokens[2]);


                // If a log with the date exists, 
                Log existingLog = createLog(year, month, day);

                if (existingLog != null){

                    if (tokens[3].equals("w")){
                        existingLog.setWeight(Float.parseFloat(tokens[4]));

                    } else if (tokens[3].equals("c")){
                        existingLog.setDesiredCalories(Float.parseFloat(tokens[4]));

                    } else if (tokens[3].equals("f")){
                        existingLog.addNourishment(nourishmentCollection.get(tokens[4].toUpperCase()), Float.parseFloat(tokens[5]));
                    } else if (tokens[3].equals("e")){
                        Exercise exercise = exerciseCollection.get(tokens[4].toUpperCase());
                        float minutes = Float.parseFloat(tokens[5]);
                        if (exercise != null) {
                            existingLog.addExercise(exercise.getName(), minutes);
                        }
                    }

                } else {
                    Log newLog = createLog(year, month, day);
                    logCollection.put(String.format("%d-%d-%d", year, month, day), newLog);
                }

             }


            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save logs into csv file
    public void saveLogs(String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (Log log: logCollection.values()) {
                System.out.println(logCollection);
                writer.write(log.toFormat());
                writer.newLine();
            }
           writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses the given CSV files and constructs the exercise collection
     * @param path
     */
    // Load exercise data from csv
    public void loadExercise(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] tokens = line.split(",");
                String name = tokens[1].toString();
                float caloriesPerHour = Float.parseFloat(tokens[2]);
                Exercise exercise = new Exercise(name, caloriesPerHour);
                exerciseCollection.put(name, exercise);
            }
            getExerciseCollection();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Save exercise data into exercise.csv
    public void saveExercise(String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (String key : exerciseCollection.keySet()) {
                Exercise exercise = exerciseCollection.get(key);
                if (exercise instanceof Exercise) {
                    Exercise typeExercise = (Exercise) exercise;
                    writer.write(typeExercise.toFormat());
                }
                writer.newLine();
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
