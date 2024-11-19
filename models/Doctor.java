package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a doctor in the hospital management system.
 * The doctor is a subclass of the {@link User} class and includes properties 
 * such as the doctor's patient list, appointment list, availability times, 
 * and their schedule.
 */
public class Doctor extends User {
    private static final long serialVersionUID = 1L;
    private List<String> patientIDs;
    private List<Appointment> appointments;
    private List<LocalDateTime> availability;
    private List<String> schedule;

    /**
     * Constructs a Doctor object with the specified user ID, name, and password.
     *
     * @param userID   The doctor's unique ID.
     * @param name     The doctor's name.
     * @param password The doctor's password for authentication.
     */
    public Doctor(String userID, String name, String password) {
        super(userID, name, password, "Doctor");
        this.patientIDs = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.availability = new ArrayList<>();
        if ((this.schedule == null) || this.schedule.isEmpty()) {
            this.schedule = new ArrayList<>();
        }
    }

    // Getters and Setters
    /**
     * Gets the list of patient IDs assigned to this doctor.
     *
     * @return A list of patient IDs associated with the doctor.
     */
    public List<String> getPatientIDs() {
        return patientIDs;
    }
    /**
     * Gets the list of appointments scheduled for this doctor.
     *
     * @return A list of {@link Appointment} objects representing the doctor's appointments.
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }
    /**
     * Gets the doctor's schedule, which is a list of strings representing the scheduled times.
     *
     * @return A list of strings representing the doctor's schedule.
     */
    public List<String> getSchedule() {
        
        return schedule;
    }

    /**
     * Sets the doctor's schedule with a new list of strings representing the available times.
     *
     * @param schedule A list of strings representing the doctor's schedule.
     */
    public void setSchedule(List<String> schedule) {
        this.schedule = schedule;
    }
}
