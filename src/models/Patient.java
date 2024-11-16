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
    public Patient(String userID, String name, String password, String dateOfBirth, String gender, String bloodType, String email, String contactNo) {
        super(userID, name, password, "Patient");
        this.medicalRecord = new MedicalRecord(userID, name, dateOfBirth, gender,  bloodType, email, contactNo);
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
    public void setMedicalRecord(String email, String contactNo) {
        if (this.medicalRecord == null){
            this.medicalRecord = new MedicalRecord(this.getUserID(), this.getName(), this.getDateOfBirth(), this.getGender(),this.getBloodType(), email, contactNo);
        } else {
            // Update existing medical record
            this.medicalRecord.setEmail(email);
            this.medicalRecord.setContactNo(contactNo);
        }
    }
}
