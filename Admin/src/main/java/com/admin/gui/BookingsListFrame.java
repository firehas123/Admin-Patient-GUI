package com.admin.gui;

import com.admin.dao.DatabaseInstance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingsListFrame extends JFrame {
    private DatabaseInstance databaseInstance;

    public BookingsListFrame(String username, String parameter1, String parameter2) throws SQLException {
        databaseInstance = DatabaseInstance.getInstance();
        setTitle("Bookings List");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new GridLayout(1, 1));

        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new MainMenuFrame(username);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(returnButton);

        add(panel);
        setVisible(true);

        // Populate table based on parameters
        populateTable(table, parameter1, parameter2);
    }

    private void populateTable(JTable table, String parameter1, String parameter2) throws SQLException {
        ResultSet resultSet = null;
        if (parameter2.equals("doctor")) {
            resultSet = databaseInstance.fetchBookingsByDoctor(parameter1);
        } else if (parameter2.equals("patient")) {
            resultSet = databaseInstance.fetchBookingsByPatient(parameter1);
        } else {
            resultSet = databaseInstance.fetchBookingsByMonthYear(parameter1, parameter2);
        }

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Doctor");
        model.addColumn("Date");
        model.addColumn("Patient");

        while (resultSet.next()) {
            String doctor = resultSet.getString("doctor");
            String date = resultSet.getString("date");
            String patient = resultSet.getString("patient");
            model.addRow(new Object[]{ doctor, date, patient});
        }

        table.setModel(model);
    }
}
