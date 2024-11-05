package views;

import models.Appointment;
import models.MedicalRecord;
import java.util.List;
import java.util.Scanner;

/**
 * Class representing the doctor view in the hospital management system.
 */
public class DoctorView {
    private Scanner scanner;

    /**
     * Constructs a DoctorView object.
     */
    public DoctorView() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the doctor menu.
     */
    public void displayMenu() {
        System.out.println("\nDoctor Menu:");
        System.out.println("1. View Patient Medical Records");
        System.out.println("2. Update Patient Medical Records");
        System.out.println("3. View Personal Schedule");
        System.out.println("4. Set Availability for Appointments");
        System.out.println("5. Accept or Decline Appointment Requests");
        System.out.println("6. View Upcoming Appointments");
        System.out.println("7. Record Appointment Outcome");
        System.out.println("8. Logout");
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
     * Displays a patient's medical record.
     *
     * @param record The medical record to display.
     */
    public void displayPatientMedicalRecord(MedicalRecord record) {
        System.out.println("\nPatient Medical Record:");
        System.out.println("Patient ID: " + record.getPatientID());
        System.out.println("Name: " + record.getName());
        // Display other details
    }

    /**
     * Gets the patient ID input from the user.
     *
     * @return The patient ID.
     */
    public String getPatientIDInput() {
        System.out.print("Enter Patient ID: ");
        return scanner.nextLine();
    }

    /**
     * Gets the diagnosis input from the user.
     *
     * @return The diagnosis.
     */
    public String getDiagnosisInput() {
        System.out.print("Enter Diagnosis: ");
        return scanner.nextLine();
    }

    /**
     * Gets the treatment input from the user.
     *
     * @return The treatment.
     */
    public String getTreatmentInput() {
        System.out.print("Enter Treatment Plan: ");
        return scanner.nextLine();
    }

    /**
     * Displays the doctor's personal schedule.
     *
     * @param schedule The list of available appointment times.
     */
    public void displayPersonalSchedule(List<String> schedule) {
        System.out.println("\nYour Personal Schedule:");
        for (String slot : schedule) {
            System.out.println(slot);
        }
    }

    /**
     * Gets the availability time input from the user.
     *
     * @return The availability time.
     */
    public String getAvailabilityInput() {
        System.out.print("Enter Availability Time (YYYY-MM-DD HH:MM): ");
        return scanner.nextLine();
    }

    /**
     * Displays appointment requests for the doctor.
     *
     * @param appointments The list of appointment requests.
     */
    public void displayAppointmentRequests(List<Appointment> appointments) {
        System.out.println("\nAppointment Requests:");
        for (Appointment appt : appointments) {
            System.out.println("Appointment ID: " + appt.getAppointmentID());
            // Display other details
        }
    }

    /**
     * Gets the appointment ID input from the user.
     *
     * @return The appointment ID.
     */
    public String getAppointmentIDInput() {
        System.out.print("Enter Appointment ID: ");
        return scanner.nextLine();
    }

    /**
     * Gets the decision input from the user (accept/decline).
     *
     * @return The decision.
     */
    public String getDecisionInput() {
        System.out.print("Accept or Decline (A/D): ");
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
}