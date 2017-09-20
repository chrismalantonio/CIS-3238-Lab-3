/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paystation.domain;

/**The strategy for calculating parking rates
 */
public interface RateStrategy {
    /**
     * return the amount of minutes parking time the provided payment is valid for.
     * @param amount payment in some currency
     * @return number of minutes parking time
     */
    public int calculateTime(int amount); 
    
}
