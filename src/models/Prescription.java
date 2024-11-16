package models;

import java.io.Serializable;

/**
 * Class representing a medication prescription.
 */
public class Prescription implements Serializable {
    private static final long serialVersionUID = 1L;
    private String medicationName;
    private int quantity;
    private String status; // pending, dispensed

    /**
     * Constructs a Prescription object.
     *
     * @param medicationName The name of the medication.
     * @param quantity
     */
    public Prescription(String medicationName) {
        this.medicationName = medicationName;
        this.quantity = quantity;
        this.status = "pending";
    }

    // Getters and Setters

    public String getMedicationName() {
        return medicationName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
