package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observable;

public class NourishmentManager extends Observable {
    private Map<String, iNourishment> nourishmentCollection = new LinkedHashMap<>();
    
    public NourishmentManager() {

    }
    /**
     * Receiving nourishment collection
     * @param name
     * @return
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

            setChanged();
            notifyObservers();
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
        setChanged();
        notifyObservers();
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
        setChanged();
        notifyObservers();
    }

    public Map<String, iNourishment> getNourishmentCollection(){
        return nourishmentCollection;
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
        setChanged();
        notifyObservers();
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
        setChanged();
        notifyObservers();
    }
}
