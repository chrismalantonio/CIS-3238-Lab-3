/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paystation.domain;

/**
 * Progressive Rate Strategy for betatown
 * Payments increase as more time is bought
 */
public class ProgressiveRateStrategy implements RateStrategy{
    public int calculateTime(int amount){
        int time = 0;
        if (amount >= 150){
            amount -= 150;
            time = 60 + amount * 3 / 10; 
        } else {
            time = amount * 2 / 5; 
        }
        return  time;
    }
   
}
