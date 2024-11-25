package controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import models.Appointment;
import models.AppointmentOutcome;
import models.Doctor;
import models.MedicalRecord;
import models.Patient;
import models.User;
import utils.SerializationUtil;
import views.PatientView;


/**
 * Controller class for handling patient-related operations.
 */
public class PatientController {
    private Patient model;
    private Doctor doctor;
    private PatientView view;
    private MedicalRecord record;
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
        model.setMedicalRecord(email, contactNumber);

        model.saveModel();
        view.displayMessage("Personal information updated.");
    }

    /**
     * Displays available appointment slots for doctors.
     */
     
    private void viewAvailableAppointmentSlots() {
        // For simplicity, assume all doctors have the same availability
        for (Map.Entry<String, Doctor> entry : doctors.entrySet()) {
        String doctorID = entry.getKey();
        Doctor doctor = entry.getValue();

        // Load the doctor's schedule based on their ID
        List<String> schedule = loadSchedule(doctorID);

        // Display the schedule if available
        if (!schedule.isEmpty()) {
            System.out.println("Available slots for " + doctor.getName() + " (" + doctorID + "):");
            for (String slot : schedule) {
                System.out.println(slot);
            }
        } else {
            System.out.println("No available slots for Dr. " + doctor.getName() + " (" + doctorID + ").");
        }
        System.out.println();
    }
    }
        

    /**
     * Retrieves a list of appointments scheduled for the patient.
     *
     * @return List of Appointment objects that belong to the patient.
     */
    private List<Appointment> getPatientAppointments() {
        List<Appointment> patientAppointments = new ArrayList<>();
        for (Appointment appt : appointments) {
            if (appt.getPatientID().equals(model.getUserID())) {
                patientAppointments.add(appt);
            }
        }
        return patientAppointments;
    }
    

    /**
     * Allows the patient to schedule an appointment.
     */
    private void scheduleAppointment() {
        
        // Step 1: Show all available appointment slots for all doctors
        System.out.println("All available appointment slots:");
        Map<String, List<String>> doctorSlots = new HashMap<>();
        
        // Collect all available slots for each doctor
        for (Map.Entry<String, Doctor> entry : doctors.entrySet()) {
            String doctorID = entry.getKey();
            Doctor doctor = entry.getValue();

            // Load the doctor's schedule based on their ID
            List<String> slots = loadSchedule(doctorID);

            // Only show the doctor and their slots if there are available slots
            if (!slots.isEmpty()) {
                System.out.println("Available slots for " + doctor.getName() + " (" + doctorID + "):");
                for (String slot : slots) {
                    System.out.println(slot);
                }
                doctorSlots.put(doctorID, slots);  // Store the slots for later selection
            } else {
                System.out.println("No available slots for " + doctor.getName() + " (" + doctorID + ").");
            }

            System.out.println();
        }

        // Step 2: Ask the user to select a doctor from the available slots
        String doctorID = view.getDoctorIDInput();  // Get the selected doctor's ID from the user input
        List<String> availableSlots = doctorSlots.get(doctorID);  // Retrieve the available slots for the selected doctor

        // Step 3: Check if the doctor exists and has available slots
        if (availableSlots == null || availableSlots.isEmpty()) {
            view.displayMessage("No available slots for the selected doctor. Please try again.");
            return;
        }

        // Step 4: Display the available slots for the selected doctor
        //view.displayAvailableSlots(availableSlots);

        // Step 5: Ask the user to select a time slot for the appointment
        String dateTimeStr = view.getAppointmentDateTime();  // Get the selected time slot from the user

        LocalDateTime dateTime;
        try {
            // Parse the selected date and time input
            dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            // Step 6: Check if the selected slot is available
            if (!availableSlots.contains(formattedDateTime)) {
                view.displayMessage("The selected slot is not available. Please choose a different time.");
                return;
            }

            // Step 7: Check if the user already has an appointment for the same time
            for (Appointment existingAppointment : appointments) {
                if (existingAppointment.getPatientID().equals(model.getUserID()) && 
                    existingAppointment.getDateTime().equals(dateTime)) {
                    view.displayMessage("You have an existing appointment request that is currently being processed on the same day and time. Please check if the appointment has gone through using the view Scheduled Appointment.");
                    return;
                }
            }

            // Step 8: Create a new appointment for the selected time slot
            Appointment appointment = new Appointment(
                    "APT" + System.currentTimeMillis(),  // Generate a unique appointment ID
                    model.getUserID(),  // Patient ID
                    doctorID,  // Doctor's ID
                    dateTime  // Selected date and time
            );

            appointment.setStatus("pending");  // Set the appointment status to pending
            appointments.add(appointment);  // Add the new appointment to the list
            saveAppointments();  // Save the updated list of appointments

            // Remove the booked slot from the available slots
            availableSlots.remove(formattedDateTime);

            view.displayMessage("Appointment scheduled successfully.");
        } catch (Exception e) {
            view.displayMessage("Invalid date and time. Please enter a valid date and time.");
        }
        }

    /**
     * Allows the patient to reschedule an appointment.
     */
    private void rescheduleAppointment() {
        
        List<Appointment> patientAppointments = new ArrayList<>();
        for (Appointment appt : appointments) {
            if (appt.getPatientID().equals(model.getUserID()) && !appt.getStatus().equals("completed")) {
                patientAppointments.add(appt);
            }
        }
        
        if (patientAppointments.isEmpty()) {
            view.displayMessage("No scheduled appointments to reschedule.");
            return;
        }

        view.displayScheduledAppointments(patientAppointments);
        String appointmentID = view.getAppointmentIDInput();

        Appointment appointmentToReschedule = null;
        for (Appointment appt : patientAppointments) {
            if (appt.getAppointmentID().equals(appointmentID)) {
                appointmentToReschedule = appt;
                break;
            }
        }

        if (appointmentToReschedule == null) {
            view.displayMessage("Appointment ID not found. Please try again.");
            return;
        }

        Map<String, List<String>> doctorSlots = new HashMap<>();
        for (Map.Entry<String, Doctor> entry : doctors.entrySet()) {
            String doctorID = entry.getKey();
            Doctor doctor = entry.getValue();

            List<String> slots = loadSchedule(doctorID);

            if (!slots.isEmpty()) {
                System.out.println("Available slots for " + doctor.getName() + " (" + doctorID + "):");
                for (String slot : slots) {
                    System.out.println(slot);
                }
                doctorSlots.put(doctorID, slots);
            }
        }

        String newDoctorID = view.getDoctorIDInput();
        List<String> availableSlotsForDoctor = doctorSlots.get(newDoctorID);
        
        if (availableSlotsForDoctor == null || availableSlotsForDoctor.isEmpty()) {
            view.displayMessage("No available slots for the selected doctor.");
            return;
        }

        String newDateTimeStr = view.getAppointmentDateTime();
        LocalDateTime newDateTime;
        
        try {
            newDateTime = LocalDateTime.parse(newDateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (Exception e) {
            view.displayMessage("Invalid date and time format. Please enter it as yyyy-MM-dd HH:mm.");
            return;
        }

        String formattedNewDateTime = newDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        if (!availableSlotsForDoctor.contains(formattedNewDateTime)) {
            view.displayMessage("The selected time slot is not available. Please choose a different time.");
            return;
        }

        for (Appointment existingAppointment : appointments) {
            if (existingAppointment.getPatientID().equals(model.getUserID()) && 
                existingAppointment.getDateTime().equals(newDateTime)) {
                view.displayMessage("You already have an appointment at this time. Please choose another slot.");
                return;
            }
        }

        String oldDateTime = appointmentToReschedule.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        availableSlotsForDoctor.add(oldDateTime);

        appointmentToReschedule.setDateTime(newDateTime);

        if (appointmentToReschedule.getStatus().equals("confirmed")) {
            appointmentToReschedule.setStatus("pending");
        }

        availableSlotsForDoctor.remove(formattedNewDateTime);

        saveAppointments();
        
        view.displayMessage("Appointment rescheduled successfully.");
    }

    /**
     * Allows the patient to cancel an appointment.
     */
    private void cancelAppointment() {
        // Implementation for canceling an appointment
        List<Appointment> patientAppointments = getPatientAppointments();

        if (patientAppointments.isEmpty()) {
            view.displayMessage("You have no scheduled appointments to cancel.");
            return;
        }

        view.displayScheduledAppointments(patientAppointments);

        String appointmentID = view.getAppointmentIDInput(); // Assuming this method retrieves the appointment ID

        Appointment appointmentToCancel = null;
        for (Appointment appt : appointments) {
            if (appt.getAppointmentID().equals(appointmentID) && appt.getPatientID().equals(model.getUserID())) {
                appointmentToCancel = appt;
                break;
            }
        }

        if (appointmentToCancel == null) {
            view.displayMessage("Appointment ID not found or you do not have permission to cancel it.");
            return;
        }

        // Update appointment status and remove it from the list
        appointmentToCancel.setStatus("canceled");
        appointments.remove(appointmentToCancel);
        
        // Save the updated appointments list
        saveAppointments();
        view.displayMessage("Appointment canceled successfully.");

    }

    /**
     * Displays the patient's scheduled appointments.
     */
    private void viewScheduledAppointments() {
        List<Appointment> patientAppointments = appointments.stream()
        .filter(appt -> !appt.getStatus().equals("completed"))  // Filter out completed appointments
        .filter(appt -> appt.getPatientID().equals(model.getUserID()))  // Filter appointments for the current patient
        .collect(Collectors.toList());  // Collect the results back into a list

        if (patientAppointments.isEmpty()) {
            view.displayMessage("You have no scheduled appointments.");
            return;
        }
        
        for (Appointment appt : patientAppointments) {
            String doctorID = appt.getDoctorID();
            Doctor doctor = doctors.get(doctorID);  // Retrieve the doctor object based on doctorID
            if (doctor != null) {
                appt.setDoctorName(doctor.getName());  // Set the doctor's name in the appointment object
            } else {
                appt.setDoctorName("Unknown Doctor");  // Handle the case where the doctor is not found
            }
        }    
        // Display the filtered list of appointments with doctor names
        view.displayScheduledAppointments(patientAppointments);
    }

    /**
     * Displays the patient's past appointment outcomes.
     */
    private void viewPastAppointmentOutcomes() {
        // Implementation for displaying past appointment outcomes
        List<AppointmentOutcome> pastOutcomes = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now(); // Get the current time

        // Loop through appointments to find past ones
        for (Appointment appointment : appointments) {
            if (appointment.getPatientID().equals(model.getUserID())) {
                // Assuming getOutcome() returns an AppointmentOutcome or a string
                if (appointment.getOutcome() != null){
                    pastOutcomes.add(appointment.getOutcome());
                }
            }
        }

        if (pastOutcomes.isEmpty()) {
            view.displayMessage("No past appointment outcomes found.");
        } else {
            view.displayPastAppointmentOutcomes(pastOutcomes); // Method in PatientView to display outcomes
        }
    }

    /**
     * Saves the patient model to the serialized file.
     */

    /**
     * Loads appointments from the serialized file.
     */
    private void loadAppointments() {
        try {
            appointments = (List<Appointment>) SerializationUtil.deserialize("appointments.ser");
            /*appointments = appointments.stream()
                                   .filter(appt -> !appt.getStatus().equals("completed"))
                                   .collect(Collectors.toList()); */
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
            doctors = new HashMap<>();
            HashMap<String, User> users = (HashMap<String, User>) SerializationUtil.deserialize("users.ser");
            for (User user : users.values()) {
                if (user.getRole().equals("Doctor")) {
                    doctors.put(user.getUserID(), (Doctor) user);
                }
            }
            
        } catch (Exception e) {
            System.out.println("Error loading Doctors.");
        }
    }

    /**
     * Loads the schedule from a serialized file.
     */    
    private List<String> loadSchedule(String docid) {
        List<String> schedule = new ArrayList<>();
    try {
        String fileName = "Schedule_" + docid + ".ser";
        // Load schedule from serialized file
        schedule = (List<String>) SerializationUtil.deserialize(fileName);
        
        // Remove slots that contain "Unavailable" at the end of the string
        schedule.removeIf(slot -> slot.trim().endsWith("Unavailable"));
        schedule.removeIf(slot -> slot.trim().contains("Confirmed"));
        
    } catch (Exception e) {
        System.out.println("Error loading schedule: " + e.getMessage());
    }
    return schedule;
    }
}
