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
 * Controller class for handling administrator-related operations.
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
     * Loads doctors from the users map.
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
     * Displays the doctor schedules menu.
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

        List<String> schedule = doctor.getSchedule();
        if (schedule == null || schedule.isEmpty()) {
            view.displayMessage("No schedule available for Dr. " + doctor.getName() + ".");
            return;
        }

        view.displayMessage("Schedule for Dr. " + doctor.getName() + " (ID: " + doctorID + "):");
        view.displayDoctorSchedule(schedule, appointments, doctorID);
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

        if (users.containsKey(userID)) {
            view.displayMessage("User ID already exists. Please try a different ID.");
            return;
        }

        User newUser = null;
        if (role.equalsIgnoreCase("Doctor")) {
            newUser = new Doctor(userID, name, password);
        } else if (role.equalsIgnoreCase("Pharmacist")) {
            newUser = new Pharmacist(userID, name, password);
        }

        if (newUser != null) {
            users.put(userID, newUser);
            saveUsers();
            loadDoctors(); // Update doctors map if a doctor was added
            view.displayMessage("Staff member added.");
        } else {
            view.displayMessage("Invalid role. Only 'Doctor' and 'Pharmacist' roles are allowed.");
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
        loadDoctors(); // Update doctors map if a doctor was updated
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
            loadDoctors(); // Update doctors map if a doctor was removed
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
     * Adds a new inventory item.
     */
    private void addInventoryItem() {
        String medicationName = view.getMedicationNameInput();
        int stockLevel = view.getInventoryItemQuantityInput();
        int lowStockAlertLevel = view.getLowStockAlertLevelInput();

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
        if (inventory.isEmpty()) {
            view.displayMessage("No inventory items found.");
            return;
        }
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
        if (appointments.isEmpty()) {
            view.displayMessage("No appointments found.");
            return;
        }
        view.displayAppointments(appointments);
    }

    /**
     * Loads users from the serialized file.
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
     * Loads inventory from the serialized file.
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
