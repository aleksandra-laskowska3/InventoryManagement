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
import model.Inventory;
import model.Part;
import model.Product;
import java.net.URL;


import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

/** Class controller that controls the main screen.
 * FUTURE ENHANCEMENT: Making the search box not case-sensitive
 *
 */
public class MainController implements Initializable {

    Stage stage;
    Parent scene;
    /**
     * Text field for part search
     */
    @FXML
    private TextField searchPart;
    /**
     * Part TableView
     */
    @FXML
    private TableView<Part> mainPartsTable;
    /**
     * ID table column
     */
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    /**
     * Name table column
     */
    @FXML
    private TableColumn<Part, String> partNameCol;
    /**
     * Inventory table column
     */
    @FXML
    private TableColumn<Part, Integer> partInventoryCol;
    /**
     * Cost table column
     */
    @FXML
    private TableColumn<Part, Double> partCostCol;
    /**
     * Text field for product search
     */
    @FXML
    private TextField searchProduct;
    /**
     * Product TableView
     */
    @FXML
    private TableView<Product> mainProductsTable;
    /**
     * ID table column
     */
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    /**
     * Name table column
     */
    @FXML
    private TableColumn<Product, String> productNameCol;
    /**
     * Inventory table column
     */
    @FXML
    private TableColumn<Product, Integer> productInventoryCol;
    /**
     * Cost table column
     */
    @FXML
    private TableColumn<Product, Double> productCostCol;

    /**
     * On action event that loads the add part form when clicked
     * @param event occurs when add part button is pressed
     * @throws IOException exception to load scene
     */
    @FXML
    void addPartButton(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * On action event that loads the add product form when clicked
     * @param event occurs when add product button is pressed
     * @throws IOException exception to load the scene
     */
    @FXML
    void addProductButton(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * On action event that deletes the selected part from the part table
     * @param event that occurs when delete part button is pressed
     */
    @FXML
    void deletePartButton(ActionEvent event) {
        if(mainPartsTable.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Part was selected for deletion");
            alert.setContentText("Please select a Part to delete");
            Optional<ButtonType> result = alert.showAndWait();
        }
           else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Confirmation");
            alert.setContentText("The Part selected has been deleted");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                int selectedPart = mainPartsTable.getSelectionModel().getSelectedIndex();
                mainPartsTable.getItems().remove(selectedPart);
            }
        }

    }

    /**
     * On action event that deletes the selected product from the product table
     * @param event occurs when delete product button is pressed
     */
    @FXML
    void deleteProductButton(ActionEvent event) {
        Product selectedProduct = mainProductsTable.getSelectionModel().getSelectedItem();
        if (mainProductsTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Product was selected for deletion");
            alert.setContentText("Please select a Product to delete");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Confirmation");
            alert.setContentText("Are you sure you want to delete this Product?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();
                if (associatedParts.size() >= 1) {
                    Alert newAlert = new Alert(Alert.AlertType.ERROR);
                    newAlert.setTitle("Error");
                    newAlert.setContentText("Remove associated Parts before deleting Product");
                    newAlert.showAndWait();
                }
                    else {
                        mainProductsTable.getItems().remove(selectedProduct);
                    }
                }
            }
        }

    /**
     * On action event which terminates the application
     * @param event occurs when the exit button is pressed
     */
    @FXML
    void exitHandler(ActionEvent event) {

        System.exit(0);

    }

    /**
     * On action event that loads modify part form with the selected part
     * RUNTIME ERROR if modify part button is pressed without a selected part an error occurs
     * A popup with directions to pick a part will appear
     * @param event occurs when modify part button is pressed
     * @throws IOException exception to load scene
     */
    @FXML
    void modifyPartButton(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            loader.load();

            ModifyPartController MPController = loader.getController();
            MPController.sendPart(mainPartsTable.getSelectionModel().getSelectedItem());


            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a Part to modify");
            alert.showAndWait();
        }
    }

    /**
     * On action event that loads modify product form with the selected part
     * RUNTIME ERROR if the modify product button is pressed without a selected part an error occurs
     * A popup with directions to pick a product will appear
     * @param event occurs when the modify product button is pressed
     * @throws IOException exception loads the scene
     */
    @FXML
    void modifyProductButton(ActionEvent event) throws IOException {

        try {


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController MPController = loader.getController();
            MPController.sendProduct(mainProductsTable.getSelectionModel().getSelectedItem());


            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a Product to modify");
            alert.showAndWait();
        }
    }

    /**
     * On action event searches the text in the parts list by pressing enter
     * If no parts are found an error message will appear
     * @param event that occurs when the part search box is used
     */
    @FXML
    void mainPartSearch(ActionEvent event){
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partFound = FXCollections.observableArrayList();
        String searchTxt = searchPart.getText();

        for (Part part : allParts) {
            if(String.valueOf(part.getId()).contains(searchTxt) || part.getName().contains(searchTxt)){
                partFound.add(part);
                mainPartsTable.setItems(partFound);
            }
        }
        mainPartsTable.setItems(partFound);
        if (partFound.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Part not found");
            alert.showAndWait();
            mainPartsTable.setItems(allParts);
        }

    }

    /**
     * On action event searches the text in the product list by pressing enter
     * If product is not found an error message will appear
     * @param event that occurs when the product search box is being used
     */
    @FXML
    void mainProductSearch(ActionEvent event){
       ObservableList<Product> allProducts = Inventory.getAllProducts();
       ObservableList<Product> productFound = FXCollections.observableArrayList();
       String searchTxt = searchProduct.getText();

       for(Product product : allProducts){
           if(String.valueOf(product.getId()).contains(searchTxt) || product.getName().contains(searchTxt)){
               productFound.add(product);
           }
       }
        mainProductsTable.setItems(productFound);
        if (productFound.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Product not found");
            alert.showAndWait();
            mainProductsTable.setItems(allProducts);
        }
    }

    /**
     * Initializes controller and populates the tables
     * @param location locator
     * @param resources loads id, name, stock and price into boxes
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){

        mainPartsTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainProductsTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));




    }

}