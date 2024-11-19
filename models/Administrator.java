package models;

/**
 * Class representing an administrator in the hospital management system.
 */
public class Administrator extends User {
    private static final long serialVersionUID = 1L;
    /**
     * Constructs an Administrator object.
     *
     * @param userID   The administrator's ID.
     * @param name     The administrator's name.
     * @param password The administrator's password.
     */
    public Administrator(String userID, String name, String password) {
        super(userID, name, password, "Administrator");
    }

    // Additional administrator-specific methods can be added here.
}
