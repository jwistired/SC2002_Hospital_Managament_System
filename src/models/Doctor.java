package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a doctor in the hospital management system.
 */
public class Doctor extends User {
    private static final long serialVersionUID = 1L;
    private List<String> patientIDs;
    private List<Appointment> appointments;
    private List<LocalDateTime> availability;
    private List<String> schedule;

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
        if ((this.schedule == null) || this.schedule.isEmpty()) {
            this.schedule = new ArrayList<>();
        }
    }

    // Getters and Setters

    public List<String> getPatientIDs() {
        return patientIDs;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    // public List<LocalDateTime> getAvailability() {
    //     return availability;
    // }

    //Returns the schedule of the doctor
    public List<String> getSchedule() {
        
        return schedule;
    }

    //Modify the schedule of the doctor
    public void setSchedule(List<String> schedule) {
        this.schedule = schedule;
    }
}
