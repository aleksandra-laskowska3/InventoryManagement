package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Creating Inventory Class for all the elements in the inventory management
 */
public class Inventory {
    /** Declare Fields
    */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**Method adds a part to the observable allParts list
     * @param newPart The new part that is added to the allParts list
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**Method adds a product to the observable allProducts list
     * @param newProduct The new product that is added to the allProducts list
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**Method for searching a part by ID by a loop
     * @return part if ID matches or return null
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            while (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }
    /**Method for searching a product by ID by a loop
     * @return product if ID matches or return null
     */
    public static Product lookupProduct(int productId){
        for (Product product : allProducts){
            while (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * lookupPart makes an observable list of searched parts
     * @param partName The part name that is being searched
     * @return partFound will return a part if the name matches
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> partFound = FXCollections.observableArrayList();
        for (Part part : allParts){
            if (part.getName().contains(partName)){
                partFound.add(part);
            }
        }
        return partFound;
    }

    /**
     * lookupProduct makes an observable list of searched products
     * @param productName Product name that is being searched
     * @return productFound will return a product if it is found
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> productFound = FXCollections.observableArrayList();
        for (Product product : allProducts){
            if(product.getName().contains(productName)){
                productFound.add(product);
            }
        }
        return productFound;
    }

    /**
     * Updates the selected part
     * @param index index of part that is updated
     * @param selectedPart the part that is selected
     */
    public static void updatePart(int index, Part selectedPart){
        Part newPart = Inventory.lookupPart(index);
        Inventory.deletePart(newPart);
        Inventory.addPart(selectedPart);

        }

    /**
     * Updates selected product
     * @param index index of the selected product
     * @param selectedProduct the selected product
     */
    public static void updateProduct( int index, Product selectedProduct){
        Product newProduct = Inventory.lookupProduct(index);
        Inventory.deleteProduct(newProduct);
        Inventory.addProduct(selectedProduct);

    }

    /**
     * Deletes part selected
     * @param selectedPart the selected part for deletion
     */
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    /**
     * Deletes product selected
     * @param selectedProduct selected product for deletion
     */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    /**
     * Gets all parts in the all parts observable list
     * @return allParts from the observable list
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * Gets all products in the all products observable list
     * @return allProducts from the observable list
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
