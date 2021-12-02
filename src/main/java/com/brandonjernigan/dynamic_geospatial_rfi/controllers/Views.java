package com.brandonjernigan.dynamic_geospatial_rfi.controllers;

import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import com.brandonjernigan.dynamic_geospatial_rfi.models.RFI;
import com.brandonjernigan.dynamic_geospatial_rfi.controllers.Database;
import com.brandonjernigan.dynamic_geospatial_rfi.utilities.MapLoader;
import com.brandonjernigan.dynamic_geospatial_rfi.views.LoginView;
import com.brandonjernigan.dynamic_geospatial_rfi.views.MapView;
import com.brandonjernigan.dynamic_geospatial_rfi.views.MenuView;
import com.brandonjernigan.dynamic_geospatial_rfi.views.SubmitRFIView;
import com.brandonjernigan.dynamic_geospatial_rfi.views.SearchRFIView;

public class Views {

    final private Stage STAGE;
    final private Database DB_CONTROLLER;
    final private MenuView MENU_VIEW;
    final private LoginView LOGIN_VIEW;
    final private SubmitRFIView SUBMIT_RFI_VIEW;
    final private SearchRFIView SEARCH_VIEW;
    final private MapLoader MAP_VIEW;

    private Group menuGroup;
    private VBox displayLayout;
    private Button loginViewButton;
    private Button searchRFIViewButton;
    private Button submitRFIViewButton;
    private Button loginButton;
    private Button submitLoginButton;
    private Button createLoginButton;
    private Button previewButton;
    private Button submitButton;
    private Button searchButton;
    private Button backButton;

    public Views(Stage stage) {

        STAGE = stage;
        DB_CONTROLLER = new Database();
        MENU_VIEW = new MenuView();
        LOGIN_VIEW = new LoginView();
        SUBMIT_RFI_VIEW = new SubmitRFIView();
        SEARCH_VIEW = new SearchRFIView();
        MAP_VIEW = new MapLoader();

        initializeButtons();
        setMenuGroup();
        addButtonEventListeners();
        setStyleOptions();
    }

    public void initializeButtons(){

        loginViewButton = MENU_VIEW.getLoginButton();
        submitRFIViewButton = MENU_VIEW.getSubmitRFIButton();
        searchRFIViewButton = MENU_VIEW.getSearchRFIButton();
        loginButton = LOGIN_VIEW.getLoginButton();
        submitLoginButton = LOGIN_VIEW.getSubmitLoginButton();
        createLoginButton = LOGIN_VIEW.getCreateLoginButton();
        previewButton = SUBMIT_RFI_VIEW.getPreviewButton();
        submitButton = SUBMIT_RFI_VIEW.getSubmitButton();
        searchButton = SEARCH_VIEW.getSearchRFIButton();

        backButton = new Button("Back");
    }

    public void setMenuGroup(){

        displayLayout = new VBox( loadMenuView() );
        displayLayout.setSpacing(75);

        menuGroup = new Group( displayLayout );
    }


    public void setStyleOptions(){

        backButton.getStyleClass().add("backbutton");
    }

    public Group getMenuDisplay(){ return menuGroup; }

    public Group getMapDisplay(){ return MAP_VIEW.loadMapView(); }

    public String getLatLng(){ return MAP_VIEW.getLatLng(); }

    private void addButtonEventListeners(){

        loginViewButton.setOnAction(event -> changeDisplayGroup( loadLoginView() ));

        submitRFIViewButton.setOnAction(event -> changeDisplayGroup( loadSubmitRFIView() ));

        searchRFIViewButton.setOnAction(event -> changeDisplayGroup( loadSearchRFIView() ));

        loginButton.setOnAction(event -> {
            try {
                changeDisplayGroup( loadLogin() );
                addRFIVTableEvents();
            }
            catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
                System.out.println(exception.getMessage());
            }
        });

        submitLoginButton.setOnAction(event -> {
            try {

                DB_CONTROLLER.createNewAccount(LOGIN_VIEW.getUsername(), LOGIN_VIEW.getPassword());
            }
            catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
                System.out.println(exception.getMessage());
            }

            changeDisplayGroup( loadLoginCreatedView() );
        });

        createLoginButton.setOnAction(event -> changeDisplayGroup( loadCreateLoginView() ));
        previewButton.setOnAction(event -> setRadius());
        submitButton.setOnAction(event -> submitRFI());
        searchButton.setOnAction(event -> SEARCH_VIEW.loadRFITable(DB_CONTROLLER.loadSearchRFITable(SEARCH_VIEW.getId())));
        backButton.setOnAction(event -> { changeDisplayGroup( loadMenuView() ); clearMarkerAndRadius(); });
    }

    private void changeDisplayGroup(Group groupToDisplay){

        displayLayout.getChildren().clear();
        displayLayout.getChildren().add( groupToDisplay );
    }

    private Group loadMenuView(){

        MAP_VIEW.addTitle();

        SUBMIT_RFI_VIEW.resetForm();

        return MENU_VIEW.loadMenuView();
    }

    private Group loadLoginView(){ return LOGIN_VIEW.loadLoginView(backButton); }

    private Group loadCreateLoginView(){ return LOGIN_VIEW.loadCreateLoginView(backButton); }

    private Group loadLoginCreatedView(){ return LOGIN_VIEW.loadLoginCreatedView(backButton); }

    private Group loadSubmitRFIView(){

        MAP_VIEW.removeTitle();

        return SUBMIT_RFI_VIEW.loadSubmissionView(backButton);
    }

    private Group loadSearchRFIView(){ return SEARCH_VIEW.loadSearchRFIView(backButton); }

    public Group loadLogin()
            throws NoSuchAlgorithmException, InvalidKeySpecException{

        boolean authenticated = DB_CONTROLLER.authenticateUser(LOGIN_VIEW.getUsername(), LOGIN_VIEW.getPassword());

        if(authenticated){ return LOGIN_VIEW.loadLoggedinView(DB_CONTROLLER.loadRFITable(), backButton); }
        else{ return LOGIN_VIEW.loadInvalidLoginView(backButton); }
    }

    public void submitRFI(){

        HashMap<String, String> rfi = SUBMIT_RFI_VIEW.submitRFI(getLatLng());

        if(SUBMIT_RFI_VIEW.checkForRequiredValues(rfi)){

            DB_CONTROLLER.logRFI(rfi);

            SUBMIT_RFI_VIEW.resetForm();

            SUBMIT_RFI_VIEW.loadValidSubmissionView(DB_CONTROLLER.getCount());

            clearMarkerAndRadius();
        }
        else{ SUBMIT_RFI_VIEW.loadInvalidSubmissionView(); }
    }

    public void setRadius(){
        try{
            MAP_VIEW.getMapApi().getDocument().getElementById("radius").setAttribute("size", SUBMIT_RFI_VIEW.getRadiusValue());
            MAP_VIEW.getMapApi().executeScript("setRadiusCircle()");
        }
        catch(Exception exception){ }
    }

    public void clearMarkerAndRadius(){

        MAP_VIEW.getMapApi().executeScript("clearMarkerAndRadius()");
    }

    private void addRFIVTableEvents(){

        TableView<RFI> rfiTable = LOGIN_VIEW.getRFITable();
        TableColumn<RFI, String> column = (TableColumn<RFI, String>) rfiTable.getColumns().get(1);

        column.setOnEditCommit(event -> {
            RFI row = event.getRowValue();
            String newValue = event.getNewValue();

            row.setStatus(newValue);

            DB_CONTROLLER.updateRFIStatus(row.getId(), newValue);
        });
    }
}
