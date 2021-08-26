package model; /**
* Supplied class Part.java 
 */

import javafx.collections.ObservableList;

/**
 *
 * @author Ashley Kestler
 */
public abstract class Part {
    private int partID;
    private String partName;
    private double partPrice;
    private int partStock;
    private int min;
    private int max;

    public Part(int partID, String partName, double partPrice, int partStock, int min, int max) {
        this.partID = partID;
        this.partName = partName;
        this.partPrice = partPrice;
        this.partStock = partStock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getPartID() {
        return partID;
    }

    /**
     * @param partID the id to set
     */
    public void setPartID(int partID) {
        this.partID = partID;
    }

    /**
     * @return the name
     */
    public String getPartName() {
        return partName;
    }

    /**
     * @param partName the name to set
     */
    public void setPartName(String partName) {
        this.partName = partName;
    }

    /**
     * @return the price
     */
    public double getPartPrice() {
        return partPrice;
    }

    /**
     * @param partPrice the price to set
     */
    public void setPartPrice(double partPrice) {
        this.partPrice = partPrice;
    }
    
    /**
     * @return the stock
     */
    public int getPartStock() {
        return partStock;
    }

    /**
     * @param partStock the stock to set
     */
    public void setPartStock(int partStock) {
        this.partStock = partStock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    
}