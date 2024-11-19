package models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Class representing an appointment in the hospital management system.
 * This class contains details about the appointment, including its ID, 
 * the patient and doctor involved, the scheduled date and time, the status of the appointment, 
 * and the outcome of the appointment after completion.
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
     * Constructs an Appointment object with the specified details.
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

    /**
     * Gets the appointment's ID.
     *
     * @return The appointment ID as a string.
     */
    public String getAppointmentID() {
        return appointmentID;
    }

    /**
     * Gets the patient's ID.
     *
     * @return The patient's ID as a string.
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * Gets the doctor's ID.
     *
     * @return The doctor's ID as a string.
     */
    public String getDoctorID() {
        return doctorID;
    }

    /**
     * Gets the doctor's name.
     *
     * @return The doctor's name as a string.
     */
    public String getDoctorName() {
        return doctorName;  // Getter for doctorName
    }

    /**
     * Sets the doctor's name.
     *
     * @param doctorName The name of the doctor as a string.
     */
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;  // Setter for doctorName
    }

    /**
     * Gets the date and time of the appointment.
     *
     * @return The date and time of the appointment as a {@link LocalDateTime} object.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    /**
     * Sets the date and time of the appointment.
     *
     * @param dateTime The new date and time for the appointment.
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime; // Update the dateTime property
    }
    /**
     * Gets the current status of the appointment.
     * Possible values include "pending", "confirmed", "canceled", and "completed".
     *
     * @return The status of the appointment as a string.
     */
    public String getStatus() {
        return status;
    }
    /**
     * Sets the status of the appointment.
     *
     * @param status The new status of the appointment.
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * Gets the outcome of the appointment, which includes details such as prescriptions and consultation notes.
     *
     * @return The {@link AppointmentOutcome} associated with this appointment.
     */
    public AppointmentOutcome getOutcome() {
        return outcome;
    }
    /**
     * Sets the outcome of the appointment, which includes details such as prescriptions and consultation notes.
     *
     * @param outcome The {@link AppointmentOutcome} to be set for this appointment.
     */
    public void setOutcome(AppointmentOutcome outcome) {
        this.outcome = outcome;
    }
}
