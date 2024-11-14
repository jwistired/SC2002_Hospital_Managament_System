package controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import models.Appointment;
import models.AppointmentOutcome;
import models.Doctor;
import models.Patient;
import models.User;
import utils.SerializationUtil;
import views.DoctorView;

/**
 * Controller class for handling doctor-related operations.
 */
public class DoctorController {
    private Doctor model;
    private DoctorView view;
    private List<Appointment> appointments;
    private HashMap<String, Patient> patients;

    private List<String> schedule;

    /**
     * Constructs a DoctorController object.
     *
     * @param model The doctor model.
     * @param view  The doctor view.
     */
    public DoctorController(Doctor model, DoctorView view) {
        this.model = model;
        this.view = view;
        this.schedule = new ArrayList<>();
        loadAppointments();
        loadPatients();
        initializeSchedule();
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
                case 9:
                    for(String slot: schedule){
                        System.out.println(slot);
                    }
                default:
                    view.displayMessage("Invalid choice. Please try again.");
            }
        } while (choice != 8);
    }

    /**
     * Initializes the schedule with time slots for the next 7 days.
     */
    private void initializeSchedule() {
        String[] timeSlots = {"09:00", "10:00", "11:00", "14:00", "15:00"};
        LocalDate date = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            LocalDate currentDate = date.plusDays(i);
            for (String time : timeSlots) {
                schedule.add(currentDate.toString() + " " + time + " - Available");
            }
        }
    }

    /**
     * Allows the doctor to view patient medical records.
     */
    private void viewPatientMedicalRecords() {
        String patientID = view.getPatientIDInput(); // Get patient ID input from DoctorView
        Patient patient = patients.get(patientID); // Get patient object from patients HashMap
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
        String patientID = view.getPatientIDInput(); // Get patient ID input from DoctorView
        Patient patient = patients.get(patientID); // Get patient object from patients HashMap
        if (patient != null) {
            String diagnosis = view.getDiagnosisInput(); // Get diagnosis input from DoctorView
            String treatment = view.getTreatmentInput(); // Get treatment input from DoctorView
            patient.getMedicalRecord().addDiagnosis(diagnosis); //Add diagnosis to patient's medical record
            patient.getMedicalRecord().addTreatment(treatment); //Add treatment to patient's medical record
            patient.saveModel(); // Save to patient model file
            view.displayMessage("Medical record updated.");
        } else {
            view.displayMessage("Patient not found.");
        }
    }

    /**
     * Displays the doctor's personal schedule.
     */
    // private void viewPersonalSchedule() {
    //     // List<String> NewSchedule = new ArrayList<>();
    //     // for (LocalDateTime time : model.getAvailability()) {
    //     //     schedule.add(time.toString());
    //     // }
    //     //schedule = NewSchedule;
    //     view.displayPersonalSchedule(schedule);
    // }

    private void viewPersonalSchedule() {

        view.displayPersonalSchedule(schedule);
    }

    /**
     * Allows the doctor to set availability for appointments.
     */
    private void setAvailability() {
        //Get list of available times
        view.displayPersonalSchedule(schedule);


        //Set availability on date
        String dateTimeStr = view.getAvailabilityInput();

        

        //Update model
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        //model.getAvailability().add(dateTime);

        System.out.println("Current availability: " + model.getAvailability());

        // Remove the specified date-time from availability
        if (model.getAvailability().remove(dateTime)) {
            view.displayMessage("Availability removed.");
            schedule.remove(dateTime.toString()); //update schedule
        } else {
            view.displayMessage("Specified date-time was not found in availability.");
        }

        model.saveModel();
        view.displayMessage("Availability updated.");
    }

    /**
     * Allows the doctor to accept or decline appointment requests.
     */
    private void handleAppointmentRequests() {
        List<Appointment> pendingAppointments = new ArrayList<>();
        //Get list of pending appointments matching doctor ID
        for (Appointment appt : appointments) {
            if (appt.getDoctorID().equals(model.getUserID()) && appt.getStatus().equals("pending")) {
                pendingAppointments.add(appt);
            }
        }

        //Display list of pending appointments
        view.displayAppointmentRequests(pendingAppointments);
        String appointmentID = view.getAppointmentIDInput();
        String decision = view.getDecisionInput();

        //Update appointment status
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
                AppointmentOutcome outcome = new AppointmentOutcome(dateOfAppointment, typeOfService, new ArrayList<>(),
                        consultationNotes);
                appt.setOutcome(outcome);
                appt.setStatus("completed");
                saveAppointments();
                view.displayMessage("Appointment outcome recorded.");
                break;
            }
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
            HashMap<String, User> users = (HashMap<String, User>) SerializationUtil.deserialize("users.ser");
            for (User user : users.values()) {
                if (user.getRole().equals("Patient")) {
                    patients.put(user.getUserID(), (Patient) user);
                }
            }
        } catch (Exception e) {
            patients = new HashMap<>();
        }
    }
}
