package models;

import java.io.Serializable;
import java.util.List;

/**
 * Class representing the outcome of a completed appointment.
 */
public class AppointmentOutcome implements Serializable {
    private static final long serialVersionUID = 1L;
    private String dateOfAppointment;
    private String typeOfService;
    private List<Prescription> prescriptions;
    private String consultationNotes;

    // Constructors, Getters, and Setters

    public AppointmentOutcome(String dateOfAppointment, String typeOfService,
                              List<Prescription> prescriptions, String consultationNotes) {
        this.dateOfAppointment = dateOfAppointment;
        this.typeOfService = typeOfService;
        this.prescriptions = prescriptions;
        this.consultationNotes = consultationNotes;
    }

    public String getDateOfAppointment() {
        return dateOfAppointment;
    }

    public String getTypeOfService() {
        return typeOfService;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }
}
