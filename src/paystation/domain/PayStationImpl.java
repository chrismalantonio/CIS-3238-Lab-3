package paystation.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the pay station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 
 * 2) Calculate parking time based on payment; 
 * 3) Know earning, parking time bought; 
 * 4) Issue receipts; 
 * 5) Handle buy and cancel events.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
public class PayStationImpl implements PayStation {
    
    private int insertedSoFar;
    private int timeBought;
    private int nickels, dimes, quarters;
    private Map<Integer, Integer> map = new HashMap<>();
    private RateStrategy rateStrategy; 

    public PayStationImpl(RateStrategy rateStrategy) {
        this.rateStrategy = rateStrategy; 
    }

    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        switch (coinValue) {
            case 5: map.put(coinValue, ++nickels); 
                break;
            case 10: map.put(coinValue, ++dimes); 
                break;
            case 25: map.put(coinValue, ++quarters); 
                break;
            default:
                throw new IllegalCoinException("Invalid coin: " + coinValue);
        }
        insertedSoFar += coinValue;
        timeBought = rateStrategy.calculateTime(insertedSoFar);
    }

    @Override
    public int readDisplay() {
        return timeBought;
    }

    @Override
    public Receipt buy() {
        Receipt r = new ReceiptImpl(timeBought);
        reset();
        return r;
    }

    @Override
    public Map<Integer,Integer> cancel() {
        Map<Integer,Integer> mapCopy = new HashMap<>();
        mapCopy.putAll(map);  
        reset(); 
        return mapCopy; 
    }
    
    private void reset() {
        timeBought = insertedSoFar = 0;
        nickels = dimes = quarters = 0; 
        map.clear(); 
    }
    
    @Override
    public int empty(){
        int totalAmount = insertedSoFar;
        reset();
        return totalAmount; 
    }
}
