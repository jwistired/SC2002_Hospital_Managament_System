import java.util.ArrayList;
import java.util.Scanner;

public class Staff_Management {
    // Attributes
    private ArrayList<String> hospitalStaff;
    private String action;

    // Constructor
    public Staff_Management() {
        hospitalStaff = new ArrayList<>();
    }

    // Method to view staff list
    public void viewStaffList() {
        if (hospitalStaff.isEmpty()) {
            System.out.println("No staff available.");
        } else {
            System.out.println("Staff List:");
            for (String staff : hospitalStaff) {
                System.out.println(staff);
            }
        }
    }

    // Method to update staff list (adding a new staff member)
    public void updateStaffList(String newStaff) {
        hospitalStaff.add(newStaff);
        System.out.println("Staff member added: " + newStaff);
    }

    // Method to remove a staff member
    public void removeStaff(String staffName) {
        if (hospitalStaff.contains(staffName)) {
            hospitalStaff.remove(staffName);
            System.out.println("Staff member removed: " + staffName);
        } else {
            System.out.println("Staff member not found: " + staffName);
        }
    }

    // Method to perform action
    public void performAction(String action) {
        this.action = action;
        System.out.println("Performing action: " + action);
    }

    // Main method for input
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Staff_Management management = new Staff_Management();
        
        while (true) {
            System.out.println("Choose an action: 1-View Staff, 2-Add Staff, 3-Remove Staff, 4-Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    management.viewStaffList();
                    break;
                case 2:
                    System.out.println("Enter staff name to add: ");
                    String newStaff = scanner.nextLine();
                    management.updateStaffList(newStaff);
                    break;
                case 3:
                    System.out.println("Enter staff name to remove: ");
                    String removeStaff = scanner.nextLine();
                    management.removeStaff(removeStaff);
                    break;
                case 4:
                    System.out.println("Exiting system.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
