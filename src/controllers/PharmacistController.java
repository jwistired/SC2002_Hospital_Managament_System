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
 */
public class PharmacistController {
    private Pharmacist model;
    private PharmacistView view;
    private List<Appointment> appointments;
    private List<InventoryItem> inventory;

    /**
     * Constructs a PharmacistController object.
     *
     * @param model The pharmacist model.
     * @param view  The pharmacist view.
     */
    public PharmacistController(Pharmacist model, PharmacistView view) {
        this.model = model;
        this.view = view;
        loadAppointments();
        loadInventory();
    }

    /**
     * Starts the pharmacist menu loop.
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
     * Displays appointment outcome records, including prescriptions.
     */
    private void viewAppointmentOutcomeRecords() {
        List<Prescription> allPrescriptions = getAllPrescriptions();
        view.displayPrescriptions(allPrescriptions);
    }

    /**
     * Allows the pharmacist to update the status of a prescription.
     */
    private void updatePrescriptionStatus() {
        String medicationName = view.getMedicationNameInput();
        int quantity = view.getQuantityInput();
        boolean found = false;

        for (Appointment appt : appointments) {
            AppointmentOutcome outcome = appt.getOutcome();
            if (outcome != null) {
                List<Prescription> prescriptions = outcome.getPrescriptions();
                for (Prescription presc : prescriptions) {
                    if (presc.getMedicationName().equalsIgnoreCase(medicationName) && presc.getQuantity() == quantity && !presc.getStatus().equals("dispensed")) {
                        for (InventoryItem item : inventory){
                            if (item.getStockLevel() >= quantity)
                            {
                                item.setStockLevel(item.getStockLevel() - quantity);
                                presc.setStatus("dispensed");
                                saveInventory();
                                found = true;
                                break;
                            }
                            else{
                                view.displayMessage("Insufficient stock for " + medicationName + ".");
                            }
                        }    
                    }
                }
            }
        }

        if (found) {
            saveAppointments();
            view.displayMessage("Prescribed " + quantity + "units of " + medicationName + ". Status Updated to 'dispensed'.");
        } else {
            view.displayMessage("Prescription not found or already dispensed.");
        }
    }

    /**
     * Displays the medication inventory.
     */
    private void viewMedicationInventory() {
        view.displayInventory(inventory);
    }

    /**
     * Allows the pharmacist to submit a replenishment request.
     */
    private void submitReplenishmentRequest() {
        String medicationName = view.getMedicationNameInput();
        int quantity = view.getQuantityInput();
        
        boolean found = false;
        for (InventoryItem item : inventory) {
            if (item.getMedicationName().equalsIgnoreCase(medicationName)) {
                //Request needs to be approved by admin
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

    /**
     * Retrieves all prescriptions from appointment outcomes.
     *
     * @return List of all prescriptions.
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