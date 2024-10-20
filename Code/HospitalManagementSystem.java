import java.util.Scanner;

public class HospitalManagementSystem {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Hospital Management System!");

        User currentUser = null;
        while (currentUser == null) {
            System.out.print("Enter Hospital ID: ");
            String hospitalId = sc.nextLine();
            System.out.print("Enter Password: ");
            String password = sc.nextLine();

            currentUser = userManager.authenticateUser(hospitalId, password);
            if (currentUser == null) {
                System.out.println("Invalid credentials. Please try again.");
            }
        }

        // Show the menu and pass the userManager to allow saving the password change
        currentUser.showMenu(userManager);
    }
}