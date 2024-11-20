package controllers;

import java.util.ArrayList;
import java.util.List;
import models.Appointment;
import models.AppointmentOutcome;
import models.InventoryItem;
import models.Pharmacist;
import models.Prescription;
import utils.SerializationUtil;
import views.PharmacistView;

/**
 * Controller class for handling pharmacist-related operations.
 * This class manages the interaction between the pharmacist, 
 * their appointments, inventory, and prescriptions.
 */
public class PharmacistController {
    private Pharmacist model;
    private PharmacistView view;
    private List<Appointment> appointments;
    private List<InventoryItem> inventory;

    /**
     * Constructs a PharmacistController object.
     *
     * @param model The pharmacist model containing pharmacist-specific data.
     * @param view  The pharmacist view for displaying information and user input.
     */
    public PharmacistController(Pharmacist model, PharmacistView view) {
        this.model = model;
        this.view = view;
        loadAppointments();
        loadInventory();
    }

    /**
     * Starts the pharmacist menu loop.
     * Displays the menu options and handles user input for various pharmacist operations.
     */
    public void start() {
        int choice;
        do {
            view.displayMenu();
            choice = view.getUserChoice();
            switch (choice) {
                case 1:
                    viewAppointmentOutcomeRecords();
                    break;
                case 2:
                    updatePrescriptionStatus();
                    break;
                case 3:
                    viewMedicationInventory();
                    break;
                case 4:
                    submitReplenishmentRequest();
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
     * Displays all appointment outcome records, including prescriptions.
     * Fetches data from appointments and displays them through the view.
     */
    private void viewAppointmentOutcomeRecords() {
        for (Appointment appt : appointments) {
            AppointmentOutcome outcome = appt.getOutcome();
            if (outcome != null) {
                view.displayMessage("Patient ID: " + appt.getPatientID());
                view.displayMessage("Appointment ID: " + appt.getAppointmentID());
                view.displayMessage("Service: " + outcome.getTypeOfService());
                view.displayMessage("Consultation Notes: " + outcome.getConsultationNotes());
                for (Prescription presc : outcome.getPrescriptions()) {
                    view.displayMessage("Medication: " + presc.getMedicationName() +
                            ", Quantity: " + presc.getQuantity() +
                            ", Status: " + presc.getStatus());
                }
            }
        }
    }

    /**
     * Updates the status of a prescription.
     * Prompts the user for medication name, appointment ID, and quantity, 
     * then verifies stock availability and updates the status if applicable.
     */
    private void updatePrescriptionStatus() {
        String medicationName = view.getMedicationNameInput();
        String apptIDinput = view.getAppointmentIDInput();
        int quantity = view.getQuantityInput();
        boolean found = false;

        for (Appointment appt : appointments) {
            AppointmentOutcome outcome = appt.getOutcome();
            if (outcome != null) {
                List<Prescription> prescriptions = outcome.getPrescriptions();
                for (Prescription presc : prescriptions) {
                    if (presc.getMedicationName().equalsIgnoreCase(medicationName) &&
                        presc.getQuantity() == quantity && 
                        !presc.getStatus().equals("dispensed") &&
                        appt.getAppointmentID().equalsIgnoreCase(apptIDinput)) {
                        
                        for (InventoryItem item : inventory) {
                            if (item.getStockLevel() >= quantity) {
                                item.setStockLevel(item.getStockLevel() - quantity);
                                presc.setStatus("dispensed");
                                saveInventory();
                                view.displayMessage("Appointment ID: " + apptIDinput);
                                found = true;
                                break;
                            } else {
                                view.displayMessage("Insufficient stock for " + medicationName + ".");
                            }
                        }
                    }
                }
            }
        }

        if (found) {
            saveAppointments();
            view.displayMessage("Prescribed " + quantity + " units of " + medicationName + ". Status updated to 'dispensed'.");
        } else {
            view.displayMessage("Prescription not found or already dispensed.");
        }
    }

    /**
     * Displays the current medication inventory.
     * Lists all inventory items along with their stock levels.
     */
    private void viewMedicationInventory() {
        view.displayInventory(inventory);
    }

    /**
     * Submits a replenishment request for a specific medication.
     * Prompts the user for medication name and quantity, then marks the 
     * item for replenishment if it exists in the inventory.
     */
    private void submitReplenishmentRequest() {
        String medicationName = view.getMedicationNameInput();
        int quantity = view.getQuantityInput();
        
        boolean found = false;
        for (InventoryItem item : inventory) {
            if (item.getMedicationName().equalsIgnoreCase(medicationName)) {
                item.setReplenishRequestAmount(quantity);
                saveInventory();
                view.displayMessage("Replenishment request submitted.");
                found = true;
                break;
            }
        }
        if (!found) {
            view.displayMessage("Medication not found.");
        }
    }

    /**
     * Loads appointment data from a serialized file.
     * If no file is found or an error occurs, initializes an empty appointment list.
     */
    private void loadAppointments() {
        try {
            appointments = (List<Appointment>) SerializationUtil.deserialize("appointments.ser");
        } catch (Exception e) {
            appointments = new ArrayList<>();
        }
    }

    /**
     * Saves appointment data to a serialized file.
     * Handles exceptions that occur during the saving process.
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
     * If no file is found or an error occurs, initializes an empty inventory list.
     */
    private void loadInventory() {
        try {
            inventory = (List<InventoryItem>) SerializationUtil.deserialize("inventory.ser");
        } catch (Exception e) {
            inventory = new ArrayList<>();
        }
    }

    /**
     * Saves inventory data to a serialized file.
     * Handles exceptions that occur during the saving process.
     */
    private void saveInventory() {
        try {
            SerializationUtil.serialize(inventory, "inventory.ser");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all prescriptions from appointment outcomes.
     *
     * @return A list of all prescriptions associated with appointments.
     */
    private List<Prescription> getAllPrescriptions() {
        List<Prescription> allPrescriptions = new ArrayList<>();
        for (Appointment appt : appointments) {
            AppointmentOutcome outcome = appt.getOutcome();
            if (outcome != null && outcome.getPrescriptions() != null) {
                allPrescriptions.addAll(outcome.getPrescriptions());
            }
        }
        return allPrescriptions;
    }
}
