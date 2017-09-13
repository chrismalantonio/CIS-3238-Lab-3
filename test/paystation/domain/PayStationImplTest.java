/**
 * Testcases for the Pay Station system.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
package paystation.domain;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class PayStationImplTest {

    PayStation ps;

    @Before
    public void setup() {
        ps = new PayStationImpl();
    }

    /**
     * Entering 5 cents should make the display report 2 minutes parking time.
     */
    @Test
    public void shouldDisplay2MinFor5Cents()
            throws IllegalCoinException {
        ps.addPayment(5);
        assertEquals("Should display 2 min for 5 cents",
                2, ps.readDisplay());
    }

    /**
     * Entering 25 cents should make the display report 10 minutes parking time.
     */
    @Test
    public void shouldDisplay10MinFor25Cents() throws IllegalCoinException {
        ps.addPayment(25);
        assertEquals("Should display 10 min for 25 cents",
                10, ps.readDisplay());
    }

    /**
     * Verify that illegal coin values are rejected.
     */
    @Test(expected = IllegalCoinException.class)
    public void shouldRejectIllegalCoin() throws IllegalCoinException {
        ps.addPayment(17);
    }

    /**
     * Entering 10 and 25 cents should be valid and return 14 minutes parking
     */
    @Test
    public void shouldDisplay14MinFor10And25Cents()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(25);
        assertEquals("Should display 14 min for 10+25 cents",
                14, ps.readDisplay());
    }

    /**
     * Buy should return a valid receipt of the proper amount of parking time
     */
    @Test
    public void shouldReturnCorrectReceiptWhenBuy()
            throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        Receipt receipt;
        receipt = ps.buy();
        assertNotNull("Receipt reference cannot be null",
                receipt);
        assertEquals("Receipt value must be 16 min.",
                16, receipt.value());
    }

    /**
     * Buy for 100 cents and verify the receipt
     */
    @Test
    public void shouldReturnReceiptWhenBuy100c()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(25);
        ps.addPayment(25);

        Receipt receipt;
        receipt = ps.buy();
        assertEquals(40, receipt.value());
    }

    /**
     * Verify that the pay station is cleared after a buy scenario
     */
    @Test
    public void shouldClearAfterBuy()
            throws IllegalCoinException {
        ps.addPayment(25);
        ps.buy(); // I do not care about the result
        // verify that the display reads 0
        assertEquals("Display should have been cleared",
                0, ps.readDisplay());
        // verify that a following buy scenario behaves properly
        ps.addPayment(10);
        ps.addPayment(25);
        assertEquals("Next add payment should display correct time",
                14, ps.readDisplay());
        Receipt r = ps.buy();
        assertEquals("Next buy should return valid receipt",
                14, r.value());
        assertEquals("Again, display should be cleared",
                0, ps.readDisplay());
    }

    /**
     * Verify that cancel clears the pay station
     */
    @Test
    public void shouldClearAfterCancel()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.cancel();
        assertEquals("Cancel should clear display",
                0, ps.readDisplay());
        ps.addPayment(25);
        assertEquals("Insert after cancel should work",
                10, ps.readDisplay());
    }

    /**
     * Verify that the total amount is returned
     */
    @Test
    public void shouldReturnTotal()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(10);
        assertEquals("empty should return total amount entered", 20, ps.empty());
    }

    /**
     * Verify that calling cancel does not add the canceled payment to the
     * amount returned by empty
     */
    @Test
    public void shouldNotAddToEmpty()
            throws IllegalCoinException {
        ps.addPayment(5);
        ps.cancel();
        ps.addPayment(25);
        assertEquals("canceled entry should not add to the amount returned by empty", 25, ps.empty());
    }

    /**
     * Verify that the total payment is reset to zero 
     */
    @Test
    public void shouldResetTotalToZero()
            throws IllegalCoinException {
        ps.addPayment(25);
        ps.addPayment(25);
        ps.addPayment(25);
        ps.empty();
        assertEquals("call to empty should reset total to zero", 0, ps.empty());
    }

    /**
     * Verify that the map contains one coin
     */
    @Test
    public void shouldReturnOneCoinMap()
            throws IllegalCoinException {
        Map<Integer, Integer> map = new HashMap<>();
        ps.addPayment(5);
        map = ps.cancel();
        int nickel = map.get(5);
        assertEquals("cancel should return a map containing one coin entered", 1, nickel);
    }

    /**
     * Verify that the map contains a mixture of coins
     */
    @Test
    public void shouldReturnMixtureOfCoins()
            throws IllegalCoinException {
        Map<Integer, Integer> map = new HashMap<>();
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(25);
        ps.addPayment(25);
        ps.addPayment(25);
        ps.addPayment(25);
        map = ps.cancel();
        int nickel = map.get(5);
        int dime = map.get(10);
        int quarter = map.get(25);
        assertEquals("should return one nickel", 1, nickel);
        assertEquals("should return two dimes", 2, dime);
        assertEquals("should return four quarters", 4, quarter);
    }

    /**
     * Verify that the map will contain 5 and 10 cent coins, but not 25
     */
    @Test
    public void shouldOnlyMapKeysForCoinsEntered()
            throws IllegalCoinException {
        Map<Integer, Integer> map = new HashMap<>();
        ps.addPayment(5);
        ps.addPayment(10);
        map = ps.cancel();
        int quarter;
        if (map.get(25) != null) {
            quarter = map.get(25);
        } else {
            quarter = 0;
        }
        assertEquals("there should be no quarters in the map", 0, quarter);
    }

    /**
     * Verify that cancel clears the map
     */
    @Test
    public void clearMapOnCancel()
            throws IllegalCoinException {
        Map<Integer, Integer> map = new HashMap<>();
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        ps.cancel();
        map = ps.cancel();
        int mapSize = map.size();
        assertEquals("the map should have no keys after cancel", 0, mapSize);
    }

    /**
     * Verify that buying time clears the map
     */
    @Test
    public void clearMapOnBuy()
            throws IllegalCoinException {
        Map<Integer, Integer> map = new HashMap<>();
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        ps.buy();
        map = ps.cancel();
        int mapSize = map.size();
        assertEquals("the map should have no keys after buying time", 0, mapSize);
    }
}
