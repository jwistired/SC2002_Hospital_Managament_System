import java.io.Serializable;

public class MedicalRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    private String patientId;
    private String name;
    private String bloodType;
    private String diagnosisHistory;

    public MedicalRecord(String patientId, String name, String bloodType, String diagnosisHistory) {
        this.patientId = patientId;
        this.name = name;
        this.bloodType = bloodType;
        this.diagnosisHistory = diagnosisHistory;
    }

    @Override
    public String toString() {
        return "ID: " + patientId + "\nName: " + name + "\nBlood Type: " + bloodType + "\nDiagnosis: " + diagnosisHistory;
    }
}
