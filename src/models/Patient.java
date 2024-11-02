package models;

/**
 * Class representing a patient in the hospital management system.
 */
public class Patient extends User {
    private static final long serialVersionUID = 1L;
    private MedicalRecord medicalRecord;

    /**
     * Constructs a Patient object.
     *
     * @param userID   The patient's ID.
     * @param name     The patient's name.
     * @param password The patient's password.
     */
    public Patient(String userID, String name, String password) {
        super(userID, name, password, "Patient");
        this.medicalRecord = new MedicalRecord(userID, name);
    }

    /**
     * Gets the patient's medical record.
     *
     * @return The medical record.
     */
    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    /**
     * Sets the patient's medical record.
     *
     * @param medicalRecord The medical record to set.
     */
    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
}
