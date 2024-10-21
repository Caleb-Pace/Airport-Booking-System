package airportbookingsystem;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.HashSet;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;


public class BookingPanel extends JPanel {
    public BookingPanel() {
        // Set layout
        setLayout(new BorderLayout());

        // Create title label
        JLabel titleLabel = new JLabel("AIRPORT BOOKING SYSTEM", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font size and style
        add(titleLabel, BorderLayout.NORTH);

        // Create panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Create buttons
        JButton makeBookingButton = new JButton("MAKE BOOKING");
        JButton viewBookingButton = new JButton("VIEW BOOKING");

        // Add buttons to the panel
        buttonPanel.add(makeBookingButton);
        buttonPanel.add(viewBookingButton);

        // Add button panel to the center of the main panel
        add(buttonPanel, BorderLayout.CENTER);

        // Action listener for making a booking
        makeBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAvailableFlights();
            }
        });
    }

    // Method to display the available flights in a table
    private void showAvailableFlights() {
        // Create a new window to display available flights
        JFrame flightsFrame = new JFrame("Available Flights");
        flightsFrame.setSize(600, 400);
        flightsFrame.setLayout(new BorderLayout());

        // Retrieve flights from FlightManager
        HashSet<Flight> flights = FlightManager.getFlights();

        // Create column names for the table
        String[] columnNames = {"Flight Number", "Origin", "Destination", "Departure"};

        // Create a table model
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // All cells are not editable
        }
    };
        // Populate table model with flight data
        for (Flight flight : flights) {
            Object[] row = {
                flight.getFlightNumber(),
                flight.getOrigin(),
                flight.getDestination(),
                flight.getDeparture()
            };
            tableModel.addRow(row);
        }

        // Create the table and set the model
        JTable flightsTable = new JTable(tableModel);
        flightsTable.setFillsViewportHeight(true); // Makes the table fill the scroll pane

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(flightsTable);
        flightsFrame.add(scrollPane, BorderLayout.CENTER);

        // Create a "Close" button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flightsFrame.dispose(); // Close the window when clicked
            }
        });
        flightsFrame.add(closeButton, BorderLayout.SOUTH);

        // Display the window
        flightsFrame.setVisible(true);
    }
}