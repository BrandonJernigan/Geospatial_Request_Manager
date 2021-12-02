package com.brandonjernigan.dynamic_geospatial_rfi.repositories;

public class RFIRepository {

    private String url;
    private String sql;

    public RFIRepository(){

        url = "jdbc:sqlite:src/main/java/com/brandonjernigan/dynamic_geospatial_rfi/rfi.db";

        sql = "CREATE TABLE IF NOT EXISTS rfis\n"
                + " (\n"
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL,\n"
                + " email text NOT NULL,\n"
                + " phoneNumber text NOT NULL,\n"
                + " company text NOT NULL,\n"
                + " userTitle text NOT NULL,\n"
                + " productType text NOT NULL,\n"
                + " location text NOT NULL,\n"
                + " dateNeeded text NOT NULL,\n"
                + " radius text NOT NULL,\n"
                + " comments text NOT NULL,\n"
                + " status text NOT NULL"
                + ");";
    }

    public String getUrl(){ return url; }

    public String getSql(){ return sql; }
}