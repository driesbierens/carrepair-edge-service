package com.project.carrepairedgeservice.model;

public class Function {
    private int id;
    private String functionName;

    public Function() {
    }

    public Function(String functionName) {
        this.functionName = functionName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
}
