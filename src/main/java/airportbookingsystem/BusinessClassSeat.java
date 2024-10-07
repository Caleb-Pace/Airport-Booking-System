/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airportbookingsystem;

/**
 *
 * @author mehta
 */
public class BusinessClassSeat extends Seat{
    public BusinessClassSeat(boolean isTaken){
        super(SeatType.BUSINESS, isTaken);
    }
    
    @Override
    public SeatType getSeatType() {
        return SeatType.BUSINESS;
    }
}
