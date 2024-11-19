package models;

import java.io.Serializable;
import java.util.List;

/**
 * Class representing the outcome of a completed appointment.
 * This class contains details such as the date of the appointment, 
 * the type of service provided, any prescriptions issued, and the consultation notes.
 */
public class AppointmentOutcome implements Serializable {
    private static final long serialVersionUID = 1L;
    private String dateOfAppointment;
    private String typeOfService;
    private List<Prescription> prescriptions;
    private String consultationNotes;

    /**
     * Constructs an AppointmentOutcome object with the specified details.
     *
     * @param dateOfAppointment The date of the appointment in a string format.
     * @param typeOfService The type of service provided during the appointment.
     * @param prescriptions A list of prescriptions issued during the appointment.
     * @param consultationNotes Any notes taken during the consultation.
     */

    public AppointmentOutcome(String dateOfAppointment, String typeOfService,
                              List<Prescription> prescriptions, String consultationNotes) {
        this.dateOfAppointment = dateOfAppointment;
        this.typeOfService = typeOfService;
        this.prescriptions = prescriptions;
        this.consultationNotes = consultationNotes;
    }

    /**
     * Gets the date of the appointment.
     *
     * @return The date of the appointment as a string.
     */
    public String getDateOfAppointment() {
        return dateOfAppointment;
    }

    /**
     * Gets the type of service provided during the appointment.
     *
     * @return The type of service as a string.
     */
    public String getTypeOfService() {
        return typeOfService;
    }

    /**
     * Gets the list of prescriptions issued during the appointment.
     *
     * @return A list of Prescription objects.
     */
    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    /**
     * Gets the consultation notes taken during the appointment.
     *
     * @return The consultation notes as a string.
     */
    public String getConsultationNotes() {
        return consultationNotes;
    }
}
