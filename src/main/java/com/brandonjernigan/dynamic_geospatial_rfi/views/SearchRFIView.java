package com.brandonjernigan.dynamic_geospatial_rfi.views;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import com.brandonjernigan.dynamic_geospatial_rfi.models.RFI;

public class SearchRFIView {

    private Group rfiSearchGroup;
    private TableView<RFI> rfiTable;
    private VBox searchBox;
    private HBox buttonBox;
    private Label searchTitle;
    private TextField idSearch;
    private Button searchButton;

    private TableColumn<RFI, String> statusColumn;
    private TableColumn<RFI, String> locationColumn;
    private TableColumn<RFI, String> companyColumn;
    private TableColumn<RFI, String> dateNeededColumn;
    private TableColumn<RFI, String> productTypeColumn;

    public SearchRFIView(){

        searchTitle = new Label("Search RFI");

        idSearch = new TextField();

        searchButton = new Button("Search");

        buttonBox = new HBox();

        searchBox = new VBox();

        rfiTable = new TableView<RFI>();

        rfiSearchGroup = new Group();

        initializeTableColumns();

        setStyleOptions();
    }

    private void initializeTableColumns(){

        statusColumn = new TableColumn<RFI, String>("Status");
        locationColumn = new TableColumn<RFI, String>("Location");
        companyColumn = new TableColumn<RFI, String>("Company");
        dateNeededColumn = new TableColumn<RFI, String>("Date Needed");
        productTypeColumn = new TableColumn<RFI, String>("Product Type");
    }

    public int getId(){ return Integer.parseInt(idSearch.getText()); }

    public Button getSearchRFIButton(){ return searchButton; }

    public void setStyleOptions(){

        searchTitle.getStyleClass().add("formtitle");
        idSearch.setId("idsearch");
        searchButton.getStyleClass().add("actionbutton");
        searchBox.setId("searchbox");
    }

    public Group loadSearchRFIView(Button backButton){

        idSearch.setPromptText("Enter ID to search");
        rfiTable.setPrefHeight(150);

        if(buttonBox != null) { buttonBox.getChildren().clear(); }
        buttonBox.getChildren().addAll(searchButton, backButton);
        buttonBox.setSpacing(30);

        if(searchBox != null) { searchBox.getChildren().clear(); }
        searchBox.getChildren().addAll(searchTitle, idSearch, rfiTable, buttonBox);
        searchBox.setSpacing(40);

        if(rfiSearchGroup != null) { rfiSearchGroup.getChildren().clear(); }
        rfiSearchGroup.getChildren().add(searchBox);

        return rfiSearchGroup;
    }

    public void loadRFITable(ObservableList<RFI> rfiList){

        if(rfiTable.getColumns() != null) { rfiTable.getColumns().clear(); }

        if(rfiTable.getItems() != null){ rfiTable.getItems().clear(); }

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<RFI, String>("status")
        );
        statusColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        statusColumn.setEditable(true);

        locationColumn.setCellValueFactory(
                new PropertyValueFactory<RFI, String>("location")
        );

        companyColumn.setCellValueFactory(
                new PropertyValueFactory<RFI, String>("companyName")
        );

        dateNeededColumn.setCellValueFactory(
                new PropertyValueFactory<RFI, String>("dateNeeded")
        );

        productTypeColumn.setCellValueFactory(
                new PropertyValueFactory<RFI, String>("productType")
        );

        rfiTable.getColumns().addAll(statusColumn,
                locationColumn,
                companyColumn,
                dateNeededColumn,
                productTypeColumn);

        rfiTable.setEditable(false);
        rfiTable.getItems().setAll(rfiList);
    }
}
