package views;

import java.util.List;
import java.util.Scanner;
import models.InventoryItem;
import models.Prescription;

/**
 * Class representing the pharmacist view in the hospital management system.
 * This class provides methods for the pharmacist to interact with the system,
 * including managing prescriptions, updating medication inventory, and submitting
 * replenishment requests.
 */
public class PharmacistView {
    private Scanner scanner;

    /**
     * Constructs a PharmacistView object.
     * Initializes the scanner for user input.
     */
    public PharmacistView() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the pharmacist menu.
     * This menu allows the pharmacist to select options for viewing appointment outcomes,
     * updating prescription statuses, managing medication inventory, and submitting replenishment requests.
     */
    public void displayMenu() {
        System.out.println("\nPharmacist Menu:");
        System.out.println("1. View Appointment Outcome Records");
        System.out.println("2. Update Prescription Status");
        System.out.println("3. View Medication Inventory");
        System.out.println("4. Submit Replenishment Request");
        System.out.println("5. Logout");
    }

    /**
     * Gets the user's menu choice.
     * Prompts the user to enter their choice and returns the selected option as an integer.
     *
     * @return The user's choice as an integer.
     */
    public int getUserChoice() {
        System.out.print("Enter your choice: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Displays a list of prescriptions.
     * This method displays the medication name, quantity, and status for each prescription.
     *
     * @param prescriptions The list of prescriptions to display.
     */
    public void displayPrescriptions(List<Prescription> prescriptions) {
        System.out.println("\nPrescriptions:");
        for (Prescription presc : prescriptions) {
            System.out.println("Medication Name: " + presc.getMedicationName());
            System.out.println("Quantity: " + presc.getQuantity());
            System.out.println("Status: " + presc.getStatus());
            System.out.println("-----------------------");
        }
    }

    /**
     * Gets the medication name input from the user.
     * Prompts the user to enter the medication name and returns the input.
     *
     * @return The medication name as a string.
     */
    public String getMedicationNameInput() {
        System.out.print("Enter Medication Name: ");
        return scanner.nextLine();
    }

    /**
     * Displays the medication inventory.
     * This method shows the medication name, stock level, and low stock alert level for each inventory item.
     *
     * @param inventory The list of inventory items to display.
     */
    public void displayInventory(List<InventoryItem> inventory) {
        System.out.println("\nMedication Inventory:");
        for (InventoryItem item : inventory) {
            System.out.println("Medication Name: " + item.getMedicationName());
            System.out.println("Stock Level: " + item.getStockLevel());
            System.out.println("Low Stock Alert Level: " + item.getLowStockAlertLevel());
            System.out.println("-----------------------");
        }
    }

    /**
     * Gets the quantity input from the user.
     * Prompts the user to enter the quantity for prescriptions or replenishment requests.
     *
     * @return The quantity as an integer.
     */
    public int getQuantityInput() {
        System.out.print("Enter Quantity: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Displays a message to the user.
     * Prints a specified message to the console.
     *
     * @param message The message to display.
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * Gets the appointment ID input from the user.
     * Prompts the user to enter an appointment ID to dispense medication.
     *
     * @return The appointment ID as a string.
     */
    public String getAppointmentIDInput() {
        System.out.print("Enter AppointmentID to dispense: ");
        return scanner.nextLine();
    }
}
