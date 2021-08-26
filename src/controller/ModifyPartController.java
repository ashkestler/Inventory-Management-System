package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller class that provides control logic for the 'Modify Part' screen of the application.
 *
 * @author Ashley Kestler
 */

public class ModifyPartController implements Initializable {
    @FXML
    private Part selectedPart;
    @FXML
    private RadioButton inHouseRadioBtn;
    @FXML
    private RadioButton outsourcedRadioBtn;
    @FXML
    private ToggleGroup partRadioBtns;
    @FXML
    private TextField partIDText;
    @FXML
    private TextField partNameText;
    @FXML
    private TextField partStockText;
    @FXML
    private TextField partPriceText;
    @FXML
    private TextField partMaxText;
    @FXML
    private TextField partMinText;
    @FXML
    private TextField partIdNameText;
    @FXML
    private Label partIdNameLabel;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;

    /** Sets label text to 'Machine ID' if In-House radio button is selected.
     *
     * @param event
     */
    public void inHouseRadioBtnSelected(ActionEvent event) {
        partIdNameLabel.setText("Machine ID");
    }

    /** Sets label text to 'Company Name' if Outsourced radio button is selected.
     *
     * @param event
     */
    public void outsourcedRadioBtnSelected(ActionEvent event) {
        partIdNameLabel.setText("Company Name");
    }

    /** Modifies selected part on clicking 'Save' button.
     * Adds new entry for modified part.
     * Removes old entry for selected part.
     *
     * @param event
     */
    public void onSaveBtn(ActionEvent event) {
        try {
            int id = Integer.parseInt(partIDText.getText());
            String name = partNameText.getText();
            int stock = Integer.parseInt(partStockText.getText());
            double price = Double.parseDouble(partPriceText.getText());
            int max = Integer.parseInt(partMaxText.getText());
            int min = Integer.parseInt(partMinText.getText());
            boolean partModifySuccess = false;

            if (minValid(min, max) && inventoryValid(min, max, stock)) {
                if (inHouseRadioBtn.isSelected()) {
                    try {
                        int machineID = Integer.parseInt(partIdNameText.getText());
                        InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineID);
                        Inventory.addPart(newInHousePart);
                        partModifySuccess = true;
                    } catch (Exception e) {
                        showAlert(2);
                    }
                }

                if (outsourcedRadioBtn.isSelected()) {
                    String companyName = partIdNameText.getText();
                    Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(newOutsourcedPart);
                    partModifySuccess = true;
                }

                if (partModifySuccess) {
                    Inventory.deletePart(selectedPart);
                    returnToMainScreen(event);
            }
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
    public void onCancelBtn(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Do you want to cancel changes and return to the main screen?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMainScreen(event);
        }
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
                showAlert(3);
            }
            return isValid;
        }

    /** Checks if inventory level is between or equal to the minimum and maximum inventory levels.
     *
      * @param min
     * @param max
     * @param stock
     */
    private boolean inventoryValid(int min, int max, int stock) {
        boolean isValid = true;

        if (stock < min || stock > max) {
            isValid = false;
            showAlert(4);
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
                alert.setHeaderText("Part was not modified");
                alert.setContentText("Form contains blank fields or invalid values");
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for 'Machine ID'");
                alert.setContentText("Machine ID may only contain numbers");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for 'Min'");
                alert.setContentText("Min must be a number between 0 and Max");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for 'Inventory'");
                alert.setContentText("Inventory must be a number between or equal to Min and Max");
                alert.showAndWait();
                break;
        }
    }

    /** Initializes controller.
     * Retrieve information for selected part.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedPart = MainScreenController.getPartToModify();

        if (selectedPart instanceof InHouse) {
            inHouseRadioBtn.setSelected(true);
            partIdNameLabel.setText("Machine ID");
            partIdNameText.setText(String.valueOf(((InHouse) selectedPart).getMachineID()));
        }

        if (selectedPart instanceof Outsourced) {
            outsourcedRadioBtn.setSelected(true);
            partIdNameLabel.setText("Company Name");
            partIdNameText.setText(((Outsourced) selectedPart).getCompanyName());
        }

        partIDText.setText(String.valueOf(selectedPart.getPartID()));
        partNameText.setText(selectedPart.getPartName());
        partStockText.setText(String.valueOf(selectedPart.getPartStock()));
        partPriceText.setText(String.valueOf(selectedPart.getPartPrice()));
        partMaxText.setText(String.valueOf(selectedPart.getMax()));
        partMinText.setText(String.valueOf(selectedPart.getMin()));
    }
}
