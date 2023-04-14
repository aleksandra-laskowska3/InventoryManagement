package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

/**
 * Inventory management application that manages an inventory of parts and products.
 * FUTURE ENHANCEMENT: Having all part and product details visible in the TableViews, currently only ID, Name,
 * Inventory and Price are visible
 * @author Aleksandra Laskowska
 */

public class Main extends Application {
    /**
     * The start method opens the FXML stage and loads the first scene
     * @param stage initial stage
     * @throws IOException exception loads the initial scene
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main_view.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sample data for the main method for when the application starts up.
     * @param args loads initial data
     */

    public static void main(String[] args) {

        Part lens = new InHouse(1,"Lens", 100.00,5, 1,20,03);
        Inventory.addPart(lens);

        Part flash = new InHouse(2,"Flash", 79.99, 3,1,15,07);
        Inventory.addPart(flash);

        Part cameraCasing = new InHouse(3,"Camera Casing", 15.00, 12, 7,30,10);
        Inventory.addPart(cameraCasing);

        Part sdCard = new Outsourced(4,"SD Card", 40.00, 10, 5,30,"Bright Connections");
        Inventory.addPart(sdCard);

        Part shutter = new Outsourced(5,"Shutter", 55.00, 11, 4,24, "Photography Inc");
        Inventory.addPart(shutter);

        Part mirror = new Outsourced(6,"Mirror", 5.00, 15, 5,40,"Reflection Connections");
        Inventory.addPart(mirror);

        Product camera = new Product(01, "Digital Camera", 650.00, 10, 5,30);
        Inventory.addProduct(camera);

        Product tripod = new Product(02, "Tripod", 49.99, 3,2,16);
        Inventory.addProduct(tripod);

        launch();
    }
}