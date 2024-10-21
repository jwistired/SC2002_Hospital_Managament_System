import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Doctor extends User {
    private static final long serialVersionUID = 1L;

    public Doctor(String hospitalId, String name, String password) {
        super(hospitalId, name, password, "Doctor");
    }

    @Override
    public void showMenu(UserManager userManager) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Doctor Menu:");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("8. Change Password");
            System.out.println("9. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

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
                    setAvailabilityForAppointments();
                    break;
                case 5:
                    manageAppointmentRequests();
                    break;
                case 6:
                    viewUpcomingAppointments();
                    break;
                case 7:
                    recordAppointmentOutcome();
                    break;
                case 8:
                    changePassword(sc, userManager);
                    break;
            }
        } while (choice != 9);
    }

    private void viewPatientMedicalRecords() {
        System.out.println("Viewing patient medical records...");
    }

    private void updatePatientMedicalRecords() {
        System.out.println("Updating patient medical records...");
    }

    private void viewPersonalSchedule() {
        System.out.println("Viewing personal schedule...");
    }

    private void setAvailabilityForAppointments() {
        System.out.println("Setting availability for appointments...");
    }

    private void manageAppointmentRequests() {
        System.out.println("Managing appointment requests...");
    }

    private void viewUpcomingAppointments() {
        System.out.println("Viewing upcoming appointments...");
    }

    private void recordAppointmentOutcome() {
        System.out.println("Viewing upcoming appointments...");
    }

}
