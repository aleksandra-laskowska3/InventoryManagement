package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class that controls modify product form.
 * A products information is altered
 */
public class ModifyProductController implements Initializable {

    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    //private int currentIndex = 0;

    Stage stage;
    Parent scene;
    /**
     * Text field for part search
     */
    @FXML
    private TextField partSearch;
    /**
     * Text field for product ID
     */
    @FXML
    private TextField modifyProductId;
    /**
     * Text field for product name
     */
    @FXML
    private TextField modifyProductName;
    /**
     * Text field for Inventory
     */
    @FXML
    private TextField modifyProductInv;
    /**
     * Text field for Price
     */
    @FXML
    private TextField modifyProductPrice;
    /**
     * Text field for Maximum product inventory
     */
    @FXML
    private TextField modifyProductMax;
    /**
     * Text field for Minimum product inventory
     */
    @FXML
    private TextField modifyProductMin;

    /**
     * Part TableView
     */
    @FXML
    private TableView<Part> modifyProductTable;

    /**
     * Part ID table column
     */
    @FXML
    private TableColumn<Part, Integer> modifyProductPartIdCol;
    /**
     * Part Name table column
     */
    @FXML
    private TableColumn<Part, String> modifyProductPartNameCol;
    /**
     * Part Inventory table column
     */
    @FXML
    private TableColumn<Part, Integer> modifyProductInventoryCol;
    /**
     * Part Cost table column
     */
    @FXML
    private TableColumn<Part, Double> modifyProductCostCol;
    /**
     * Associated part TableView
     */
    @FXML
    private TableView<Part> modifyProductAssociatedTable;
    /**
     * Associated part ID table column
     */
    @FXML
    private TableColumn<Part, Integer> associatedModifyProductPartIdCol;
    /**
     * Associated part Name table column
     */
    @FXML
    private TableColumn<Part, String> associatedModifyProductPartNameCol;
    /**
     * Associated part Inventory table column
     */
    @FXML
    private TableColumn<Part, Integer> associatedModifyProductInventoryCol;
    /**
     * Associated part Cost table column
     */
    @FXML
    private TableColumn<Part, Double> associatedModifyProductCostCol;

    /**
     * Received data from the main screen
     * @param productChosen product that is chosen to be modified
     */
    public void sendProduct(Product productChosen){

        modifyProductId.setText(String.valueOf(productChosen.getId()));
        modifyProductName.setText(productChosen.getName());
        modifyProductInv.setText(String.valueOf(productChosen.getStock()));
        modifyProductPrice.setText(String.valueOf(productChosen.getPrice()));
        modifyProductMax.setText(String.valueOf(productChosen.getMax()));
        modifyProductMin.setText(String.valueOf(productChosen.getMin()));
        associatedPartsList.addAll(productChosen.getAllAssociatedParts());

    }

    /**
     * On action event button that adds a part from the Modify Product table to the Modify Product Associated Table
     * @param event that occurs when the ass button is pressed
     */
    @FXML
    void modifyProductAddButton(ActionEvent event) {
        Part partSelected = modifyProductTable.getSelectionModel().getSelectedItem();
        if(partSelected != null){
            associatedPartsList.add(partSelected);
            modifyProductAssociatedTable.setItems(associatedPartsList);
        }
        if(partSelected == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Choose a Part to add");
            alert.showAndWait();
        }
    }

    /**
     * On action event that cancels any modification and returns to the main screen
     * @param event that occurs when the cancel button is pressed
     * @throws IOException exception to load the scene
     */
    @FXML
    void modifyProductCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main_view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * On action event that removes a part from the associated table
     * @param event that occurs when the remove associated part button is pressed
     */
    @FXML
    void modifyProductRemoveAssociatedPartButton(ActionEvent event) {
        Part partSelected = modifyProductAssociatedTable.getSelectionModel().getSelectedItem();

        if(partSelected == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Choose a Part to remove");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Selected Part has been removed");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                associatedPartsList.remove(partSelected);
                modifyProductAssociatedTable.setItems(associatedPartsList);

            }
        }
    }

    /**
     * On action event that saves any of the modifications that were made to the product
     * RUNTIME ERROR if too large of a number is added to Price text field a NumberFormatException error occurs
     * To fix this issue an alert pops up stating an incorrect input was placed
     * @param event that occurs when the save button is pressed
     * @throws IOException exception to load the scene
     */
    @FXML
    void modifyProductSave(ActionEvent event) throws IOException {

        try {

            int id = Integer.parseInt(modifyProductId.getText());
            String name = modifyProductName.getText();
            int stock = Integer.parseInt(modifyProductInv.getText());
            double price = Double.parseDouble(modifyProductPrice.getText());
            int max = Integer.parseInt(modifyProductMax.getText());
            int min = Integer.parseInt(modifyProductMin.getText());

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

            Product product = new Product(id, name, price, stock, min, max);
            Inventory.updateProduct(id, product);
            if(product != associatedPartsList){
                Inventory.updateProduct(id, product);
            }
            for(Part part : associatedPartsList){
                if(part != associatedPartsList)
                    product.addAssociatedPart(part);
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
     * On action event that searches for a part by ID or name
     * @param event that occurs when the search box is used
     */
    @FXML
    void modifyProductSearchBox(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partFound = FXCollections.observableArrayList();
        String searchTxt = partSearch.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchTxt) || part.getName().contains(searchTxt)) {
                partFound.add(part);
            }
        }
        modifyProductTable.setItems(partFound);
        if (partFound.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Part not found");
            alert.showAndWait();
            modifyProductTable.setItems(allParts);

        }
    }

    /**
     * Initializes the controller and places the selected product's information in the textfields
     * @param url location
     * @param resources modified product
     */
    @Override
    public void initialize(URL url, ResourceBundle resources){
        modifyProductTable.setItems(Inventory.getAllParts());
        modifyProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        modifyProductAssociatedTable.setItems(associatedPartsList);
        associatedModifyProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedModifyProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedModifyProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedModifyProductCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
