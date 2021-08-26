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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller class that provides control logic for the 'Modify Product' screen of the application.
 *
 * @author Ashley Kestler
 */

public class ModifyProductController implements Initializable {
    @FXML
    private Product selectedProduct;
    @FXML
    private TextField productIDText;
    @FXML
    private TextField productNameText;
    @FXML
    private TextField productStockText;
    @FXML
    private TextField productPriceText;
    @FXML
    private TextField productMinText;
    @FXML
    private TextField productMaxText;
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
    private TableView<Part> associatedPartTable;
    @FXML
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    @FXML
    private TableColumn associatedPartIDCol;
    @FXML
    private TableColumn associatedPartNameCol;
    @FXML
    private TableColumn associatedPartInventoryCol;
    @FXML
    private TableColumn associatedPartPriceCol;
    @FXML
    private Button addAssocPartBtn;
    @FXML
    private Button removeAssocPartBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField searchPart;

    /** Adds associated part on clicking 'Add Associated Part' button.
     *
     * @param event
     */
    public void OnAddAssocPart(ActionEvent event) {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            showAlert(5);
        }
        else {
            associatedParts.add(selectedPart);
            associatedPartTable.setItems(associatedParts);
        }
    }

    /** Removes associated part on clicking 'Remove Associated Part' button.
     *
     * @param event
     */
    public void OnRemoveAssocPart(ActionEvent event) {
        Part selectedPart = associatedPartTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            showAlert(4);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Do you want to remove the selected associated part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                associatedPartTable.setItems(associatedParts);
            }
        }
    }

    /** Modifies selected product on clicking 'Save' button.
     * Adds new entry for modified product.
     * Removes old entry for selected product.
     *
     * @param event
     */
    public void OnSaveBtn(ActionEvent event) {
        try {
            int id = Integer.parseInt(productIDText.getText());
            String name = productNameText.getText();
            int stock = Integer.parseInt(productStockText.getText());
            double price = Double.parseDouble(productPriceText.getText());
            int max = Integer.parseInt(productMaxText.getText());
            int min = Integer.parseInt(productMinText.getText());
            boolean productModifySuccess = false;

            if (minValid(min, max) && inventoryValid(min, max, stock)) {
                Product modifiedProduct = new Product(id, name, price, stock, min, max);

                for (Part p : associatedParts) {
                    modifiedProduct.addAssociatedPart(p);
                }

                Inventory.addProduct(modifiedProduct);
                productModifySuccess = true;
            }
            if (productModifySuccess) {
                Inventory.deleteProduct(selectedProduct);
                returnToMainScreen(event);
            }
        } catch (Exception e) {
            showAlert(1);
        }
    }

    /** Shows message for confirmation of cancel on clicking the 'Cancel' button.
     * Returns to main screen if 'OK' button clicked.
     *
     * @param event
     * @throws IOException
     */
    public void OnCancelBtn(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Do you want to cancel changes and return to the main screen?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMainScreen(event);
        }
    }

    /** Initializes search for part on pressing 'Enter'.
     *
     * @param actionEvent
     */
    public void OnEnter(ActionEvent actionEvent) {
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

    /** Returns to main screen.
     *
     * @param event
     * @throws IOException
     */
    private void returnToMainScreen(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    /** Checks if minimum inventory level is within valid parameters.
     *
     * @param min
     * @param max
     * @return
     */
    private boolean minValid(int min, int max) {
        boolean isValid = true;

        if (min <=0 || min >= max) {
            isValid = false;
            showAlert(2);
        }
        return isValid;
    }

    /** Checks if inventory level is between or equal to the minimum and maximum inventory levels.
     *
     * @param min
     * @param max
     * @param stock
     * @return
     */
    private boolean inventoryValid(int min, int max, int stock) {
        boolean isValid = true;

        if (stock < min || stock > max) {
            isValid = false;
            showAlert(3);
        }
        return isValid;
    }

    /** Shows various error messages.
     *
     * @param alertType
     */
    private void showAlert(int alertType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Product was not modified");
                alert.setContentText("Form contains blank fields or invalid values");
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for 'Min'");
                alert.setContentText("Min must be a number between 0 and Max");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for 'Inventory'");
                alert.setContentText("Inventory must be a number between or equal to Min and Max");
                alert.showAndWait();
                break;

            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Part not selected");
                alert.setContentText("You must select an associated part to remove");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("Part not selected");
                alert.setContentText("You must select an associated part to add");
                alert.showAndWait();
                break;
        }
    }

    /** Initializes controller.
     * Retrieve information for selected product.
     * Set column values for parts and associated parts tables.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedProduct = MainScreenController.getProductToModify();
        associatedParts = selectedProduct.getAllAssociatedParts();

        productIDText.setText(String.valueOf(selectedProduct.getProductID()));
        productNameText.setText(selectedProduct.getProductName());
        productStockText.setText(String.valueOf(selectedProduct.getProductStock()));
        productPriceText.setText(String.valueOf(selectedProduct.getProductPrice()));
        productMaxText.setText(String.valueOf(selectedProduct.getMax()));
        productMinText.setText(String.valueOf(selectedProduct.getMin()));

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        partTable.setItems(Inventory.getAllParts());

        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        associatedPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        associatedPartTable.setItems(associatedParts);

    }
}
