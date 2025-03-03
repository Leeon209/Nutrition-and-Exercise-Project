package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class LogManager extends Observable{
    private String logFilePath; // New instance variable to store the file path
    private Map<String, Log> logCollection = new LinkedHashMap<>();
    private NourishmentManager nourishmentManager;
    private ExerciseManager exerciseManager;

    /**
     * Takes in the date parameters and retrieves the log object
     * 
     * @param year  - int
     * @param month - int
     * @param day   - int
     * @return Log
     */

    // Constructor with file path, nourishmentManager, and exerciseManager
    public LogManager(String logFilePath, NourishmentManager nourishmentManager, ExerciseManager exerciseManager) {
        this.logFilePath = logFilePath;
        this.nourishmentManager = nourishmentManager;
        this.exerciseManager = exerciseManager;
    }

    // Existing no-arg constructor (optional, in case you still need it)
    public LogManager() {
    }

    /**
     * Receiving log from collection
     * 
     * @param year
     * @param month
     * @param day
     * @return
     */
    public Log getLog(int year, int month, int day) {
        Log log = logCollection.get(String.format("%d-%d-%d", year, month, day));
        if (log != null) {
            System.out.println("[SUCCESS] Log retrieved!");
            return log;
        }

        System.out.println("[ERROR] Log does not exist!");
        return null;
    }

    public Map<String, Log> getLogs() {
        return logCollection;
    }

    /**
     * Creates a new log object and adds it to the 'logCollection' collection
     * [Overloaded Method for non-food construction]
     * 
     * @param year
     * @param month
     * @param day
     * @return
     */

    public Log createLog(int year, int month, int day) {
        String logID = String.format("%d-%d-%d", year, month, day);

        Log existingLog = logCollection.get(logID);
        if (existingLog != null) {
            return existingLog;
        }

        Log newLog = new Log(year, month, day);
        logCollection.put(logID, newLog);

        setChanged();
        notifyObservers();
        return newLog;
    }

    /**
     * Updates a log by replacing it with a new log
     * 
     * @param year
     * @param month
     * @param day
     * @param weight
     * @param desiredCalories
     * @param foodNames
     */

    public Log updateLog(int year, int month, int day, float weight, float desiredCalories, List<String> foodNames,
            List<Float> quantities, List<String> exerciseNames, List<Double> minutes) {
        String logID = String.format("%d-%d-%d", year, month, day);

        Log existingLog = logCollection.get(logID);
        if (existingLog != null) {
            Log newLog = createLog(year, month, day);
            newLog.setWeight(weight);
            newLog.setDesiredCalories(desiredCalories);

            for (int i = 0; i < foodNames.size(); i++) {
                iNourishment food = nourishmentManager.getNourishment(foodNames.get(i).toUpperCase());
                newLog.addNourishment(food, quantities.get(i));

            }

            for (int i = 0; i < exerciseNames.size(); i++) {
                Exercise exercise = exerciseManager.getExercise(exerciseNames.get(i));
                newLog.addExercise(exercise.getName(), minutes.get(i));
            }

            logCollection.remove(logID);
            logCollection.put(logID, newLog);

            setChanged();
            notifyObservers();
            return newLog;
        } else {
            return existingLog;
        }
    }

    /**
     * Deletes a log given the date
     * 
     * @param year
     * @param month
     * @param day
     */
    public void deleteLog(int year, int month, int day) {
        String logID = String.format("%d-%d-%d", year, month, day);

        Log existingLog = logCollection.get(logID);
        if (existingLog != null) {
            logCollection.remove(logID);
        }
        setChanged();
        notifyObservers();
    }

    public void loadLogs() {
        // Use the stored file path for loading logs
        if (logFilePath == null) {
            System.out.println("[ERROR] Log file path is not set.");
            return;
        }

        try {
            FileReader fileReader = new FileReader(logFilePath);
            BufferedReader reader = new BufferedReader(fileReader);

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] tokens = line.split(",");

                int year = Integer.parseInt(tokens[0]);
                int month = Integer.parseInt(tokens[1]);
                int day = Integer.parseInt(tokens[2]);

                Log existingLog = createLog(year, month, day);

                if (existingLog != null) {
                    if (tokens[3].equals("w")) {
                        existingLog.setWeight(Float.parseFloat(tokens[4]));
                    } else if (tokens[3].equals("c")) {
                        existingLog.setDesiredCalories(Float.parseFloat(tokens[4]));
                    } else if (tokens[3].equals("f")) {
                        iNourishment food = nourishmentManager.getNourishment(tokens[4].toUpperCase());
                        existingLog.addNourishment(food, Float.parseFloat(tokens[5]));
                    } else if (tokens[3].equals("e")) {
                        Exercise exercise = exerciseManager.getExercise(tokens[4]);
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
        setChanged();
        notifyObservers();
    }

    public void saveLogs() {
        // Use the stored file path for saving logs
        if (logFilePath == null) {
            System.out.println("[ERROR] Log file path is not set.");
            return;
        }

        try {
            FileWriter fileWriter = new FileWriter(logFilePath);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (Log log : logCollection.values()) {
                writer.write(log.toFormat());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers();
    }

    public float calculateCaloriesConsumed(Log log) {
        float totalCalories = 0;
        for (Map.Entry<iNourishment, Float> entry : log.getFoodItems().entrySet()) {
            iNourishment food = entry.getKey();
            float quantity = entry.getValue();
            if (food != null) {
                totalCalories += food.getCalories() * quantity;
            }

        }
        return totalCalories;
    }

    public float calculateCaloriesExpanded(Log log) {
        float totalCalories = 0;
        for (Map.Entry<String, Float> entry : log.getExerciseItems().entrySet()) {
            Exercise exercise = exerciseManager.getExercise(entry.getKey());
            if (exercise != null) {
                float minutes = entry.getValue();
                totalCalories += (exercise.getCaloriesPerHour() / 60) * minutes * (log.getWeight() / 100);
            }
        }
        return totalCalories;
    }

    public float calculateNetCalories(Log log) {
        return calculateCaloriesConsumed(log) - calculateCaloriesExpanded(log);
    }

    public void updateWeight(Log log, float weight) {

    }

    public float calculateFatsPercentage(Log log) {
        float totalFats = 0;
        float totalNutrients = 0;
        float totalFatsPercentage = 0;
        for (Map.Entry<iNourishment, Float> entry : log.getFoodItems().entrySet()) {
            iNourishment nourishment = entry.getKey();
            float quantity = entry.getValue();

            if (nourishment != null) {
                totalFats += nourishment.getFat() * quantity;
                totalNutrients += (nourishment.getFat() + nourishment.getCarbs() + nourishment.getProtein()) * quantity;
            }
        }
        totalFatsPercentage = (totalFats / totalNutrients) * 100;
        return totalFatsPercentage;
    }

    public float calculateCarbsPercentage(Log log) {
        float totalCarbs = 0;
        float totalNutrients = 0;
        float totalCarbsPercentage = 0;
        for (Map.Entry<iNourishment, Float> entry : log.getFoodItems().entrySet()) {
            iNourishment nourishment = entry.getKey();
            float quantity = entry.getValue();

            if (nourishment != null) {
                totalCarbs += nourishment.getCarbs() * quantity;
                totalNutrients += (nourishment.getFat() + nourishment.getCarbs() + nourishment.getProtein()) * quantity;
            }
        }
        totalCarbsPercentage = (totalCarbs / totalNutrients) * 100;
        return totalCarbsPercentage;
    }

    public float calculateProteinsPercentage(Log log) {
        float totalProteins = 0;
        float totalNutrients = 0;
        float totalProteinsPercentage = 0;
        for (Map.Entry<iNourishment, Float> entry : log.getFoodItems().entrySet()) {
            iNourishment nourishment = entry.getKey();
            float quantity = entry.getValue();

            if (nourishment != null) {
                totalProteins += nourishment.getProtein() * quantity;
                totalNutrients += (nourishment.getFat() + nourishment.getCarbs() + nourishment.getProtein()) * quantity;
            }
        }
        totalProteinsPercentage = (totalProteins / totalNutrients) * 100;
        return totalProteinsPercentage;
    }

    public boolean exceedsCaloriesLimit(Log log) {
        float netCalories = calculateNetCalories(log);
        return netCalories > log.getDesiredCalories();
    }

    public float calculateSodiumPercentage(Log log) {
        float totalSodium = 0;
        float totalSodiumPercentage = 0;
        for (Map.Entry<iNourishment, Float> entry : log.getFoodItems().entrySet()) {
            iNourishment nourishment = entry.getKey();
            float quantity = entry.getValue();

            if (nourishment != null) {
                totalSodium += nourishment.getSodium() * quantity;
            }
        }
        totalSodiumPercentage = (totalSodium / 2300) * 100;
        return totalSodiumPercentage;
    }

}
