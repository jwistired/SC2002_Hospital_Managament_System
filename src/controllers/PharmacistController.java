package controllers;

import models.Pharmacist;
import models.Prescription;
import models.InventoryItem;
import views.PharmacistView;
import utils.SerializationUtil;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller class for handling pharmacist-related operations.
 */
public class PharmacistController {
    private Pharmacist model;
    private PharmacistView view;
    private List<Prescription> prescriptions;
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
        loadPrescriptions();
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
     * Displays appointment outcome records.
     */
    private void viewAppointmentOutcomeRecords() {
        view.displayPrescriptions(prescriptions);
    }

    /**
     * Allows the pharmacist to update the status of a prescription.
     */
    private void updatePrescriptionStatus() {
        String medicationName = view.getMedicationNameInput();
        for (Prescription presc : prescriptions) {
            if (presc.getMedicationName().equals(medicationName)) {
                presc.setStatus("dispensed");
                savePrescriptions();
                view.displayMessage("Prescription status updated.");
                return;
            }
        }
        view.displayMessage("Prescription not found.");
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
        // For simplicity, we just add the quantity to the inventory
        for (InventoryItem item : inventory) {
            if (item.getMedicationName().equals(medicationName)) {
                item.setStockLevel(item.getStockLevel() + quantity);
                saveInventory();
                view.displayMessage("Replenishment request submitted.");
                return;
            }
        }
        view.displayMessage("Medication not found.");
    }

    /**
     * Loads prescriptions from the serialized file.
     */
    private void loadPrescriptions() {
        try {
            prescriptions = (List<Prescription>) SerializationUtil.deserialize("prescriptions.ser");
        } catch (Exception e) {
            prescriptions = new ArrayList<>();
        }
    }

    /**
     * Saves prescriptions to the serialized file.
     */
    private void savePrescriptions() {
        try {
            SerializationUtil.serialize(prescriptions, "prescriptions.ser");
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
