/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paystation.domain;

import org.junit.Before;

/**
 *
 * @author cmala
 */
public class TestAlternatingRate {

    RateStrategy rs;
    PayStation ps;

    @Before
    public void setup() {
        rs = new AlternatingRateStrategy();

    }

    private void addHalfDollar()
            throws IllegalCoinException {
        ps.addPayment(25);
        ps.addPayment(25);
    }

    private void addOneDollar()
            throws IllegalCoinException {
        addHalfDollar();
        addHalfDollar();
    }
}
