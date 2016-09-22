package com.pillar.kata.vending.tests;

import com.pillar.kata.vending.Coin;
import com.pillar.kata.vending.VendingMachine;
import com.pillar.kata.vending.exceptions.UnrecognizedCoinInserted;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the VendingMachine.
 * @author Hariharan
 */
public class VendingMachineBasicTests {
    
    public VendingMachineBasicTests() {
    }
    
    VendingMachine vendingMachine;    
    
    @Before
    public void setUp() {
        vendingMachine = new VendingMachine();
    }
    
    @After
    public void tearDown() {
        vendingMachine = null;// remove the reference for the vending machine to reset.
        // TODO: add any finalizing code required later
    }
    
    @Test
    public void whenDisplayReadAtStartReturnsInsertCoinMessage() {
        assertEquals("INSERT COIN", vendingMachine.readDisplay());
    }

    @Test
    public void whenAPennyIsInsertedAndDisplayReadReturnsInsertCoinMessageAndPutsOnePennyInReturnTray() throws UnrecognizedCoinInserted {
        vendingMachine.insertCoin("Penny");
        assertEquals("INSERT COIN", vendingMachine.readDisplay());
        assertTrue("Not exactly one coin in return tray", vendingMachine.getReturnTray().size() == 1);
        assertTrue("Penny not found in return tray", vendingMachine.getReturnTray().contains(Coin.Penny));        
    }
    
    @Test
    public void whenANickelIsInsertedAndDisplayReadReturnsNickelValueMessageAndContainsNickelValueInCurrentAmount() throws UnrecognizedCoinInserted {
        vendingMachine.insertCoin("Nickel");
        assertEquals("CURRENT: $0.05", vendingMachine.readDisplay());
        assertEquals(0.05f, vendingMachine.getCurrentAmount(),0f);        
    }
    
    @Test
    public void whenDimeInsertedAndDisplayReadReturnsDimeValueAndContainsDimeValueInCurrentAmount() throws UnrecognizedCoinInserted {
        vendingMachine.insertCoin("Dime");
        assertEquals("CURRENT: $0.10", vendingMachine.readDisplay());
        
    }
}
