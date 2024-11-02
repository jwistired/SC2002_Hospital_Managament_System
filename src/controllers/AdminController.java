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
                    manageMedicationInventory();
                    break;
                case 4:
                    approveReplenishmentRequests();
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
                    view.displayMessage("Returning to main menu...");
                    break;
                default:
                    view.displayMessage("Invalid choice. Please try again.");
            }
        } while (choice != 4);
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
        // Implementation for updating a staff member
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
     * Displays appointment details.
     */
    private void viewAppointmentDetails() {
        view.displayAppointments(appointments);
    }

    /**
     * Manages the medication inventory.
     */
    private void manageMedicationInventory() {
        // Implementation for managing medication inventory
    }

    /**
     * Approves replenishment requests.
     */
    private void approveReplenishmentRequests() {
        // Implementation for approving replenishment requests
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
     * Loads inventory from the serialized file.
     */
    private void loadInventory() {
        try {
            inventory = (List<InventoryItem>) SerializationUtil.deserialize("inventory.ser");
        } catch (Exception e) {
            inventory = new ArrayList<>();
        }
    }
}
