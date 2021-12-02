module com.brandonjernigan.dynamic_geospatial_rfi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.web;

    opens com.brandonjernigan.dynamic_geospatial_rfi to javafx.fxml;
    exports com.brandonjernigan.dynamic_geospatial_rfi;
}