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
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

public class BookingPanel extends JPanel {
    private DefaultTableModel tableModel;

    public BookingPanel() {
        // Set layout
        setLayout(new BorderLayout());

        // Create title label
        JLabel titleLabel = new JLabel("AIRPORT BOOKING SYSTEM", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
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

        add(buttonPanel, BorderLayout.CENTER);

        // Action listener for making a booking
        makeBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAvailableFlights();
            }
        });
        viewBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewBooking(); // Call the viewBooking method when clicked
            }
        });
    }

    // Method to display the available flights in a table
    private void showAvailableFlights() {
        JFrame flightsFrame = new JFrame("Available Flights");
        flightsFrame.setSize(600, 400);
        flightsFrame.setLayout(new BorderLayout());

        // Retrieve flights from FlightDataController
        HashSet<Flight> flights = FlightDataController.getFlights();

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
                    Flight selectedFlight = FlightDataController.getFlightByNumber(selectedFlightNumber);
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
        bookingFrame.setSize(500, 400);
        bookingFrame.setLayout(new BorderLayout());
    
        // JTextArea to display the flight and seat details
        JTextArea flightDetailsArea = new JTextArea();
        flightDetailsArea.setEditable(false); // Make it read-only
        flightDetailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    
        // Capture the output of the display() method
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
    
        try {
            // Redirect System.out to capture the display() method's output
            System.setOut(printStream);
    
            // Call the display() method (which prints to System.out)
            flight.getAeroplane().display();
    
            // Flush the stream and get the captured text
            System.out.flush();
            String flightDetails = outputStream.toString();
    
            // Set the captured text in the JTextArea
            flightDetailsArea.setText(flightDetails);
        } finally {
            // Restore the original System.out
            System.setOut(originalOut);
        }
    
        // Add text area to scroll pane
        JScrollPane scrollPane = new JScrollPane(flightDetailsArea);
        bookingFrame.add(scrollPane, BorderLayout.CENTER);
    
        // Panel for seat selection and name input
        JPanel selectionPanel = new JPanel(new FlowLayout());
    
        // Get seat map from the flight
        HashMap<String, Seat> seatMap = FlightDataController.getSeatMap(flight);
        String[] seatNumbers = seatMap.keySet().toArray(new String[0]);
    
        Arrays.sort(seatNumbers);

        // Create a dropdown for available seats
        JComboBox<String> seatDropdown = new JComboBox<>(seatNumbers);
        selectionPanel.add(new JLabel("Select Seat:"));
        selectionPanel.add(seatDropdown);
    
        // Button to confirm seat selection
        JButton selectSeatButton = new JButton("Confirm Seat");
        selectionPanel.add(selectSeatButton);
    
        // Input field for passenger name
        JTextField nameField = new JTextField(15);
        selectionPanel.add(new JLabel("Enter Name:"));
        selectionPanel.add(nameField);
    
        bookingFrame.add(selectionPanel, BorderLayout.SOUTH);
    
        // Action listener for seat confirmation
        selectSeatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSeat = (String) seatDropdown.getSelectedItem();
                Seat seat = seatMap.get(selectedSeat);
    
                // Check if the seat is already taken
                if (seat.isTaken) {
                    JOptionPane.showMessageDialog(bookingFrame, "Seat " + selectedSeat + " is already taken. Please select another seat.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String passengerName = nameField.getText().trim();
    
                    if (passengerName.isEmpty()) {
                        JOptionPane.showMessageDialog(bookingFrame, "Please enter a name for the ticket.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Proceed with booking
                        seat.isTaken = true; // Mark seat as taken
                        BookingController.addBooking(passengerName, flight, selectedSeat);
    
                        JOptionPane.showMessageDialog(bookingFrame, "Booking successful for " + passengerName + " on seat " + selectedSeat + ".", "Success", JOptionPane.INFORMATION_MESSAGE);

                        bookingFrame.dispose(); // Close window after successful booking
                    }
                }
            }
        });
    
        // Display the booking window
        bookingFrame.setVisible(true);
    }
    private void viewBooking() {
        // Create a new frame to display booking information
        JFrame bookingFrame = new JFrame("View Bookings");
        bookingFrame.setSize(600, 400);
        bookingFrame.setLayout(new BorderLayout());
    
        // Define column names for the table
        String[] columnNames = {"Booking ID", "Passenger Name", "Flight Number", "Seat Number"};
    
        
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    
        // Retrieve all booking IDs
        ArrayList<Integer> ids = BookingController.getBookingIDs();
        for (int id : ids) {
            Booking booking = BookingController.getBooking(id); // Retrieve booking by ID
            if (booking != null) {
                // Add a row for each booking
                tableModel.addRow(new Object[]{
                        booking.getId(),
                        booking.getName(),
                        booking.getFlight().getFlightNumber(), 
                        booking.getSeatNumber() 
                });
            }
        }
    
        // Create a JTable with the table model
        JTable bookingTable = new JTable(tableModel);
        bookingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        bookingFrame.add(scrollPane, BorderLayout.CENTER);
    
        // Add a mouse listener to handle row selection
        bookingTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = bookingTable.getSelectedRow();
                if (selectedRow != -1) {
                    int bookingId = (int) tableModel.getValueAt(selectedRow, 0); // Get Booking ID
                    Booking booking = BookingController.getBooking(bookingId); // Retrieve the selected booking
                    showBookingDetails(booking, bookingTable, tableModel); // Call method to display details
                }
            }
        });
    
        // Create a "Close" button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> bookingFrame.dispose());
        bookingFrame.add(closeButton, BorderLayout.SOUTH);
    
        // Display the booking window
        bookingFrame.setVisible(true);
    }
    

    private void showBookingDetails(Booking booking, JTable bookingTable, DefaultTableModel tableModel) {
        // Create a new frame to display booking details
        JFrame detailsFrame = new JFrame("Booking Details");
        detailsFrame.setSize(300, 400);
        detailsFrame.setLayout(new BorderLayout());
    
        // Create a text area to display ticket details
        JTextArea detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsTextArea.setText(
                "***************************************\n" +
                "*           BOARDING PASS             *\n" +
                "***************************************\n" +
                String.format("* %-35s *\n", "ID: " + booking.getId()) +
                String.format("* %-35s *\n", "Passenger Name: " + booking.getName()) +
                String.format("* %-35s *\n", "Flight Number: " + booking.getFlight().getFlightNumber()) +
                "*-------------------------------------*\n" +
                String.format("* %-35s *\n", "Seat Number: " + booking.getSeatNumber()) +
                "***************************************"
        );
    
        JScrollPane scrollPane = new JScrollPane(detailsTextArea);
        detailsFrame.add(scrollPane, BorderLayout.CENTER);
    
        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();  // Declare the buttonPanel here
    
        // Create a Cancel Booking button
        JButton cancelButton = new JButton("Cancel Booking");
        cancelButton.addActionListener(e -> {
            // Cancel the booking
            BookingController.cancel(booking);
            tableModel.removeRow(bookingTable.getSelectedRow()); // Remove from the table
            detailsFrame.dispose();
    
            JOptionPane.showMessageDialog(null, booking.getId() + " has been cancelled.", "Booking Cancelled", JOptionPane.INFORMATION_MESSAGE);
        });
        buttonPanel.add(cancelButton);  // Add the cancel button to the panel
    
        // Create a Close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> detailsFrame.dispose()); // Close the window
        buttonPanel.add(closeButton);  // Add the close button to the panel
    
        detailsFrame.add(buttonPanel, BorderLayout.SOUTH); // Add the button panel to the frame
    
        // Display the details window
        detailsFrame.setVisible(true);
    }
    
}
