package models;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

/**
 * Class representing a doctor in the hospital management system.
 */
public class Doctor extends User {
    private static final long serialVersionUID = 1L;
    private List<String> patientIDs;
    private List<Appointment> appointments;
    private List<LocalDateTime> availability;

    /**
     * Constructs a Doctor object.
     *
     * @param userID   The doctor's ID.
     * @param name     The doctor's name.
     * @param password The doctor's password.
     */
    public Doctor(String userID, String name, String password) {
        super(userID, name, password, "Doctor");
        this.patientIDs = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.availability = new ArrayList<>();
    }

    // Getters and Setters

    public List<String> getPatientIDs() {
        return patientIDs;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public List<LocalDateTime> getAvailability() {
        return availability;
    }
}
