package com.brandonjernigan.dynamic_geospatial_rfi.controllers;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.brandonjernigan.dynamic_geospatial_rfi.models.RFI;
import com.brandonjernigan.dynamic_geospatial_rfi.repositories.RFIRepository;
import com.brandonjernigan.dynamic_geospatial_rfi.repositories.UserRepository;
import com.brandonjernigan.dynamic_geospatial_rfi.utilities.Account;


public class Database {

    private final UserRepository USER_DB;
    private final RFIRepository  RFI_DB;
    private final Account ACCOUNT_HELPER;

    public Database() {

        USER_DB = new UserRepository();
        RFI_DB = new RFIRepository();
        ACCOUNT_HELPER = new Account();

        createDatabase(RFI_DB.getUrl(), RFI_DB.getSql());
        createDatabase(USER_DB.getUrl(), USER_DB.getSql());
    }

    private void createDatabase(String url, String sql){

        File file = new File(url.replace("jdbc:sqlite:", ""));

        if(!file.exists()){

            try(Connection connection = DriverManager.getConnection(url);
                Statement statement = connection.createStatement() ){

                statement.execute(sql);
                connection.close();
            }
            catch(SQLException exception){ System.out.println(exception.getMessage()); }
        }
    }

    public void createNewAccount(String usernameInput, String passwordInput)
            throws NoSuchAlgorithmException, InvalidKeySpecException{

        byte[] salt = ACCOUNT_HELPER.generateSalt();
        byte[] modifiedPassword = ACCOUNT_HELPER.modifyPassword(passwordInput, salt);
        byte[] storedPassword = new byte[modifiedPassword.length + salt.length];

        String sql = "INSERT INTO users(username, password) VALUES(?, ?)";

        try(Connection connection = DriverManager.getConnection(USER_DB.getUrl());
            Statement statement = connection.createStatement() ){

            System.arraycopy(modifiedPassword, 0, storedPassword, 0, modifiedPassword.length);
            System.arraycopy(salt, 0, storedPassword, modifiedPassword.length, salt.length);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usernameInput);
            preparedStatement.setBytes(2, storedPassword);
            preparedStatement.executeUpdate();

            connection.close();
        }
        catch(SQLException exception){ System.out.println(exception.getMessage()); }
    }

    public boolean authenticateUser(String usernameInput, String passwordInput)
            throws NoSuchAlgorithmException, InvalidKeySpecException{

        try(Connection connection = DriverManager.getConnection(USER_DB.getUrl());
            Statement statement = connection.createStatement() ){

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            preparedStatement.setString(1, usernameInput);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next() == true){

                byte[] currentPassword = resultSet.getBytes("password");
                byte[] salt = new byte[8];

                System.arraycopy(currentPassword, currentPassword.length - 8, salt, 0, salt.length); // Get original salt stored at the end of encrypted password.

                connection.close();

                return ACCOUNT_HELPER.authenticate(passwordInput, currentPassword, salt);
            }
            else{ connection.close(); return false; }
        }
        catch(SQLException exception){ System.out.println(exception.getMessage()); return false; }
    }

    public void logRFI(HashMap<String, String> rfi){

        try(Connection connection = DriverManager.getConnection(RFI_DB.getUrl());
            Statement statement = connection.createStatement() ){

            String sql = "INSERT INTO rfis(name, email, phoneNumber, company, userTitle, productType, location, dateNeeded, radius, comments, status)"
                    + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, rfi.get("name"));
            preparedStatement.setString(2, rfi.get("email"));
            preparedStatement.setString(3, rfi.get("phoneNumber"));
            preparedStatement.setString(4, rfi.get("companyName"));
            preparedStatement.setString(5, rfi.get("userTitle"));
            preparedStatement.setString(6, rfi.get("productType"));
            preparedStatement.setString(7, rfi.get("latlng"));
            preparedStatement.setString(8, rfi.get("date"));
            preparedStatement.setString(9, rfi.get("radius"));
            preparedStatement.setString(10, rfi.get("comments"));
            preparedStatement.setString(11, "Submitted");

            preparedStatement.executeUpdate();

            connection.close();

        }
        catch(SQLException exception){ System.out.println(exception.getMessage()); }
    }

    public int getCount(){

        int count;

        try(Connection connection = DriverManager.getConnection(RFI_DB.getUrl());
            Statement statement = connection.createStatement() ){

            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM rfis");
            resultSet.next();

            count = resultSet.getInt(1);

            connection.close();
            return count;
        }
        catch(SQLException exception){ System.out.println(exception.getMessage()); return 0; }
    }

    public ObservableList<RFI> loadRFITable(){

        ObservableList<RFI> rfiList = FXCollections.observableArrayList();

        try(Connection connection = DriverManager.getConnection(RFI_DB.getUrl());
            Statement statement = connection.createStatement() ){

            String sql = "SELECT * FROM rfis";

            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){

                int id = resultSet.getRow();
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phoneNumber");
                String companyName = resultSet.getString("company");
                String userTitle = resultSet.getString("userTitle");
                String productType = resultSet.getString("productType");
                String location = resultSet.getString("location");
                String dateNeeded = resultSet.getString("dateNeeded");
                String radius = resultSet.getString("radius");
                String comments = resultSet.getString("comments");
                String status = resultSet.getString("status");

                rfiList.add(new RFI(id, name, email, phoneNumber, companyName, userTitle, productType, location, dateNeeded, radius, comments, status));
            }

            connection.close();
        }
        catch(SQLException exception){ System.out.println(exception.getMessage()); }

        return rfiList;
    }

    public ObservableList<RFI> loadSearchRFITable(int rfiID){

        ObservableList<RFI> rfiList = FXCollections.observableArrayList();

        try(Connection connection = DriverManager.getConnection(RFI_DB.getUrl());
            Statement statement = connection.createStatement() ){

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM rfis WHERE id = ?");
            preparedStatement.setInt(1, rfiID);

            ResultSet resultSet = preparedStatement.executeQuery();

            int id = resultSet.getRow();
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String phoneNumber = resultSet.getString("phoneNumber");
            String companyName = resultSet.getString("company");
            String userTitle = resultSet.getString("userTitle");
            String productType = resultSet.getString("productType");
            String location = resultSet.getString("location");
            String dateNeeded = resultSet.getString("dateNeeded");
            String radius = resultSet.getString("radius");
            String comments = resultSet.getString("comments");
            String status = resultSet.getString("status");

            rfiList.add(new RFI(id, name, email, phoneNumber, companyName, userTitle, productType, location, dateNeeded, radius, comments, status));

            connection.close();
        }
        catch(SQLException exception){ System.out.println(exception.getMessage()); }

        return rfiList;
    }

    public void updateRFIStatus(int id, String status){

        try(Connection connection = DriverManager.getConnection(RFI_DB.getUrl());
            Statement statement = connection.createStatement() ){

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE rfis SET status = ? WHERE id = ?");
            preparedStatement.setInt(2, id);
            preparedStatement.setString(1, status);
            preparedStatement.executeUpdate();

            connection.close();
        }
        catch(SQLException exception){ System.out.println(exception.getMessage()); }
    }
}
