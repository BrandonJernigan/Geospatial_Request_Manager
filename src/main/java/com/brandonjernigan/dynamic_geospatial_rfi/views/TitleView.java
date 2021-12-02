package com.brandonjernigan.dynamic_geospatial_rfi.views;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TitleView {

    private Group titleGroup;
    private StackPane titlePane;
    private VBox vBox;
    private Label title;
    private Label subTitle;

    public TitleView(){

        titleGroup = new Group();
        titlePane = new StackPane();
        vBox = new VBox();

        title = new Label("Map Connect");
        subTitle = new Label("Helping you find your way!");

        setStyleOptions();
    }

    private void setStyleOptions(){

        title.setId("title");
        subTitle.setId("subtitle");
        titlePane.setId("titlepane");
    }

    public Group loadTitleView(){

        titlePane.toFront();
        titlePane.setPrefSize(500, 300);
        titlePane.setMaxSize(500, 300);

        vBox.getChildren().addAll(title, subTitle);
        vBox.setSpacing(50);
        vBox.setAlignment(Pos.CENTER);

        titlePane.getChildren().add(vBox);

        titleGroup.getChildren().add(titlePane);

        return titleGroup;
    }
}