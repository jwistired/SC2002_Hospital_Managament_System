import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

public class Inventory implements Serializable {
    private Map<String, Integer> medicationStock = new HashMap<>();
    private Map<String, Integer> replenishmentRequests = new HashMap<>();

    public Inventory() {
        // Initialize some sample medications
        medicationStock.put("Paracetamol", 100);
        medicationStock.put("Ibuprofen", 50);
        medicationStock.put("Amoxicillin", 30);
    }

    public void displayInventory() {
        System.out.println("Medication Inventory:");
        for (Map.Entry<String, Integer> entry : medicationStock.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " units");
        }
    }

    public void updateMedication(String medicationName, int quantity) {
        medicationStock.put(medicationName, quantity);
    }

    public void submitReplenishmentRequest(String medicationName, int quantity) {
        replenishmentRequests.put(medicationName, quantity);
    }

    public void displayReplenishmentRequests() {
        System.out.println("Replenishment Requests:");
        for (Map.Entry<String, Integer> entry : replenishmentRequests.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " units requested");
        }
    }

    public void approveReplenishmentRequest(String medicationName) {
        if (replenishmentRequests.containsKey(medicationName)) {
            int quantity = replenishmentRequests.remove(medicationName);
            medicationStock.put(medicationName, medicationStock.getOrDefault(medicationName, 0) + quantity);
            System.out.println("Replenishment request approved. " + quantity + " units added.");
        } else {
            System.out.println("No such replenishment request found.");
        }
    }
}