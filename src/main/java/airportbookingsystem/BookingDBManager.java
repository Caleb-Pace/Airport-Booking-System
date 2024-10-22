package airportbookingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Caleb
 */
// TODO: Comment
public class BookingDBManager {
    private static final String databaseURL = "jdbc:derby:db/BookingDB_Ebd;create=true";
    private static final String user = "BookingManager";
    private static final String password = "GoO0dPawsword";

    private static Connection conn = null;

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


            Statement statement = conn.createStatement(); // Create sql statement
            
            // Create booking table
            String createTableSQL = "CREATE TABLE bookings ("
                                    + "booking_id  INT NOT NULL PRIMARY KEY,"
                                    + "Name VARCHAR(50),"
                                    + "flight_number VARCHAR(10),"
                                    + "seat_number VARCHAR(4))";
            statement.execute(createTableSQL);
            System.out.println("Table 'bookings' created successfully!");
            
            statement.close(); // Close sql statement
        } catch (ClassNotFoundException | SQLException ex) {
            if (ex.getClass().getName().equals("java.sql.SQLException")
             && ex.getMessage().contains("already exists in Schema"))
                return; // Table already created

            System.err.println("An error occured while setting up the database!");
            ex.printStackTrace();
        }
    }

    public static void close() {
        if (conn == null)
            return;

        try {
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
