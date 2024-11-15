package controllers;

import models.Administrator;
import models.User;
import models.Doctor;
import models.Pharmacist;
import models.Appointment;
import models.InventoryItem;
import views.AdminView;
import utils.SerializationUtil;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller class for handling administrator-related operations.
 */
public class AdminController {
    private Administrator model;
    private AdminView view;
    private HashMap<String, User> users;
    private List<Appointment> appointments;
    private List<InventoryItem> inventory;

    /**
     * Constructs an AdminController object.
     *
     * @param model The administrator model.
     * @param view  The administrator view.
     */
    public AdminController(Administrator model, AdminView view) {
        this.model = model;
        this.view = view;
        loadUsers();
        loadAppointments();
        loadInventory();
    }

    /**
     * Starts the administrator menu loop.
     */
    public void start() {
        int choice;
        do {
            view.displayMenu();
            choice = view.getUserChoice();
            switch (choice) {
                case 1:
                    manageHospitalStaff();
                    break;
                case 2:
                    viewAppointmentDetails();
                    break;
                case 3:
                    manageMedicationInventoryAndReplenishment();
                    break;
                case 4:
                    view.displayMessage("Logging out...");
                    break;
                default:
                    view.displayMessage("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }


        /**
     * Manages hospital staff by adding, updating, or removing staff members.
     */
    private void manageHospitalStaff() {
        int choice;
        do {
            view.displayStaffMenu();
            choice = view.getUserChoice();
            switch (choice) {
                case 1:
                    addStaffMember();
                    break;
                case 2:
                    updateStaffMember();
                    break;
                case 3:
                    removeStaffMember();
                    break;
                case 4:
                    viewStaffMembers();
                    break;
                case 5:
                    view.displayMessage("Returning to main menu...");
                    break;
                default:
                    view.displayMessage("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    /**
    * Displays a list of all staff members.
    */
    private void viewStaffMembers() {
        if (users.isEmpty()) {
            view.displayMessage("No staff members found.");
            return;
        }
        view.displayStaffList(new ArrayList<>(users.values()));
    }

    /**
     * Adds a new staff member.
     */
    private void addStaffMember() {
        String userID = view.getUserIDInput();
        String name = view.getNameInput();
        String password = view.getPasswordInput();
        String role = view.getRoleInput();

        User newUser = null;
        if (role.equalsIgnoreCase("Doctor")) {
            newUser = new Doctor(userID, name, password);
        } else if (role.equalsIgnoreCase("Pharmacist")) {
            newUser = new Pharmacist(userID, name, password);
        }

        if (newUser != null) {
            users.put(userID, newUser);
            saveUsers();
            view.displayMessage("Staff member added.");
        } else {
            view.displayMessage("Invalid role.");
        }
    }

    /**
     * Updates an existing staff member.
     */
    private void updateStaffMember() {
        String userID = view.getUserIDInput();
        if (!users.containsKey(userID)) {
            view.displayMessage("User ID not found.");
            return;
        }
    
        User oldUser = users.get(userID);
        String newName = view.getNameInput();
        String newPassword = view.getPasswordInput();
    
        // Create a new User object with updated details
        User updatedUser = null;
        if (oldUser instanceof Doctor) {
            updatedUser = new Doctor(userID, newName, newPassword);
        } else if (oldUser instanceof Pharmacist) {
            updatedUser = new Pharmacist(userID, newName, newPassword);
        } else {
            view.displayMessage("Unknown user role. Cannot update.");
            return;
        }
    
        // Replace the old user with the updated user
        users.put(userID, updatedUser);
        saveUsers();
        view.displayMessage("Staff member updated successfully.");
    }
    

    /**
     * Removes a staff member.
     */
    private void removeStaffMember() {
        String userID = view.getUserIDInput();
        if (users.containsKey(userID)) {
            users.remove(userID);
            saveUsers();
            view.displayMessage("Staff member removed.");
        } else {
            view.displayMessage("User ID not found.");
        }
    }


    /**
     * Manages medication inventory and approves replenishment requests.
     */
    private void manageMedicationInventoryAndReplenishment() {
        int choice;
        do {
            view.displayMedicationMenu();
            choice = view.getMedicationMenuChoice();
            switch (choice) {
                case 1:
                    addInventoryItem();
                    break;
                case 2:
                    updateInventoryItem();
                    break;
                case 3:
                    removeInventoryItem();
                    break;
                case 4:
                    viewInventoryItems();
                    break;
                case 5:
                    approveReplenishmentRequest();
                    break;
                case 6:
                    view.displayMessage("Returning to main menu...");
                    break;
                default:
                    view.displayMessage("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    /**
     * Adds a new inventory item.
     */
    private void addInventoryItem() {
        String medicationName = view.getMedicationNameInput();
        int stockLevel = view.getInventoryItemQuantityInput();
        int lowStockAlertLevel = view.getLowStockAlertLevelInput(); // Ensure this method exists in AdminView

        InventoryItem newItem = new InventoryItem(medicationName, stockLevel, lowStockAlertLevel);
        inventory.add(newItem);
        saveInventory();
        view.displayMessage("Inventory item added successfully.");
    }

    /**
     * Updates an existing inventory item.
     */
    private void updateInventoryItem() {
        String medicationName = view.getMedicationNameInput();
        InventoryItem item = findInventoryItemByName(medicationName);

        if (item == null) {
            view.displayMessage("Medication not found in inventory.");
            return;
        }

        System.out.println("Current Stock Level: " + item.getStockLevel());
        int newStockLevel = view.getInventoryItemQuantityInput();
        item.setStockLevel(newStockLevel);

        System.out.println("Current Low Stock Alert Level: " + item.getLowStockAlertLevel());
        int newLowStockAlertLevel = view.getLowStockAlertLevelInput();
        item.setLowStockAlertLevel(newLowStockAlertLevel);

        saveInventory();
        view.displayMessage("Inventory item updated successfully.");
    }

    /**
     * Removes an inventory item.
     */
    private void removeInventoryItem() {
        String medicationName = view.getMedicationNameInput();
        InventoryItem item = findInventoryItemByName(medicationName);

        if (item != null) {
            inventory.remove(item);
            saveInventory();
            view.displayMessage("Inventory item removed successfully.");
        } else {
            view.displayMessage("Medication not found in inventory.");
        }
    }

    /**
     * Displays all inventory items.
     */
    private void viewInventoryItems() {
        view.displayInventory(inventory);
    }

    /**
     * Approves a replenishment request by updating inventory quantities.
     */
    private void approveReplenishmentRequest() {
        List<InventoryItem> pendingRequests = getPendingReplenishmentRequests();
        view.displayReplenishmentRequests(pendingRequests);

        if (pendingRequests.isEmpty()) {
            return;
        }

        String medicationName = view.getReplenishmentApprovalInput();
        InventoryItem itemToApprove = findInventoryItemByName(medicationName);

        if (itemToApprove != null && itemToApprove.getReplenishRequestAmount() > 0) {
            int replenishAmount = itemToApprove.getReplenishRequestAmount();
            itemToApprove.setStockLevel(itemToApprove.getStockLevel() + replenishAmount);
            itemToApprove.setReplenishRequestAmount(0); // Reset after approval
            saveInventory();
            view.displayMessage("Replenishment request approved and inventory updated.");
        } else {
            view.displayMessage("Invalid Medication Name or no pending replenishment request for this medication.");
        }
    }

    /**
     * Retrieves inventory items with pending replenishment requests.
     *
     * @return List of inventory items with replenishRequestAmount > 0.
     */
    private List<InventoryItem> getPendingReplenishmentRequests() {
        List<InventoryItem> pending = new ArrayList<>();
        for (InventoryItem item : inventory) {
            if (item.getReplenishRequestAmount() > 0) {
                pending.add(item);
            }
        }
        return pending;
    }

    /**
     * Finds an inventory item by its medication name.
     *
     * @param medicationName The name of the medication.
     * @return The InventoryItem object or null if not found.
     */
    private InventoryItem findInventoryItemByName(String medicationName) {
        for (InventoryItem item : inventory) {
            if (item.getMedicationName().equalsIgnoreCase(medicationName)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Displays appointment details.
     */
    private void viewAppointmentDetails() {
        view.displayAppointments(appointments);
    }
      

    /**
     * Loads users from the serialized file.
     */
    private void loadUsers() {
        try {
            users = (HashMap<String, User>) SerializationUtil.deserialize("users.ser");
        } catch (Exception e) {
            users = new HashMap<>();
        }
    }

    /**
     * Saves users to the serialized file.
     */
    private void saveUsers() {
        try {
            SerializationUtil.serialize(users, "users.ser");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads appointments from the serialized file.
     */
    private void loadAppointments() {
        try {
            appointments = (List<Appointment>) SerializationUtil.deserialize("appointments.ser");
        } catch (Exception e) {
            appointments = new ArrayList<>();
        }
    }

    /**
     * Saves appointments to the serialized file.
     */
    private void saveAppointments() {
        try {
            SerializationUtil.serialize(appointments, "appointments.ser");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads inventory from the serialized file.
     */
    private void loadInventory() {
        try {
            inventory = (List<InventoryItem>) SerializationUtil.deserialize("inventory.ser");
        } catch (Exception e) {
            inventory = new ArrayList<>();
        }
    }

    /**
     * Saves inventory to the serialized file.
     */
    private void saveInventory() {
        try {
            SerializationUtil.serialize(inventory, "inventory.ser");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
