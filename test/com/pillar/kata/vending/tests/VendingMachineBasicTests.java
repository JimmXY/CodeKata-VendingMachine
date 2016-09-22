package com.pillar.kata.vending.tests;

import com.pillar.kata.vending.Coin;
import com.pillar.kata.vending.VendingMachine;
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
        assertEquals("INSERT COIN", vendingMachine.ReadDisplay());
    }

    @Test
    public void whenAPennyIsInsertedAndDisplayReadReturnsInsertCoinMessageAndPutsOnePennyInReturnTray() {
        vendingMachine.InsertCoin("Penny");
        assertEquals("INSERT COIN", vendingMachine.ReadDisplay());
        assertTrue("Not exactly one coin in return tray", vendingMachine.getReturnTray().size() == 1);
        assertTrue("Penny not found in return tray", vendingMachine.getReturnTray().contains(Coin.Penny));        
    }
    
    
}
