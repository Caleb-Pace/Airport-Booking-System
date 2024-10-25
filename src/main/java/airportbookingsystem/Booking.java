package airportbookingsystem;

/**
 * Represents a booking in the airport booking system.
 * 
 * @author Caleb
 */
public class Booking
{
    private final int id;                 // Unique ID for the booking
    private final String name;            // Name of the passenger
    private final Flight flight;          // Flight associated with the booking
    private final String seatNumber;      // Seat number assigned to the booking
    private static int CURRENT_ID = 1000; // Static counter to generate unique IDs

    /**
     * Constructor to create a new booking with a unique ID.
     *
     * @param name       The name of the passenger.
     * @param flight     The flight associated with the booking.
     * @param seatNumber The seat number assigned to the booking.
     */
    public Booking(String name, Flight flight, String seatNumber)
    {
        this.id = CURRENT_ID++;          // Assign and increment unique ID
        this.name = name;
        this.flight = flight;
        this.seatNumber = seatNumber;
    }
    
    /**
     * Constructor to create a booking with a specified ID (used for loading existing bookings).
     *
     * @param id           The ID of the booking.
     * @param name         The name of the passenger.
     * @param flightNumber The flight number associated with the booking.
     * @param seatNumber   The seat number assigned to the booking.
     */
    public Booking(int id, String name, String flightNumber, String seatNumber)
    {
        this.id = id;                                  // Set the booking ID
        CURRENT_ID = ++id;                             // Update CURRENT_ID to ensure uniqueness
        
        this.name = name;
        this.flight = FlightManager.getFlightByNumber(flightNumber); // Retrieve flight by number
        this.seatNumber = seatNumber;
    }
    
    /**
     * Displays booking information in a formatted table row.
     */
    public void display() {
        String flightNumber = (flight != null ? flight.getFlightNumber() : "Unknown");

        System.out.printf("| %-4d | %-20s | %-10s | %-4s |%n",
                          getId(), 
                          name,
                          flightNumber, 
                          seatNumber);
    }
    
    /**
     * Prints the boarding pass for the booking.
     */
    public void printTicket() {
        Airline airline = flight.getAeroplane().getAirline(); // Get airline information

        // Print the formatted boarding pass
        System.out.println("***************************************");
        System.out.println("*           BOARDING PASS             *");
        System.out.println("***************************************");
        System.out.printf("* %-35s *\n", "ID: " + this.id);
        System.out.printf("* %-35s *\n", airline.getBanner());
        System.out.printf("* %-35s *\n", "Airline: " + airline.getName());
        System.out.printf("* %-35s *\n", "Flight Number: " + flight.getFlightNumber());
        System.out.println("*-------------------------------------*");
        System.out.printf("* %-35s *\n", "Origin: " + flight.getOrigin());
        System.out.printf("* %-35s *\n", "Departure: " + flight.getDeparture());
        System.out.printf("* %-35s *\n", "Gate: " + flight.getGateNumber());
        System.out.println("*-------------------------------------*");
        System.out.printf("* %-35s *\n", "Destination: " + flight.getDestination());
        System.out.printf("* %-35s *\n", "Arrival: " + flight.getEstimatedArrival());
        System.out.println("*-------------------------------------*");
        System.out.printf("* %-35s *\n", "Passenger Name: " + this.name);
        System.out.printf("* %-35s *\n", "Seat: " + this.seatNumber);
        System.out.println("***************************************");
    }
    
    /**
     * Checks if this booking is equal to another object.
     * 
     * @param o The object to compare to.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Booking)) return false;
        
        return (this.getId() == ((Booking)o).getId()); // Compare based on booking ID
    }
    
    /**
     * Generates a hash code for the booking.
     * 
     * @return The booking's ID as its hash code.
     */
    @Override
    public int hashCode() {
        return getId(); // Use the booking ID as the hash code
    }
    
    /**
     * Converts the booking to a string in CSV format.
     * 
     * @return A string representation of the booking.
     */
    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s\n", getId(), name, flight.getFlightNumber(), seatNumber);
    }

    /**
     * Gets the ID of the booking.
     * 
     * @return The booking ID.
     */
    public int getId()
    {
        return id;
    }
}
