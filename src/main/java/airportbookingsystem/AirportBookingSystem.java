package airportbookingsystem;

import static airportbookingsystem.InputHandler.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Main class for the airport booking system.
 * 
 * @author Caleb
 */
public class AirportBookingSystem
{   
    public static void main(String[] args) {
        // Load in bookings
        FlightManager.load(); // Has to run first! (to load bookings)
        // BookingManager.load();
        
        // // Start
        // selectMode(false);

        // TODO: Remove and put into test cases
        Connection a = BookingDBManager.getConnection();
        if (a == null)
            System.err.println("Oh no!");
        else
            System.out.println("It works!");

        int id = 1001;
        boolean wasAdded = BookingDBManager.add(id, "Bob", "SL9999", "B12");

        if (wasAdded) {
            Booking test = BookingDBManager.getByID(id);
            test.display();
        } else {
            System.out.println("Booking was not added!");
        }

        BookingDBManager.close();
   }
    
    /**
     * Displays the main menu and handles user selection.
     * 
     * @param continuation Indicates if the menu is being shown after a previous action.
     */
    public static void selectMode(boolean continuation) {
        // Allow user to read first
        if (continuation) {
            InputHandler.pause(); // Pause if continuing
        }
        
        Display.Banner(); // Show system banner
        
        // Prompt user for action
        System.out.println("(Q/q to quit)");
        int option = InputHandler.GetInt("0: View booking\n1: Make booking\n> ", 1, false);
        switch (option) {
            case 0:
                viewBooking(); // View existing bookings
                break;
            case 1:
                makeBooking(); // Create a new booking
                break;
            default:
                break;
        }
    }
    
    /**
     * Allows the user to view and manage existing bookings.
     */
    public static void viewBooking() {
        BookingManager.load(); // Refresh booking data

        // Display bookings
        Display.Banner();              // Show system banner
        BookingManager.listBookings(); // Display all bookings
        
        // Get ids
        int[] ids = BookingManager.getBookingIDs(); // Get all booking IDs
        if (ids.length == 0) selectMode(true);      // No bookings available
        
        // Get booking id
        int abortInt = -1;
        System.out.println("(Q/q to quit; B/b to go back)");
        int id = InputHandler.GetListItem("Booking ID: ", ids, abortInt);
        if (id == abortInt) selectMode(true);            // Abort if requested
        Booking booking = BookingManager.getBooking(id); // Get specific booking
        
        Display.Banner(); // Show system banner
        booking.display(); // Display booking details
        System.out.println("(Q/q to quit; B/b to go back)");
        int option = InputHandler.GetInt("0: Print ticket\n1: Cancel ticket\n> ", 1, true);
        switch (option) {
            case -1: // Back
                viewBooking();
                break;
            case 0:
                Display.Banner();      // Show system banner
                booking.printTicket(); // Print booking ticket
                break;
            case 1:
                Display.Banner();          // Show system banner
                BookingManager.cancel(id); // Cancel booking
                System.out.println("Booking cancelled!");
                break;
            default:
                break;
        }
        
        selectMode(true);
    }
    
    /**
     * Allows the user to make a new booking.
     */
    public static void makeBooking() {
        Display.Banner(); // Show system banner

        String[] flightNums = FlightManager.getFlightNumbers(); // Get flight numbers

        // Display flights
        Display.Flights(flightNums);
        System.out.println("\n\n");

        System.out.println("(Q/q to quit; B/b to go back)");
        int index = GetInt("Select Flight #: ", flightNums.length - 1, true);
        if (index == -1) selectMode(true); // Abort if requested
        System.out.printf("\nFlight \"%s\" was selected!\n\n", flightNums[index]);

        // Get flight
        Display.Banner();                                                   // Show system banner
        Flight flight = FlightManager.getFlightByNumber(flightNums[index]); // Get selected flight
        flight.getAeroplane().display();                                    // Display aeroplane info
        
        // Get seat numbers
        HashMap<String, Seat> seatMap = FlightManager.getSeatMap(flight); // Get seat map
        String[] seatNumbers = seatMap.keySet().toArray(new String[0]);
        
        // Print seat guide
        System.out.println("Seat Guide:");
        System.out.println("[F]: First class seat");
        System.out.println("[B]: Business class seat");
        System.out.println("[E]: Economy class seat");
        System.out.println("[.]: Seat taken");
        System.out.println("[ ]: Not a passenger seat");
        System.out.println();

        // Get seat
        System.out.println("(Q/q to quit; B/b to go back)");
        String selectedSeat;
        Seat seat = null;
        while (true) {
            selectedSeat = InputHandler.GetListItem("Select a seat (e.g., B3): ", seatNumbers, null);
            if (selectedSeat == null) makeBooking(); // Back if requested
            seat = seatMap.get(selectedSeat);
            
            if (!seat.isTaken) {
                System.out.printf("Seat %s is now selected!%n%n", selectedSeat);
                break;
            } else {
                System.out.println("Seat is already taken, please choose another one.");
            }
        }
        
        // Get name
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name for Ticket: ");
        String name = sc.nextLine(); // Get passenger name

        // Create booking
        Display.Banner();                                                    // Show system banner
        Booking booking = BookingManager.create(name, flight, selectedSeat); // Create booking
        BookingManager.save();                                               // Save updated bookings
        
        seat.isTaken = true; // Mark seat as taken
        
        FlightManager.save(); // Save updated flight data
        
        // Success
        System.out.println("\nBooking successful, Enjoy your flight!\n");
        booking.printTicket(); // Print ticket
        
        selectMode(true); // Return to main menu
    }
}
