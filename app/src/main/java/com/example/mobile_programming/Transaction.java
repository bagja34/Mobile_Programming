package com.example.mobile_programming;

public class Transaction {
    private String description;
    private String date;
    private double amount;

    public Transaction(String description, String date, double amount) {
        this.description = description;
        this.date = date;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }
}
