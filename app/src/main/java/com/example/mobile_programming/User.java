package com.example.mobile_programming;

public class User {
    private int id;
    private String nama, email, uri;
    public User(int id, String nama, String email, String uri){
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.uri = uri;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNama() {
        return nama;
    }

    public String getUri() {
        return uri;
    }
}
