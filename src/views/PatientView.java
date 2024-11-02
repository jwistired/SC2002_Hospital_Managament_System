package views;

import models.MedicalRecord;
import models.Appointment;
import models.AppointmentOutcome;

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
        for (Appointment appt : appointments) {
            System.out.println("Appointment ID: " + appt.getAppointmentID());
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
            // Display other details
        }
    }
}
