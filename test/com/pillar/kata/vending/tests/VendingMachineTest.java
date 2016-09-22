package com.pillar.kata.vending.tests;

import com.pillar.kata.vending.VendingMachine;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the VendingMachine.
 * @author Hariharan
 */
public class VendingMachineTest {
    
    public VendingMachineTest() {
    }
    
    VendingMachine vendingMachine;    
    
    @Before
    public void setUp() {
        vendingMachine = new VendingMachine();
    }
    
    @After
    public void tearDown() {
        vendingMachine = null;// remove the reference for the vending machine for garbage collection.
        // TODO: add any finalizing code required later
    }
    
    @Test
    public void whenDisplayReadAtStartReturnsInsertCoinMessage() {
        assertEquals("INSERT COIN", vendingMachine.ReadDisplay());
    }

    
    
}
