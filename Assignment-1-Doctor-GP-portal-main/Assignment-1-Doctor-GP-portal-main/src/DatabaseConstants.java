public interface DatabaseConstants {
    public final String fetchBookingByDoctor = "SELECT * FROM booking as b, booking_details as bd WHERE b.doctor = ? AND MONTH(b.date) = ? AND YEAR(b.date) = ? and b.id = bd.booking_id";
    public final String fetchDoctorByUsername = "select * from doctor where name = ?";
}
