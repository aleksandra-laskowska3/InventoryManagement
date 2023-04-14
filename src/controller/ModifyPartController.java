package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import model.*;
import javafx.scene.control.RadioButton;

import java.io.IOException;


/**
 * Controller class that is in control of modify part form.
 * A part's information is altered.
 */
public class ModifyPartController {



    Stage stage;
    Parent scene;
    public Part partChosen;
    /**
     * InHouse radio button
     */
    @FXML
    private RadioButton inHouseModifyPart;
    /**
     * Toggle Group for radio buttons
     */
    @FXML
    private ToggleGroup buttonsModifyPart;
    /**
     * OutSourced radio button
     */
    @FXML
    private RadioButton outsourcedModifyPart;
    /**
     * Machine ID or Company Name label
     */
    @FXML
    private Label machineIDorCompanyName;
    /**
     * Text field for ID
     */
    @FXML
    private TextField modifyPartId;
    /**
     * Text field for Name
     */
    @FXML
    private TextField modifyPartName;
    /**
     * Text field for Inventory
     */
    @FXML
    private TextField modifyPartInv;
    /**
     * Text field for Cost
     */
    @FXML
    private TextField modifyPartCost;
    /**
     * Text field Maximum part inventory
     */
    @FXML
    private TextField modifyPartMax;
    /**
     * Text field for Machine ID
     */
    @FXML
    private TextField modifyPartMachineId;
    /**
     * Text field for Minimum part inventory
     */
    @FXML
    private TextField modifyPartMin;

    /**
     * On action event that cancels any changes that were made to the part and takes you back to the main screen
     * @param event that occurs when the cancel button is pressed
     * @throws IOException exception to load scene
     */
    @FXML
    public void modifyPartCancelButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main_view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Receiving date about the part from the main screen and setting if the part has a machine ID or a Company Name
     * @param partChosen part that is chosen to be modified
     */
    public void sendPart(Part partChosen){


        modifyPartId.setText(String.valueOf(partChosen.getId()));
        modifyPartName.setText(partChosen.getName());
        modifyPartInv.setText(String.valueOf(partChosen.getStock()));
        modifyPartCost.setText(String.valueOf(partChosen.getPrice()));
        modifyPartMax.setText(String.valueOf(partChosen.getMax()));
        modifyPartMin.setText(String.valueOf(partChosen.getMin()));


        if(partChosen instanceof InHouse) {
            inHouseModifyPart.setSelected(true);
            machineIDorCompanyName.setText("Machine ID");
            modifyPartMachineId.setText(String.valueOf(((InHouse)partChosen).getMachineId()));
        }
        if(partChosen instanceof Outsourced) {
            outsourcedModifyPart.setSelected(true);
            machineIDorCompanyName.setText("Company Name");
            Outsourced outSourcedPart = (Outsourced)partChosen;
            modifyPartMachineId.setText(outSourcedPart.getCompanyName());
        }



    }

    /**
     * On action event that saves all the modified data about the part and takes you back to the main screen
     * RUNTIME ERROR if too large of a number is added to Price text field a NumberFormatException error occurs
     * To fix this issue an alert pops up stating an incorrect input was placed
     * @param event that occurs when the save button is pressed
     * @throws IOException exception to load the scene
     */
    @FXML
    public void modifyPartSaveButton(ActionEvent event) throws IOException {

        try {
            int id = Integer.parseInt(modifyPartId.getText());
            String name = modifyPartName.getText();
            int stock = Integer.parseInt(modifyPartInv.getText());
            double price = Double.parseDouble(modifyPartCost.getText());
            int max = Integer.parseInt(modifyPartMax.getText());
            int min = Integer.parseInt(modifyPartMin.getText());
            int machineId;
            String companyName;

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Min cannot be larger than Max");
                alert.showAndWait();
                return;
            }
            if (stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Inventory must be within Min and Max range");
                alert.showAndWait();
                return;
            }
            if (inHouseModifyPart.isSelected()) {
                machineId = Integer.parseInt(modifyPartMachineId.getText());
                InHouse selectedPart = new InHouse(id, name, price, stock, min, max, machineId);
                Inventory.updatePart(id, selectedPart);
            }
            if (outsourcedModifyPart.isSelected()) {
                companyName = modifyPartMachineId.getText();
                Outsourced updatedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.updatePart(id, updatedPart);
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main_view.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Check all fields for correct input");
            alert.showAndWait();
        }



    }

    /**
     * On action event that changes the part to InHouse and changes the last textfield to Machine ID
     * @param event that occurs when a InHouse is picked
     */
    @FXML
    public void switchToInHouseModifyPart(ActionEvent event) {
        machineIDorCompanyName.setText("Machine ID");

    }

    /**
     * On action event that changes the part to OutSourced and changes the last textfield to Company Name
     * @param event that occurs when OutSourced is picked
     */
    @FXML
    public void switchToOutsourced(ActionEvent event) {
        machineIDorCompanyName.setText("Company Name");

    }




}
