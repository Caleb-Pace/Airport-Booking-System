package airportbookingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * @author Caleb
 */
public class BookingDBManager {
    private static final String databaseURL = "jdbc:derby:db/BookingDB_Ebd;create=true";
    private static final String user = "BookingManager";
    private static final String password = "GoO0dPawsword";

    private static Connection conn = null;
    private static Statement statement = null;

    /**
     * Retrieves a database connection, initializing it if not already set up.
     *
     * @return Connection - the active database connection
     */
    public static Connection getConnection() {
        if (conn == null)
            setup(); // Set up the connection if it's not initialized
        
        return conn; // Return the established connection
    }

    /**
     * Closes the database connection if it is active.
     */
    public static void close() {
        if (conn == null)
            return; // No open connection

        try {
            conn.close(); // Attempt to close the connection
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Adds a booking to the database.
     *
     * @param id            Unique booking ID
     * @param passengerName Name of the passenger
     * @param flightNumber  Flight number for the booking
     * @param seatNumber    Seat number for the booking
     * @return boolean      True if booking was successfully added, false if invalid or duplicate
     */
    public static boolean add(int id, String passengerName , String flightNumber, String seatNumber) {
        //-/ Safety checks/Early exits
        // Validate string lengths
        if (passengerName.length() > 20)
            return false; // Passenger's name is too long
        if (flightNumber.length() > 10)
            return false; // Flight number is too long
        if (seatNumber.length() > 4)
            return false; // Seat number is too long
        
        // Validate ID
        if (!isIDValid(id))
            return false; // Invalid ID

        // Check if ID already exists
        if (dbContains(id))
            return false; // Reject duplicate ID

        //-/ Add to database
        try {
            // Add booking data
            String insertSQL = "INSERT INTO bookings VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setInt(1, id);
                pstmt.setString(2, flightNumber);
                pstmt.setString(3, seatNumber);
    
                pstmt.executeUpdate();
            }

            // Add person
            insertSQL = "INSERT INTO people VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setInt(1, id);
                pstmt.setString(2, passengerName );
    
                pstmt.executeUpdate();
            }

            return true; // Booking added
        } catch (SQLException ex) {
            System.err.println("An error occured while setting up the database!");
            ex.printStackTrace();
        }

        return false; // Not added
    }

    /**
     * Removes a booking from both tables in the database by its ID.
     *
     * @param id Unique booking ID to remove
     */
    public static void removeByID(int id) {
        // Validate ID
        if (!isIDValid(id))
            return; // Invalid ID
        
        // Check if ID exists
        if (!dbContains(id))
            return; // ID not found

        try {
            statement = conn.createStatement(); // Create sql statement

            // Remove from booking
            String deleteSQL = "DELETE FROM bookings\n"
                                + "WHERE booking_id = " + id;
            statement.executeUpdate(deleteSQL);

            // Remove from person
            deleteSQL = "DELETE FROM people\n"
                        + "WHERE booking_id = " + id;
            statement.executeUpdate(deleteSQL);

            statement.close(); // Close sql statement
        } catch (SQLException ex) {
            System.err.println("An error occured while setting up the database!");
            ex.printStackTrace();
        }
    }

    /**
     * Resets the database by deleting both tables.
     */
    public static void resetDB() {
        try {
            statement = conn.createStatement(); // Create sql statement

            // Delete booking table
            try {
                String deleteTableSQL = "DROP TABLE bookings";
                statement.executeUpdate(deleteTableSQL);
            } catch (SQLException ex) {
                // Rethrow exception if it’s not a "table does not exist" error.
                if (!ex.getMessage().contains("does not exist"))
                    throw ex;
            }

            // Delete people table
            try {
                String deleteTableSQL = "DROP TABLE people";
                statement.executeUpdate(deleteTableSQL);
            } catch (SQLException ex) {
                // Rethrow exception if it’s not a "table does not exist" error.
                if (!ex.getMessage().contains("does not exist"))
                    throw ex;
            }

            statement.close(); // Close sql statement
        } catch (SQLException ex) {
            System.err.println("An error occured while setting up the database!");
            ex.printStackTrace();
        }
    }

    /**
     * Retrieves a booking by its ID.
     *
     * @param id Unique booking ID to search for
     * @return Booking The booking object if found, or null if invalid or not found
     */
    public static Booking getByID(int id) {
        Booking booking = null;

        // Validate ID
        if (!isIDValid(id))
            return null; // Invalid ID

        // Execute query
        String sqlQuery = "SELECT p.passenger_name, b.flight_number, b.seat_number\n"
                        + "FROM bookings b, people p\n"
                        + "WHERE b.booking_id = p.booking_id\n"
                        + "AND b.booking_id = " + id;
        ResultSet rs = queryDB(sqlQuery);

        // No results
        if (rs == null)
            return null;

        try {
            // Retrieve booking
            if (rs.next()) {
                String name = rs.getString("PASSENGER_NAME");
                String flightNumber = rs.getString("FLIGHT_NUMBER");
                String seatNumber = rs.getString("SEAT_NUMBER");

                booking =  new Booking(id, name, flightNumber, seatNumber);
            }

            // Clean up
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }

        return booking;
    }

    /**
     * Retrieves all bookings from the database.
     *
     * @return ArrayList<Booking> List of all booking objects
     */
    public static ArrayList<Booking> getAllBookings() {
        ArrayList<Booking> bookings = new ArrayList<>();
        ArrayList<Integer> ids = getAllIDs(); // Fetch all booking IDs

        // Retrieve each booking by its ID and add to the list
        for (int id : ids) {
            Booking b = getByID(id);
            if (b == null)
                continue; // Skip if no booking found for the ID

            bookings.add(b); // Add the valid booking to the list
        }

        return bookings;
    }

    /**
     * Retrieves all booking IDs from the database.
     *
     * @return ArrayList<Integer> List of all booking IDs
     */
    public static ArrayList<Integer> getAllIDs() {
        ArrayList<Integer> ids = new ArrayList<>();

        // Execute query
        String sqlQuery = "SELECT p.booking_id\n"
                        + "FROM bookings b, people p\n"
                        + "WHERE b.booking_id = p.booking_id";
        ResultSet rs = queryDB(sqlQuery);

        // No results
        if (rs == null)
            return ids;

        try {
            // Retrieve bookings
            while (rs.next()) {
                ids.add(rs.getInt("BOOKING_ID"));
            }

            // Clean up
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }

        return ids;
    }

    /**
     * Checks if the database contains a booking with the specified ID.
     *
     * @param id Unique booking ID to check for
     * @return boolean True if the ID exists in the database, otherwise false
     */
    private static boolean dbContains(int id) {
        boolean isPresent = false;

        // Execute query
        String sqlQuery = "SELECT b.booking_id\n"
                        + "FROM bookings b, people p\n"
                        + "WHERE b.booking_id = p.booking_id\n"
                        + "AND b.booking_id = " + id;
        ResultSet rs = queryDB(sqlQuery);

        // No results
        if (rs == null)
            return isPresent; // (false)

        try {
            // Does query have data
            isPresent = rs.next();
            
            // Clean up
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }

        return isPresent;
    }

    /**
     * Establishes the connection to the embedded database.
     */
    private static void setup() {
        try {
            // Load the embedded Derby driver
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

            // Establish a connection to the database
            conn = DriverManager.getConnection(databaseURL, user, password);

            //-/ Create tables
            statement = conn.createStatement(); // Create sql statement
            
            // Create booking table
            try {
                String createTableSQL = "CREATE TABLE bookings ("
                                      + "booking_id  INT NOT NULL PRIMARY KEY,"
                                      + "flight_number VARCHAR(10),"
                                      + "seat_number VARCHAR(4))";
                statement.execute(createTableSQL);

                System.out.println("  Database: Table 'bookings' created successfully!");
            } catch (SQLException ex) {
                // Rethrow exception if it’s not a "table already exists" error.
                if (!ex.getMessage().contains("already exists in Schema"))
                    throw ex;
            }

            // Create people table
            try {
                String createTableSQL = "CREATE TABLE people ("
                                      + "booking_id  INT NOT NULL PRIMARY KEY,"
                                      + "passenger_name VARCHAR(20))";
                statement.execute(createTableSQL);
                
                System.out.println("  Database: Table 'people' created successfully!");
            } catch (SQLException ex) {
                // Rethrow exception if it’s not a "table already exists" error.
                if (!ex.getMessage().contains("already exists in Schema"))
                    throw ex;
            }
            
            statement.close(); // Close sql statement
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("An error occured while setting up the database!");
            ex.printStackTrace();
        }
    }

    /**
     * Executes a SQL query on the database and returns the result set.
     *
     * @param sqlQuery The SQL query string to execute
     * @return ResultSet The result set obtained from executing the query
     */
    private static ResultSet queryDB(String sqlQuery) {
        statement = null;
        ResultSet rs = null;
        
        try {
            statement = conn.createStatement();    // Create sql statement
            rs = statement.executeQuery(sqlQuery); // Execute query
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
        
        return rs;
    }

    /**
     * Validates the given ID.
     * Currently, it only checks the length of the ID.
     *
     * @param id The ID to validate
     * @return boolean True if the ID is valid, otherwise false
     */
    private static boolean isIDValid(int id) {
        int idDigits = String.valueOf(id).length();
        return (idDigits > 0 && idDigits <= 4);
    }
}
