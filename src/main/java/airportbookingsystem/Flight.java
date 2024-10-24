/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airportbookingsystem;

/**
 *
 * @author mehta
 */
public class Flight {

    //Variables to store details about the flight
    private Aeroplane aeroplane;
    private String flightNumber;
    private String origin;
    private String destination;
    private String departure;
    private boolean delayed;
    private String estimatedArrival;
    private String gateNumber;

    //This is the constructor to initialize a flight object
    public Flight(Aeroplane aeroplane, String flightNumber, String origin, String destination, String departure,
            String estimatedArrival, String gateNumber) {
        this.aeroplane = aeroplane;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
        this.delayed = false;
        this.estimatedArrival = estimatedArrival;
        this.gateNumber = gateNumber;
    }

    public Flight(String flightNumber2, String origin2, String destination2, String departure2) {
        //TODO Auto-generated constructor stub
    }

    public void display() {
        System.out.printf("| %-10s | %-20s | %-20s | %-20s |%n",
                getFlightNumber(),
                getOrigin(),
                getDestination(),
                getDeparture());
    }

    //Getters and Setters
    public Aeroplane getAeroplane() {
        return aeroplane;
    }

    public void setAeroplane(Aeroplane aeroplane) {
        this.aeroplane = aeroplane;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public boolean isDelayed() {
        return delayed;
    }

    public void setDelayed(boolean delayed) {
        this.delayed = delayed;
    }

    public String getEstimatedArrival() {
        return estimatedArrival;
    }

    public void setEstimatedArrival(String estimatedArrival) {
        this.estimatedArrival = estimatedArrival;
    }

    public String getGateNumber() {
        return gateNumber;
    }

    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }
}
