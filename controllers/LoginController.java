// controllers/LoginController.java

package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import models.Administrator;
import models.Doctor;
import models.Patient;
import models.Pharmacist;
import models.User;
import utils.PasswordUtil;
import utils.SerializationUtil;
import views.LoginView;

/**
 * Controller class for handling user login and authentication in the Hospital Management System.
 * This class is responsible for authenticating users, managing their login flow,
 * and redirecting them to the appropriate role-specific controller.
 */
public class LoginController {
    private LoginView view;
    private HashMap<String, User> users;

    /**
     * Constructs a LoginController object that handles the login process.
     *
     * @param view The view object for displaying login-related messages and inputs.
     */
    public LoginController(LoginView view) {
        this.view = view;
        loadUsers();
    }

    /**
     * Loads users from a serialized file.
     * If no user data exists, creates a default administrator account.
     */
    private void loadUsers() {
        try {
            users = (HashMap<String, User>) SerializationUtil.deserialize("users.ser");
            System.out.println("User data loaded successfully.");
        } catch (FileNotFoundException e) {
            // users.ser does not exist; create default users
            users = new HashMap<>();
            Administrator admin = new Administrator("admin", "Administrator", "password");
            users.put(admin.getUserID(), admin);
            saveUsers();
            System.out.println("No existing user data found. Created default administrator account.");
        } catch (IOException | ClassNotFoundException e) {
            view.displayMessage("Error loading user data: " + e.getMessage());
            e.printStackTrace();
            System.exit(1); // Terminate the program to prevent data loss
        }
    }

    /**
     * Authenticates the user based on the input credentials.
     * If the credentials are valid, redirects the user to their role-specific controller.
     * If authentication fails, prompts the user to try again.
     */
    public void authenticate() {
        String userID = view.getUserID();
        String password = view.getPassword();

        User user = users.get(userID);
        if (user != null) {
            String hashedInputPassword = PasswordUtil.hashPassword(password, user.getSalt());
            if (hashedInputPassword.equals(user.getHashedPassword())) {
                view.displayMessage("Login successful!\n");

                // Check if it's the user's first login
                if (user.isFirstLogin()) {
                    promptPasswordChange(user);
                }

                redirectToRoleController(user);
            } else {
                view.displayMessage("Invalid password.");
                authenticate(); // Retry authentication
            }
        } else {
            view.displayMessage("User ID not found.");
            authenticate(); // Retry authentication
        }
    }

    /**
     * Prompts the user to change their password if it is their first login.
     * Ensures that the new password and confirm password match before updating the user's password.
     *
     * @param user The user who needs to change their password.
     */
    private void promptPasswordChange(User user) {
        view.displayMessage("You are required to change your password upon first login.");
        String newPassword = view.getNewPassword();
        String confirmPassword = view.getConfirmPassword();

        while (!newPassword.equals(confirmPassword)) {
            view.displayMessage("Passwords do not match. Please try again.");
            newPassword = view.getNewPassword();
            confirmPassword = view.getConfirmPassword();
        }

        // Update the user's password
        user.setSalt(PasswordUtil.getSalt());
        user.setHashedPassword(PasswordUtil.hashPassword(newPassword, user.getSalt()));
        user.setFirstLogin(false);

        saveUsers();

        view.displayMessage("Password changed successfully.\n");
    }

    /**
     * Redirects the authenticated user to their respective controller based on their role.
     *
     * @param user The authenticated user.
     */
    private void redirectToRoleController(User user) {
        switch (user.getRole()) {
            case "Patient":
                PatientController patientController = new PatientController((Patient) user, new views.PatientView());
                patientController.start();
                break;
            case "Doctor":
                DoctorController doctorController = new DoctorController((Doctor) user, new views.DoctorView());
                doctorController.start();
                break;
            case "Pharmacist":
                PharmacistController pharmacistController = new PharmacistController((Pharmacist) user,
                        new views.PharmacistView());
                pharmacistController.start();
                break;
            case "Administrator":
                AdminController adminController = new AdminController((Administrator) user, new views.AdminView());
                adminController.start();
                break;
            default:
                view.displayMessage("Invalid role.");
        }
        // After the user logs out, terminate the program
        view.displayMessage("Thank you for using the Hospital Management System. Goodbye!");
        System.exit(0);
    }

    /**
     * Saves the users to the serialized file to preserve their data.
     */
    private void saveUsers() {
        try {
            SerializationUtil.serialize(users, "users.ser");
            System.out.println("Data saved successfully");
        } catch (IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}