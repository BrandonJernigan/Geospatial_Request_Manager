package com.brandonjernigan.dynamic_geospatial_rfi.repositories;

public class UserRepository {

    private String url;
    private String sql;

    public UserRepository(){

        url = "jdbc:sqlite:src/main/java/com/brandonjernigan/dynamic_geospatial_rfi/users.db";

        sql = "CREATE TABLE IF NOT EXISTS users\n"
                + " (\n"
                + " id integer PRIMARY KEY,\n"
                + " username text NOT NULL,\n"
                + " password binary NOT NULL\n"
                + ");";
    }

    public String getUrl(){ return url; }

    public String getSql(){ return sql; }
}

