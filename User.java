package com.example.security;

public class User {
    public String name, email, phone,address,guardian;

    public User(String name, String email, String phone,String guardian,String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address=address;
        this.guardian=guardian;
    }
}