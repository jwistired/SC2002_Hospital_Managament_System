package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a medical record of a patient.
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
     * Constructs a MedicalRecord object.
     *
     * @param patientID The patient's ID.
     * @param name      The patient's name.
     * @param dateOfBirth The patient's date of birth.
     * @param gender    The patient's gender.
     * @param bloodType The patient's blood type.
     * @param contactInfo The patient's contact information.
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

    public String getPatientID() {
        return patientID;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String  getEmail() {
        return email;
    }

    public  String getContactNo() {
        return contactNo;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setContactNo(String contactNo){
        this.contactNo = contactNo;
    }

    public List<String> getPastDiagnoses() {
        return pastDiagnoses;
    }

    public List<String> getPastTreatments() {
        return pastTreatments;
    }

    // Other getters and setters for the fields

    public void addDiagnosis(String diagnosis) {
        pastDiagnoses.add(diagnosis);
    }

    public void addTreatment(String treatment) {
        pastTreatments.add(treatment);
    }
}
