package defaultMain;

import model.HealthProgram;
import view.ViewGUI;

/**
 * Health application default file |
 * This creates and run the program
 */
public class HealthApplication {
    public static void main(String[] args) {
        HealthProgram program = new HealthProgram();
        // ViewCLI view;
        // view = new ViewCLI(program);
        // view.display();

        ViewGUI gui;
        gui = new ViewGUI(program);
        gui.display();

    }
}
