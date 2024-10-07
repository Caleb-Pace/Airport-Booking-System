/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airportbookingsystem;

/**
 *
 * @author mehta
 */
public class EconomyClassSeat extends Seat{
    public EconomyClassSeat(boolean isTaken){
        super(SeatType.ECONOMY, isTaken);
    }
    
    @Override
    public SeatType getSeatType(){
        return SeatType.ECONOMY;
    }
}
