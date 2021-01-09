package com.project.carrepairedgeservice.model;

public class Category {
    private int id;
    private String name;
    private String categoryId;

    public Category() {

    }

    public Category(String name, String categoryId) {
        this.name = name;
        this.categoryId = categoryId;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
