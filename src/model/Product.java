package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Models a Product.
 * Product may contain associated parts.
 *
 * @author Ashley Kestler
 */

public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String productName;
    private double productPrice;
    private int productStock;
    private int min;
    private int max;

    /** The constructor for new instance of Product object.
     *
     * @param productID Unique identifier for product
     * @param productName Name for product
     * @param productPrice Price of product
     * @param productStock Inventory level of product
     * @param min The minimum inventory level
     * @param max The maximum inventory level
     */
    public Product(int productID, String productName, double productPrice, int productStock, int min, int max) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.min = min;
        this.max = max;
    }

    /**
     *
     * @return product ID
     */
    public int getProductID() {
        return productID;
    }

    /** Sets product ID
     *
     * @param productID
     */
    public void setProductID(int productID) {
        this.productID = productID;
    }

    /**
     *
     * @return product name
     */
    public String getProductName() {
        return productName;
    }

    /** Sets product name
     *
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     *
     * @return product price
     */
    public double getProductPrice() {
        return productPrice;
    }

    /** Sets product price.
     *
     * @param productPrice
     */
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    /**
     *
     * @return product inventory level
     */
    public int getProductStock() {
        return productStock;
    }

    /** Sets product inventory level.
     *
     * @param productStock
     */
    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    /**
     *
     * @return minimum inventory level
     */
    public int getMin() {
        return min;
    }

    /** Sets minimum inventory level.
     *
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     *
     * @return maximum inventory level
     */
    public int getMax() {
        return max;
    }

    /** Sets maximum inventory level.
     *
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     *
     * @return associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() { return associatedParts; }

    /** Adds selected part to associated parts.
     *
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
}
