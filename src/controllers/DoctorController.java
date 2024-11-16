package controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import models.Appointment;
import models.AppointmentOutcome;
import models.Doctor;
import models.InventoryItem;
import models.Patient;
import models.Prescription;
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
    private List<InventoryItem> availableMedication;
    private List<String> nameofMedication;

    //private List<String> schedule;

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
        loadInventory();
        loadtoList();

        if (patients != null) {
            view.displayMessage("Patients loaded.");
        }

        if (this.model.getSchedule() == null) {
            view.displayMessage("Schedule Missing. Initializing...");
            initializeSchedule();
            saveSchedule();
            view.displayMessage("Schedule initialized.");
        }
        else{
            loadSchedule();
            view.displayMessage("Schedule loaded.");
        }
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
     * Initializes the schedule with time slots for the next 7 days. Placeholder for actual implementation.
     */
    private void initializeSchedule() {
        String[] timeSlots = {"09:00", "10:00", "11:00", "13:00","14:00", "15:00", "16:00"};
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<String> tempschedule = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate currentDate = date.plusDays(i);
            for (String time : timeSlots) {
                LocalTime localTime = LocalTime.parse(time);
                LocalDateTime dateTime = LocalDateTime.of(currentDate, localTime);
                tempschedule.add(dateTime.format(formatter));
            }
        }
        model.setSchedule(tempschedule);
        view.displayMessage("Schedule initialized.");
    }

    /**
     * Saves the schedule to a serialized file.
     */
    private void saveSchedule() {
        try {
            String fileName = "Schedule_" + model.getUserID() + ".ser";
            SerializationUtil.serialize(model.getSchedule(), fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the schedule from a serialized file.
     */    
    private void loadSchedule() {
        try {
            String fileName = "Schedule_" + model.getUserID() + ".ser";
            //Load schedule from serialized file
            model.setSchedule((List<String>) SerializationUtil.deserialize(fileName));
        } catch (Exception e) {
            view.displayMessage("Error loading schedule.");
        }
    }

    /**
     * Allows the doctor to view patient medical records.
     */
    private void viewPatientMedicalRecords() {

        String patientID = view.getPatientIDInput(); // Get patient ID input from DoctorView
        Patient patient = patients.get(patientID); // Get patient object from patients HashMap
        if (patient != null) {
            view.displayMessage("Viewing "+ patient.getName() + "'s medical record:");
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
            view.displayMessage("Do you want to add a diagnosis?");
            String decisionDiagnosis = view.getDecisionInput();

            if (decisionDiagnosis.equalsIgnoreCase("A")) {
                String diagnosis = view.getDiagnosisInput(); // Get diagnosis input from DoctorView
                patient.getMedicalRecord().addDiagnosis(diagnosis); // Add diagnosis to patient's medical record
            }

            view.displayMessage("Do you want to add a treatment?");
            String decisionTreatment = view.getDecisionInput();

            if (decisionTreatment.equalsIgnoreCase("A")) {
                String treatment = view.getTreatmentInput(); // Get treatment input from DoctorView
                patient.getMedicalRecord().addTreatment(treatment); // Add treatment to patient's medical record
            }
            patient.saveModel(); // Save to patient model file
            view.displayMessage("Medical record updated.");
        } else {
            view.displayMessage("Patient not found.");
        }
    }

    /**
     * Displays the doctor's personal schedule.
     */
    private void viewPersonalSchedule() {
        view.displayPersonalSchedule(model.getSchedule());
    }

    /**
     * Allows the doctor to set availability for appointments.
     */
    private void setAvailability() {

        List<String> schedule = model.getSchedule();
        //Get list of available times
        view.displayMessage("Available times:");
        view.displayPersonalSchedule(schedule);

        //Set availability on date
        String dateTimeStr = view.getAvailabilityInput();

        //Update model
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, inputFormatter);

        // Convert to the same format used in the schedule
        String formattedDateTimeStr = dateTime.format(inputFormatter) + " Unavailable";

        //find the index of existing date-time and replace it with the new one
        int index = -1;
        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).contains(dateTime.format(inputFormatter))) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            schedule.set(index, formattedDateTimeStr);
            view.displayMessage("Availability updated.");
        } else {
            view.displayMessage("Specified date-time was not found in availability.");
        }

        saveSchedule();
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
        List<String> schedule = model.getSchedule();
        view.displayAppointmentRequests(pendingAppointments);

        //Check if there are any pending appointments
        if (pendingAppointments.isEmpty()) {
            view.displayMessage("No pending appointments.");
            return;
        }
        
        //check for valid appointment ID
        boolean validAppointmentID = false;
        String appointmentID = "";
        while (!validAppointmentID) {
            appointmentID = view.getAppointmentIDInput();
            for (Appointment appt : pendingAppointments) {
                if (appt.getAppointmentID().equals(appointmentID)) {
                    validAppointmentID = true;
                    break;
                }
            }
            if (!validAppointmentID) {
                view.displayMessage("Invalid appointment ID. Please try again.");
                return;
            }
        }
        
        //Display Apt with Patient Name and Date
        for (Appointment appt : pendingAppointments) {
            view.displayMessage("Appointment ID: " + appt.getAppointmentID() + " with " + patients.get(appt.getPatientID()).getName() + " on " + appt.getDateTime());
        }

        //Get appointment ID and decision from user
        String decision = view.getDecisionInput();

        //Update appointment status
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Appointment appt : pendingAppointments) {
            if (appt.getAppointmentID().equals(appointmentID)) {
                if (decision.equalsIgnoreCase("A")) {
                    LocalDateTime dateTime = appt.getDateTime();
                    String formattedDateTimeStr = dateTime.format(formatter) + " Confirmed with" + patients.get(appt.getPatientID()).getName();

                    //Find index of date-time in schedule
                    int index = -1;
                    for (int i = 0; i < schedule.size(); i++) {
                        if (schedule.get(i).startsWith(dateTime.format(formatter))) {
                            index = i;
                            break;
                        }
                    }

                     // Replace the existing entry if found, otherwise add the new entry
                    if (index != -1) {
                        schedule.set(index, formattedDateTimeStr);
                        appt.setStatus("confirmed");
                        view.displayMessage("Availability updated.");
                    } else {
                        view.displayMessage("Specified date-time was not found in availability. Please try again.");
                    }
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
     * 
     * @param nameofMedication List of medication names
     * @param appointmentID The appointment ID
     *
     */
    private void recordAppointmentOutcome() {
        int apptIndex = -1;
        String appointmentID = view.getAppointmentIDInput();

        for (Appointment appt : appointments) {
            if (appt.getAppointmentID().equals(appointmentID) && appt.getDoctorID().equals(model.getUserID())) {
                apptIndex = appointments.indexOf(appt);
                String dateOfAppointment = appt.getDateTime().toString();
                String typeOfService = view.getDiagnosisInput(); // Reusing method for simplicity
                String consultationNotes = view.getTreatmentInput(); // Reusing method for simplicity
                
                List<Prescription> patientPrescriptions = new ArrayList<>();
                int choice = -1;
                int quantity = 0;

                //Loop to add multiple prescriptions until Doctor decides to stop
                while (choice != 0) {
                    choice = view.getMedications(nameofMedication);
                    if (choice > 0 && choice <= nameofMedication.size()) {
                        String medicationName = nameofMedication.get(choice-1);
                        quantity = view.getMedicationQuantity();
                        Prescription newPrescription = new Prescription(medicationName, quantity);
                        patientPrescriptions.add(newPrescription);
                        view.displayMessage(medicationName + " added.");
                    } 
                    else if (choice == 0) {
                        break;
                    }
                    else{
                        view.displayMessage("Invalid choice. Please try again.");
                    }
                }

                AppointmentOutcome outcome = new AppointmentOutcome(dateOfAppointment, typeOfService, patientPrescriptions, consultationNotes);
                appt.setOutcome(outcome);
                appt.setStatus("completed");
                saveAppointments();
                view.displayMessage("Appointment outcome recorded.");
                break;
            }
        }

        if(apptIndex == -1){
            view.displayMessage("Appointment not found.");
        }
    }

    /**
     * Loads appointments from the serialized file.
     */
    private void loadAppointments() {
        try {
            appointments = (List<Appointment>) SerializationUtil.deserialize("appointments.ser");
        } catch (Exception e) {
            view.displayMessage("Error loading appointments.");
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
    @SuppressWarnings("unchecked")
    private void loadPatients() {
        try {
            patients = new HashMap<>();
            HashMap<String, User> users = (HashMap<String, User>) SerializationUtil.deserialize("users.ser");
            for (User user : users.values()) {
                if (user.getRole().equals("Patient")) {
                    patients.put(user.getUserID(), (Patient) user);
                }
            }
            
        } catch (Exception e) {
            System.out.println("Error loading patients.");
        }
    }

    /**
     * Loads inventory from the serialized file.
     */
    private void loadInventory() {
            try {
                availableMedication = (List<InventoryItem>) SerializationUtil.deserialize("inventory.ser");
            } catch (Exception e) {
                    view.displayMessage("Error loading inventory.");            
                }
        }
    
    /**
     * Saves medicine names to a list.
     */
    private void loadtoList(){
        nameofMedication = new ArrayList<>();
        for (InventoryItem item : availableMedication) {
            nameofMedication.add(item.getMedicationName());            
        }
    }

}
