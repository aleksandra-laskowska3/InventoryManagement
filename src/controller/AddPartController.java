package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import model.*;

import static model.Inventory.getAllParts;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Controller for the add part form.
 * Parts are able to be added to the observable parts list
 *
 */
public class AddPartController {
    Stage stage;
    Parent scene;
    /**
     * InHouse radio button
     */
    @FXML
    private RadioButton inhouseButton;
    /**
     * Toggle Group for the radio buttons
     */
    @FXML
    private ToggleGroup buttons;
    /**
     * OutSourced radio button
     */
    @FXML
    private RadioButton outsourcedButton;
    /**
     * Label for Machine ID or Company Name
     */
    @FXML
    private Label machineIDorCompanyName;
    /**
     * Text field for ID
     */
    @FXML
    private TextField addPartId;
    /**
     * Text field for Name
     */
    @FXML
    private TextField addPartName;
    /**
     * Text field for Inventory
     */
    @FXML
    private TextField addPartInv;
    /**
     * Text field for Cost
     */
    @FXML
    private TextField addPartCost;
    /**
     * Text field for Maximum inventory
     */
    @FXML
    private TextField addPartMax;
    /**
     * Text field for Machine ID
     */
    @FXML
    private TextField addPartMachineId;
    /**
     * Text field for Minimum inventory
     */
    @FXML
    private TextField addPartMin;


    /**
     * On action event that loads the main screen
     * @param event occurs when the cancel button is pressed
     * @throws IOException exception for loading the scene
     */
    @FXML
    void addPartCancelButton(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main_view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * On action event that saves a new part to the parts list
     * Loads main screen
     *RUNTIME ERROR if too large of a number is added to Price text field a NumberFormatException error occurs
     *To fix this issue an alert pops up stating an incorrect input was placed
     * @param event occurs when the save button is pressed
     * @throws IOException exception that loads the scene
     */
    @FXML
    void addPartSaveButton(ActionEvent event) throws IOException {

        try {
            int id = 0;
            for (Part part : Inventory.getAllParts()) {
                if (part.getId() > id)
                    id = part.getId();
            }
            addPartId.setText(String.valueOf(++id));
            String name = addPartName.getText();
            int stock = Integer.parseInt(addPartInv.getText());
            double price = Double.parseDouble(addPartCost.getText());
            int max = Integer.parseInt(addPartMax.getText());
            int min = Integer.parseInt(addPartMin.getText());

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
            if (inhouseButton.isSelected()) {
                int machineId = Integer.parseInt(addPartMachineId.getText());
                InHouse addPart = new InHouse(id, name, price, stock, min, max, machineId);
                Inventory.addPart(addPart);
            }
            if (outsourcedButton.isSelected()) {
                String companyName = addPartMachineId.getText();
                Outsourced addPart = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.addPart(addPart);
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
     * @param event that occurs when InHouse is chosen
     */
    @FXML
    void switchToInhouse(ActionEvent event) {
        machineIDorCompanyName.setText("Machine ID");

    }

    /**
     * On action event that changes the part to OutSourced and changes the last text field to Company Name
     * @param event that occurs when OutSourced is chosen
     */
    @FXML
    void switchToOutsouced(ActionEvent event) {
        machineIDorCompanyName.setText("Company Name");

    }


}

