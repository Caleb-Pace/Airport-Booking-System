package airportbookingsystem;

import java.util.HashSet;

/**
 * Manages bookings within the airport booking system.
 * 
 * @author Caleb
 */
public class BookingManager
{
    private static HashSet<Booking> bookings = new HashSet<>(); // Stores all bookings
    
    static final String FILEPATH = "./resources/bookings.csv"; // File path for saving/loading bookings
    
    /**
     * Creates a new booking and adds it to the system.
     *
     * @param name       The name of the passenger.
     * @param flight     The flight associated with the booking.
     * @param seatNumber The seat number for the booking.
     * @return The created Booking object.
     */
    public static Booking create(String name, Flight flight, String seatNumber) {
        Booking b = new Booking(name, flight, seatNumber);
        bookings.add(b); // Add the new booking to the set
        
        return b;
    }
    
    /**
     * Lists all current bookings by displaying them.
     */
    public static void listBookings() {
        Display.Bookings(bookings); // Display all bookings
    }
    
    /**
     * Retrieves a booking by its ID.
     *
     * @param id The ID of the booking.
     * @return The booking if found, otherwise null.
     */
    public static Booking getBooking(int id) {
        for (Booking b : bookings) {
            if (b.hashCode() == id) // Compare hashCode to ID
                return b; // Return the matching booking
        }
        
        return null; // Return null if no match is found
    }
    
    /**
     * Retrieves all booking IDs.
     *
     * @return An array of booking IDs.
     */
    public static int[] getBookingIDs() {
        int[] ids = new int[bookings.size()]; // Create an array for IDs
        
        int index = 0;
        for (Booking b : bookings) {
            ids[index++] = b.getId(); // Store each booking's ID
        }
        
        return ids; // Return the array of IDs
    }
    
    /**
     * Cancels a booking by its ID.
     *
     * @param bookingID The ID of the booking to cancel.
     */
    public static void cancel(int bookingID) {
        Booking bookingToRemove = null;
    
        // Find the booking with the given ID
        for (Booking b : bookings) {
            if (b.getId() == bookingID) {
                bookingToRemove = b; // Mark the booking for removal
                break;
            }
        }

        // Remove the booking if found
        if (bookingToRemove != null) {
            bookings.remove(bookingToRemove); // Remove the booking from the set
            System.out.println("Booking with ID " + bookingID + " has been canceled.");
            
            // Save the updated bookings to file
            save();
        } else {
            System.out.println("Booking with ID " + bookingID + " not found.");
        }
    }
    
    /**
     * Loads bookings from a CSV file.
     */
    public static void load() {
        String data = FileManager.load(FILEPATH); // Load data from file
        String[] lines = data.split("\n"); // Split data into lines
        
        for (int i = 0; i < lines.length; i++) {
            String[] values = lines[i].split(","); // Split each line into values
            
            // Skip invalid bookings
            if (values.length != 4) continue;
            
            // Extract variables
            int id = -1;
            try {
                id = Integer.parseInt(values[0]); // Parse ID
            } catch (NumberFormatException ex) {
                continue; // Skip if ID is invalid
            }
            String name = values[1]; // Name
            String flightNum = values[2]; // Flight number
            String seatNum = values[3]; // Seat number
            
            // Add booking to the set
            bookings.add(new Booking(id, name, flightNum, seatNum));
        }
    }
    
    /**
     * Saves all current bookings to a CSV file.
     */
    public static void save() {
        String data = "";
        
        // Build the data string from all bookings
        for (Booking b : bookings) {
            data += b.toString(); // Append each booking's data
        }
        
        FileManager.save(FILEPATH, data); // Save data to file
    }
}
