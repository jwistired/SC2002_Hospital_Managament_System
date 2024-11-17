package views;

import java.util.List;
import java.util.Scanner;
import models.InventoryItem;
import models.Prescription;

/**
 * Class representing the pharmacist view in the hospital management system.
 */
public class PharmacistView {
    private Scanner scanner;

    /**
     * Constructs a PharmacistView object.
     */
    public PharmacistView() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the pharmacist menu.
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
     *
     * @return The user's choice.
     */
    public int getUserChoice() {
        System.out.print("Enter your choice: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Displays a list of prescriptions.
     *
     * @param prescriptions The list of prescriptions.
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
     *
     * @return The medication name.
     */
    public String getMedicationNameInput() {
        System.out.print("Enter Medication Name: ");
        return scanner.nextLine();
    }

    /**
     * Displays the medication inventory.
     *
     * @param inventory The list of inventory items.
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
     *
     * @return The quantity.
     */
    public int getQuantityInput() {
        System.out.print("Enter Quantity: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Displays a message to the user.
     *
     * @param message The message to display.
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }
    public String getAppointmentIDInput() {
        System.out.print("Enter AppointmentID to dispense: ");
        return scanner.nextLine();
    }

}
