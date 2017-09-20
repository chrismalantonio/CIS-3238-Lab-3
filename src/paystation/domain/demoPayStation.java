/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paystation.domain;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author cmala
 */
public class demoPayStation {

    public static void main(String[] args) {

        //initialize rateStrategy to alphatown's linear rate strategy
        RateStrategy rateStrategy = new LinearRateStrategy(); 
        PayStationImpl paystation = new PayStationImpl(rateStrategy);
        int timeBought, totalValue, nickels, dimes, quarters;

        while (true) {
            //1. deposit coins
            //2. display
            //3. buy ticket
            //4. cancel
            //5. change rate strategy
            String[] choices = {"Deposit coins", "Display", "Buy ticket", "Cancel", "Change rate strategy"};
            String input = (String) JOptionPane.showInputDialog(null, "Choose now...",
                    "PayStation options", JOptionPane.QUESTION_MESSAGE, null, // Use
                    choices, // Array of choices
                    choices[0]); // Initial choice
            System.out.println(input);

            if (input == null) {
                return;
            } else {
                switch (input) {
                    case "Deposit coins":
                        while (true) {
                            String coinValue[] = {"5", "10", "25"};
                            String inputCoin = (String) JOptionPane.showInputDialog(null, "Choose now...",
                                    "Coin selection", JOptionPane.QUESTION_MESSAGE, null, // Use
                                    coinValue, // Array of choices
                                    coinValue[0]); // Initial choice
                            System.out.println(inputCoin);
                            try {
                                paystation.addPayment(Integer.valueOf(inputCoin));
                            } catch (Exception e) {
                                break;
                            }
                        }
                        break;
                    case "Display":
                        JOptionPane.showMessageDialog(null,
                                "Time bought: " + paystation.readDisplay() + " minutes");
                        break;
                    case "Buy ticket":
                        timeBought = paystation.readDisplay();
                        ReceiptImpl receipt = new ReceiptImpl(paystation.empty());
                        JOptionPane.showMessageDialog(null,
                                "Parking time receipt\n Total change spent: " + receipt.value() + " cents\n Time bought: "
                                + timeBought + " minutes");
                        break;
                    case "Cancel":
                        Map<Integer, Integer> map = new HashMap<>();
                        map = paystation.cancel();
                        nickels = dimes = quarters = 0;
                        
                        try {
                            nickels = map.remove(5);
                        } catch (Exception e) {
                        }
                        try {
                            dimes = map.remove(10);
                        } catch (Exception e) {
                        }
                        try {
                            quarters = map.remove(25);

                        } catch (Exception e) {
                        }
                        
                        System.out.println("Nickels: " + nickels);
                        System.out.println("Dimes: " + dimes);
                        System.out.println("Quarters: " + quarters);
                        totalValue = (nickels * 5) + (dimes * 10) + (quarters * 25);
                        System.out.println("Total value: " + totalValue);
                        JOptionPane.showMessageDialog(null,
                                "Transaction canceled\nTotal amount returned: " + totalValue + " cents\n" 
                        + "Nickels: " + nickels + "\nDimes: " + dimes + "\nQuarters: " + quarters);

                        return;
                    case "Change rate strategy":
                        break;
                }
            }

        }
    }
}
