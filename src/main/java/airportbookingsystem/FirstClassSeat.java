/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airportbookingsystem;

/**
 *
 * @author mehta
 */
public class FirstClassSeat extends Seat {
    public FirstClassSeat(boolean isTaken) {
        super(SeatType.FIRST_CLASS, isTaken);
    }

    @Override
    public SeatType getSeatType() {
        return SeatType.FIRST_CLASS;
    }
}
