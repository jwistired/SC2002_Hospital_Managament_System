package models;

/**
 * Class representing a patient in the hospital management system.
 * This class extends the User class and holds a patient's medical record.
 */
public class Patient extends User {
    private static final long serialVersionUID = 1L;
    private MedicalRecord medicalRecord;

    /**
     * Constructs a Patient object with the specified user details and initializes the medical record.
     *
     * @param userID    The patient's ID.
     * @param name      The patient's name.
     * @param password  The patient's password.
     * @param dateOfBirth The patient's date of birth.
     * @param gender    The patient's gender.
     * @param bloodType The patient's blood type.
     * @param email     The patient's email address.
     * @param contactNo The patient's contact number.
     */
    public Patient(String userID, String name, String password, String dateOfBirth, String gender, String bloodType, String email, String contactNo) {
        super(userID, name, password, "Patient");
        this.medicalRecord = new MedicalRecord(userID, name, dateOfBirth, gender, bloodType, email, contactNo);
    }

    /**
     * Gets the patient's medical record.
     *
     * @return The patient's medical record.
     */
    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    /**
     * Sets or updates the patient's medical record with new contact information.
     * If the medical record does not exist, a new one will be created.
     *
     * @param email     The new email address of the patient.
     * @param contactNo The new contact number of the patient.
     */
    public void setMedicalRecord(String email, String contactNo) {
        if (this.medicalRecord == null){
            this.medicalRecord = new MedicalRecord(this.getUserID(), this.getName(), this.getDateOfBirth(), this.getGender(), this.getBloodType(), email, contactNo);
        } else {
            // Update existing medical record with new contact details
            this.medicalRecord.setEmail(email);
            this.medicalRecord.setContactNo(contactNo);
        }
    }
}
