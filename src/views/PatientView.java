package views;

import models.MedicalRecord;
import models.Appointment;
import models.AppointmentOutcome;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Class representing the patient view in the hospital management system.
 */
public class PatientView {
    private Scanner scanner;

    /**
     * Constructs a PatientView object.
     */
    public PatientView() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the patient menu.
     */
    public void displayMenu() {
        System.out.println("\nPatient Menu:");
        System.out.println("1. View Medical Record");
        System.out.println("2. Update Personal Information");
        System.out.println("3. View Available Appointment Slots");
        System.out.println("4. Schedule an Appointment");
        System.out.println("5. Reschedule an Appointment");
        System.out.println("6. Cancel an Appointment");
        System.out.println("7. View Scheduled Appointments");
        System.out.println("8. View Past Appointment Outcome Records");
        System.out.println("9. Logout");
    }

    /**
     * Gets the user's menu choice.
     *
     * @return The user's choice.
     */
    public int getUserChoice() {
        System.out.print("Enter your choice: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Displays the medical record.
     *
     * @param record The medical record to display.
     */
    public void displayMedicalRecord(MedicalRecord record) {
        System.out.println("\nMedical Record:");
        System.out.println("Patient ID: " + record.getPatientID());
        System.out.println("Name: " + record.getName());
        System.out.println("Date Of Birth : "+ record.getDateOfBirth());
        System.out.println("Email: " + record.getEmail());
        System.out.println("Contact Number: " + record.getContactNo());
        System.out.println("Blood Type: " +  record.getBloodType());
        
        // Display other details
    }

    /**
     * Gets the updated email input from the user.
     *
     * @return The new email.
     */
    public String getEmailInput() {
        System.out.print("Enter new email: ");
        return scanner.nextLine();
    }

    /**
     * Gets the updated contact number input from the user.
     *
     * @return The new contact number.
     */
    public String getContactNumberInput() {
        System.out.print("Enter new contact number: ");
        return scanner.nextLine();
    }

    /**
     * Displays a list of available appointment slots.
     *
     * @param slots The list of available slots.
     */
    public void displayAvailableSlots(List<String> slots) {
        System.out.println("\nAvailable Appointment Slots:");
        for (String slot : slots) {
            System.out.println(slot);
        }
    }

    /**
     * Gets the doctor ID input from the user.
     *
     * @return The doctor ID.
     */
    public String getDoctorIDInput() {
        System.out.print("Enter Doctor ID: ");
        return scanner.nextLine();
    }

    /**
     * Gets the appointment date and time input from the user.
     *
     * @return The appointment date and time.
     */
    public String getAppointmentDateTime() {
        System.out.print("Enter Appointment Date and Time (YYYY-MM-DD HH:MM): ");
        return scanner.nextLine();
    }
    /**
     * Prompts the user to enter the appointment ID and returns the input.
     * 
     * @return The appointment ID input by the user.
     */
    public String getAppointmentIDInput() {
        System.out.print("Please enter the Appointment ID you want to cancel: ");
        return scanner.nextLine().trim(); // Trim to remove any leading or trailing whitespace
    }

    /**
     * Displays a message to the user.
     *
     * @param message The message to display.
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays the list of scheduled appointments.
     *
     * @param appointments The list of appointments.
     */
    public void displayScheduledAppointments(List<Appointment> appointments) {
        System.out.println("\nScheduled Appointments:");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Appointment appt : appointments) {
            String dateTime = appt.getDateTime().format(formatter);
            System.out.println("Appointment ID: " + appt.getAppointmentID() + ", Date and Time: " + dateTime + ", Status: " + appt.getStatus());
            // Display other details
        }
    }

    /**
     * Displays the list of past appointment outcome records.
     *
     * @param outcomes The list of appointment outcomes.
     */
    public void displayPastAppointmentOutcomes(List<AppointmentOutcome> outcomes) {
        System.out.println("\nPast Appointment Outcomes:");
        for (AppointmentOutcome outcome : outcomes) {
            System.out.println("Date: " + outcome.getDateOfAppointment());
            System.out.println("Service: " + outcome.getTypeOfService());
            System.out.println("Prescription: " + outcome.getPrescriptions());
            System.out.println("Note: " + outcome.getConsultationNotes());
        }
    }
}
