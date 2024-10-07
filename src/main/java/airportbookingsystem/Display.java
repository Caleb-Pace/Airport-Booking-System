package airportbookingsystem;

import java.text.MessageFormat;
import java.util.HashSet;

/**
 * Utility class for displaying information related to the airport booking system.
 * 
 * @author Caleb
 */
public class Display
{
    /**
     * Displays a stylized banner for the system.
     */
    public static void Banner()
    {
        String[] lines =
        {
            "",
            "    ___    _                       __",
            "   /   |  (_)________  ____  _____/ /_",
            "  / /| | / / ___/ __ \\/ __ \\/ ___/ __/   ___            __    _",
            " / ___ |/ / /  / /_/ / /_/ / /  / /_    / _ )___  ___  / /__ (_)__  ___ _",
            "/_/  |_/_/_/  / .___/\\____/_/   \\__/   / _  / _ \\/ _ \\/  '_// / _ \\/ _ `/",
            "             /_/                      /____/\\___/\\___/_/\\_\\/_/_//_/\\_, /",
            "                                                                  /___/",
            "",
            "                                                          S Y S T E M",
            "",
            "",
        };
        System.out.println(String.join("\n", lines));
    }
    
    /**
     * Displays a table of flight information.
     * 
     * @param flightNums Array of flight numbers to display.
     */
    public static void Flights(String[] flightNums) {
        // Headers
        System.out.printf("| %-2s | %-10s | %-20s | %-20s | %-20s |%n",
                          "#",
                          "Flight Num",
                          "Origin",
                          "Destination",
                          "Departure Time");
        System.out.println(MessageFormat.format("| {0} | {1} | {1}{1} | {1}{1} | {1}{1} |",
                                                "--",
                                                "----------"));
        
        // Flights
        for (int i = 0; i < flightNums.length; i++) {
            System.out.printf("| %-2d ", i);
            FlightManager.getFlightByNumber(flightNums[i]).display();
        }
    }
    
    /**
     * Displays a table of booking details.
     * 
     * @param bookings Set of bookings to display.
     */
    public static void Bookings(HashSet<Booking> bookings) {
        // Headers
        System.out.printf("| %-4s | %-20s | %-10s | %-4s |%n",
                          "ID",
                          "Name",
                          "Flight Num",
                          "Seat");
        System.out.println(MessageFormat.format("| {0} | {1}{1} | {1} | {0} |",
                                                "----",
                                                "----------"));
        
        // Bookings
        for (Booking b : bookings)
        {
            b.display();
        }
        
        if (bookings.isEmpty())
            System.out.println("There are no bookings!");
        else
            System.out.println();
    }
}