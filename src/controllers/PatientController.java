package controllers;

import models.Patient;
import models.Appointment;
import models.AppointmentOutcome;
import models.Doctor;
import views.PatientView;
import utils.SerializationUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * Controller class for handling patient-related operations.
 */
public class PatientController {
    private Patient model;
    private PatientView view;
    private List<Appointment> appointments;
    private HashMap<String, Doctor> doctors;

    /**
     * Constructs a PatientController object.
     *
     * @param model The patient model.
     * @param view  The patient view.
     */
    public PatientController(Patient model, PatientView view) {
        this.model = model;
        this.view = view;
        loadAppointments();
        loadDoctors();
    }

    /**
     * Starts the patient menu loop.
     */
    public void start() {
        int choice;
        do {
            view.displayMenu();
            choice = view.getUserChoice();
            switch (choice) {
                case 1:
                    viewMedicalRecord();
                    break;
                case 2:
                    updatePersonalInfo();
                    break;
                case 3:
                    viewAvailableAppointmentSlots();
                    break;
                case 4:
                    scheduleAppointment();
                    break;
                case 5:
                    rescheduleAppointment();
                    break;
                case 6:
                    cancelAppointment();
                    break;
                case 7:
                    viewScheduledAppointments();
                    break;
                case 8:
                    viewPastAppointmentOutcomes();
                    break;
                case 9:
                    view.displayMessage("Logging out...");
                    break;
                default:
                    view.displayMessage("Invalid choice. Please try again.");
            }
        } while (choice != 9);
    }

    /**
     * Displays the patient's medical record.
     */
    private void viewMedicalRecord() {
        view.displayMedicalRecord(model.getMedicalRecord());
    }

    /**
     * Allows the patient to update personal information.
     */
    private void updatePersonalInfo() {
        String email = view.getEmailInput();
        String contactNumber = view.getContactNumberInput();
        model.setEmail(email);
        model.setContactNumber(contactNumber);
        saveModel();
        view.displayMessage("Personal information updated.");
    }

    /**
     * Displays available appointment slots for doctors.
     */
    private void viewAvailableAppointmentSlots() {
        // For simplicity, assume all doctors have the same availability
        List<String> slots = new ArrayList<>();
        slots.add("2023-10-01 09:00");
        slots.add("2023-10-01 10:00");
        slots.add("2023-10-01 11:00");
        view.displayAvailableSlots(slots);
    }

    /**
     * Allows the patient to schedule an appointment.
     */
    private void scheduleAppointment() {
        String doctorID = view.getDoctorIDInput();
        String dateTimeStr = view.getAppointmentDateTime();
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Appointment appointment = new Appointment(
                "APT" + System.currentTimeMillis(),
                model.getUserID(),
                doctorID,
                dateTime
        );
        appointment.setStatus("pending");
        appointments.add(appointment);
        saveAppointments();
        view.displayMessage("Appointment scheduled successfully.");
    }

    /**
     * Allows the patient to reschedule an appointment.
     */
    private void rescheduleAppointment() {
        // Implementation similar to scheduling but updating an existing appointment
    }

    /**
     * Allows the patient to cancel an appointment.
     */
    private void cancelAppointment() {
        // Implementation for canceling an appointment
    }

    /**
     * Displays the patient's scheduled appointments.
     */
    private void viewScheduledAppointments() {
        List<Appointment> patientAppointments = new ArrayList<>();
        for (Appointment appt : appointments) {
            if (appt.getPatientID().equals(model.getUserID())) {
                patientAppointments.add(appt);
            }
        }
        view.displayScheduledAppointments(patientAppointments);
    }

    /**
     * Displays the patient's past appointment outcomes.
     */
    private void viewPastAppointmentOutcomes() {
        // Implementation for displaying past appointment outcomes
    }

    /**
     * Saves the patient model to the serialized file.
     */
    private void saveModel() {
        try {
            // Assuming users are stored in a HashMap
            HashMap<String, Patient> patients = (HashMap<String, Patient>) SerializationUtil.deserialize("patients.ser");
            patients.put(model.getUserID(), model);
            SerializationUtil.serialize(patients, "patients.ser");
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
     * Loads doctors from the serialized file.
     */
    private void loadDoctors() {
        try {
            doctors = (HashMap<String, Doctor>) SerializationUtil.deserialize("doctors.ser");
        } catch (Exception e) {
            doctors = new HashMap<>();
        }
    }
}
