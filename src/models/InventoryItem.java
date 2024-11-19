package models;

import java.io.Serializable;

/**
 * Class representing an inventory item (medication) in the hospital.
 * This class tracks the medication name, stock level, low stock alert level,
 * and replenish request amount for the hospital's inventory system.
 */
public class InventoryItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private String medicationName;
    private int stockLevel;
    private int lowStockAlertLevel;
    private int replenishRequestAmount;

    /**
     * Constructs an InventoryItem object with the specified medication name, stock level, 
     * and low stock alert level.
     *
     * @param medicationName     The name of the medication.
     * @param stockLevel         The current stock level of the medication.
     * @param lowStockAlertLevel The stock level at which an alert should be triggered for low stock.
     */
    public InventoryItem(String medicationName, int stockLevel, int lowStockAlertLevel) {
        this.medicationName = medicationName;
        this.stockLevel = stockLevel;
        this.lowStockAlertLevel = lowStockAlertLevel;
    }

    // Getters and Setters

    /**
     * Gets the name of the medication.
     *
     * @return The name of the medication.
     */
    public String getMedicationName() {
        return medicationName;
    }

    /**
     * Gets the current stock level of the medication.
     *
     * @return The current stock level.
     */
    public int getStockLevel() {
        return stockLevel;
    }

    /**
     * Gets the low stock alert level, which triggers an alert when stock falls below this level.
     *
     * @return The low stock alert level.
     */
    public int getLowStockAlertLevel() {
        return lowStockAlertLevel;
    }

    /**
     * Sets the current stock level of the medication.
     *
     * @param stockLevel The new stock level of the medication.
     */
    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    /**
     * Sets the low stock alert level, which triggers an alert when stock falls below this level.
     *
     * @param lowStockAlertLevel The new low stock alert level.
     */
    public void setLowStockAlertLevel(int lowStockAlertLevel) {
        this.lowStockAlertLevel = lowStockAlertLevel;
    }

    /**
     * Gets the amount of medication to request for replenishment.
     *
     * @return The replenish request amount.
     */
    public int getReplenishRequestAmount() {
        return replenishRequestAmount;
    }

    /**
     * Sets the amount of medication to request for replenishment.
     *
     * @param replenishRequestAmount The amount of medication to request for replenishment.
     */
    public void setReplenishRequestAmount(int replenishRequestAmount) {
        this.replenishRequestAmount = replenishRequestAmount;
    }
}
