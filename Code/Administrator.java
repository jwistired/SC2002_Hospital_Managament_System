import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Administrator extends User {
    private static final long serialVersionUID = 1L;
    private List<Appointment> appointmentRecords;
    private Inventory inventory;

    public Administrator(String hospitalId, String name, String password,
            List<Appointment> appointmentRecords, Inventory inventory) {
        super(hospitalId, name, password, "Administrator");
        this.appointmentRecords = appointmentRecords;
        this.inventory = inventory;
    }

    @Override
    public void showMenu(UserManager userManager) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Administrator Menu:");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointment Details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Change Password");
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewAndManageHospitalStaff(sc, userManager);
                    break;
                case 2:
                    viewAppointmentDetails();
                    break;
                case 3:
                    manageMedicationInventory(sc);
                    break;
                case 4:
                    approveReplenishmentRequests(sc);
                    break;
                case 5:
                    changePassword(sc, userManager);
                    break;
            }
        } while (choice != 6);
    }

    private void viewAndManageHospitalStaff(Scanner sc, UserManager userManager) {
        System.out.println("Hospital Staff:");
        userManager.displayUsers();

        System.out.print("Enter 'add' to add a new staff or 'remove' to remove a staff: ");
        String action = sc.next();
        if (action.equalsIgnoreCase("add")) {
            System.out.print("Enter ID: ");
            String id = sc.next();
            System.out.print("Enter Name: ");
            String name = sc.next();
            System.out.print("Enter Role (Doctor/Pharmacist): ");
            String role = sc.next();
            System.out.print("Enter Password: ");
            String password = sc.next();
            if (role.equalsIgnoreCase("Doctor")) {
                userManager.addUser(new Doctor(id, name, password));
            } else if (role.equalsIgnoreCase("Pharmacist")) {
                userManager.addUser(new Pharmacist(id, name, password, appointmentRecords, inventory));
            }
            System.out.println("Staff added successfully.");
        } else if (action.equalsIgnoreCase("remove")) {
            System.out.print("Enter Staff ID to remove: ");
            String id = sc.next();
            userManager.removeUser(id);
            System.out.println("Staff removed successfully.");
        }
    }

    private void viewAppointmentDetails() {
        System.out.println("Appointment Details:");
        for (Appointment appointment : appointmentRecords) {
            System.out.println(appointment);
        }
    }

    private void manageMedicationInventory(Scanner sc) {
        inventory.displayInventory();
        System.out.print("Enter medication name to update quantity: ");
        String name = sc.next();
        System.out.print("Enter new quantity: ");
        int quantity = sc.nextInt();
        inventory.updateMedication(name, quantity);
        System.out.println("Inventory updated successfully.");
    }

    private void approveReplenishmentRequests(Scanner sc) {
        inventory.displayReplenishmentRequests();
        System.out.print("Enter medication name to approve request: ");
        String medicationName = sc.next();
        inventory.approveReplenishmentRequest(medicationName);
    }
}