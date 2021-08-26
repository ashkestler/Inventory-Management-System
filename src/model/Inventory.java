package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Models an inventory of parts and products.
 * Provides persistent data for the application.
 *
 * @author Ashley Kestler
 */

public class Inventory {

    /** Create list for all parts
     *
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /** Create list for all products
     *
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Create attribute to generate unique part IDs
     *
     */
    private static int partID = -1;
    /** Create attribute to generate unique product IDs
     *
     */
    private static  int productID = 0;

    /**
     *
     * @return list of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     *
     * @return list of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /** Adds a new part.
     *
     * @param newPart
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** Adds a new product.
     *
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     *
     * @return new unique part ID
     */
    public static int getNewPartID() {
        return partID += 2;
    }

    /**
     *
     * @return new unique product ID
     */
    public static int getNewProductID() { return productID += 2; }

    /** Deletes selected part from all parts list.
     *
     * @param selectedPart
     */
    public static boolean deletePart (Part selectedPart) {
        if(allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /** Deletes selected product from all products list.
     *
     * @param selectedProduct
     */
    public static boolean deleteProduct (Product selectedProduct) {
        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }

    /** Finds part by part ID.
     *
     * @param partID
     * @return found part
     */
    public static Part lookupPart(int partID) {
        Part foundPart = null;

        for (Part p : allParts) {
            if (p.getPartID() == partID) {
                foundPart = p;
            }
        }

        return foundPart;
    }

    /** Finds part by part name.
     *
     * @param partName
     * @return found part
     */
    public static Part lookupPart(String partName) {
        Part foundPart = null;

        for (Part p : allParts) {
            if (p.getPartName() == partName) {
                foundPart = p;
            }
        }

        return foundPart;
    }

    /** Finds product by product ID.
     *
     * @param productID
     * @return found product
     */
    public static Product lookupProduct(int productID) {
        Product foundProduct = null;

        for (Product p : allProducts) {
            if (p.getProductID() == productID) {
                foundProduct = p;
            }
        }
        return foundProduct;
    }

    /** Finds product by product name.
     *
     * @param productName
     * @return found product
     */
    public static Product lookupProduct(String productName) {
        Product foundProduct = null;

        for (Product p : allProducts) {
            if (p.getProductName() == productName) {
                foundProduct = p;
            }
        }
        return foundProduct;
    }

    /** Replaces a part in all parts list.
     *
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /** Replaces a product in all products list.
     *
     * @param index
     * @param selectedProduct
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

}

