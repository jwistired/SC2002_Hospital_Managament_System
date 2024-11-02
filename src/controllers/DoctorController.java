package controllers;

import models.Doctor;
import models.Patient;
import models.MedicalRecord;
import models.Appointment;
import models.AppointmentOutcome;
import views.DoctorView;
import utils.SerializationUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * Controller class for handling doctor-related operations.
 */
public class DoctorController {
    private Doctor model;
    private DoctorView view;
    private List<Appointment> appointments;
    private HashMap<String, Patient> patients;

    /**
     * Constructs a DoctorController object.
     *
     * @param model The doctor model.
     * @param view  The doctor view.
     */
    public DoctorController(Doctor model, DoctorView view) {
        this.model = model;
        this.view = view;
        loadAppointments();
        loadPatients();
    }

    /**
     * Starts the doctor menu loop.
     */
    public void start() {
        int choice;
        do {
            view.displayMenu();
            choice = view.getUserChoice();
            switch (choice) {
                case 1:
                    viewPatientMedicalRecords();
                    break;
                case 2:
                    updatePatientMedicalRecords();
                    break;
                case 3:
                    viewPersonalSchedule();
                    break;
                case 4:
                    setAvailability();
                    break;
                case 5:
                    handleAppointmentRequests();
                    break;
                case 6:
                    viewUpcomingAppointments();
                    break;
                case 7:
                    recordAppointmentOutcome();
                    break;
                case 8:
                    view.displayMessage("Logging out...");
                    break;
                default:
                    view.displayMessage("Invalid choice. Please try again.");
            }
        } while (choice != 8);
    }

    /**
     * Allows the doctor to view patient medical records.
     */
    private void viewPatientMedicalRecords() {
        String patientID = view.getPatientIDInput();
        Patient patient = patients.get(patientID);
        if (patient != null) {
            view.displayPatientMedicalRecord(patient.getMedicalRecord());
        } else {
            view.displayMessage("Patient not found.");
        }
    }

    /**
     * Allows the doctor to update patient medical records.
     */
    private void updatePatientMedicalRecords() {
        String patientID = view.getPatientIDInput();
        Patient patient = patients.get(patientID);
        if (patient != null) {
            String diagnosis = view.getDiagnosisInput();
            String treatment = view.getTreatmentInput();
            patient.getMedicalRecord().addDiagnosis(diagnosis);
            patient.getMedicalRecord().addTreatment(treatment);
            savePatients();
            view.displayMessage("Medical record updated.");
        } else {
            view.displayMessage("Patient not found.");
        }
    }

    /**
     * Displays the doctor's personal schedule.
     */
    private void viewPersonalSchedule() {
        List<String> schedule = new ArrayList<>();
        for (LocalDateTime time : model.getAvailability()) {
            schedule.add(time.toString());
        }
        view.displayPersonalSchedule(schedule);
    }

    /**
     * Allows the doctor to set availability for appointments.
     */
    private void setAvailability() {
        String dateTimeStr = view.getAvailabilityInput();
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr);
        model.getAvailability().add(dateTime);
        saveModel();
        view.displayMessage("Availability updated.");
    }

    /**
     * Allows the doctor to accept or decline appointment requests.
     */
    private void handleAppointmentRequests() {
        List<Appointment> pendingAppointments = new ArrayList<>();
        for (Appointment appt : appointments) {
            if (appt.getDoctorID().equals(model.getUserID()) && appt.getStatus().equals("pending")) {
                pendingAppointments.add(appt);
            }
        }
        view.displayAppointmentRequests(pendingAppointments);
        String appointmentID = view.getAppointmentIDInput();
        String decision = view.getDecisionInput();
        for (Appointment appt : pendingAppointments) {
            if (appt.getAppointmentID().equals(appointmentID)) {
                if (decision.equalsIgnoreCase("A")) {
                    appt.setStatus("confirmed");
                } else {
                    appt.setStatus("declined");
                }
                saveAppointments();
                view.displayMessage("Appointment " + (decision.equalsIgnoreCase("A") ? "accepted." : "declined."));
                break;
            }
        }
    }

    /**
     * Displays the doctor's upcoming appointments.
     */
    private void viewUpcomingAppointments() {
        List<Appointment> upcomingAppointments = new ArrayList<>();
        for (Appointment appt : appointments) {
            if (appt.getDoctorID().equals(model.getUserID()) && appt.getStatus().equals("confirmed")) {
                upcomingAppointments.add(appt);
            }
        }
        view.displayAppointmentRequests(upcomingAppointments);
    }

    /**
     * Allows the doctor to record the outcome of an appointment.
     */
    private void recordAppointmentOutcome() {
        String appointmentID = view.getAppointmentIDInput();
        for (Appointment appt : appointments) {
            if (appt.getAppointmentID().equals(appointmentID) && appt.getDoctorID().equals(model.getUserID())) {
                String dateOfAppointment = appt.getDateTime().toString();
                String typeOfService = view.getDiagnosisInput(); // Reusing method for simplicity
                String consultationNotes = view.getTreatmentInput(); // Reusing method for simplicity
                AppointmentOutcome outcome = new AppointmentOutcome(dateOfAppointment, typeOfService, new ArrayList<>(), consultationNotes);
                appt.setOutcome(outcome);
                appt.setStatus("completed");
                saveAppointments();
                view.displayMessage("Appointment outcome recorded.");
                break;
            }
        }
    }

    /**
     * Saves the doctor model to the serialized file.
     */
    private void saveModel() {
        try {
            HashMap<String, Doctor> doctors = (HashMap<String, Doctor>) SerializationUtil.deserialize("doctors.ser");
            doctors.put(model.getUserID(), model);
            SerializationUtil.serialize(doctors, "doctors.ser");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads appointments from the serialized file.
     */
    private void loadAppointments() {
        try {
            appointments = (List<Appointment>) SerializationUtil.deserialize("appointments.ser");
        } catch (Exception e) {
            appointments = new ArrayList<>();
        }
    }

    /**
     * Saves appointments to the serialized file.
     */
    private void saveAppointments() {
        try {
            SerializationUtil.serialize(appointments, "appointments.ser");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads patients from the serialized file.
     */
    private void loadPatients() {
        try {
            patients = (HashMap<String, Patient>) SerializationUtil.deserialize("patients.ser");
        } catch (Exception e) {
            patients = new HashMap<>();
        }
    }

    /**
     * Saves patients to the serialized file.
     */
    private void savePatients() {
        try {
            SerializationUtil.serialize(patients, "patients.ser");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
