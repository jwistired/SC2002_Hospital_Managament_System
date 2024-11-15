package controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import models.Appointment;
import models.AppointmentOutcome;
import models.Doctor;
import models.MedicalRecord;
import models.Patient;
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
    //private HashMap<String, Doctor> doctors;


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
                    viewAvailableAppointmentSlots(); //Extract Schedule from Doctor 
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
        String doctorID = view.getDoctorIDInput(); // Assume this method gets the ID of the selected doctor
        List<String> slots = doctor.getSchedule(); //To be changed
        view.displayAvailableSlots(slots);
    }
        

    /**
 * Retrieves available appointment slots for a specific doctor.
 */
    private List<String> getAvailableAppointmentSlots(String doctorID) {
        List<String> slots = doctor.getSchedule();

        // Filter out slots that are already taken
        for (Appointment appt : appointments) {
            if (appt.getDoctorID().equals(doctorID)) {
                String bookedSlot = appt.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                slots.remove(bookedSlot);
            }
        }

        return slots;
    } /* 

    /**
     * Generates available appointment slots for the next 15 days.
     *
     * @return List of available appointment slots formatted as "yyyy-MM-dd HH:mm".
     */
    /*private List<String> generateAvailableAppointmentSlots() {
        List<String> availableSlots = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        String[] timeSlots = {"09:00", "10:00", "11:00", "14:00", "15:00", "16:00"};

        // Loop through the next 15 days
        for (int i = 0; i < 15; i++) {
            // Calculate the date for the current day
            LocalDateTime date = now.plusDays(i);
            String dateString = date.toLocalDate().toString(); // Get the date in yyyy-MM-dd format

            // Loop through each time slot
            for (String time : timeSlots) {
                // Combine the date and time into a single string
                String slot = dateString + " " + time;
                availableSlots.add(slot);
            }
        }

        return availableSlots;
    }
    */    

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
        String doctorID = view.getDoctorIDInput(); //Instead of DoctorID, maybe we should ask for the Doctor's name? Print out the list of Doctors?
        String dateTimeStr = view.getAppointmentDateTime();
        LocalDateTime dateTime; 
        List<String> slots = getAvailableAppointmentSlots(doctorID);
        String formattedDateTime;

        try{
            dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); 
            formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //Include try and exception handling

            if (!slots.contains(formattedDateTime)) {
                view.displayMessage("The selected slot is not available. Please choose a different time.");
                return;
            }
        
            Appointment appointment = new Appointment(
                    "APT" + System.currentTimeMillis(),
                    model.getUserID(),
                    doctorID,
                    dateTime
            );
            appointment.setStatus("pending");
            appointments.add(appointment);
            saveAppointments();
            slots.remove(formattedDateTime);
            view.displayMessage("Appointment scheduled successfully.");
            } catch (Exception e){
                view.displayMessage("Invalid date and time. Please enter a valid date and time.");
            }
        }

    /**
     * Allows the patient to reschedule an appointment.
     */
    private void rescheduleAppointment() {
        
        List<Appointment> patientAppointments = new ArrayList<>();
        for (Appointment appt : appointments) {
            if (appt.getPatientID().equals(model.getUserID())) {
                patientAppointments.add(appt);
            }
        }
        
        if (patientAppointments.isEmpty()) {
            view.displayMessage("No scheduled appointments to reschedule.");
            return;
        }

        view.displayScheduledAppointments(patientAppointments);
        String appointmentID = view.getAppointmentIDInput(); // Assuming the user inputs the appointment ID they want to reschedule

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

        String newDateTimeStr = view.getAppointmentDateTime();
        LocalDateTime newDateTime;
        
        try {
            newDateTime = LocalDateTime.parse(newDateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (Exception e) {
            view.displayMessage("Invalid date and time format. Please enter it as yyyy-MM-dd HH:mm.");
            return;
        }
    
        String doctorID = appointmentToReschedule.getDoctorID();
        List<String> availableSlots = getAvailableAppointmentSlots(doctorID);
    
        // Check if the new date and time is available
        String formattedNewDateTime = newDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        if (!availableSlots.contains(formattedNewDateTime)) {
            view.displayMessage("The selected time slot is not available. Please choose a different time.");
            return;
        }
    
        // Add the old appointment time back to available slots
        String oldDateTime = appointmentToReschedule.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        availableSlots.add(oldDateTime);
    
        // Update the appointment with the new date and time
        appointmentToReschedule.setDateTime(newDateTime);
        availableSlots.remove(formattedNewDateTime); // Remove the newly rescheduled appointment slot from available slots
    
        saveAppointments(); // Save the updated appointments list
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

        String doctorID = appointmentToCancel.getDoctorID();
        String canceledSlot = appointmentToCancel.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // Add the canceled appointment slot back to available slots
        List<String> availableSlots = getAvailableAppointmentSlots(doctorID);
        if (!availableSlots.contains(canceledSlot)) {
            availableSlots.add(canceledSlot);
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
        List<AppointmentOutcome> pastOutcomes = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now(); // Get the current time

        // Loop through appointments to find past ones
        for (Appointment appointment : appointments) {
            if (appointment.getPatientID().equals(model.getUserID()) && appointment.getDateTime().isBefore(now)) {
                // Assuming getOutcome() returns an AppointmentOutcome or a string
                pastOutcomes.add(appointment.getOutcome());
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
            
            HashMap<String, Doctor> doctors = (HashMap<String, Doctor>) SerializationUtil.deserialize("doctors.ser");
        } catch (Exception e) {
            HashMap<String, Doctor> doctors = new HashMap<>();
        }
    }
}
