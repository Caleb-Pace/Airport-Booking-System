package airportbookingsystem;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.HashSet;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.GridLayout;


public class BookingPanel extends JPanel {
    private DefaultTableModel tableModel;

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
        String[] columnNames = { "Flight Number", "Origin", "Destination", "Departure" };

        // Create a table model
        tableModel = new DefaultTableModel(columnNames, 0) {
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

        // Filter panel with flight number search functionality
        JPanel filterPanel = new JPanel();
        JTextField flightNumberField = new JTextField(15); // Text field for flight number
        JButton filterButton = new JButton("Filter Flight");
        JButton resetButton = new JButton("Reset");

        // Add components to the filter panel
        filterPanel.add(new JLabel("Enter Flight Number:"));
        filterPanel.add(flightNumberField);
        filterPanel.add(filterButton);
        filterPanel.add(resetButton);

        // Action listener for filtering flights
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String flightNumber = flightNumberField.getText().trim().toLowerCase(); // Get input and convert to lowercase
                boolean flightFound = false;

                // Clear previous table rows
                tableModel.setRowCount(0);

                // Populate table model with filtered flight data
                for (Flight flight : flights) {
                    if (flight.getFlightNumber().toLowerCase().equals(flightNumber)) {
                        Object[] row = {
                                flight.getFlightNumber(),
                                flight.getOrigin(),
                                flight.getDestination(),
                                flight.getDeparture()
                        };
                        tableModel.addRow(row);
                        flightFound = true; // Flight found
                    }
                }

                // Show popup if flight not found
                if (!flightFound) {
                    JOptionPane.showMessageDialog(flightsFrame, "Invalid flight number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Reset button to show all flights again
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the text field and repopulate the table with all flights
                flightNumberField.setText("");
                tableModel.setRowCount(0); // Clear previous rows

                // Repopulate with all flights
                for (Flight flight : flights) {
                    Object[] row = {
                            flight.getFlightNumber(),
                            flight.getOrigin(),
                            flight.getDestination(),
                            flight.getDeparture()
                    };
                    tableModel.addRow(row);
                }
            }
        });

        // Add the filter panel to the top of the frame
        flightsFrame.add(filterPanel, BorderLayout.NORTH);

        // Add close button at the bottom
        flightsFrame.add(closeButton, BorderLayout.SOUTH);

        // Add mouse listener to table rows for flight selection
        flightsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = flightsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String selectedFlightNumber = (String) flightsTable.getValueAt(selectedRow, 0);
                    Flight selectedFlight = FlightManager.getFlightByNumber(selectedFlightNumber);
                    displayFlightDetails(selectedFlight);
                }
            }
        });

        // Display the window
        flightsFrame.setVisible(true);
    }

    // Method to display the selected flight's details in a new window
    private void displayFlightDetails(Flight flight) {
        JFrame bookingFrame = new JFrame("Flight Booking - " + flight.getFlightNumber());
        bookingFrame.setSize(400, 300);
        bookingFrame.setLayout(new BorderLayout());

        // JTextArea to display the flight and seat details
        JTextArea flightDetailsArea = new JTextArea();
        flightDetailsArea.setEditable(false); // Make it read-only
        flightDetailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Set flight and seat layout information
       flight.getAeroplane().display();

        // Add text area to scroll pane
        JScrollPane scrollPane = new JScrollPane(flightDetailsArea);
        bookingFrame.add(scrollPane, BorderLayout.CENTER);

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookingFrame.dispose();
            }
        });

        bookingFrame.add(closeButton, BorderLayout.SOUTH);

        // Display the booking window
        bookingFrame.setVisible(true);
    }
}
/*
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
        String[] columnNames = { "Flight Number", "Origin", "Destination", "Departure" };

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

        // Add a mouse listener to handle row selection
        flightsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = flightsTable.getSelectedRow(); // Get the selected row index
                if (selectedRow != -1) { // Ensure a row is selected
                    displayFlightDetails(selectedRow, tableModel);
                }
            }
        });

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

        // Create a filter panel
        JPanel filterPanel = new JPanel();
        JTextField flightNumberField = new JTextField(15); // Text field for flight number
        JButton filterButton = new JButton("Filter Flight");
        JButton resetButton = new JButton("Reset");

        // Add components to the filter panel
        filterPanel.add(new JLabel("Enter Flight Number:"));
        filterPanel.add(flightNumberField);
        filterPanel.add(filterButton);
        filterPanel.add(resetButton);

        // Add action listener for the filter button
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String flightNumber = flightNumberField.getText().trim().toLowerCase(); // Get input and convert to
                                                                                        // lower case
                boolean flightFound = false;

                // Clear previous table rows
                tableModel.setRowCount(0);

                // Populate table model with filtered flight data
                for (Flight flight : flights) {
                    if (flight.getFlightNumber().toLowerCase().equals(flightNumber)) {
                        Object[] row = {
                                flight.getFlightNumber(),
                                flight.getOrigin(),
                                flight.getDestination(),
                                flight.getDeparture()
                        };
                        tableModel.addRow(row);
                        flightFound = true; // Flight found
                    }
                }

                // Show popup if flight not found
                if (!flightFound) {
                    JOptionPane.showMessageDialog(flightsFrame, "Invalid flight number.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add action listener for the reset button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the text field and repopulate the table with all flights
                flightNumberField.setText("");
                tableModel.setRowCount(0); // Clear previous rows

                // Repopulate with all flights
                for (Flight flight : flights) {
                    Object[] row = {
                            flight.getFlightNumber(),
                            flight.getOrigin(),
                            flight.getDestination(),
                            flight.getDeparture()
                    };
                    tableModel.addRow(row);
                }
            }
        });

        // Add filter panel to the top of the frame
        flightsFrame.add(filterPanel, BorderLayout.NORTH);
        flightsFrame.add(closeButton, BorderLayout.SOUTH);

        // Display the window
        flightsFrame.setVisible(true);
    }

    // Method to display the details of the selected flight
    private void displayFlightDetails(int rowIndex, DefaultTableModel tableModel) {
        String flightNumber = (String) tableModel.getValueAt(rowIndex, 0);
        String origin = (String) tableModel.getValueAt(rowIndex, 1);
        String destination = (String) tableModel.getValueAt(rowIndex, 2);
        String departure = (String) tableModel.getValueAt(rowIndex, 3);

        // Create a dialog to show the flight details
        String details = String.format("Flight Number: %s\nOrigin: %s\nDestination: %s\nDeparture: %s",
                flightNumber, origin, destination, departure);
        JOptionPane.showMessageDialog(null, details, "Flight Details", JOptionPane.INFORMATION_MESSAGE);
    }
}
    */