public interface DatabaseConstants {
    public final String fetchBookingByDoctor = "SELECT * FROM booking WHERE doctor = ? AND MONTH(date) = ? AND YEAR(date) = ? ";
    public final String fetchDoctorByUsername = "select * from doctor where name = ?";
}
