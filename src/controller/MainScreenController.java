package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.*;

import java.awt.Label;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller class that provides control logic for the 'Main' screen of the application.
 *
 * @author Ashley Kestler
 */

public class MainScreenController implements Initializable {

    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn partIDCol;
    @FXML
    private TableColumn partNameCol;
    @FXML
    private TableColumn partInventoryCol;
    @FXML
    private TableColumn partPriceCol;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn productIDCol;
    @FXML
    private TableColumn productNameCol;
    @FXML
    private TableColumn productInventoryCol;
    @FXML
    private TableColumn productPriceCol;
    @FXML
    private Button addPart;
    @FXML
    private Button modifyPart;
    @FXML
    private Button deletePart;
    @FXML
    private Button addProduct;
    @FXML
    private Button modifyProduct;
    @FXML
    private Button deleteProduct;
    @FXML
    private Button exitBtn;
    @FXML
    private TextField searchPart;
    @FXML
    private TextField searchProduct;

    private static Part partToModify;
    private static Product productToModify;

    /** Launches 'Add Part' screen on clicking 'Add' button.
     *
     * @param event
     * @throws IOException
     */
    public void onAddPart(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AddPart.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    /** Launches the 'Modify Part' screen for the selected part on clicking the 'Modify' button.
     * Shows error message if no part is selected.
     *
     * @param event
     * @throws IOException
     */
    public void onModifyPart(ActionEvent event) throws IOException {
        boolean isNoPartSelected = partTable.getSelectionModel().isEmpty();
        partToModify = partTable.getSelectionModel().getSelectedItem();

        if (isNoPartSelected) {
            showAlert(3);
        }
        else {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/ModifyPart.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        }
    }

    /** Removes selected part from all parts list on clicking 'Delete' button.
     * Asks for confirmation before deletion.
     *
     * @param event
     */
    public void onDeletePart(ActionEvent event) {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            showAlert(5);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Do you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }
    }

    /** Opens 'Add Product' screen on clicking 'Add' button.
     *
     * @param event
     * @throws IOException
     */
    public void onAddProduct(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AddProduct.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    /** Launches the 'Modify Part' screen for the selected part on clicking the 'Modify' button.
     * Shows error message if no part is selected.
     *
     * @param event
     * @throws IOException
     */
    public void onModifyProduct(ActionEvent event) throws IOException {
        boolean isNoProductSelected = productTable.getSelectionModel().isEmpty();
        productToModify = productTable.getSelectionModel().getSelectedItem();

        if (isNoProductSelected) {
            showAlert(3);
        }
        else {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/ModifyProduct.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        }
    }

    /** Removes selected part from all parts list on clicking 'Delete' button.
     * Checks if product has associated parts before deletion.
     * Asks for confirmation before deletion.
     *
     * @param event
     */
    public void onDeleteProduct(ActionEvent event) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert(6);
        }

        if (selectedProduct.getAllAssociatedParts().size() != 0) {
            showAlert(7);
        }

        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Do you want to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProduct);
            }
        }
    }

    /** Exits the application.
     *
     * @param event
     */
    public void onExitBtn(ActionEvent event) {
        System.exit(0);
    }

    /** Initializes search for part on pressing 'Enter'.
     *
     * @param actionEvent
     */
    public void onPartEnter(ActionEvent actionEvent) {
        searchPart.setOnKeyPressed(event -> {
            if( event.getCode() == KeyCode.ENTER ) {
                String q = searchPart.getText();
                ObservableList<Part> parts = searchForPart(q);

                partTable.setItems(parts);
            }
        } );
    }

    /** Searches for part by part ID or name.
     *
     * @param searchString
     * @return
     */
    private ObservableList<Part> searchForPart(String searchString) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        for (Part p : allParts) {
            if (p.getPartName().contains(searchString) ||
                String.valueOf(p.getPartID()).contains(searchString)) {
                namedParts.add(p);

            }

        }
        if (namedParts.size() == 0) {
            showAlert(1);
        }
        searchPart.setText("");
        return namedParts;
    }

    /** Initializes search for product on pressing 'Enter'.
     *
     * @param actionEvent
     */
    public void onProductEnter(ActionEvent actionEvent) {
        searchProduct.setOnKeyPressed( event -> {
            if( event.getCode() == KeyCode.ENTER ) {
                String q = searchProduct.getText();
                ObservableList<Product> products = searchForProduct(q);

                productTable.setItems(products);
            }
        } );
    }

    /** Searches for product by product ID or name.
     *
     * @param searchString
     * @return
     */
    private ObservableList<Product> searchForProduct(String searchString) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        for (Product p : allProducts) {
            if (p.getProductName().contains(searchString) ||
                    String.valueOf(p.getProductID()).contains(searchString)) {
                namedProducts.add(p);
            }
            }
        if (namedProducts.size() == 0) {
            showAlert(2);
        }
        searchProduct.setText("");
        return namedProducts;
    }

    /**
     *
     * @return part to modify
     */
    public static Part getPartToModify() {
        return partToModify;
    }

    /**
     *
     * @return product to modify
     */
    public static Product getProductToModify() {
        return productToModify;
    }

    /** Shows various alerts.
     *
     * @param alertType
     */
    private void showAlert(int alertType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Information");
                alert.setHeaderText("Part not found. Remember search is case sensitive.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Information");
                alert.setHeaderText("Product not found. Remember search is case sensitive.");
                alert.showAndWait();
                break;
            case 3:
                alertError.setTitle("Error");
                alertError.setHeaderText("Part not selected");
                alertError.setContentText("You must select a part to modify");
                alertError.showAndWait();
                break;
            case 4:
                alertError.setTitle("Error");
                alertError.setHeaderText("Product not selected");
                alertError.setContentText("You must select a product to modify");
                alertError.showAndWait();
                break;
            case 5:
                alertError.setTitle("Error");
                alertError.setHeaderText("Part not selected");
                alertError.setContentText("You must select a part to delete");
                alertError.showAndWait();
                break;
            case 6:
                alertError.setTitle("Error");
                alertError.setHeaderText("Product not selected");
                alertError.setContentText("You must select a product to delete");
                alertError.showAndWait();
                break;
            case 7:
                alertError.setTitle("Error");
                alertError.setHeaderText("Parts Associated");
                alertError.setContentText("All parts must be removed from product before deletion.");
                alertError.showAndWait();
                break;
        }
    }

    /** Initializes main screen controller.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getAllParts());
        productTable.setItems(Inventory.getAllProducts());

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

        productIDCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("productStock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
    }
}
