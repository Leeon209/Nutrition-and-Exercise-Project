package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observable;

public class ExerciseManager extends Observable {

    private Map<String, Exercise> exerciseCollection = new LinkedHashMap<>();

    public ExerciseManager() {

    }

    public void createExercise(String exerciseName, double caloriesPerHour) {
        if (!exerciseCollection.containsKey(exerciseName)) {
            Exercise exercise = new Exercise(exerciseName, caloriesPerHour);
            exerciseCollection.put(exerciseName, exercise);
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Receiving exercise from exercise collection
     * 
     * @param name
     * @return
     */
    public Exercise getExercise(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("[ERROR] Invalid exercise name provided.\n\n");
            return null;
        }

        Exercise exercise = exerciseCollection.get(name);
        if (exercise != null) {
            System.out.println(exercise.toString());
        } else {
            System.out.println("[ERROR] Exercise not found! \n\n");
        }

        setChanged();
        notifyObservers();
        return exercise;

    }

    public void updateExercise(String exerciseName, double newCaloriesPerHour) {
        if (exerciseCollection.containsKey(exerciseName)) {
            Exercise exercise = exerciseCollection.get(exerciseName);
            exercise.setCaloriesPerHour(newCaloriesPerHour);
            setChanged();
            notifyObservers(exercise);
        }
    }

    public void deleteExercise(String name) {
        Exercise exercise = exerciseCollection.remove(name);
        if (exercise != null) {
            System.out.println("[SUCCESS] Exercise deleted! \n\n");
        } else {
            System.out.println("[ERROR] Exercise not found! \n\n");
        }
        setChanged();
        notifyObservers();
    }

    /**
     * receive exerciseCollection
     * 
     * @return
     */
    public Map<String, Exercise> getExerciseCollection() {
        return exerciseCollection;
    }

    /**
     * Parses the given CSV files and constructs the exercise collection
     * 
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
        setChanged();
        notifyObservers();
    }

    /**
     * Saving exercise data into csv file
     * 
     * @param path
     */
    // Save exercise data into exercise.csv
    public void saveExercise(String path) {
        System.out.println("SAVING EXERCISE");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
