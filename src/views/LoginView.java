// views/LoginView.java

package views;

import java.util.Scanner;

/**
 * Class representing the login view for the hospital management system.
 */
public class LoginView {
    private Scanner scanner;

    /**
     * Constructs a LoginView object.
     */
    public LoginView() {
        scanner = new Scanner(System.in);
    }

    /**
     * Gets the user ID input from the user.
     *
     * @return The user ID.
     */
    public String getUserID() {
        System.out.print("Enter User ID: ");
        return scanner.nextLine();
    }

    /**
     * Gets the password input from the user.
     *
     * @return The password.
     */
    public String getPassword() {
        System.out.print("Enter Password: ");
        return scanner.nextLine();
    }

    /**
     * Gets the new password input from the user.
     *
     * @return The new password.
     */
    public String getNewPassword() {
        System.out.print("Enter New Password: ");
        return scanner.nextLine();
    }

    /**
     * Gets the confirmation of the new password input from the user.
     *
     * @return The confirmation password.
     */
    public String getConfirmPassword() {
        System.out.print("Confirm New Password: ");
        return scanner.nextLine();
    }

    /**
     * Displays a message to the user.
     *
     * @param message The message to display.
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }
}