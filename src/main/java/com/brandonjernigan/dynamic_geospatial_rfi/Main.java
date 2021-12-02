package com.brandonjernigan.dynamic_geospatial_rfi;
/*
 * This program is a client side application to demonstrate an example of a Request for Information
 * of geospatial products.
 * Users fill out an interactive form for the product request.
 * The form will auto-fill location data based on a map displayed beside the form.
 * Once the form is submitted, an admin will get an email with the details of the product request.
 *
 * @author Brandon Jernigan
 * @version 1.0
 */
import javafx.application.Application;
import javafx.stage.Stage;

import com.brandonjernigan.dynamic_geospatial_rfi.controllers.Views;
import com.brandonjernigan.dynamic_geospatial_rfi.utilities.SceneLoader;

public class Main extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) {

        SceneLoader sceneLoader = new SceneLoader();
        Views viewController = new Views(stage);

        stage.setTitle("Geospatial Request Manager");
        stage.setScene(sceneLoader.loadScene(viewController.getMapDisplay(), viewController.getMenuDisplay()));
        stage.getScene().getStylesheets().add("StyleSheet.css");
        stage.show();
        stage.setResizable(false);
    }
}