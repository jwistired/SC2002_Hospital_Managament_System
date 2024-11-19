package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a medical record of a patient.
 * This class stores personal information, past diagnoses, and treatments of a patient.
 */
public class MedicalRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    private String patientID;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String bloodType;
    private String email;
    private String contactNo;
    private List<String> pastDiagnoses;
    private List<String> pastTreatments;

    /**
     * Constructs a MedicalRecord object with the specified patient information.
     *
     * @param patientID The patient's ID.
     * @param name      The patient's name.
     * @param dateOfBirth The patient's date of birth.
     * @param gender    The patient's gender.
     * @param bloodType The patient's blood type.
     * @param email     The patient's email address.
     * @param contactNo The patient's contact number.
     */
    public MedicalRecord(String patientID, String name, String dateOfBirth, String gender, String bloodType, String email, String contactNo){
        this.patientID = patientID;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.email = email;
        this.contactNo = contactNo;
        if (this.pastDiagnoses == null) {
            this.pastDiagnoses = new ArrayList<>();
        }
        if (this.pastTreatments == null) {
            this.pastTreatments = new ArrayList<>();
        }
    }

    // Getters and Setters

    /**
     * Gets the patient's ID.
     *
     * @return The patient's ID.
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * Gets the patient's name.
     *
     * @return The patient's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the patient's date of birth.
     *
     * @return The patient's date of birth.
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Gets the patient's gender.
     *
     * @return The patient's gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Gets the patient's blood type.
     *
     * @return The patient's blood type.
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * Gets the patient's email address.
     *
     * @return The patient's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the patient's contact number.
     *
     * @return The patient's contact number.
     */
    public String getContactNo() {
        return contactNo;
    }

    /**
     * Sets the patient's email address.
     *
     * @param email The new email address of the patient.
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * Sets the patient's contact number.
     *
     * @param contactNo The new contact number of the patient.
     */
    public void setContactNo(String contactNo){
        this.contactNo = contactNo;
    }

    /**
     * Gets the list of the patient's past diagnoses.
     *
     * @return The list of past diagnoses.
     */
    public List<String> getPastDiagnoses() {
        return pastDiagnoses;
    }

    /**
     * Gets the list of the patient's past treatments.
     *
     * @return The list of past treatments.
     */
    public List<String> getPastTreatments() {
        return pastTreatments;
    }

    /**
     * Adds a diagnosis to the patient's medical record.
     *
     * @param diagnosis The diagnosis to be added.
     */
    public void addDiagnosis(String diagnosis) {
        pastDiagnoses.add(diagnosis);
    }

    /**
     * Adds a treatment to the patient's medical record.
     *
     * @param treatment The treatment to be added.
     */
    public void addTreatment(String treatment) {
        pastTreatments.add(treatment);
    }
}
