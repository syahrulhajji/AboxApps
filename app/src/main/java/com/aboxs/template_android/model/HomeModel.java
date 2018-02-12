package com.aboxs.template_android.model;

/**
 * Created by Abox's on 11/02/2018.
 */

public class HomeModel {
    private String status, name, description, amount;

    public HomeModel() {}

    public HomeModel(String status, String name, String description, String amount) {
        this.status = status;
        this.name = name;
        this.description = description;
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
