package com.brandonjernigan.dynamic_geospatial_rfi.views;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import com.brandonjernigan.dynamic_geospatial_rfi.models.RFI;

public class LoginView {

    private Group loginGroup;
    private TableView<RFI> rfiTable;
    private VBox loginBox;
    private HBox buttonBox;
    private Label loginTitle;
    private Label warningLabel;
    private TextField username;
    private PasswordField password;
    private Button loginButton;
    private Button submitLoginButton;
    private Button createLoginButton;
    private Button updateStatusButton;

    private TableColumn<RFI, Integer> idColumn;
    private TableColumn<RFI, String> statusColumn;
    private TableColumn<RFI, String> locationColumn;
    private TableColumn<RFI, String> radiusColumn;
    private TableColumn<RFI, String> dateNeededColumn;
    private TableColumn<RFI, String> productTypeColumn;
    private TableColumn<RFI, String> companyNameColumn;
    private TableColumn<RFI, String> userTitleColumn;
    private TableColumn<RFI, String> nameColumn;
    private TableColumn<RFI, String> emailColumn;
    private TableColumn<RFI, String> phoneNumberColumn;
    private TableColumn<RFI, String> commentsColumn;

    public LoginView(){

        username = new TextField();
        password = new PasswordField();

        loginButton = new Button("Login");
        submitLoginButton = new Button("Submit Login");
        createLoginButton = new Button("Create Login");
        updateStatusButton = new Button("Update Status");

        loginTitle = new Label("Admin Login");
        warningLabel = new Label("");

        buttonBox = new HBox();
        loginBox = new VBox();

        rfiTable = new TableView<RFI>();

        loginGroup = new Group();

        initializeTableColumns();

        setStyleOptions();
    }

    private void initializeTableColumns(){

        idColumn = new TableColumn<RFI, Integer>("Id");
        statusColumn = new TableColumn<RFI, String>("Status");
        locationColumn = new TableColumn<RFI, String>("Location");
        radiusColumn = new TableColumn<RFI, String>("Radius");
        dateNeededColumn = new TableColumn<RFI, String>("Date Needed");
        productTypeColumn = new TableColumn<RFI, String>("Product Type");
        companyNameColumn = new TableColumn<RFI, String>("Company Name");
        userTitleColumn = new TableColumn<RFI, String>("User Title");
        nameColumn = new TableColumn<RFI, String>("Name");
        emailColumn = new TableColumn<RFI, String>("Email");
        phoneNumberColumn = new TableColumn<RFI, String>("Phone Number");
        commentsColumn = new TableColumn<RFI, String>("Comments");
    }

    private void setStyleOptions(){

        loginBox.setId("loginbox");
        loginTitle.getStyleClass().add("formtitle");
        warningLabel.getStyleClass().add("warning");
        username.getStyleClass().add("logininput");
        password.getStyleClass().add("logininput");
        loginButton.getStyleClass().add("actionbutton");
        submitLoginButton.getStyleClass().add("actionbutton");
        createLoginButton.getStyleClass().add("actionbutton");
        updateStatusButton.getStyleClass().add("actionbutton");
    }

    public String getUsername(){ return username.getText(); }

    public String getPassword(){ return password.getText(); }

    public Button getLoginButton(){ return loginButton; }

    public Button getCreateLoginButton(){ return createLoginButton; }

    public Button getSubmitLoginButton(){ return submitLoginButton; }

    public Button getUpdateStatusButton(){ return updateStatusButton; }

    public TableView<RFI> getRFITable(){ return rfiTable; }

    public Group loadLoginView(Button backButton){

        loadView();

        buttonBox.getChildren().addAll(loginButton, createLoginButton, backButton);

        loginTitle.setText("Admin Login");

        loginBox.getChildren().addAll(loginTitle, username, password, buttonBox);

        return loginGroup;
    }

    public Group loadCreateLoginView(Button backButton){

        loadView();

        buttonBox.getChildren().addAll(submitLoginButton, backButton);

        loginTitle.setText("Create Login");

        loginBox.getChildren().addAll(loginTitle, username, password, buttonBox);

        return loginGroup;
    }

    public Group loadLoginCreatedView(Button backButton){

        loadView();

        buttonBox.getChildren().add(backButton);

        loginTitle.setText("Login Created");

        loginBox.getChildren().addAll(loginTitle, buttonBox);

        return loginGroup;
    }

    public Group loadLoggedinView(ObservableList<RFI> rfiList, Button backButton){

        loadView();

        loadRFITable(rfiList);

        buttonBox.getChildren().addAll(backButton);

        loginTitle.setText("RFIs");

        loginBox.getChildren().addAll(loginTitle, rfiTable, buttonBox);

        return loginGroup;
    }

    public Group loadInvalidLoginView(Button backButton){

        loadView();

        buttonBox.getChildren().addAll(loginButton, createLoginButton, backButton);

        loginTitle.setText("Admin Login");
        warningLabel.setText("Invalid username or password");

        loginBox.getChildren().addAll(loginTitle, warningLabel, username, password, buttonBox);

        return loginGroup;
    }

    private void loadView(){

        resetLogin();

        username.setPromptText("Username");
        password.setPromptText("Passowrd");

        if(buttonBox != null) { buttonBox.getChildren().clear(); }
        buttonBox.setSpacing(10);

        if(loginBox != null) { loginBox.getChildren().clear(); }
        loginBox.setSpacing(20);
        loginBox.setPrefSize(550, 600);
        loginBox.setMinSize(550, 600);

        rfiTable.setPrefSize(400, 350);
        rfiTable.setMinSize(400, 350);

        if(loginGroup != null) { loginGroup.getChildren().clear(); }
        loginGroup.getChildren().add(loginBox);
    }

    private void resetLogin(){

        username.clear();
        password.clear();
    }

    private void loadRFITable(ObservableList<RFI> rfiList){

        if(rfiTable.getColumns() != null) { rfiTable.getColumns().clear(); }

        if(rfiTable.getItems() != null){ rfiTable.getItems().clear(); }

        idColumn.setCellValueFactory(
                new PropertyValueFactory<RFI, Integer>("id")
        );

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<RFI, String>("status")
        );
        statusColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        statusColumn.setEditable(true);

        locationColumn.setCellValueFactory(
                new PropertyValueFactory<RFI, String>("location")
        );

        radiusColumn.setCellValueFactory(
                new PropertyValueFactory<RFI, String>("radius")
        );

        dateNeededColumn.setCellValueFactory(
                new PropertyValueFactory<RFI, String>("dateNeeded")
        );

        productTypeColumn.setCellValueFactory(
                new PropertyValueFactory<RFI, String>("productType")
        );

        companyNameColumn.setCellValueFactory(
                new PropertyValueFactory<RFI, String>("companyName")
        );

        userTitleColumn.setCellValueFactory(
                new PropertyValueFactory<RFI, String>("userTitle")
        );

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<RFI, String>("name")
        );

        emailColumn.setCellValueFactory(
                new PropertyValueFactory<RFI, String>("email")
        );

        phoneNumberColumn.setCellValueFactory(
                new PropertyValueFactory<RFI, String>("phoneNumber")
        );

        commentsColumn.setCellValueFactory(
                new PropertyValueFactory<RFI, String>("comments")
        );

        rfiTable.getColumns().addAll(idColumn,
                statusColumn,
                nameColumn,
                emailColumn,
                phoneNumberColumn,
                companyNameColumn,
                userTitleColumn,
                productTypeColumn,
                locationColumn,
                dateNeededColumn,
                radiusColumn,
                commentsColumn);

        rfiTable.setEditable(true);
        rfiTable.getItems().setAll(rfiList);
    }
}

