import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Patient extends User {
    private static final long serialVersionUID = 1L;
    private MedicalRecord medicalRecord;

    public Patient(String hospitalId, String name, String password, MedicalRecord medicalRecord) {
        super(hospitalId, name, password, "Patient");
        this.medicalRecord = medicalRecord;
    }

    @Override
    public void showMenu(UserManager userManager) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Patient Menu:");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointment Slots");
            System.out.println("4. Schedule an Appointment");
            System.out.println("5. Reschedule an Appointment");
            System.out.println("6. Cancel an Appointment");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointment Outcome Records");
            System.out.println("9. Change Password");
            System.out.println("10. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

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
                    reScheduleAppointment();
                    break;
                case 6:
                    cancelAppointment();
                    break;
                case 7:
                    viewScheduledAppointments();
                    break;
                case 8:
                    viewPastAppointmentRecord();
                    break;
                case 9:
                    changePassword(sc, userManager);
                    break;
            }
        } while (choice != 10);
    }

    private void viewMedicalRecord() {
        System.out.println("Medical Record for: " + this.name);
        System.out.println(medicalRecord.toString());
    }

    private void updatePersonalInfo() {
        // Logic for updating personal information
        System.out.println("Updating personal info...");
    }

    private void viewAvailableAppointmentSlots() {
        // Logic for viewing available slots
        System.out.println("Viewing available appointment slots...");
    }

    private void scheduleAppointment() {
        // Logic for scheduling an appointment
        System.out.println("Scheduling appointment...");
    }

    private void reScheduleAppointment() {
        // Logic for scheduling an appointment
        System.out.println("Scheduling appointment...");
    }

    private void cancelAppointment() {
        // Logic for scheduling an appointment
        System.out.println("Scheduling appointment...");
    }

    private void viewScheduledAppointments() {
        // Logic for viewing scheduled appointments
        System.out.println("Viewing scheduled appointments...");
    }

    private void viewPastAppointmentRecord() {
        // Logic for viewing scheduled appointments
        System.out.println("Viewing scheduled appointments...");
    }

}
