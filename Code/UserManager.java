import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private Map<String, User> users = new HashMap<>();
    private static final String USERS_FILE = "Data/users.dat";

    public UserManager() {
        loadUsers();
    }

    // Load users from file
    private void loadUsers() {
        File file = new File(USERS_FILE);

        if (file.exists()) {
            // initializeUsers(); // For testing
            Object deserializedObject = SerializationUtils.deserialize(USERS_FILE);
            if (deserializedObject instanceof Map) {
                users = (Map<String, User>) deserializedObject;
            }
        } else {
            // If file doesn't exist, initialize with some hardcoded users
            initializeUsers();
        }
    }

    // Save users to file
    public void saveUsers() {
        SerializationUtils.serialize(users, USERS_FILE);
    }

    public void initializeUsers() {
        // Initialize with some sample users
        MedicalRecord patientRecord = new MedicalRecord("P001", "John Doe", "A+", "Diabetes");
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment("A001", "D001", "P001", "2024-10-15", "Completed"));
        appointments.add(new Appointment("A002", "D002", "P002", "2024-10-16", "Pending"));
        Inventory inventory = new Inventory();

        users.put("P001", new Patient("P001", "John Doe", "password", patientRecord));
        users.put("D001", new Doctor("D001", "Dr. Smith", "password"));
        users.put("PH001", new Pharmacist("PH001", "Alice", "password", appointments, inventory));
        users.put("A001", new Administrator("A001", "Admin", "password", appointments, inventory));
        saveUsers(); // Save the initial user list to the file
    }

    public User authenticateUser(String hospitalId, String password) {
        User user = users.get(hospitalId);
        if (user != null && user.checkPassword(password)) {
            return user;
        }
        return null;
    }

    public void addUser(User user) {
        users.put(user.getHospitalId(), user);
        saveUsers(); // Save after adding new user
    }

    public void removeUser(String id) {
        users.remove(id);
        saveUsers(); // Save after adding new user
    }

    public void displayUsers() {
        System.out.println("Users:");
        for (User user : users.values()) {
            System.out.println("ID: " + user.getHospitalId() + ", Name: " + user.name + ", Role: " + user.role);
        }
    }
}
