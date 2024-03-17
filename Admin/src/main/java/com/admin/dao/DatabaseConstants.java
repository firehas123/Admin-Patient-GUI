package com.admin.dao;

public interface DatabaseConstants {
    public final String checkUserExists = "SELECT * FROM users WHERE username = ?";
    public final String authQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
    public final String registerQuery = "INSERT INTO users (username, password, is_admin) VALUES (?, ?, 1)";
    public final String insertDoctorQuery = "INSERT INTO doctor (name, phone_number, background) VALUES (?, ?, ?)";
    public final String getAllDoctorQuery = "SELECT name FROM doctor";
    public final String insertBookingQuery = "INSERT INTO booking (doctor, date, patient) VALUES (?, ?, ?)";
    public final String checkDoctorAvailableQuery = "select * from booking where doctor = ? and date = ?";
    String updatePatientDoctorQuery = "UPDATE booking SET doctor = ? WHERE patient = ? AND doctor = ? AND date = ?";
    public final String getAllPatientQuery = "SELECT username FROM users where is_admin = 0";
    public final String fetchBookingByDoctor = "SELECT * FROM booking WHERE doctor = ?";
    public final String fetchBookingByPatient = "SELECT * FROM booking WHERE patient = ?";
    public final String fetchBookingByMonthYear = "SELECT * FROM booking WHERE YEAR(date) = ? AND MONTH(date) = ?;";
}
