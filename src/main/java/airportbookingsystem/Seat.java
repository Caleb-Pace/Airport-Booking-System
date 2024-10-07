/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airportbookingsystem;

/**
 *
 * @author mehta
 */
public abstract class Seat {

    private SeatType seatType;
    public boolean isTaken;

    public enum SeatType {
        ECONOMY,
        FIRST_CLASS,
        BUSINESS
    }

    public Seat(SeatType seatType, boolean isTaken) {
        this.isTaken = isTaken;
        this.seatType = seatType;

    }
    
    public SeatType getSeatType(){
        return seatType;
    }
}
