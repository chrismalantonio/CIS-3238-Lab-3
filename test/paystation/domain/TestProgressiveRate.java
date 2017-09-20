/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paystation.domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author cmala
 */
public class TestProgressiveRate {

    RateStrategy rs;
    PayStation ps;

    @Before
    public void setup() {
        rs = new ProgressiveRateStrategy();

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

    @Test
    public void shouldGive120MinFor350cent() {
        assertEquals(2 * 60, rs.calculateTime(350));
    }

    @Test
    public void shouldIntegrateProgressiveRateCorrectly()
            throws IllegalCoinException {
        ps = new PayStationImpl(new ProgressiveRateStrategy());

        addOneDollar();
        addOneDollar();
        
        assertEquals("Progressive Rate: $2 should give 75 min ", 75, ps.readDisplay());
    }
}
