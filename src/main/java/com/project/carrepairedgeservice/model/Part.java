package com.project.carrepairedgeservice.model;

public class Part {
    private int id;
    private String name;
    private String description;
    private String eanNumber;
    private double price;
    private int categoryID;

    public Part() {
    }

    public Part(String name, String description, String eanNumber, double price, int categoryID) {
        this.name = name;
        this.description = description;
        this.eanNumber = eanNumber;
        this.price = price;
        this.categoryID = categoryID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEanNumber() {
        return eanNumber;
    }

    public void setEanNumber(String eanNumber) {
        this.eanNumber = eanNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}

