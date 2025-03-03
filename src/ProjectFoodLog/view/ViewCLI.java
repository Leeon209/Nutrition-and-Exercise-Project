package view;

import java.util.*;

import model.HealthProgram;
import controller.*;

public class ViewCLI implements UserInterface, Observer {

    private HealthProgram program; // Reference to our program file

    private LogAction logAction;
    private FoodAction foodAction;
    private Scanner scanner;

    public ViewCLI(HealthProgram program) {
        this.program = program;
        logAction = new LogAction(program);
        foodAction = new FoodAction(program);
        scanner = new Scanner(System.in);

        program.addObserver(this);
    }

    @Override
    public void update(Observable obs, Object o) {

        /*
         * If received from other than the program we're
         * observing, just return.
         */
        if (obs != program) {
            return;
        }

        if (o == null) {
            System.out.println("[ERROR]");
            return;
        }

        System.out.println("OBSERVER UPDATE: ");
        System.out.println(o);

    }

    @Override
    public void display() {
        System.out.println("Welcome to the HealthNCare App!");
        boolean running = true;

        while (running) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Create Food");
            System.out.println("2. Update Food");
            System.out.println("3. Create Recipe");
            System.out.println("4. Update Recipe");
            System.out.println("5. View Nourishment Item");
            System.out.println("6. Delete Nourishment");
            System.out.println("7. Create Log");
            System.out.println("8. View Log");
            System.out.println("9. Update Log");
            System.out.println("10. Delete Log");
            System.out.println("11: Save Data");
            System.out.println("12. Exit");

            int choice = getChoice();
            switch (choice) {
                case 1 -> createFood();
                case 2 -> updateFood();
                case 3 -> createRecipe();
                case 4 -> updateRecipe();
                case 5 -> viewFood();
                case 6 -> deleteFood();
                case 7 -> createLog();
                case 8 -> viewLog();
                // case 9 -> updateLog();
                case 10 -> deleteLog();
                case 11 -> {
                    saveLogs();
                    saveNourishment();
                }
                case 12 -> {
                    System.out.println("Exiting CLI. Goodbye!");
                    scanner.close();
                    running = false;

                }
                default ->
                    System.out.println("Invalid option choice. Please choose the number for the option you'd like.");
            }
        }
    }

    private int getChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number for your chosen option: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    // Method to create a new log entry in the UserData
    public void createLog() {
        System.out.println("Enter log date information: ");

        System.out.println("Year: ");
        int year = scanner.nextInt();

        System.out.println("Month: ");
        int month = scanner.nextInt();

        System.out.println("Day: ");
        int day = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Weight: ");
        float weight = scanner.nextFloat();
        scanner.nextLine();

        System.out.print("Desired Calories: ");
        float desiredCalories = scanner.nextFloat();
        scanner.nextLine();

        System.out.println("Enter food names (seperate with a comma): ");
        String foodInput = scanner.nextLine();

        List<String> foodNames;
        if (foodInput.trim().isEmpty()) {
            foodNames = new ArrayList<>();
        } else {
            foodNames = Arrays.asList(foodInput.split("\\s*,\\s*"));
        }

        System.out.print("Enter quantities of the food in the same order (seperate with a comma): ");
        String foodQuantityInput = scanner.nextLine();

        List<String> foodQuantityString;
        List<Float> foodQuantity = new ArrayList<>();
        if (foodQuantityInput.trim().isEmpty()) {
            foodQuantity = new ArrayList<>();
        } else {
            foodQuantityString = Arrays.asList(foodInput.split("\\s*,\\s*"));

            for (String string : foodQuantityString) {
                foodQuantity.add(Float.parseFloat(string));
            }
        }

        // logAction.updateLog(year, month, day, weight, desiredCalories, foodNames,
        // foodQuantity);
        System.out.println("Log updated successfully.");
    }

    public void viewLog() {
        System.out.println("Enter log date information: ");
        System.out.print("Year: ");
        int year = scanner.nextInt();
        System.out.print("Month: ");
        int month = scanner.nextInt();
        System.out.print("Day: ");
        int day = scanner.nextInt();
        scanner.nextLine();

        logAction.viewLog(year, month, day);
    }

    // public void updateLog(){
    // System.out.println("Enter log date information: ");
    // System.out.print("Year: ");
    // int year = scanner.nextInt();
    // System.out.print("Month: ");
    // int month = scanner.nextInt();
    // System.out.print("Day: ");
    // int day = scanner.nextInt();
    // scanner.nextLine();
    // System.out.print("Weight: ");
    // float weight = scanner.nextFloat();
    // scanner.nextLine();
    // System.out.print("Desired Calories: ");
    // float desiredCalories = scanner.nextFloat();
    // scanner.nextLine();

    // System.out.print("Enter food names (seperate with a comma): ");
    // String foodInput = scanner.nextLine();

    // List<String> foodNames;
    // if (foodInput.trim().isEmpty()) {
    // foodNames = new ArrayList<>();
    // } else {
    // foodNames = Arrays.asList(foodInput.split("\\s*,\\s*"));
    // }

    // System.out.print("Enter quantities of the food in the same order (seperate
    // with a comma): ");
    // String foodQuantityInput = scanner.nextLine();

    // List<String> foodQuantityString;
    // List<Float> foodQuantity = new ArrayList<>();
    // if (foodQuantityInput.trim().isEmpty()) {
    // foodQuantity = new ArrayList<>();
    // } else {
    // foodQuantityString = Arrays.asList(foodInput.split("\\s*,\\s*"));

    // for (String string : foodQuantityString) {
    // foodQuantity.add(Float.parseFloat(string));
    // }
    // }

    // logAction.updateLog(year, month, day, weight, desiredCalories, foodNames,
    // foodQuantity);
    // System.out.println("Log updated successfully.");
    // }

    public void deleteLog() {
        System.out.println("Enter log date information: ");
        System.out.print("Year: ");
        int year = scanner.nextInt();
        System.out.print("Month: ");
        int month = scanner.nextInt();
        System.out.print("Day: ");
        int day = scanner.nextInt();
        scanner.nextLine();

        logAction.deleteLog(year, month, day);
        System.out.println("Log deleted successfully");
    }

    public void createFood() {
        scanner.nextLine();
        System.out.println("Enter food name: ");
        String name = scanner.nextLine();

        System.out.println("Enter calories: ");
        float calories = scanner.nextFloat();

        System.out.println("Enter fat content (grams): ");
        float fat = scanner.nextFloat();

        System.out.println("Enter carbohydrate content (grams): ");
        float carbs = scanner.nextFloat();

        System.out.println("Enter protein content (grams): ");
        float protein = scanner.nextFloat();

        System.out.println("Enter sodium content (grams): ");
        float sodium = scanner.nextFloat();

        foodAction.createFood(name, calories, fat, carbs, protein, sodium);
        System.out.println("Food created successfully.");
    }

    public void updateFood() {
        scanner.nextLine();

        System.out.print("Enter food name to update: ");
        String name = scanner.nextLine();

        System.out.print("Enter new calories: ");
        float calories = scanner.nextFloat();

        System.out.print("Enter new fat content (grams): ");
        float fat = scanner.nextFloat();

        System.out.print("Enter new carbohydrate content (grams): ");
        float carbs = scanner.nextFloat();

        System.out.print("Enter new protein content (grams): ");
        float protein = scanner.nextFloat();

        System.out.print("Enter new sodium content (grams): ");
        float sodium = scanner.nextFloat();

        foodAction.updateFood(name, calories, fat, carbs, protein, sodium);
        System.out.println("Food item updated successfully.");
    }

    public void viewFood() {
        System.out.println("Enter food name to view: ");
        scanner.nextLine();

        String name = scanner.nextLine();
        foodAction.viewFood(name);

    }

    public void deleteFood() {
        System.out.println("Enter food to delete: ");
        scanner.nextLine();

        String name = scanner.nextLine();
        foodAction.deleteNourishmentItem(name);
        System.out.println("Food deleted successfully.");
    }

    public void createRecipe() {
        scanner.nextLine();
        System.out.println("Enter recipe name: ");

        System.out.println("Name: ");
        String name = scanner.nextLine();

        System.out.println("Enter food names (seperate with a comma): ");
        String foodInput = scanner.nextLine();

        List<String> foodNames;
        if (foodInput.trim().isEmpty()) {
            foodNames = new ArrayList<>();
        } else {
            foodNames = Arrays.asList(foodInput.split("\\s*,\\s*"));
        }

        System.out.print("Enter quantities of the food in the same order (seperate with a comma): ");
        String foodQuantityInput = scanner.nextLine();

        List<String> foodQuantityString;
        List<Float> foodQuantity = new ArrayList<>();
        if (foodQuantityInput.trim().isEmpty()) {
            foodQuantity = new ArrayList<>();
        } else {
            foodQuantityString = Arrays.asList(foodQuantityInput.split("\\s*,\\s*"));

            for (String string : foodQuantityString) {
                foodQuantity.add(Float.parseFloat(string));
            }
        }

        foodAction.createRecipe(name, foodNames, foodQuantity);
    }

    public void updateRecipe() {
        scanner.nextLine();

        System.out.println("Enter recipe name to update: ");

        System.out.println("Name: ");
        String name = scanner.nextLine();

        System.out.println("Enter food names (seperate with a comma): ");
        String foodInput = scanner.nextLine();

        List<String> foodNames;
        if (foodInput.trim().isEmpty()) {
            foodNames = new ArrayList<>();
        } else {
            foodNames = Arrays.asList(foodInput.split("\\s*,\\s*"));
        }

        System.out.print("Enter quantities of the food in the same order (seperate with a comma): ");
        String foodQuantityInput = scanner.nextLine();

        List<String> foodQuantityString;
        List<Float> foodQuantity = new ArrayList<>();
        if (foodQuantityInput.trim().isEmpty()) {
            foodQuantity = new ArrayList<>();
        } else {
            foodQuantityString = Arrays.asList(foodQuantityInput.split("\\s*,\\s*"));

            for (String string : foodQuantityString) {
                foodQuantity.add(Float.parseFloat(string));
            }
        }

        foodAction.updateRecipe(name, foodNames, foodQuantity);
    }

    public void saveNourishment() {
        foodAction.saveNourishment();
    }

    public void saveLogs() {
        logAction.saveLogs();
    }

}
