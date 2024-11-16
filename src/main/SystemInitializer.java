package main;

import models.*;
import utils.SerializationUtil;
import utils.Config;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to initialize system data for the Hospital Management System.
 */
public class SystemInitializer {
    /**
     * Initializes the system data only if it doesn't already exist.
     */
    public static void initializeSystem() {
        try {
            // Check if users data file exists
            File usersFile = new File(Config.DATABASE_DIR + "users.ser");
            if (!usersFile.exists()) {
                initializeUsers();
            } else {
                System.out.println("Users data already exists. Skipping user initialization.");
            }

            // Check if appointments data file exists
            File appointmentsFile = new File(Config.DATABASE_DIR + "appointments.ser");
            if (!appointmentsFile.exists()) {
                initializeAppointments();
            } else {
                System.out.println("Appointments data already exists. Skipping appointment initialization.");

            }

            // Check if inventory data file exists
            File inventoryFile = new File(Config.DATABASE_DIR + "inventory.ser");
            if (!inventoryFile.exists()) {
                initializeInventory();
            } else {
                System.out.println("Inventory data already exists. Skipping inventory initialization.");
            }

            System.out.println("System initialization complete.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initializeUsers() throws Exception {
        HashMap<String, User> users = new HashMap<>();

        // Add administrator
        Administrator admin = new Administrator("admin", "Administrator", "password");
        users.put(admin.getUserID(), admin);

        // Add a doctor
        Doctor doctor = new Doctor("doc1", "Dr. Smith", "password");
        users.put(doctor.getUserID(), doctor);

        // Add another doctor
        Doctor doctor2 = new Doctor("doc2", "Dr. Johnson", "password");
        users.put(doctor2.getUserID(), doctor2);

        // Add a pharmacist
        Pharmacist pharmacist = new Pharmacist("pharm1", "Pharmacist John", "password");
        users.put(pharmacist.getUserID(), pharmacist);

        // Add a patient
        Patient patient = new Patient("patient1", "Jane Doe", "password", "1999-10-20", "F", "A", "wongtiji1@gmail.com", "9861235");
        System.out.println("Initialized patient: " + patient.getMedicalRecord().getDateOfBirth());
        users.put(patient.getUserID(), patient);

        // Add another patient
        Patient patient2 = new Patient("patient2", "John Smith", "password", "2001-09-12",  "M", "B", "wong1922@gmail.com", "98271243");
        users.put(patient2.getUserID(), patient2);

        SerializationUtil.serialize(users, "users.ser");
        System.out.println("Initialized users data.");
    }

    private static void initializeAppointments() throws Exception {
        List<Appointment> appointments = new ArrayList<>();

        // Create some dummy appointments
        Appointment appt1 = new Appointment(
                "APT1001",
                "patient1",
                "doc1",
                LocalDateTime.now().plusDays(1).withHour(9).withMinute(0));
        appt1.setStatus("confirmed");

        Appointment appt2 = new Appointment(
                "APT1002",
                "patient2",
                "doc1",
                LocalDateTime.now().plusDays(2).withHour(10).withMinute(30));
        appt2.setStatus("pending");

        Appointment appt3 = new Appointment(
                "APT1003",
                "patient1",
                "doc2",
                LocalDateTime.now().minusDays(3).withHour(14).withMinute(0));
        appt3.setStatus("completed");

        // Optionally, add an outcome to the completed appointment
        AppointmentOutcome outcome = new AppointmentOutcome(
                appt3.getDateTime().toString(),
                "Consultation",
                new ArrayList<>(), // No prescriptions
                "Patient reported mild symptoms. Recommended rest.");
        appt3.setOutcome(outcome);

        // Add appointments to the list
        appointments.add(appt1);
        appointments.add(appt2);
        appointments.add(appt3);

        // Serialize the appointments
        SerializationUtil.serialize(appointments, "appointments.ser");
        System.out.println("Initialized appointments data with dummy appointments.");
    }

    private static void initializeInventory() throws Exception {
        List<InventoryItem> inventory = new ArrayList<>();
        inventory.add(new InventoryItem("Aspirin", 100, 20));
        inventory.add(new InventoryItem("Paracetamol", 150, 30));
        inventory.add(new InventoryItem("Ibuprofen", 80, 15));
        SerializationUtil.serialize(inventory, "inventory.ser");
        System.out.println("Initialized inventory data.");
    }
}