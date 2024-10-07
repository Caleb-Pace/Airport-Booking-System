/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airportbookingsystem;

/**
 *
 * @author mehta
 */
public class Aeroplane {

    public static final int SEAT_ROWS = 3; // Number of seat rows in the airplane

    private Seat[] seats;
    private Airline airline;
    private String model;
    private String callSign;

    // Constructor to initialize an Aeroplane object
    public Aeroplane(Seat[] seats, Airline airline, String model, String callSign) {
        this.seats = seats;
        this.airline = airline;
        this.model = model;
        this.callSign = callSign;
    }

    // Private method to print the representation of a seat based on its type and availability
    private void printSeat(Seat seat) {
        String seatStr = "";

        if (seat.isTaken) { // Taken seat
            seatStr = ".";
        } else { // Seat  available
            switch (seat.getSeatType()) {
                case ECONOMY:
                    seatStr = "E";
                    break;
                case FIRST_CLASS:
                    seatStr = "F";
                    break;
                case BUSINESS:
                    seatStr = "B";
                    break;
                default:
                    seatStr = "?";
                    break;
            }
        }

        System.out.printf("[%s] ", seatStr); // Print seat representation
    }

    // Method to count and return the total number of available seats
    public int getTotalAvaliableSeats() {
        int availableSeats = seats.length;

        for (int i = 0; i < seats.length; i++) {
            if (seats[i].isTaken) {
                availableSeats--;
            }
        }

        return availableSeats;
    }

    // Method to display the aeroplane's details and seat layou
    public void display() {
        // Print airline name and banner
        System.out.printf("%n%s%n%s%n%n", airline.getName(), airline.getBanner());
        System.out.printf("Aeroplane Model: %s%n", model);
        System.out.println("Seat:");

        //~/ ASCII art
        // Nose
        String[] noseSegments = {
            "  /. ",
            "(    ",
            "  \\. ",};
        // Tail
        String[] tailSegments = {
            "\\ //",
            "-==>",
            "/ \\\\",};
        // Wings
        String[] rightWing = {
            "   ,",
            "  /|",
            "{/ |>",
            "/  |",};
        String[] leftWing = {
            "\\  |",
            "{\\ |>",
            "  \\|",
            "   `",};

        // Calculate offsets
        int xOffset = 9;
        int columns = (int) Math.ceil((seats.length / (double) SEAT_ROWS));
        int planeMidpoint = xOffset + ((int) Math.floor(columns / 2.0) * 3);
        String wingOffset = " ".repeat(planeMidpoint);

        // Column guide
        if (columns > 26) {
            return; // Failsafe: Too many columns (Should not be reached!)
        }        // Print guide
        System.out.print(" ".repeat(xOffset)); // Align with seats
        for (int c = 0; c < columns; c++) {
            System.out.printf("(%c) ", (65 + c)); // 'A' = 65
        }
        System.out.println();

        //~/ Print aeroplane
        // Print right wing
        System.out.printf("%s%s%n", wingOffset, String.join(("\n" + wingOffset), rightWing));
        // Seats
        for (int j = 0; j < SEAT_ROWS; j++) {
            // Row guide
            System.out.printf("(%d) ", (j + 1));

            // Print nose segment
            System.out.print(noseSegments[j]);

            // Print seat column
            for (int i = j; i < seats.length; i += SEAT_ROWS) {
                printSeat(seats[i]);
            }

            // Seat filler (formatting)
            int emptySeatSlots = (SEAT_ROWS - (seats.length % SEAT_ROWS)) % SEAT_ROWS;
            if (j >= (SEAT_ROWS - emptySeatSlots)) {
                System.out.print("[ ] ");
            }

            // Print tail segment
            System.out.println(tailSegments[j]);
        }
        // Print left wing
        System.out.printf("%s%s%n", wingOffset, String.join(("\n" + wingOffset), leftWing));

        // Print seat avaliablity
        System.out.printf("%n    %d out of %d seats  available.%n%n", getTotalAvaliableSeats(), seats.length);
    }

    // Getters and Setters
    public Seat[] getSeats() {
        return seats;
    }

    public void setSeats(Seat[] seats) {
        this.seats = seats;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }
}
