package views;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import models.Appointment;
import models.MedicalRecord;

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
        // Display patient details
        System.out.println("\nPatient Medical Record:");
        System.out.println("Patient ID: " + record.getPatientID());
        System.out.println("Name: " + record.getName());
        System.out.println("Date of Birth: " + record.getDateOfBirth());
        System.out.println();

        // Display other details
        System.out.println("Past Medical History:");
        List<String> pastDiagnoses = record.getPastDiagnoses();
        List<String> pastTreatments = record.getPastTreatments();
        if (!pastDiagnoses.isEmpty())
        {
            for(int i = 0; i < pastDiagnoses.size(); i++){
                System.out.println("Past Diagnosis: " + pastDiagnoses.get(i));
                }
        }
        else{
            System.out.println("No past diagnoses.");
        }

        if (!pastTreatments.isEmpty())
        {
            for(int i = 0; i < pastTreatments.size(); i++){
                System.out.println("Past Treatment: " + pastTreatments.get(i));
                }
        }
        else{
            System.out.println("No past treatments.");
        }
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateTimeStr;
        while (true) {
            System.out.print("Enter Date and Time to block (YYYY-MM-DD HH:MM): ");
            dateTimeStr = scanner.nextLine();
            try {
                LocalDateTime.parse(dateTimeStr, formatter);
                break; // Exit loop if parsing is successful
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Please enter the date and time in the format YYYY-MM-DD HH:MM.");
            }
        }
        return dateTimeStr;
    }



     //Original Code
    // public String getAvailabilityInput() {
    //     //To be changed once confirmed
    //     System.out.print("Enter Availability Time (YYYY-MM-DD HH:MM): ");
    //     scanner.nextLine();
    //     return scanner.nextLine();
    // }

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
