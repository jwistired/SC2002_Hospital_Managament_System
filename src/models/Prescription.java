package models;

import java.io.Serializable;

/**
 * Class representing a medication prescription.
 */
public class Prescription implements Serializable {
    private static final long serialVersionUID = 1L;
    private String medicationName;
    private String status; // pending, dispensed

    /**
     * Constructs a Prescription object.
     *
     * @param medicationName The name of the medication.
     */
    public Prescription(String medicationName) {
        this.medicationName = medicationName;
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
}
