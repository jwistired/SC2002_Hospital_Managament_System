package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a pharmacist in the hospital management system.
 * This class extends the User class and manages a list of prescriptions assigned to the pharmacist.
 */
public class Pharmacist extends User {
    private static final long serialVersionUID = 1L;
    private List<Prescription> prescriptions;

    /**
     * Constructs a Pharmacist object with the specified user details.
     * Initializes an empty list of prescriptions.
     *
     * @param userID   The pharmacist's ID.
     * @param name     The pharmacist's name.
     * @param password The pharmacist's password.
     */
    public Pharmacist(String userID, String name, String password) {
        super(userID, name, password, "Pharmacist");
        this.prescriptions = new ArrayList<>();
    }

    /**
     * Gets the list of prescriptions assigned to the pharmacist.
     *
     * @return A list of prescriptions.
     */
    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }
}
