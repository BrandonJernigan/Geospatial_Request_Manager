package com.brandonjernigan.dynamic_geospatial_rfi.views;

import java.util.HashMap;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;

public class SubmitRFIView {

    private Group formGroup;
    private VBox formBox;
    private Label title;
    private Label infoText;
    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private TextField phoneNumber;
    private TextField companyName;
    private TextField userTitle;
    private TextField date;
    private TextField radius;
    private TextArea comments;
    private ComboBox<String> productType;
    private Button submitButton;
    private Button previewButton;

    public SubmitRFIView(){

        initializeTextFields();
        setPromptText();
        setStyleOptions();
    }

    public void setStyleOptions(){

        title.getStyleClass().add("formtitle");
        infoText.getStyleClass().add("warning");
        firstName.getStyleClass().add("submitinput");
        lastName.getStyleClass().add("submitinput");
        email.getStyleClass().add("submitinput");
        phoneNumber.getStyleClass().add("submitinput");
        companyName.getStyleClass().add("submitinput");
        userTitle.getStyleClass().add("submitinput");
        date.getStyleClass().add("submitinput");
        radius.getStyleClass().add("submitinput");

        productType.getStyleClass().add("combobox");

        submitButton.getStyleClass().add("actionbutton");
        previewButton.getStyleClass().add("actionbutton");

        formBox.setId("formbox");
    }

    public Button getSubmitButton(){ return submitButton; }

    public Button getPreviewButton(){ return previewButton; }

    public String getRadiusValue(){ return radius.getText(); }

    private void initializeTextFields(){

        title = new Label("Submit New RFI Form");
        infoText = new Label("");
        firstName = new TextField();
        lastName = new TextField();
        email = new TextField();
        phoneNumber = new TextField();
        companyName = new TextField();
        userTitle = new TextField();
        date = new TextField();
        radius = new TextField();
        comments = new TextArea();

        productType = new ComboBox<String>();
        productType.getItems().addAll("Analog", "Digital", "Data");

        submitButton = new Button("Submit");
        previewButton = new Button("Preview");

        formBox = new VBox();
        formGroup = new Group();
    }

    private void setPromptText(){

        firstName.setPromptText("First Name");
        lastName.setPromptText("Last Name");
        email.setPromptText("Email");
        phoneNumber.setPromptText("Phone Number");
        companyName.setPromptText("Company");
        userTitle.setPromptText("Title/Position");

        productType.setPromptText("Product Type");
        productType.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty) ;
                if (empty || item == null) {
                    setText("Product Type");
                } else {
                    setText(item);
                }
            }
        });

        date.setPromptText("Date Anticipated");
        radius.setPromptText("Radius(km)");
        comments.setPromptText("Additional Comments");
    }

    public Group loadSubmissionView(Button backButton){

        infoText.setText("");

        HBox name = new HBox(firstName, lastName);
        name.setSpacing(30);
        name.setAlignment(Pos.CENTER);

        HBox contact = new HBox(email, phoneNumber);
        contact.setSpacing(30);
        contact.setAlignment(Pos.CENTER);

        HBox company = new HBox(companyName, userTitle);
        company.setSpacing(30);
        company.setAlignment(Pos.CENTER);

        HBox product = new HBox(productType, date);
        product.setSpacing(30);
        product.setAlignment(Pos.CENTER);

        HBox location = new HBox(radius, previewButton);
        location.setSpacing(50);
        location.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox(submitButton, backButton);
        buttonBox.setSpacing(30);

        if(formBox != null) { formBox.getChildren().clear(); }
        formBox.getChildren().addAll(
                title,
                infoText,
                name,
                contact,
                company,
                product,
                location,
                comments,
                buttonBox
        );
        formBox.setSpacing(40);
        formBox.setAlignment(Pos.CENTER);

        if(formGroup != null) { formGroup.getChildren().clear(); }
        formGroup.getChildren().add(formBox);

        return formGroup;
    }

    public void loadInvalidSubmissionView(){

        infoText.getStyleClass().clear();
        infoText.getStyleClass().add("warning");
        infoText.setText("RFI not submitted. Ensure all fields are filled out and marker is placed on the map.");
    }

    public void loadValidSubmissionView(int id){

        infoText.getStyleClass().clear();
        infoText.getStyleClass().add("success");
        infoText.setText("RFI submitted. ID: " + id);
    }

    public void resetForm(){

        infoText.setText("");

        firstName.clear();
        lastName.clear();
        email.clear();
        phoneNumber.clear();
        companyName.clear();
        userTitle.clear();
        productType.getSelectionModel().clearSelection();
        date.clear();
        radius.clear();
        comments.clear();
    }

    public HashMap<String, String> submitRFI(String latlng){

        HashMap<String, String> rfi = new HashMap<>();

        rfi.put("name", firstName.getText() + " " + lastName.getText());
        rfi.put("email", email.getText());
        rfi.put("phoneNumber", phoneNumber.getText());
        rfi.put("companyName", companyName.getText());
        rfi.put("userTitle", userTitle.getText());
        rfi.put("productType", productType.getSelectionModel().getSelectedItem());
        rfi.put("date", date.getText());
        rfi.put("latlng", latlng);
        rfi.put("radius", radius.getText());
        rfi.put("comments", comments.getText());

        return rfi;
    }

    public boolean checkForRequiredValues(HashMap<String, String> values){

        boolean requiredValues = true;

        for(String value : values.values()){

            if(value == null || value.isEmpty()){ requiredValues = false; }
        }

        return requiredValues;
    }
}