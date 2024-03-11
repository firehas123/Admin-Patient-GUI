package com.admin.dao;

public interface DatabaseConstants {
    public final String checkUserExists = "SELECT * FROM users WHERE username = ?";
    public final String authQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
    public final String registerQuery = "INSERT INTO users (username, password) VALUES (?, ?)";

    public final String insertDoctorQuery = "INSERT INTO doctor (name, phone_number, background) VALUES (?, ?, ?)";

}
