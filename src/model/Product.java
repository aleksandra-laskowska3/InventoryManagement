package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Declaring the Product class from the UML Diagram
 */
public class Product {
    /** Declaring Fields
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Constructor for a new instance of a product
     *
     * @param id product ID
     * @param name product name
     * @param price product price
     * @param stock product inventory
     * @param min is the minimum product inventory
     * @param max is the maximum product inventory
     */
    public Product(int id, String name, double price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }

    /**
     * Getter for Id
     * @return id of product
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for id
     * @param id setting ID
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Getter for name
     * @return name of product
     */
    public String getName(){

        return name;
    }

    /**
     * Setter for name
     * @param name name of product
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Getter for price
     * @return price of product
     */
    public double getPrice(){

        return price;
    }

    /**
     * Setter for price
     * @param price of product
     */
    public void setPrice(double price){
        this.price = price;

    }

    /**
     * Getter for stock
     * @return stock inventory of product
     */
    public int getStock(){
        return stock;
    }

    /**
     * Setter for stock
     * @param stock inventory of product
     */
    public void setStock(int stock){
        this.stock = stock;
    }

    /**
     * Getter for min
     * @return minimum of stock of product
     */
    public int getMin(){

        return min;
    }

    /**
     * Setter for min
     * @param min minimum of stock of product
     */
    public void setMin(int min){
        this.min = min;
    }

    /**
     * Getter for max
     * @return max maximum stock of product
     */
    public int getMax(){

        return max;
    }

    /**
     * Setter for max
     * @param max maximum stock of product
     */
    public void setMax(int max){
        this.max = max;
    }

    /** Adds a part to the associated parts observable list
     */
    public void addAssociatedPart(Part part){

        associatedParts.add(part);
    }
    /** Deletes an associated part of product
     * @return true
     */
    public  boolean deleteAssociatedPart(Part selectedAssociatedPart){
        return associatedParts.remove(selectedAssociatedPart);

    }

    /** Returns associated parts for selected product
     *
     * @return associatedParts parts that are associated to a product
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
