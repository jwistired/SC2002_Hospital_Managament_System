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
     * Displays the main administrator menu.
     */
    public void displayMenu() {
        System.out.println("\n--- Administrator Menu ---");
        System.out.println("1. View and Manage Hospital Staff");
        System.out.println("2. View Appointment Details");
        System.out.println("3. Manage Medication Inventory & Approve Replenishment Requests");
        System.out.println("4. Logout");
    }

    /**
     * Displays the staff management submenu.
     */
    public void displayStaffMenu() {
        System.out.println("\n--- Manage Hospital Staff ---");
        System.out.println("1. Add Staff Member");
        System.out.println("2. Update Staff Member");
        System.out.println("3. Remove Staff Member");
        System.out.println("4. View All Staff Members");
        System.out.println("5. Return to Main Menu");
    }

    /**
     * Displays the medication inventory and replenishment submenu.
     */
    public void displayMedicationMenu() {
        System.out.println("\n--- Medication Inventory & Replenishment ---");
        System.out.println("1. Add Inventory Item");
        System.out.println("2. Update Inventory Item");
        System.out.println("3. Remove Inventory Item");
        System.out.println("4. View Inventory Items");
        System.out.println("5. Approve Replenishment Requests");
        System.out.println("6. Return to Main Menu");
    }

    /**
     * Displays a list of staff members.
     *
     * @param staff The list of staff members.
     */
    public void displayStaffList(List<User> staff) {
        System.out.println("\n--- Hospital Staff ---");
        if (staff.isEmpty()) {
            System.out.println("No staff members found.");
            return;
        }
        for (User user : staff) {
            System.out.println("User ID: " + user.getUserID());
            System.out.println("Name: " + user.getName());
            System.out.println("Role: " + user.getRole());
            System.out.println("-----------------------");
        }
    }

    /**
     * Displays appointment details.
     *
     * @param appointments The list of appointments.
     */
    public void displayAppointments(List<Appointment> appointments) {
        System.out.println("\n--- Appointment Details ---");
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        for (Appointment appt : appointments) {
            System.out.println("Appointment ID: " + appt.getAppointmentID());
            // Add more details as needed
            System.out.println("-----------------------");
        }
    }

    /**
     * Displays medication inventory.
     *
     * @param inventory The list of inventory items.
     */
    public void displayInventory(List<InventoryItem> inventory) {
        System.out.println("\n--- Medication Inventory ---");
        if (inventory.isEmpty()) {
            System.out.println("No inventory items found.");
            return;
        }
        for (InventoryItem item : inventory) {
            System.out.println("Medication Name: " + item.getMedicationName());
            System.out.println("Stock Level: " + item.getStockLevel());
            System.out.println("Low Stock Alert Level: " + item.getLowStockAlertLevel());
            if (item.getReplenishRequestAmount() > 0) {
                System.out.println("Replenishment Requested: " + item.getReplenishRequestAmount());
            }
            System.out.println("-----------------------");
        }
    }

    /**
     * Displays replenishment requests based on inventory items with pending requests.
     *
     * @param inventory The list of inventory items.
     */
    public void displayReplenishmentRequests(List<InventoryItem> inventory) {
        System.out.println("\n--- Replenishment Requests ---");
        boolean hasRequests = false;
        for (InventoryItem item : inventory) {
            if (item.getReplenishRequestAmount() > 0) {
                hasRequests = true;
                System.out.println("Medication Name: " + item.getMedicationName());
                System.out.println("Requested Quantity: " + item.getReplenishRequestAmount());
                System.out.println("-----------------------");
            }
        }
        if (!hasRequests) {
            System.out.println("No replenishment requests found.");
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
     * Gets the user's main menu choice.
     *
     * @return The user's choice as an integer.
     */
    public int getUserChoice() {
        System.out.print("Enter your choice: ");
        while (true) {
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                return choice;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    /**
     * Gets the staff-related menu choice.
     *
     * @return The user's choice as an integer.
     */
    public int getStaffMenuChoice() {
        System.out.print("Enter your choice: ");
        while (true) {
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                return choice;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    /**
     * Gets the medication inventory-related menu choice.
     *
     * @return The user's choice as an integer.
     */
    public int getMedicationMenuChoice() {
        System.out.print("Enter your choice: ");
        while (true) {
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                return choice;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    /**
     * Gets the user ID input from the user.
     *
     * @return The user ID as a string.
     */
    public String getUserIDInput() {
        System.out.print("Enter User ID: ");
        return scanner.nextLine();
    }

    /**
     * Gets the name input from the user.
     *
     * @return The name as a string.
     */
    public String getNameInput() {
        System.out.print("Enter Name: ");
        return scanner.nextLine();
    }

    /**
     * Gets the password input from the user.
     *
     * @return The password as a string.
     */
    public String getPasswordInput() {
        System.out.print("Enter Password: ");
        return scanner.nextLine();
    }

    /**
     * Gets the role input from the user.
     *
     * @return The role as a string.
     */
    public String getRoleInput() {
        System.out.print("Enter Role (Doctor/Pharmacist): ");
        return scanner.nextLine();
    }

    /**
     * Gets the inventory item ID input from the user.
     * Note: Since InventoryItem does not have an itemId, we'll use medicationName instead.
     *
     * @return The medication name as a string.
     */
    public String getMedicationNameInput() {
        System.out.print("Enter Medication Name: ");
        return scanner.nextLine();
    }

    /**
     * Gets the inventory item quantity input from the user.
     *
     * @return The inventory item quantity as an integer.
     */
    public int getInventoryItemQuantityInput() {
        System.out.print("Enter Inventory Item Quantity: ");
        while (true) {
            String input = scanner.nextLine();
            try {
                int quantity = Integer.parseInt(input);
                if (quantity < 0) {
                    System.out.print("Quantity cannot be negative. Enter again: ");
                } else {
                    return quantity;
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    /**
     * Gets the replenish request approval input from the user.
     *
     * @return The medication name to approve as a string.
     */
    public String getReplenishmentApprovalInput() {
        System.out.print("Enter Medication Name to approve replenishment: ");
        return scanner.nextLine();
    }

    /**
     * Gets the replenish request amount input from the user.
     *
     * @return The replenish request amount as an integer.
     */
    public int getReplenishRequestAmountInput() {
        System.out.print("Enter Replenishment Amount: ");
        while (true) {
            String input = scanner.nextLine();
            try {
                int amount = Integer.parseInt(input);
                if (amount <= 0) {
                    System.out.print("Replenishment amount must be positive. Enter again: ");
                } else {
                    return amount;
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    /**
     * Displays a generic message to the user.
     *
     * @param message The message to display.
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }
}
