/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paystation.domain;

/**
 * Alternating Rate Strategy for gammatown
 * Rate strategy varies between Linear rate strategy on weekdays 
 * and Progressive rate stategy on weekends
 */
public class AlternatingRateStrategy implements RateStrategy {
    public int calculateTime(int amount){
        return amount * 2 / 5;
    }
}
