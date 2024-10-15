import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
        users.put("P001", new Patient("P001", "John Doe", "password", patientRecord));
        users.put("D001", new Doctor("D001", "Dr. Smith", "password"));
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
}
