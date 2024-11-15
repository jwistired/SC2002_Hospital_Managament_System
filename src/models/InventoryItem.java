package models;

import java.io.Serializable;

/**
 * Class representing an inventory item (medication) in the hospital.
 */
public class InventoryItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private String medicationName;
    private int stockLevel;
    private int lowStockAlertLevel;
    private int replenishRequestAmount;

    /**
     * Constructs an InventoryItem object.
     *
     * @param medicationName     The name of the medication.
     * @param stockLevel         The current stock level.
     * @param lowStockAlertLevel The low stock alert level.
     */
    public InventoryItem(String medicationName, int stockLevel, int lowStockAlertLevel) {
        this.medicationName = medicationName;
        this.stockLevel = stockLevel;
        this.lowStockAlertLevel = lowStockAlertLevel;
    }

    // Getters and Setters

    public String getMedicationName() {
        return medicationName;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public int getLowStockAlertLevel() {
        return lowStockAlertLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    public void setLowStockAlertLevel(int lowStockAlertLevel) {
        this.lowStockAlertLevel = lowStockAlertLevel;
    }

    public int getReplenishRequestAmount() {
        return replenishRequestAmount;
    }

    public void setReplenishRequestAmount(int replenishRequestAmount) {
        this.replenishRequestAmount = replenishRequestAmount;
    }
}
