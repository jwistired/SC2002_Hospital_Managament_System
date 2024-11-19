package models;

import java.io.Serializable;

/**
 * Class representing a medication prescription in the hospital management system.
 * This class holds details about a specific prescription, including the medication name, quantity, and status.
 */
public class Prescription implements Serializable {
    private static final long serialVersionUID = 1L;
    private String medicationName;
    private int quantity;
    private String status; // pending, dispensed

    /**
     * Constructs a Prescription object with the specified medication name and quantity.
     * The status is initialized to "pending".
     *
     * @param medicationName The name of the medication prescribed.
     * @param quantity The quantity of the medication prescribed.
     */
    public Prescription(String medicationName, int quantity) {
        this.medicationName = medicationName;
        this.quantity = quantity;
        this.status = "pending"; // Default status is "pending"
    }

    /**
     * Gets the name of the medication prescribed.
     *
     * @return The name of the medication.
     */
    public String getMedicationName() {
        return medicationName;
    }

    /**
     * Gets the current status of the prescription.
     * The status can be "pending" or "dispensed".
     *
     * @return The status of the prescription.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the prescription.
     * This can be used to mark the prescription as either "pending" or "dispensed".
     *
     * @param status The new status of the prescription.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the quantity of the medication prescribed.
     *
     * @return The quantity of medication prescribed.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the medication prescribed.
     *
     * @param quantity The new quantity of medication to prescribe.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
