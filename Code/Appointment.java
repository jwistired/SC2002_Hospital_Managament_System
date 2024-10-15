import java.io.Serializable;

public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;
    private String appointmentId;
    private String doctorId;
    private String patientId;
    private String date;
    private String status;

    public Appointment(String appointmentId, String doctorId, String patientId, String date, String status) {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Appointment ID: " + appointmentId + "\nDoctor: " + doctorId + "\nPatient: " + patientId + "\nDate: " + date + "\nStatus: " + status;
    }
}
