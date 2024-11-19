package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import models.Administrator;
import models.Appointment;
import models.Doctor;
import models.InventoryItem;
import models.Pharmacist;
import models.User;
import utils.SerializationUtil;
import views.AdminView;

/**
 * Controller class for handling administrator-related operations in the hospital management system.
 * This class provides functionalities for managing hospital staff, viewing and managing doctor schedules,
 * handling appointments, and managing medication inventory.
 */
public class AdminController {
    private Administrator model;
    private AdminView view;
    private HashMap<String, User> users;
    private List<Appointment> appointments;
    private List<InventoryItem> inventory;
    private HashMap<String, Doctor> doctors; // To manage doctor-specific operations

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
        loadDoctors(); // Initialize doctors map
    }

    /**
     * Starts the administrator menu loop, allowing the admin to interact with various options.
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
                    viewDoctorSchedulesMenu(); // New menu option
                    break;
                case 5:
                    view.displayMessage("Logging out...");
                    break;
                default:
                    view.displayMessage("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    /**
     * Loads doctor information from the users map and initializes the doctors map.
     */
    @SuppressWarnings("unchecked")
    private void loadDoctors() {
        doctors = new HashMap<>();
        for (User user : users.values()) {
            if (user instanceof Doctor) {
                doctors.put(user.getUserID(), (Doctor) user);
            }
        }
    }

    /**
     * Displays a menu for managing doctor schedules, including viewing all schedules
     * or an individual doctor's schedule.
     */
    private void viewDoctorSchedulesMenu() {
        int choice;
        do {
            view.displayDoctorScheduleMenu();
            choice = view.getUserChoice();
            switch (choice) {
                case 1:
                    viewAllDoctorSchedule();
                    break;
                case 2:
                    String doctorID = view.getDoctorIDInput();
                    viewIndividualDoctorSchedule(doctorID);
                    break;
                case 3:
                    view.displayMessage("Returning to main menu...");
                    break;
                default:
                    view.displayMessage("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    /**
     * Displays the schedules of all doctors.
     */
    public void viewAllDoctorSchedule() {
        if (doctors.isEmpty()) {
            view.displayMessage("No doctors found in the system.");
            return;
        }

        for (String doctorID : doctors.keySet()) {
            view.displayMessage("--------------------------------------------------");
            view.displayMessage("Doctor ID: " + doctorID);
            viewIndividualDoctorSchedule(doctorID);
            view.displayMessage("--------------------------------------------------\n");
        }
    }

    /**
     * Displays the schedule of an individual doctor.
     *
     * @param doctorID The ID of the doctor whose schedule is to be viewed.
     */
    public void viewIndividualDoctorSchedule(String doctorID) {
        Doctor doctor = doctors.get(doctorID);
        if (doctor == null) {
            view.displayMessage("Doctor with ID " + doctorID + " not found.");
            return;
        }

        // Load the doctor's schedule from a serialized file
        loadDoctorSchedule(doctor);

        List<String> schedule = doctor.getSchedule();

        if (schedule == null || schedule.isEmpty()) {
            view.displayMessage("No schedule available for Dr. " + doctor.getName() + ".");
            return;
        }
        view.displayMessage("Schedule for Dr. " + doctor.getName() + " (ID: " + doctorID + "):");
        view.displayDoctorSchedule(schedule, appointments, doctorID);
    }

    /**
     * Loads the schedule for a specific doctor from a serialized file.
     *
     * @param doctor The Doctor object whose schedule is to be loaded.
     */
    private void loadDoctorSchedule(Doctor doctor) {
        try {
            String fileName = "Schedule_" + doctor.getUserID() + ".ser";
            // Load schedule from serialized file
            List<String> loadedSchedule = (List<String>) SerializationUtil.deserialize(fileName);
            doctor.setSchedule(loadedSchedule);
        } catch (Exception e) {
            view.displayMessage("Error loading schedule for Dr. " + doctor.getName());
        }
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
     * Displays a list of all staff members, including doctors, pharmacists, and administrators.
     */
    private void viewStaffMembers() {
        List<User> staffList = new ArrayList<>();

        for (User user : users.values()) {
            if (user instanceof Doctor || user instanceof Pharmacist || user instanceof Administrator) {
                staffList.add(user);
            }
        }

        if (staffList.isEmpty()) {
            view.displayMessage("No staff members found.");
            return;
        }

        view.displayStaffList(staffList);
    }


    /**
     * Adds a new staff member to the system.
     * Prompts the user for user ID, name, password, and role, and validates the input.
     * Creates a new user if the role is valid and the user ID does not already exist.
     * Updates the users and doctors map if applicable and persists the changes.
     */
    private void addStaffMember() {
        String userID = view.getUserIDInput();
        String name = view.getNameInput();
        String password = view.getPasswordInput();
        String role = view.getRoleInput();

        if (users.containsKey(userID)) {
            view.displayMessage("User ID already exists. Please try a different ID.");
            return;
        }

        User newUser = null;
        if (role.equalsIgnoreCase("Doctor")) {
            newUser = new Doctor(userID, name, password);
        } else if (role.equalsIgnoreCase("Pharmacist")) {
            newUser = new Pharmacist(userID, name, password);
        } else if (role.equalsIgnoreCase("Admin")) {
            newUser = new Administrator(userID, name, password);
        }

        if (newUser != null) {
            users.put(userID, newUser);
            saveUsers();
            loadDoctors(); // Update doctors map if a doctor was added
            view.displayMessage("Staff member added.");
        } else {
            view.displayMessage("Invalid role. Only Admin, Doctor and Pharmacist roles are allowed.");
        }
    }

    /**
     * Updates an existing staff member's details.
     * Prompts the user for the user ID, validates its existence, and displays current details.
     * Updates the user's name and password and persists the changes.
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

        // Display current details before updating
        view.displayMessage("Current Name: " + oldUser.getName());
        view.displayMessage("Current Password: " + oldUser.getHashedPassword());

        // Create a new User object with updated details
        User updatedUser = null;
        if (oldUser instanceof Doctor) {
            updatedUser = new Doctor(userID, newName, newPassword);
        } else if (oldUser instanceof Pharmacist) {
            updatedUser = new Pharmacist(userID, newName, newPassword);
        } else if (oldUser instanceof Administrator) {
            updatedUser = new Administrator(userID, newName, newPassword);
        }
        else {
            view.displayMessage("Unknown user role. Cannot update.");
            return;
        }

        // Replace the old user with the updated user
        users.put(userID, updatedUser);
        saveUsers();
        loadDoctors(); // Update doctors map if a doctor was updated
        view.displayMessage("Staff member updated successfully.");
    }

    /**
     * Removes an existing staff member.
     * Prompts the user for the user ID and validates its existence.
     * Removes the user and updates the doctors map if necessary, then persists the changes.
     */
    private void removeStaffMember() {
        String userID = view.getUserIDInput();
        if (users.containsKey(userID)) {
            users.remove(userID);
            saveUsers();
            loadDoctors(); // Update doctors map if a doctor was removed
            view.displayMessage("Staff member removed.");
        } else {
            view.displayMessage("User ID not found.");
        }
    }

    /**
     * Manages the medication inventory, allowing the user to add, update, remove,
     * view inventory items, and approve replenishment requests.
     */
    private void manageMedicationInventoryAndReplenishment() {
        int choice;
        do {
            view.displayMedicationMenu();
            choice = view.getUserChoice();
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
     * Adds a new inventory item to the system.
     * Validates input for medication name, stock level, and low stock alert level.
     * Ensures the medication does not already exist before adding it.
     */
    private void addInventoryItem() {
        String medicationName = view.getMedicationNameInput().trim();
        
        // Validate medication name
        if (medicationName.isEmpty()) {
            view.displayMessage("Error: Medication name cannot be empty.");
            return;
        }
    
        int stockLevel = view.getInventoryItemQuantityInput();
        int lowStockAlertLevel = view.getLowStockAlertLevelInput();
    
        // Validate stock levels
        if (stockLevel < 0) {
            view.displayMessage("Error: Stock level cannot be negative.");
            return;
        }
    
        if (lowStockAlertLevel < 0) {
            view.displayMessage("Error: Low stock alert level cannot be negative.");
            return;
        }
    
        // Check if the medication already exists in the inventory (ignoring case)
        boolean exists = inventory.stream()
                .anyMatch(item -> item.getMedicationName().equalsIgnoreCase(medicationName));
    
        if (exists) {
            view.displayMessage("Error: Medication '" + medicationName + "' already exists in the inventory.");
            return; // Exit the method without adding the duplicate
        }
    
        // If medication is unique, proceed to add it
        InventoryItem newItem = new InventoryItem(medicationName, stockLevel, lowStockAlertLevel);
        inventory.add(newItem);
        saveInventory();
        view.displayMessage("Inventory item '" + medicationName + "' added successfully.");
    }
    

    /**
     * Updates an existing inventory item.
     * Validates input for new stock levels and low stock alert levels.
     * Ensures the item exists in inventory before applying changes.
     */
    private void updateInventoryItem() {
        // Prompt and retrieve the medication name, trimming any leading/trailing spaces
        String medicationName = view.getMedicationNameInput().trim();
        
        // Validate that the medication name is not empty
        if (medicationName.isEmpty()) {
            view.displayMessage("Error: Medication name cannot be empty.");
            return;
        }
    
        // Attempt to find the inventory item by name (case-insensitive)
        InventoryItem item = findInventoryItemByName(medicationName);
    
        if (item == null) {
            view.displayMessage("Error: Medication '" + medicationName + "' not found in inventory.");
            return;
        }
    
        // Display current stock levels
        view.displayMessage("Current Stock Level: " + item.getStockLevel());
        
        // Prompt and retrieve the new stock level
        int newStockLevel = view.getInventoryItemQuantityInput();
        
        // Validate that the new stock level is non-negative
        if (newStockLevel < 0) {
            view.displayMessage("Error: Stock level cannot be negative.");
            return;
        }
    
        // Prompt and retrieve the new low stock alert level
        view.displayMessage("Current Low Stock Alert Level: " + item.getLowStockAlertLevel());
        int newLowStockAlertLevel = view.getLowStockAlertLevelInput();
        
        // Validate that the new low stock alert level is non-negative
        if (newLowStockAlertLevel < 0) {
            view.displayMessage("Error: Low stock alert level cannot be negative.");
            return;
        }
    
        // Optional: Ensure that low stock alert level does not exceed stock level
        if (newLowStockAlertLevel > newStockLevel) {
            view.displayMessage("Error: Low stock alert level cannot exceed the stock level.");
            return;
        }
    
        // Update the inventory item with validated values
        item.setStockLevel(newStockLevel);
        item.setLowStockAlertLevel(newLowStockAlertLevel);
    
        // Save the updated inventory to persistent storage
        saveInventory();
    
        // Notify the user of the successful update
        view.displayMessage("Inventory item '" + medicationName + "' updated successfully.");
    }
    

    /**
     * Removes an inventory item from the system.
     * Prompts for the medication name and validates its existence before removal.
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
     * Displays all inventory items to the user.
     * If the inventory is empty, an appropriate message is displayed.
     */
    private void viewInventoryItems() {
        if (inventory.isEmpty()) {
            view.displayMessage("No inventory items found.");
            return;
        }
        view.displayInventory(inventory);
    }

    /**
     * Approves a pending replenishment request for a specific inventory item.
     * Updates the stock level and resets the replenish request amount.
     * Validates the existence of the item and ensures a valid request exists.
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
     * Retrieves a list of inventory items with pending replenishment requests.
     *
     * @return A list of inventory items where replenishRequestAmount > 0.
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
     * Displays all appointment details to the user.
     * If no appointments exist, a message is displayed.
     */
    private void viewAppointmentDetails() {
        if (appointments.isEmpty()) {
            view.displayMessage("No appointments found.");
            return;
        }
        view.displayAppointments(appointments);
    }

    /**
     * Loads user data from a serialized file.
     * Initializes the users map if no data exists or if an error occurs.
     */
    private void loadUsers() {
        try {
            users = (HashMap<String, User>) SerializationUtil.deserialize("users.ser");
            if (users == null) {
                users = new HashMap<>();
            }
        } catch (Exception e) {
            users = new HashMap<>();
            view.displayMessage("Error loading users data.");
        }
    }

    /**
     * Saves the current user data to a serialized file.
     */
    private void saveUsers() {
        try {
            SerializationUtil.serialize(users, "users.ser");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads appointment data from a serialized file.
     * Initializes the appointments list if no data exists or if an error occurs.
     */
    private void loadAppointments() {
        try {
            appointments = (List<Appointment>) SerializationUtil.deserialize("appointments.ser");
            if (appointments == null) {
                appointments = new ArrayList<>();
            }
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
     * Loads inventory data from a serialized file.
     * Initializes the inventory list if no data exists or if an error occurs.
     */
    private void loadInventory() {
        try {
            inventory = (List<InventoryItem>) SerializationUtil.deserialize("inventory.ser");
            if (inventory == null) {
                inventory = new ArrayList<>();
            }
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
