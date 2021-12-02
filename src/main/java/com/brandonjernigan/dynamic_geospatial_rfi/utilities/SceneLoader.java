package com.brandonjernigan.dynamic_geospatial_rfi.utilities;

import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.layout.TilePane;

public class SceneLoader {

    private TilePane content;

    public SceneLoader(){
        content = new TilePane();
        setStyleOptions();
    }

    private void setStyleOptions(){
        content.setId("content");
    }

    public Scene loadScene(Group leftGroup, Group rightGroup){
        return new Scene(setLayout(leftGroup, rightGroup), 1280, 960);
    }

    private TilePane setLayout(Group leftGroup, Group rightGroup){
        content.setPrefColumns(2);
        content.setPrefRows(1);

        content.setPrefTileHeight(960);
        content.setPrefTileWidth(630);

        content.getChildren().add(0, leftGroup);
        content.getChildren().add(1, rightGroup);

        return content;
    }
}

