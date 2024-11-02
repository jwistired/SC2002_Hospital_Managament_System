package main;

import controllers.LoginController;
import views.LoginView;

/**
 * Main class to start the Hospital Management System application.
 */
public class HMSApplication {
    /**
     * The main method to run the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Hospital Management System!");

        // Initialize system data only if not already initialized
        SystemInitializer.initializeSystem();

        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginView);
        loginController.authenticate();
    }
}