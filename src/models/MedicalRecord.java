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
    private List<String> pastDiagnoses;
    private List<String> pastTreatments;

    /**
     * Constructs a MedicalRecord object.
     *
     * @param patientID The patient's ID.
     * @param name      The patient's name.
     */
    public MedicalRecord(String patientID, String name) {
        this.patientID = patientID;
        this.name = name;
        this.pastDiagnoses = new ArrayList<>();
        this.pastTreatments = new ArrayList<>();
    }

    // Getters and Setters

    public String getPatientID() {
        return patientID;
    }

    public String getName() {
        return name;
    }

    // Other getters and setters for the fields

    public List<String> getpastDiagnoses(){
        return pastDiagnoses;
    }

    public List<String> getpastTreatments(){
        return pastTreatments;
    }

    public void addDiagnosis(String diagnosis) {
        pastDiagnoses.add(diagnosis);
    }

    public void addTreatment(String treatment) {
        pastTreatments.add(treatment);
    }
}
