import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;

public class LoginPanel extends JFrame {

    protected JTextField usernameField;
    protected JButton viewBookings;

    private DatabaseInstance databaseInstance;

    public LoginPanel() throws SQLException {
        databaseInstance = DatabaseInstance.getInstance();
        setTitle("Doctor Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel usernameLabel = new JLabel("Enter your Name:");
        inputPanel.add(usernameLabel);
        usernameField = new JTextField(15);
        inputPanel.add(usernameField);
        panel.add(inputPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        viewBookings = new JButton("View Booking");
        buttonPanel.add(viewBookings);
        panel.add(buttonPanel);

        viewBookings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                try {
                    dispose();
                    if(databaseInstance.checkIfDoctorExists(username))
                        new ViewBookingFrame(username);
                    else
                        JOptionPane.showMessageDialog(LoginPanel.this, "No Doctor With "+username+ " Exists");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        add(panel);
        setVisible(true);
    }
}
