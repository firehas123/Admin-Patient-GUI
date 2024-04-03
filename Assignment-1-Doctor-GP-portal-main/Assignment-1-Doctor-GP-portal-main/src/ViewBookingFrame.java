import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewBookingFrame extends JFrame {
    private final DatabaseInstance databaseInstance;

    public ViewBookingFrame(String username) throws SQLException {
        databaseInstance = DatabaseInstance.getInstance();
        setTitle("Bookings List");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel monthLabel = new JLabel("Enter Month:");
        inputPanel.add(monthLabel);
        JTextField monthField = new JTextField(10);
        inputPanel.add(monthField);
        JLabel yearLabel = new JLabel("Enter Year:");
        inputPanel.add(yearLabel);
        JTextField yearField = new JTextField(10);
        inputPanel.add(yearField);
        JButton viewButton = new JButton("View");
        inputPanel.add(viewButton);
        panel.add(inputPanel, BorderLayout.NORTH);

        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton returnButton = new JButton("Return");
        buttonPanel.add(returnButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String month = monthField.getText();
                String year = yearField.getText();
                try {
                    populateTable(table, username, month, year);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new LoginPanel();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        add(panel);
        setVisible(true);
    }

    private void populateTable(JTable table, String username, String month, String year) throws SQLException {
        ResultSet resultSet = databaseInstance.fetchBookingsByDoctor(username, month, year);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Doctor");
        model.addColumn("Date");
        model.addColumn("Patient");
        model.addColumn("Phone Num");
        model.addColumn("Summary");

        while (resultSet.next()) {
            String doctor = resultSet.getString("doctor");
            String date = resultSet.getString("date");
            String patient = resultSet.getString("patient");
            String phoneNum = resultSet.getString("phone_num");
            String summary = resultSet.getString("summary");
            model.addRow(new Object[]{doctor, date, patient,phoneNum,summary});
        }

        table.setModel(model);
    }
}
