package airportbookingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.PreparedStatement;

/**
 * @author Caleb
 */
// TODO: Comment
public class BookingDBManager {
    private static final String databaseURL = "jdbc:derby:db/BookingDB_Ebd;create=true";
    private static final String user = "BookingManager";
    private static final String password = "GoO0dPawsword";

    private static Connection conn = null;
    private static Statement statement = null;

    // TODO: Comment
    // TODO: Rethink - Does this need to be exposed?
    public static Connection getConnection() {
        if (conn == null)
            setup();
        
        return conn;
    }

    // TODO: Comment
    public static void setup() {
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

                System.out.println("Table 'bookings' created successfully!");
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
                
                System.out.println("Table 'people' created successfully!");
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

    // TODO: Comment
    public static void close() {
        if (conn == null)
            return;

        try {
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    // TODO: Comment
    // TODO: Validate data
    public static boolean add(int id, String passengerName , String flightNumber, String seatNumber) {
        //-/ Safety checks/Early exits
        // Validate string lengths
        if (passengerName .length() > 20)
            return false; // Passenger's name is too long
        if (flightNumber.length() > 10)
            return false; // Flight number is too long
        if (seatNumber.length() > 4)
            return false; // Seat number is too long
        
        // Validate ID
        if (!isIDValid(id))
            return false; // Invalid ID

        // Check if ID already exists
        // TODO: Implement

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

    // TODO: Comment
    public static Booking getByID(int id) {
        // Validate ID
        if (!isIDValid(id))
            return null; // Invalid ID

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

                return new Booking(id, name, flightNumber, seatNumber);
            }
            
            // Clean up
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }

        return null;
    }

    // TODO: Comment
    private static ResultSet queryDB(String sqlQuery) {
        statement = null;
        ResultSet rs = null;
        
        try {
            statement = conn.createStatement(); // Create sql statement
            rs = statement.executeQuery(sqlQuery);        // Execute query
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
        
        return rs;
    }

    // TODO: Comment
    private static boolean isIDValid(int id) {
        int idDigits = String.valueOf(id).length();
        return (idDigits > 0 && idDigits <= 4);
    }
}
