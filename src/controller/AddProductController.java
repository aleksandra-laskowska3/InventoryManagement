package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.net.URL;


import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class that controls the add product form and manipulates product inventory data
 * FUTURE ENHANCEMENT: Make the search box not case-sensitive
 */
public class AddProductController implements Initializable {

    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    Stage stage;
    Parent scene;
    /**
     * Text field for search box
     */
    @FXML
    private TextField partSearch;
    /**
     * Text field for product ID
     */
    @FXML
    private TextField addProductId;

    /**
     * Text field for product name
     */

    @FXML
    private TextField addProductName;

    /**
     * Text field for product Inventory
     */
    @FXML
    private TextField addProductInv;

    /**
     * Text field for product price
     */
    @FXML
    private TextField addProductPrice;
    /**
     * Text field for Maximum inventory
     */
    @FXML
    private TextField addProductMax;
    /**
     * Text field for Minimum Inventory
     */
    @FXML
    private TextField addProductMin;
    /**
     * TableView for parts
     */
    @FXML
    private TableView<Part> addProductTable;
    /**
     * ID table column
     */
    @FXML
    private TableColumn<Part, Integer> addProductPartIdCol;
    /**
     * Name table column
     */
    @FXML
    private TableColumn<Part, String> addProductPartNameCol;
    /**
     * Inventory table column
     */
    @FXML
    private TableColumn<Part, Integer> addProductInventoryCol;
    /**
     * Cost table column
     */
    @FXML
    private TableColumn<Part, Double> addProductCostCol;
    /**
     * TableView of product associated table
     */
    @FXML
    private TableView<Part> addProductAssociatedTable;
    /**
     * ID table column
     */
    @FXML
    private TableColumn<Part, Integer> associatedAddProductPartIdCol;
    /**
     * Name table column
     */
    @FXML
    private TableColumn<Part, String> associatedAddProductPartNameCol;
    /**
     * Inventory table column
     */
    @FXML
    private TableColumn<Part, Integer> associatedAddProductInventoryCol;
    /**
     * Cost table column
     */
    @FXML
    private TableColumn<Part, Double> associatedAddProductCostCol;

    /**
     * On action event that adds a selected part from the addProductTable to the addProductAssociatedTable
     * If a part os not selected then an error message pops up
     * @param event occurs when the add button is used
     */
    @FXML
    void addProductAddButton(ActionEvent event) {
        Part partSelected = addProductTable.getSelectionModel().getSelectedItem();
        if(partSelected != null){
            associatedPartsList.add(partSelected);
            addProductAssociatedTable.setItems(associatedPartsList);
        }
        if(partSelected == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Choose a Part to add");
            alert.showAndWait();
        }

    }

    /**
     * On action event that loads the main screen without any new data
     * @param event occurs when the cancel button is pressed
     * @throws IOException exception that loads the scene
     */
    @FXML
    void addProductCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main_view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * On action event that removes a part from the associatedPartList and the addProductAssociatedTable
     * @param event occurs when the remove associated part button is pressed
     */
    @FXML
    void addProductRemoveAssociatedPartButton(ActionEvent event) {
        Part partSelected = addProductAssociatedTable.getSelectionModel().getSelectedItem();

        if(partSelected == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Choose a Part to remove");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Selected Part has been removed");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {

                associatedPartsList.remove(partSelected);
                addProductAssociatedTable.setItems(associatedPartsList);

            }
        }
    }

    /**
     * On action event that saves the added product to the product list
     * Saves any associated parts to the product
     * Loads the main screen
     * RUNTIME ERROR if too large of a number is added to Price textfield a NumberFormatException error occurs
     * To fix this issue an alert pops up stating an incorrect input was placed
     * @param event occurs when save button is pressed
     * @throws IOException exception that loads the scene
     */
    @FXML
    void addProductSave(ActionEvent event) throws IOException {
        try {
            int id = 0;
            for (Product product : Inventory.getAllProducts()) {
                if (product.getId() > id)
                    id = product.getId();
            }
            addProductId.setText(String.valueOf(++id));
            String name = addProductName.getText();
            int stock = Integer.parseInt(addProductInv.getText());
            double price = Double.parseDouble(addProductPrice.getText());
            int max = Integer.parseInt(addProductMax.getText());
            int min = Integer.parseInt(addProductMin.getText());

            if(min > max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Min cannot be larger than Max");
                alert.showAndWait();
                return;
            }
            if(stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Inventory must be within Min and Max range");
                alert.showAndWait();
                return;
            }

            Product product = new Product(id, name, price, stock, min, max);
            Inventory.getAllProducts().add(product);

            for(Part part : associatedPartsList){
                if(part != associatedPartsList)
                    product.addAssociatedPart(part);
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main_view.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Incorrect value was entered");
            alert.showAndWait();
            return;
        }
    }

    /**
     * On action event searches text in the observable parts list when pressed enter
     * If no part is found error messages pops up
     * @param event occurs when the search box is being used
     */
    @FXML
    void addProductSearchBox(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partFound = FXCollections.observableArrayList();
        String searchTxt = partSearch.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchTxt) || part.getName().contains(searchTxt)) {
                partFound.add(part);
            }
        }
        addProductTable.setItems(partFound);
        if (partFound.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Part not found");
            alert.showAndWait();
            addProductTable.setItems(allParts);
        }
    }

    /**
     * Initializes the controller and loads data to tables
     * @param url locator
     * @param resources sets cell values to the id, name, stock and price of the parts
     */
    @Override
    public void initialize(URL url, ResourceBundle resources){
        addProductTable.setItems(Inventory.getAllParts());
        addProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        associatedAddProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedAddProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedAddProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedAddProductCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }


}