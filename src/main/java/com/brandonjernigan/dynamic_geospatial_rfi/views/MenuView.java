package com.brandonjernigan.dynamic_geospatial_rfi.views;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MenuView {

    private Group menuGroup;
    private VBox menuBox;
    private Button loginButton;
    private Button submitRFIButton;
    private Button searchRFIButton;

    public MenuView(){

        menuGroup = new Group();
        menuBox = new VBox();
        loginButton = new Button("Login as Admin");
        submitRFIButton = new Button("Submit new RFI");
        searchRFIButton = new Button("Search for an RFI");

        setStyleOptions();
    }

    public void setStyleOptions(){

        menuBox.setId("menubox");
        loginButton.getStyleClass().add("menubutton");
        submitRFIButton.getStyleClass().add("menubutton");
        searchRFIButton.getStyleClass().add("menubutton");
    }

    public Button getLoginButton(){ return loginButton; }

    public Button getSubmitRFIButton(){ return submitRFIButton; }

    public Button getSearchRFIButton(){ return searchRFIButton; }

    public Group loadMenuView(){

        if(menuBox != null) { menuBox.getChildren().clear(); }
        menuBox.getChildren().addAll(loginButton, submitRFIButton, searchRFIButton);
        menuBox.setSpacing(100);

        if(menuGroup != null){ menuGroup.getChildren().clear(); }
        menuGroup.getChildren().add(menuBox);

        return menuGroup;
    }
}