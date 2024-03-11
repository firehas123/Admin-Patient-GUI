package com.patient.dao;

public interface DatabaseConstants {
    public final String checkUserExists = "SELECT * FROM users WHERE username = ?";
    public final String authQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
    public final String registerQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
    public final String getAllDoctorQuery = "SELECT name FROM doctor";
    public final String insertBookingQuery = "INSERT INTO booking (doctor, date, patient) VALUES (?, ?, ?)";
    public final String checkDoctorAvailableQuery = "select * from booking where doctor = ? and date = ?";
}
