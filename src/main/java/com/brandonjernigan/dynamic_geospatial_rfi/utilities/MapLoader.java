package com.brandonjernigan.dynamic_geospatial_rfi.utilities;

import java.net.URL;

import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

import com.brandonjernigan.dynamic_geospatial_rfi.views.MapView;
import com.brandonjernigan.dynamic_geospatial_rfi.views.TitleView;

public class MapLoader {

    final private MapView MAP_VIEW;
    final private TitleView TITLE_VIEW;
    final private WebEngine MAP_API;
    final URL MAP_URL;

    private Group mapGroup;
    private StackPane titlePane;

    public MapLoader() {

        MAP_URL = getClass().getClassLoader().getResource("mapapi.html");
        MAP_VIEW = new MapView(new WebView(), new Group(), MAP_URL);
        TITLE_VIEW = new TitleView();
        MAP_API = MAP_VIEW.getEngine();

        mapGroup = new Group(loadInitialMapView());
    }

    public Group loadMapView() { return mapGroup; }

    public Group loadInitialMapView(){

        titlePane = new StackPane();
        titlePane.getChildren().addAll(MAP_VIEW.loadMapView(), TITLE_VIEW.loadTitleView());
        titlePane.getChildren().get(0).setDisable(true);

        return new Group(titlePane);
    }

    public WebEngine getMapApi(){ return MAP_API; }

    public String getLatLng(){
        try{
            String latlng = MAP_API.getDocument().getElementById("location").getAttribute("latlng").toString();
            return latlng;
        }
        catch(Exception exception){ return ""; }
    }

    public void removeTitle(){

        titlePane.getChildren().get(1).setVisible(false);
        titlePane.getChildren().get(0).setDisable(false);
    }

    public void addTitle(){

        titlePane.getChildren().get(1).setVisible(true);
        titlePane.getChildren().get(0).setDisable(true);
    }
}