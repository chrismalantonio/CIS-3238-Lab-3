/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paystation.domain;

import java.util.Date;
import java.util.Calendar; 
import java.util.GregorianCalendar;

/**
 * Alternating Rate Strategy for gammatown
 * Rate strategy varies between Linear rate strategy on weekends 
 * and Progressive rate stategy on weekdays
 */
public class AlternatingRateStrategy implements RateStrategy {
    RateStrategy weekendStrategy, weekdayStrategy, currentState;
    public AlternatingRateStrategy(RateStrategy weekdayStrategy, RateStrategy weekendStrategy){
        this.weekdayStrategy = weekdayStrategy;
        this.weekendStrategy = weekendStrategy; 
        this.currentState = null; 
    }
    public int calculateTime(int amount){
        if (isWeekend()){
            currentState = weekendStrategy;
        } else {
            currentState = weekdayStrategy; 
        }
        return currentState.calculateTime(amount);
    }
    
    private boolean isWeekend(){
        Date d = new Date();
        Calendar c = new GregorianCalendar();
        c.setTime(d);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
    }
}
