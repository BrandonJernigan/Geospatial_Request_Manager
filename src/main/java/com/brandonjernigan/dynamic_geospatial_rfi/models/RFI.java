package com.brandonjernigan.dynamic_geospatial_rfi.models;

public class RFI {

    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private String companyName;
    private String userTitle;
    private String productType;
    private String location;
    private String dateNeeded;
    private String radius;
    private String comments;
    private String status;

    public RFI(int id, String name, String email, String phoneNumber, String companyName, String userTitle,
                    String productType, String location, String dateNeeded, String radius, String comments, String status){

        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.userTitle = userTitle;
        this.productType = productType;
        this.location = location;
        this.dateNeeded = dateNeeded;
        this.radius = radius;
        this.comments = comments;
        this.status = status;
    }

    public int getId(){ return id; }

    public String getName(){ return name; }

    public String getEmail(){ return email; }

    public String getPhoneNumber(){ return phoneNumber; }

    public String getCompanyName(){ return companyName; }

    public String getUserTitle(){ return userTitle; }

    public String getProductType(){ return productType; }

    public String getLocation(){ return location; }

    public String getDateNeeded(){ return dateNeeded; }

    public String getRadius(){ return radius; }

    public String getComments(){ return comments; }

    public String getStatus(){ return status; }

    public void setStatus(String status){ this.status = status; }
}