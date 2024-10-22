package airportbookingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("An error occured while setting up the database!");
            ex.printStackTrace();
        }
    }
}
