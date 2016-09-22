package com.pillar.kata.vending;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code VendingMachine} class emulates the actions of the vending machine
 * as per the kata description.
 * @author Hariharan
 */
public class VendingMachine {
    
    private List<Coin> returnTray; 

    public List<Coin> getReturnTray() {
        return returnTray;
    }
    
    public VendingMachine() {
        // Empty return tray initialized
        returnTray = new ArrayList<>();
    }
    
    public String ReadDisplay() {
        return Messages.INSERT_COINS;
    }

    public void InsertCoin(String coinName) {
        returnTray.add(Coin.Penny);
        
    }

    
    public Coin ReadCoinFromName(String coinName) {
        return Coin.Penny;
        
    }

    
    
}
