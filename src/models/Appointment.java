package models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Class representing an appointment in the hospital management system.
 */
public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;
    private String appointmentID;
    private String patientID;
    private String doctorID;
    private String doctorName;  // Add doctorName property
    private LocalDateTime dateTime;
    private String status; // confirmed, canceled, completed
    private AppointmentOutcome outcome;

    /**
     * Constructs an Appointment object.
     *
     * @param appointmentID The appointment's ID.
     * @param patientID     The patient's ID.
     * @param doctorID      The doctor's ID.
     * @param dateTime      The date and time of the appointment.
     */
    public Appointment(String appointmentID, String patientID, String doctorID, LocalDateTime dateTime) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.dateTime = dateTime;
        this.status = "pending";
    }

    // Getters and Setters

    public String getAppointmentID() {
        return appointmentID;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getDoctorName() {
        return doctorName;  // Getter for doctorName
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;  // Setter for doctorName
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime; // Update the dateTime property
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AppointmentOutcome getOutcome() {
        return outcome;
    }

    public void setOutcome(AppointmentOutcome outcome) {
        this.outcome = outcome;
    }
}
