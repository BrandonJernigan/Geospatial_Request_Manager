package com.brandonjernigan.dynamic_geospatial_rfi.views;

import java.net.URL;
import javafx.scene.Group;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MapView {

    private WebView mapView;
    private WebEngine mapApi;
    private URL mapURL;
    private Group mapGroup;

    public MapView(WebView webView, Group group, URL path){

        mapView = webView;
        mapURL = path;
        mapGroup = group;

        mapApi = mapView.getEngine();
    }

    public WebEngine getEngine(){ return mapApi; }

    public Group loadMapView(){

        mapApi.load(mapURL.toExternalForm());
        mapApi.setJavaScriptEnabled(true);

        mapView.setPrefHeight(960);
        mapView.setPrefWidth(640);

        mapGroup.getChildren().add(mapView);
        mapGroup.prefWidth(640);
        mapGroup.prefHeight(960);

        return mapGroup;
    }
}

