import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pharmacist extends User {
    private static final long serialVersionUID = 1L;
    private List<Appointment> appointmentRecords; // List of appointment outcome records
    private Inventory inventory; // Medication inventory

    public Pharmacist(String hospitalId, String name, String password, List<Appointment> appointmentRecords,
            Inventory inventory) {
        super(hospitalId, name, password, "Pharmacist");
        this.appointmentRecords = appointmentRecords;
        this.inventory = inventory;
    }

    @Override
    public void showMenu(UserManager userManager) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Pharmacist Menu:");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Change Password");
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewAppointmentOutcomeRecord();
                    break;
                case 2:
                    updatePrescriptionStatus(sc);
                    break;
                case 3:
                    viewMedicationInventory();
                    break;
                case 4:
                    submitReplenishmentRequest(sc);
                    break;
                case 5:
                    changePassword(sc, userManager);
                    break;
            }
        } while (choice != 6);
    }

    private void viewAppointmentOutcomeRecord() {
        System.out.println("Appointment Outcome Records:");
        for (Appointment appointment : appointmentRecords) {
            System.out.println(appointment.toString());
        }
    }

    private void updatePrescriptionStatus(Scanner sc) {
        System.out.print("Enter Appointment ID: ");
        String appointmentId = sc.next();
        Appointment appointment = findAppointmentById(appointmentId);

        if (appointment != null) {
            System.out.print("Enter new status for the prescription: ");
            String status = sc.next();
            appointment.setPrescriptionStatus(status);
            System.out.println("Prescription status updated successfully.");
        } else {
            System.out.println("Appointment not found.");
        }
    }

    private void viewMedicationInventory() {
        System.out.println("Medication Inventory:");
        inventory.displayInventory();
    }

    private void submitReplenishmentRequest(Scanner sc) {
        System.out.print("Enter medication name: ");
        String medicationName = sc.next();
        System.out.print("Enter quantity to replenish: ");
        int quantity = sc.nextInt();

        inventory.submitReplenishmentRequest(medicationName, quantity);
        System.out.println("Replenishment request submitted.");
    }

    private Appointment findAppointmentById(String appointmentId) {
        for (Appointment appointment : appointmentRecords) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                return appointment;
            }
        }
        return null;
    }
}