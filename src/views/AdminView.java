package views;

import models.User;
import models.Appointment;
import models.InventoryItem;

import java.util.List;
import java.util.Scanner;

/**
 * Class representing the administrator view in the hospital management system.
 */
public class AdminView {
    private Scanner scanner;

    /**
     * Constructs an AdminView object.
     */
    public AdminView() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the administrator menu.
     */
    public void displayMenu() {
        System.out.println("\nAdministrator Menu:");
        System.out.println("1. View and Manage Hospital Staff");
        System.out.println("2. View Appointment Details");
        System.out.println("3. View and Manage Medication Inventory");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("5. Logout");
    }

    /**
     * Gets the user's menu choice.
     *
     * @return The user's choice.
     */
    public int getUserChoice() {
        System.out.print("Enter your choice: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Displays the staff management menu.
     */
    public void displayStaffMenu() {
        System.out.println("Staff Management Menu:");
        System.out.println("1. Add Staff Member");
        System.out.println("2. Update Staff Member");
        System.out.println("3. Remove Staff Member");
        System.out.println("4. View Staff Members");
        System.out.println("5. Return to Main Menu");
    }
    

    /**
     * Displays a list of staff members.
     *
     * @param staff The list of staff members.
     */
    public void displayStaffList(List<User> staff) {
        System.out.println("\nHospital Staff:");
        for (User user : staff) {
            System.out.println("User ID: " + user.getUserID());
            System.out.println("Name: " + user.getName());
            System.out.println("Role: " + user.getRole());
            System.out.println("-----------------------");
        }
    }

    /**
     * Gets the user ID input from the user.
     *
     * @return The user ID.
     */
    public String getUserIDInput() {
        System.out.print("Enter User ID: ");
        return scanner.nextLine();
    }

    /**
     * Gets the name input from the user.
     *
     * @return The name.
     */
    public String getNameInput() {
        System.out.print("Enter Name: ");
        return scanner.nextLine();
    }

    /**
     * Gets the password input from the user.
     *
     * @return The password.
     */
    public String getPasswordInput() {
        System.out.print("Enter Password: ");
        return scanner.nextLine();
    }

    /**
     * Gets the role input from the user.
     *
     * @return The role.
     */
    public String getRoleInput() {
        System.out.print("Enter Role (Doctor/Pharmacist): ");
        return scanner.nextLine();
    }

    /**
     * Displays appointment details.
     *
     * @param appointments The list of appointments.
     */
    public void displayAppointments(List<Appointment> appointments) {
        System.out.println("\nAppointment Details:");
        for (Appointment appt : appointments) {
            System.out.println("Appointment ID: " + appt.getAppointmentID());
            // Display other details
        }
    }

    /**
     * Displays medication inventory.
     *
     * @param inventory The list of inventory items.
     */
    public void displayInventory(List<InventoryItem> inventory) {
        System.out.println("\nMedication Inventory:");
        for (InventoryItem item : inventory) {
            System.out.println("Medication Name: " + item.getMedicationName());
            // Display other details
        }
    }

    /**
     * Gets the low stock alert level input from the user.
     *
     * @return The alert level.
     */
    public int getLowStockAlertLevelInput() {
        System.out.print("Enter Low Stock Alert Level: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Displays replenishment requests.
     *
     * @param requests The list of replenishment requests.
     */
    public void displayReplenishmentRequests(List<String> requests) {
        System.out.println("\nReplenishment Requests:");
        for (String request : requests) {
            System.out.println(request);
        }
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
