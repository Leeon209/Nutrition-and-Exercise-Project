package view;

import controller.ExerciseController.*;
import controller.LogController.*;
import controller.NourishmentController.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.logging.LogManager;

import javax.swing.*;

import model.Exercise;
import model.ExerciseManager;
import model.HealthProgram;
import model.Log;
import model.NourishmentManager;
import model.iNourishment;

@SuppressWarnings("deprecation")
public class ViewGUI extends JFrame implements UserInterface, Observer {

    private HealthProgram program;
    private model.LogManager logManager;
    private NourishmentManager nourishmentManager;
    private ExerciseManager exerciseManager;
    private BarGraph barGraph; // initialize bargraph

    public ViewGUI(HealthProgram program) {
        this.program = program;
        this.logManager = program.getLogManager();
        this.nourishmentManager = program.getNourishmentManager();
        this.exerciseManager = program.getExerciseManager();

        logManager.addObserver(this);
        nourishmentManager.addObserver(this);
        exerciseManager.addObserver(this);

        initializeGUI();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o != logManager && o != nourishmentManager && o != exerciseManager){
            return;
        }
        return;
/*         if (o == logManager) {
            JOptionPane.showMessageDialog(this, "Log Update: " + arg, "Update", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (o == nourishmentManager) {
            JOptionPane.showMessageDialog(this, "Nourishment Update: " + arg, "Update", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (o == exerciseManager) {
            JOptionPane.showMessageDialog(this, "Exercise Update: " + arg, "Update", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (arg == null) {
            JOptionPane.showMessageDialog(this, createRedErrorMessage("Error: Update received with no data."), "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } */

    }

    @Override
    public void display() {
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
        });
    }

    private void initializeGUI() {
        setTitle("HealthNCare App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the tabbed pane in GUI
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add tabs to GUI for Food, Logs, and Recipes
        tabbedPane.addTab("Food Management", createFoodPanel());
        tabbedPane.addTab("Logs Management", createLogPanel());
        tabbedPane.addTab("Recipes Management", createRecipePanel());
        tabbedPane.addTab("Exercise Management", createExercisePanel());

        // Create a panel to hold the BarGraph and add it as its own tab
        JPanel barGraphPanel = new JPanel(new BorderLayout());
        barGraph = new BarGraph(0, 0, 0);
        barGraphPanel.add(barGraph, BorderLayout.CENTER);
        tabbedPane.addTab("Nutrition Bar Graph", barGraphPanel);

        // Adds the tabbed pane to the main frame
        add(tabbedPane, BorderLayout.CENTER);

        // Adds sChangeListener to update selected tab text color
        tabbedPane.addChangeListener(e -> updateTabColors(tabbedPane));

        // Sets initial tab colors
        updateTabColors(tabbedPane);

        // Adds the tabbed pane to the main frame
        add(tabbedPane, BorderLayout.CENTER);

        /**
         * Initialize and add the BarGraph to display nutritional information
         * Changed the menupanel to BorderLayout.West so the menu is on the left and the
         * bar graph is in the center
         */

    }

    // Button Factory
    private JButton createMenuButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    private JLabel createRedErrorMessage(String message) {
        JLabel label = new JLabel(message);
        label.setForeground(Color.RED);
        return label;
    }

    // Food Management Panel
    private JPanel createFoodPanel() {
        JPanel foodPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        foodPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        foodPanel.add(createMenuButton("Create Food", e -> createFood()));
        foodPanel.add(createMenuButton("Update Food", e -> updateFood()));
        foodPanel.add(createMenuButton("View Food", e -> viewFood()));
        foodPanel.add(createMenuButton("Delete Food", e -> deleteFood()));
        foodPanel.add(createMenuButton("Save Data", e -> saveNourishment()));
        foodPanel.add(createMenuButton("Exit App", e -> exitApp()));

        return foodPanel;
    }

    // Logs Management Panel
    private JPanel createLogPanel() {
        JPanel logPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        logPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        logPanel.add(createMenuButton("Create Log", e -> createLog()));
        logPanel.add(createMenuButton("View Log", e -> viewLog()));
        logPanel.add(createMenuButton("Update Log", e -> updateLog()));
        logPanel.add(createMenuButton("Delete Log", e -> deleteLog()));
        logPanel.add(createMenuButton("Save Logs", e -> saveLogs()));
        logPanel.add(createMenuButton("Exit App", e -> exitApp()));

        return logPanel;
    }

    // Recipes Management Panel
    private JPanel createRecipePanel() {
        JPanel recipePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        recipePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        recipePanel.add(createMenuButton("Create Recipe", e -> createRecipe()));
        recipePanel.add(createMenuButton("Update Recipe", e -> updateRecipe()));
        recipePanel.add(createMenuButton("Save Data", e -> saveNourishment()));
        recipePanel.add(createMenuButton("Exit App", e -> exitApp()));

        return recipePanel;
    }

    // Exercise Management Panel
    private JPanel createExercisePanel() {
        JPanel exercisePanel = new JPanel(new GridLayout(3, 2, 10, 10));

        exercisePanel.add(createMenuButton("Create Exercise", e -> createExercise()));
        exercisePanel.add(createMenuButton("Update Exercise", e -> updateExercise()));
        exercisePanel.add(createMenuButton("View Exercise", e -> viewExercise()));
        exercisePanel.add(createMenuButton("Delete Exercise", e -> deleteExercise()));
        exercisePanel.add(createMenuButton("Save Data", e -> saveExercises()));
        exercisePanel.add(createMenuButton("Exit App", e -> exitApp()));

        return exercisePanel;

    }

    // Helper method to update text color selected tab
    private void updateTabColors(JTabbedPane tabbedPane) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (tabbedPane.getSelectedIndex() == i) {
                tabbedPane.setForegroundAt(i, Color.BLUE); // Selected tab text color
            } else {
                tabbedPane.setForegroundAt(i, Color.BLACK); // Default tab text color
            }
        }
    }

    private void createFood() {
        // Panel for user input
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        JTextField nameField = new JTextField();
        JTextField caloriesField = new JTextField();
        JTextField fatField = new JTextField();
        JTextField carbsField = new JTextField();
        JTextField proteinField = new JTextField();
        JTextField sodiumField = new JTextField();

        panel.add(new JLabel("Food Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Calories:"));
        panel.add(caloriesField);
        panel.add(new JLabel("Fat (grams):"));
        panel.add(fatField);
        panel.add(new JLabel("Carbohydrates (grams):"));
        panel.add(carbsField);
        panel.add(new JLabel("Protein (grams):"));
        panel.add(proteinField);
        panel.add(new JLabel("Sodium (grams):"));
        panel.add(sodiumField);

        JButton okButton = new JButton("Create Food");
        JButton cancelButton = new JButton("Cancel");

        // Tie the listener to the create button
        okButton.addActionListener(e -> {
            try {
                String foodName = nameField.getText().trim();
                if (foodName.isEmpty()) {
                    throw new IllegalArgumentException("Food name cannot be empty.");
                }

                float calories = Float.parseFloat(caloriesField.getText().trim());
                float fat = Float.parseFloat(fatField.getText().trim());
                float carbs = Float.parseFloat(carbsField.getText().trim());
                float protein = Float.parseFloat(proteinField.getText().trim());
                float sodium = Float.parseFloat(sodiumField.getText().trim());

                // Uses the ActionListener to create the food
                CreateFoodListener listener = new CreateFoodListener(
                        program, foodName, calories, fat, carbs, protein, sodium);
                listener.actionPerformed(e);

                JOptionPane.showMessageDialog(this, "Food created successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                SwingUtilities.getWindowAncestor(okButton).dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        createRedErrorMessage("Please enter valid numeric values for the nutritional fields."),
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, createRedErrorMessage(ex.getMessage()), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add cancel button functionality
        cancelButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(cancelButton).dispose();
            JOptionPane.showMessageDialog(this, "Create food canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        Object[] options = { okButton, cancelButton };
        JOptionPane.showOptionDialog(
                this,
                panel,
                "Create Food",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null);
    }

    private void updateFood() {

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        JTextField nameField = new JTextField();
        JTextField caloriesField = new JTextField();
        JTextField fatField = new JTextField();
        JTextField carbsField = new JTextField();
        JTextField proteinField = new JTextField();
        JTextField sodiumField = new JTextField();

        panel.add(new JLabel("Name of the food to update:"));
        panel.add(nameField);
        panel.add(new JLabel("New Calories:"));
        panel.add(caloriesField);
        panel.add(new JLabel("New Fat (grams):"));
        panel.add(fatField);
        panel.add(new JLabel("New Carbohydrates (grams):"));
        panel.add(carbsField);
        panel.add(new JLabel("New Protein (grams):"));
        panel.add(proteinField);
        panel.add(new JLabel("New Sodium (grams):"));
        panel.add(sodiumField);

        JButton okButton = new JButton("Update Food");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(e -> {
            try {
                String foodName = nameField.getText().trim();
                if (foodName.isEmpty()) {
                    throw new IllegalArgumentException("Food name cannot be empty.");
                }

                float calories = Float.parseFloat(caloriesField.getText().trim());
                float fat = Float.parseFloat(fatField.getText().trim());
                float carbs = Float.parseFloat(carbsField.getText().trim());
                float protein = Float.parseFloat(proteinField.getText().trim());
                float sodium = Float.parseFloat(sodiumField.getText().trim());

                // Uses the UpdateFoodListener to update the food
                UpdateFoodListener listener = new UpdateFoodListener(
                        program, foodName, calories, fat, carbs, protein, sodium);
                listener.actionPerformed(e);

                JOptionPane.showMessageDialog(this, "Food updated successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                SwingUtilities.getWindowAncestor(okButton).dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        createRedErrorMessage("Please enter valid numeric values for the nutritional fields."),
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, createRedErrorMessage(ex.getMessage()), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(cancelButton).dispose();
            JOptionPane.showMessageDialog(this, "Update food canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        Object[] options = { okButton, cancelButton };
        JOptionPane.showOptionDialog(
                this,
                panel,
                "Update Food",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null);
    }

    private void viewFood() {

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        JTextField nameField = new JTextField();

        panel.add(new JLabel("Enter Food Name:"));
        panel.add(nameField);

        JButton okButton = new JButton("View Food");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(e -> {
            try {
                String foodName = nameField.getText().trim();
                if (foodName.isEmpty()) {
                    throw new IllegalArgumentException("Food name cannot be empty.");
                }

                GetNourishmentListener listener = new GetNourishmentListener(program, foodName);
                listener.actionPerformed(e);

                iNourishment nourishment = program.getNourishment(foodName);
                if (nourishment != null) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Food Details:\n" + nourishment.toString(),
                            "Food Info",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            createRedErrorMessage("Food not found."),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

                SwingUtilities.getWindowAncestor(okButton).dispose();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, createRedErrorMessage(ex.getMessage()), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(cancelButton).dispose();
            JOptionPane.showMessageDialog(this, "View food canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        Object[] options = { okButton, cancelButton };
        JOptionPane.showOptionDialog(
                this,
                panel,
                "View Food",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null);
    }

    private void deleteFood() {

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        JTextField foodNameField = new JTextField();

        panel.add(new JLabel("Enter the name of the food to delete:"));
        panel.add(foodNameField);

        JButton okButton = new JButton("Delete Food");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(e -> {
            String foodName = foodNameField.getText().trim();
            if (foodName.isEmpty()) {
                JOptionPane.showMessageDialog(this, createRedErrorMessage("Food name cannot be empty."), "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {

                DeleteNourishmentListener listener = new DeleteNourishmentListener(program, foodName);
                listener.actionPerformed(e);

                JOptionPane.showMessageDialog(this, "Food item deleted successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                SwingUtilities.getWindowAncestor(okButton).dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, createRedErrorMessage("Error deleting the food item."), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(cancelButton).dispose();
            JOptionPane.showMessageDialog(this, "Delete operation canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        Object[] options = { okButton, cancelButton };
        JOptionPane.showOptionDialog(
                this,
                panel,
                "Delete Food",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null);
    }

    private void createRecipe() {

        JPanel panel = new JPanel(new GridLayout(1, 1, 10, 10));

        JTextField recipeNameField = new JTextField();

        panel.add(new JLabel("Recipe Name:"));
        panel.add(recipeNameField);

        JButton okButton = new JButton("Create Recipe");
        JButton cancelButton = new JButton("Cancel");

        // Attach listener to the create button
        okButton.addActionListener(e -> {
            try {

                String recipeName = recipeNameField.getText().trim();
                if (recipeName.isEmpty()) {
                    throw new IllegalArgumentException("Recipe name cannot be empty.");
                }

                // Collects ingredients and quantities
                List<String> ingredients = new ArrayList<>();
                List<Float> quantities = new ArrayList<>();

                // Prompts for ingredients
                boolean addIngredients = true;
                while (addIngredients) {
                    String ingredient = JOptionPane.showInputDialog(this, "Enter Ingredient Name (or Cancel to skip):");
                    if (ingredient != null && !ingredient.trim().isEmpty()) {
                        String quantityStr = JOptionPane.showInputDialog(this,
                                "Enter Quantity for " + ingredient + ":");
                        try {
                            float quantity = Float.parseFloat(quantityStr.trim());
                            ingredients.add(ingredient.trim());
                            quantities.add(quantity);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this,
                                    createRedErrorMessage("Invalid quantity value. Skipping this ingredient."),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        addIngredients = false; // Stop prompting for ingredients
                    }
                }

                // Uses the CreateRecipeListener
                CreateRecipeListener listener = new CreateRecipeListener(
                        program, recipeName, ingredients, quantities);
                listener.actionPerformed(e);

                JOptionPane.showMessageDialog(this, "Recipe created successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.getWindowAncestor(okButton).dispose();

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, createRedErrorMessage(ex.getMessage()), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // cancel button
        cancelButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(cancelButton).dispose();
            JOptionPane.showMessageDialog(this, "Recipe creation canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        // Display dialog
        Object[] options = { okButton, cancelButton };
        JOptionPane.showOptionDialog(
                this,
                panel,
                "Create Recipe",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null);
    }

    private void updateRecipe() {

        JPanel panel = new JPanel(new GridLayout(1, 1, 10, 10));

        JTextField recipeNameField = new JTextField();

        panel.add(new JLabel("Recipe Name to Update:"));
        panel.add(recipeNameField);

        JButton okButton = new JButton("Update Recipe");
        JButton cancelButton = new JButton("Cancel");

        // button listener
        okButton.addActionListener(e -> {
            try {

                String recipeName = recipeNameField.getText().trim();
                if (recipeName.isEmpty()) {
                    throw new IllegalArgumentException("Recipe name cannot be empty.");
                }

                // Collects updated ingredients and quantities
                List<String> ingredients = new ArrayList<>();
                List<Float> quantities = new ArrayList<>();

                // Prompts for ingredients
                boolean addIngredients = true;
                while (addIngredients) {
                    String ingredient = JOptionPane.showInputDialog(this, "Enter Ingredient Name (or Cancel to skip):");
                    if (ingredient != null && !ingredient.trim().isEmpty()) {
                        String quantityStr = JOptionPane.showInputDialog(this,
                                "Enter Quantity for " + ingredient + ":");
                        try {
                            float quantity = Float.parseFloat(quantityStr.trim());
                            ingredients.add(ingredient.trim());
                            quantities.add(quantity);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this,
                                    createRedErrorMessage("Invalid quantity value. Skipping this ingredient."),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        addIngredients = false; // Stops prompting for ingredients
                    }
                }

                // Uses the UpdateRecipeListener
                UpdateRecipeListener listener = new UpdateRecipeListener(
                        program, recipeName, ingredients, quantities);
                listener.actionPerformed(e);

                JOptionPane.showMessageDialog(this, "Recipe updated successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.getWindowAncestor(okButton).dispose();

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, createRedErrorMessage(ex.getMessage()), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // cancel button
        cancelButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(cancelButton).dispose();
            JOptionPane.showMessageDialog(this, "Recipe update canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        // Display dialog
        Object[] options = { okButton, cancelButton };
        JOptionPane.showOptionDialog(
                this,
                panel,
                "Update Recipe",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null);
    }

    private void createLog() {

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        JTextField yearField = new JTextField();
        JTextField monthField = new JTextField();
        JTextField dayField = new JTextField();
        JTextField weightField = new JTextField();
        JTextField desiredCaloriesField = new JTextField();

        panel.add(new JLabel("Year:"));
        panel.add(yearField);
        panel.add(new JLabel("Month:"));
        panel.add(monthField);
        panel.add(new JLabel("Day:"));
        panel.add(dayField);
        panel.add(new JLabel("Weight (kg):"));
        panel.add(weightField);
        panel.add(new JLabel("Desired Calories:"));
        panel.add(desiredCaloriesField);

        JButton okButton = new JButton("Create Log");
        JButton cancelButton = new JButton("Cancel");

        // ActionListner for the create button
        okButton.addActionListener(e -> {
            try {
                // Parse log details from fields
                int year = Integer.parseInt(yearField.getText().trim());
                int month = Integer.parseInt(monthField.getText().trim());
                int day = Integer.parseInt(dayField.getText().trim());
                float weight = Float.parseFloat(weightField.getText().trim());
                float desiredCalories = Float.parseFloat(desiredCaloriesField.getText().trim());

                // Collecst food and exercise entries
                List<String> foodNames = new ArrayList<>();
                List<Float> quantities = new ArrayList<>();
                List<String> exerciseNames = new ArrayList<>();
                List<Double> minutes = new ArrayList<>();

                // Prompts user to add a single food item
                String foodName = JOptionPane.showInputDialog(this, "Enter Food Name (leave blank to skip):");
                if (foodName != null && !foodName.trim().isEmpty()) {
                    String quantityStr = JOptionPane.showInputDialog(this, "Enter Quantity for " + foodName + ":");
                    try {
                        float quantity = Float.parseFloat(quantityStr.trim());
                        foodNames.add(foodName.trim());
                        quantities.add(quantity);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this,
                                createRedErrorMessage("Invalid quantity value. Skipping food entry."), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

                // Prompts user to add a single exercise
                String exerciseName = JOptionPane.showInputDialog(this, "Enter Exercise Name (leave blank to skip):");
                if (exerciseName != null && !exerciseName.trim().isEmpty()) {
                    String minutesStr = JOptionPane.showInputDialog(this,
                            "Enter Minutes Spent on " + exerciseName + ":");
                    try {
                        double duration = Double.parseDouble(minutesStr.trim());
                        exerciseNames.add(exerciseName.trim());
                        minutes.add(duration);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this,
                                createRedErrorMessage("Invalid minutes value. Skipping exercise entry."), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

                // Uses the CreateLogListener to handle log creation
                CreateLogListener listener = new CreateLogListener(
                        program, year, month, day, weight, desiredCalories,
                        foodNames, quantities, exerciseNames, minutes);
                listener.actionPerformed(e);

                JOptionPane.showMessageDialog(this, "Log created successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.getWindowAncestor(okButton).dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        createRedErrorMessage("Please enter valid numeric values for the log fields."), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Attach ActionListener to Cancel button
        cancelButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(cancelButton).dispose();
            JOptionPane.showMessageDialog(this, "Log creation canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        // Display dialog
        Object[] options = { okButton, cancelButton };
        JOptionPane.showOptionDialog(
                this,
                panel,
                "Create Log",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null);
    }

    private void viewLog() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JTextField yearField = new JTextField();
        JTextField monthField = new JTextField();
        JTextField dayField = new JTextField();

        panel.add(new JLabel("Year:"));
        panel.add(yearField);
        panel.add(new JLabel("Month:"));
        panel.add(monthField);
        panel.add(new JLabel("Day:"));
        panel.add(dayField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "View Log",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int year = Integer.parseInt(yearField.getText().trim());
                int month = Integer.parseInt(monthField.getText().trim());
                int day = Integer.parseInt(dayField.getText().trim());

                Log log = program.getLog(year, month, day);

                if (log != null) {

                    // get nut data from the log
                    int fat = log.getTotalFat();
                    int carbs = log.getTotalCarbs();
                    int protein = log.getTotalProtein();
                    int sodium = log.getTotalSodium();
                    float totalCalories = log.getTotalCalories();
                    float desiredCalories = log.getDesiredCalories();
                    float totalSodiumPercentage = (sodium / 2300) * 100;

                    // Get nutrient breakdown percentages
                    float fatsPercentage = Math.round(calculateFatsPercentage(log));
                    float carbsPercentage = Math.round(calculateCarbsPercentage(log));
                    float proteinsPercentage = Math.round(calculateProteinsPercentage(log));

                    // Adjust percentages to ensure they sum to 100%
                    float totalPercentage = fatsPercentage + carbsPercentage + proteinsPercentage;
                    if (totalPercentage != 100) {
                        proteinsPercentage += (100 - totalPercentage);
                    }
                    System.out.println(totalPercentage);

                    // Update the bg with said values
                    barGraph.updateValues(fat, carbs, protein);

                    // dialog the log details
                    StringBuilder details = new StringBuilder();
                    details.append("Date: ").append(year).append("-").append(month).append("-").append(day)
                            .append("\n");
                    details.append("Weight: ").append(log.getWeight()).append(" kg\n");
                    details.append("Desired Calories: ").append(log.getDesiredCalories()).append(" kcal\n");
                    details.append("Total Calories: ").append(totalCalories).append(" kcal\n");

                    details.append("\n");

                    details.append("Total Fat: ").append(fat).append(" g\n");
                    details.append("Total Carbs: ").append(carbs).append(" g\n");
                    details.append("Total Protein: ").append(protein).append(" g\n");
                    details.append("Total Sodium: ").append(sodium).append(" mg\n");
                    details.append("Sodium Intake (daily recommended %): ").append(Math.round(totalSodiumPercentage))
                            .append("%\n");

                    details.append("\n");

                    // prints macro percentages
                    details.append("\nNutrient Breakdown:\n");
                    details.append("Fats: ").append(fatsPercentage).append("%\n");
                    details.append("Carbs: ").append(carbsPercentage).append("%\n");
                    details.append("Protein: ").append(proteinsPercentage).append("%\n");

                    details.append("\n");

                    // message if sodium consumed is over daily limit
                    if (totalSodiumPercentage > 100) {
                        details.append("Darn: Total sodium is higher than the reccommended daily amount!\n");
                    } else {
                        details.append("Nice: Total sodium is lower than the reccommended daily amount!\n");
                    }

                    // message if calories consumed is over desired calories
                    if (totalCalories > desiredCalories) {
                        details.append("Darn: Total Calories is higher than Desired Calories!!!\n");
                    } else {
                        details.append("Nice: Total Calories is lower than Desired Calories!!!\n");
                    }

                    details.append("\n");

                    // Add breakdown of individual food items
                    details.append("Food Items:\n");
                    for (Map.Entry<iNourishment, Float> entry : log.getFoodItems().entrySet()) {
                        iNourishment nourishment = entry.getKey();
                        Float quantity = entry.getValue();
                        float itemCalories = nourishment.getCalories() * quantity;
                        if (nourishment != null) {
                            details.append("- ").append(nourishment.getName()).append(": ").append(quantity)
                                    .append(" units (").append(itemCalories).append(" kcal)\n");
                        }
                    }

                    // Add exercises
                    details.append("Exercises:\n");
                    for (Map.Entry<String, Float> entry : log.getExerciseItems().entrySet()) {
                        String exerciseName = entry.getKey();
                        Float minutes = entry.getValue();
                        Exercise exercise = program.getExercise(exerciseName);
                        exercise.getCaloriesPerHour();
                        details.append("- ").append(exerciseName).append(": ")
                                .append(exercise.calculateCaloriesBurned(log.getWeight(), minutes))
                                .append(" calories burned.\n");
                    }

                    // Displays the log details
                    JOptionPane.showMessageDialog(
                            this,
                            details.toString(),
                            "Log Details",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // No log found
                    JOptionPane.showMessageDialog(
                            this,
                            createRedErrorMessage("No log found for the specified date."),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        this,
                        createRedErrorMessage("Please enter valid numeric values for the date."),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "View log canceled.",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        }
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

    private void updateLog() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        JTextField yearField = new JTextField();
        JTextField monthField = new JTextField();
        JTextField dayField = new JTextField();
        JTextField weightField = new JTextField();
        JTextField desiredCaloriesField = new JTextField();

        panel.add(new JLabel("Year:"));
        panel.add(yearField);
        panel.add(new JLabel("Month:"));
        panel.add(monthField);
        panel.add(new JLabel("Day:"));
        panel.add(dayField);
        panel.add(new JLabel("Weight (kg):"));
        panel.add(weightField);
        panel.add(new JLabel("Desired Calories:"));
        panel.add(desiredCaloriesField);

        JButton okButton = new JButton("Update Log");
        JButton cancelButton = new JButton("Cancel");

        // ActionListner for the create button
        okButton.addActionListener(e -> {
            try {
                // Parse log details from fields
                int year = Integer.parseInt(yearField.getText().trim());
                int month = Integer.parseInt(monthField.getText().trim());
                int day = Integer.parseInt(dayField.getText().trim());
                float weight = Float.parseFloat(weightField.getText().trim());
                float desiredCalories = Float.parseFloat(desiredCaloriesField.getText().trim());

                // Collecst food and exercise entries
                List<String> foodNames = new ArrayList<>();
                List<Float> quantities = new ArrayList<>();
                List<String> exerciseNames = new ArrayList<>();
                List<Double> minutes = new ArrayList<>();

                // Prompts user to add a single food item
                String foodName = JOptionPane.showInputDialog(this, "Enter Food Name (leave blank to skip):");
                if (foodName != null && !foodName.trim().isEmpty()) {
                    String quantityStr = JOptionPane.showInputDialog(this, "Enter Quantity for " + foodName + ":");
                    try {
                        float quantity = Float.parseFloat(quantityStr.trim());
                        foodNames.add(foodName.trim());
                        quantities.add(quantity);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this,
                                createRedErrorMessage("Invalid quantity value. Skipping food entry."), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

                // Prompts user to add a single exercise
                String exerciseName = JOptionPane.showInputDialog(this, "Enter Exercise Name (leave blank to skip):");
                if (exerciseName != null && !exerciseName.trim().isEmpty()) {
                    String minutesStr = JOptionPane.showInputDialog(this,
                            "Enter Minutes Spent on " + exerciseName + ":");
                    try {
                        double duration = Double.parseDouble(minutesStr.trim());
                        exerciseNames.add(exerciseName.trim());
                        minutes.add(duration);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this,
                                createRedErrorMessage("Invalid minutes value. Skipping exercise entry."), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

                // Uses the UpdateLogListener to handle log creation
                UpdateLogListener listener = new UpdateLogListener(
                        program, year, month, day, weight, desiredCalories,
                        foodNames, quantities, exerciseNames, minutes);
                listener.actionPerformed(e);

                JOptionPane.showMessageDialog(this, "Log created successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.getWindowAncestor(okButton).dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        createRedErrorMessage("Please enter valid numeric values for the log fields."), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Attach ActionListener to Cancel button
        cancelButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(cancelButton).dispose();
            JOptionPane.showMessageDialog(this, "Log creation canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        // Display dialog
        Object[] options = { okButton, cancelButton };
        JOptionPane.showOptionDialog(
                this,
                panel,
                "Update Log",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null);
    }

    private void deleteLog() {

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JTextField yearField = new JTextField();
        JTextField monthField = new JTextField();
        JTextField dayField = new JTextField();

        panel.add(new JLabel("Year:"));
        panel.add(yearField);
        panel.add(new JLabel("Month:"));
        panel.add(monthField);
        panel.add(new JLabel("Day:"));
        panel.add(dayField);

        JButton okButton = new JButton("Delete Log");
        JButton cancelButton = new JButton("Cancel");

        // Listener attached to button
        okButton.addActionListener(e -> {
            try {
                // Parses date from fields
                int year = Integer.parseInt(yearField.getText().trim());
                int month = Integer.parseInt(monthField.getText().trim());
                int day = Integer.parseInt(dayField.getText().trim());

                // Uses the DeleteLogListener to handle log deletion
                DeleteLogListener listener = new DeleteLogListener(program, year, month, day);
                listener.actionPerformed(e);

                JOptionPane.showMessageDialog(this, "Log deleted successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.getWindowAncestor(okButton).dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        createRedErrorMessage("Please enter valid numeric values for the date fields."), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Cancel button
        cancelButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(cancelButton).dispose();
            JOptionPane.showMessageDialog(this, "Log deletion canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        // Display dialog
        Object[] options = { okButton, cancelButton };
        JOptionPane.showOptionDialog(
                this,
                panel,
                "Delete Log",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null);
    }

    private void createExercise() {

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        JTextField nameField = new JTextField();
        JTextField caloriesPerHourField = new JTextField();

        panel.add(new JLabel("Exercise Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Calories Burned per Hour:"));
        panel.add(caloriesPerHourField);

        JButton okButton = new JButton("Create Exercise");
        JButton cancelButton = new JButton("Cancel");

        // Tie the listener to the create button
        okButton.addActionListener(e -> {
            try {
                String exerciseName = nameField.getText().trim();
                if (exerciseName.isEmpty()) {
                    throw new IllegalArgumentException("Exercise name cannot be empty.");
                }

                double caloriesPerHour = Double.parseDouble(caloriesPerHourField.getText().trim());

                // Uses the ActionListener to create the exercise
                CreateExerciseListener listener = new CreateExerciseListener(
                        program, exerciseName, caloriesPerHour);
                listener.actionPerformed(e);

                JOptionPane.showMessageDialog(this, "Exercise created successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                SwingUtilities.getWindowAncestor(okButton).dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        createRedErrorMessage("Please enter a valid number for calories burned per hour."),
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, createRedErrorMessage(ex.getMessage()), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(cancelButton).dispose();
            JOptionPane.showMessageDialog(this, "Create exercise canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        Object[] options = { okButton, cancelButton };
        JOptionPane.showOptionDialog(
                this,
                panel,
                "Create Exercise",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null);
    }

    private void updateExercise() {

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        JTextField nameField = new JTextField();
        JTextField caloriesField = new JTextField();

        panel.add(new JLabel("Exercise Name to Update:"));
        panel.add(nameField);
        panel.add(new JLabel("New Calories Burned per Hour:"));
        panel.add(caloriesField);

        JButton okButton = new JButton("Update Exercise");
        JButton cancelButton = new JButton("Cancel");

        // Ties the listener to the update button
        okButton.addActionListener(e -> {
            try {
                String exerciseName = nameField.getText().trim();
                if (exerciseName.isEmpty()) {
                    throw new IllegalArgumentException("Exercise name cannot be empty.");
                }

                double caloriesPerHour = Double.parseDouble(caloriesField.getText().trim());

                // Checks if the exercise exists
                if (program.getExercise(exerciseName) == null) {
                    JOptionPane.showMessageDialog(this, createRedErrorMessage("Exercise not found."), "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Uses the ActionListener to update the exercise
                UpdateExerciseListener listener = new UpdateExerciseListener(program, exerciseName, caloriesPerHour);
                listener.actionPerformed(e);

                JOptionPane.showMessageDialog(this, "Exercise updated successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                SwingUtilities.getWindowAncestor(okButton).dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        createRedErrorMessage("Please enter a valid numeric value for calories."), "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, createRedErrorMessage(ex.getMessage()), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(cancelButton).dispose();
            JOptionPane.showMessageDialog(this, "Update exercise canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        Object[] options = { okButton, cancelButton };
        JOptionPane.showOptionDialog(
                this,
                panel,
                "Update Exercise",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null);
    }

    private void deleteExercise() {

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));

        JTextField nameField = new JTextField();
        panel.add(new JLabel("Exercise Name:"));
        panel.add(nameField);

        JButton okButton = new JButton("Delete Exercise");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(e -> {
            try {
                String exerciseName = nameField.getText().trim();
                if (exerciseName.isEmpty()) {
                    throw new IllegalArgumentException("Exercise name cannot be empty.");
                }

                // Uses the ActionListener to delete the exercise
                DeleteExerciseListener listener = new DeleteExerciseListener(program, exerciseName);
                listener.actionPerformed(e);

                JOptionPane.showMessageDialog(this, "Exercise deleted successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                SwingUtilities.getWindowAncestor(okButton).dispose();

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, createRedErrorMessage(ex.getMessage()), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(cancelButton).dispose();
            JOptionPane.showMessageDialog(this, "Delete exercise canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        Object[] options = { okButton, cancelButton };
        JOptionPane.showOptionDialog(
                this,
                panel,
                "Delete Exercise",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null);
    }

    private void viewExercise() {

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));

        JTextField nameField = new JTextField();
        panel.add(new JLabel("Exercise Name:"));
        panel.add(nameField);

        JButton okButton = new JButton("View Exercise");
        JButton cancelButton = new JButton("Cancel");

        // Tie the listener to the view button
        okButton.addActionListener(e -> {
            try {
                String exerciseName = nameField.getText().trim();
                if (exerciseName.isEmpty()) {
                    throw new IllegalArgumentException("Exercise name cannot be empty.");
                }

                Exercise exercise = program.getExercise(exerciseName);

                if (exercise != null) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Exercise Details:\n" + exercise.toString(),
                            "Exercise Info",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            createRedErrorMessage("Exercise not found."),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

                SwingUtilities.getWindowAncestor(okButton).dispose();

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, createRedErrorMessage(ex.getMessage()), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(cancelButton).dispose();
            JOptionPane.showMessageDialog(this, "View exercise canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        Object[] options = { okButton, cancelButton };
        JOptionPane.showOptionDialog(
                this,
                panel,
                "View Exercise",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null);
    }

    private void exitApp() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            saveNourishment();
            saveLogs();
            saveExercises();
            dispose();
        }
    }

    private void saveNourishment() {
        JButton saveButton = new JButton("Save Nourishment");
        saveButton.addActionListener(new SaveNourishmentListener(program));
        JOptionPane.showMessageDialog(
                this,
                "Save Nourishment Data?",
                "Save Nourishment",
                JOptionPane.INFORMATION_MESSAGE,
                null);
        saveButton.doClick(); // Triggers save when called

        JOptionPane.showMessageDialog(this, "Data saved successfully.");

    }

    public void saveLogs() {
        JButton saveButton = new JButton("Save Logs");
        saveButton.addActionListener(new SaveLogListener(program));
        JOptionPane.showMessageDialog(
                this,
                "Save Log Data?",
                "Save Logs",
                JOptionPane.INFORMATION_MESSAGE,
                null);
        saveButton.doClick();

        JOptionPane.showMessageDialog(this, "Data saved successfully.");
    }

    public void saveExercises() {
        JButton saveButton = new JButton("Save Exercises");
        saveButton.addActionListener(new SaveExerciseListener(program));
        JOptionPane.showMessageDialog(
                this,
                "Save Exercise Data?",
                "Save Exercises",
                JOptionPane.INFORMATION_MESSAGE,
                null);
        saveButton.doClick();

        JOptionPane.showMessageDialog(this, "Data saved successfully.");
    }
}
