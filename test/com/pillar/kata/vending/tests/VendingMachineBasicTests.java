package com.pillar.kata.vending.tests;

import com.pillar.kata.vending.Coin;
import com.pillar.kata.vending.VendingMachine;
import com.pillar.kata.vending.exceptions.UnrecognizedCoinInserted;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the VendingMachine.
 *
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
        assertEquals(BigDecimal.valueOf(0.05), vendingMachine.getCurrentAmount());
    }

    @Test
    public void whenDimeInsertedAndDisplayReadReturnsDimeValueAndContainsDimeValueInCurrentAmount() throws UnrecognizedCoinInserted {
        vendingMachine.insertCoin("Dime");
        assertEquals("CURRENT: $0.10", vendingMachine.readDisplay());
        assertEquals(0.10d, vendingMachine.getCurrentAmount().doubleValue(), 0d);
    }

    @Test
    public void whenQuarterInsertedAndDisplayReadReturnsQuarterValueAndContainsQuarterValueInCurrentAmount() throws UnrecognizedCoinInserted {
        vendingMachine.insertCoin("Quarter");
        assertEquals("CURRENT: $0.25", vendingMachine.readDisplay());
        assertEquals(0.25d, vendingMachine.getCurrentAmount().doubleValue(), 0d);
    }

    @Test
    public void whenTwoQuartersInsertedAndDisplayReadReturnsHalfDollarValueAndHasHalfDollarValueInCurrentAmount() throws UnrecognizedCoinInserted {
        vendingMachine.insertCoin("Quarter");
        vendingMachine.insertCoin("Quarter");
        assertEquals("CURRENT: $0.50", vendingMachine.readDisplay());
        assertEquals(0.50d, vendingMachine.getCurrentAmount().doubleValue(), 0d);
    }

    @Test
    public void whenQuarterDimeNickelInsertedAndDisplayReadAfterEachReturnsCorrespondingValueAndUpdatesCurrentAmountAccordingly() throws UnrecognizedCoinInserted {
        vendingMachine.insertCoin("Quarter");
        assertEquals("CURRENT: $0.25", vendingMachine.readDisplay());
        assertEquals(0.25d, vendingMachine.getCurrentAmount().doubleValue(), 0d);

        vendingMachine.insertCoin("Dime");
        assertEquals("CURRENT: $0.35", vendingMachine.readDisplay());
        assertEquals(0.35d, vendingMachine.getCurrentAmount().doubleValue(), 0d);

        vendingMachine.insertCoin("Nickel");
        assertEquals("CURRENT: $0.40", vendingMachine.readDisplay());
        assertEquals(0.4d, vendingMachine.getCurrentAmount().doubleValue(), 0.0d);

    }

    @Test(expected = UnrecognizedCoinInserted.class)
    public void whenHalfDollarInsertedExpectUnrecognizedCoinException() throws UnrecognizedCoinInserted {
        vendingMachine.insertCoin("Half Dollar");
    }

    @Test
    public void whenProduct1IsSelectedWithoutAnyMoneyAndDisplayReadReturnsPriceOfProduct1() {
        vendingMachine.SelectProduct("1");
        assertEquals("PRICE: $1.00", vendingMachine.readDisplay());
    }

    @Test
    public void whenProduct2IsSelectedWithoutAnyMoneyAndDisplayReadReturnsPriceOfProduct2() {
        vendingMachine.SelectProduct("2");
        assertEquals("PRICE: $0.50", vendingMachine.readDisplay());
    }

    @Test
    public void whenProduct3IsSelectedWithoutAnyMoneyAndDisplayReadReturnsPriceOfProduct3() {
        vendingMachine.SelectProduct("3");
        assertEquals("PRICE: $0.65", vendingMachine.readDisplay());
    }

    @Test
    public void whenFourQuartersInsertedAndProduct1SelectedAndDisplayReadReturnsThankYouAndProduct1AddedToDispenerTray() throws UnrecognizedCoinInserted {
        vendingMachine.insertCoin("Quarter");
        vendingMachine.insertCoin("Quarter");
        vendingMachine.insertCoin("Quarter");
        vendingMachine.insertCoin("Quarter");
        vendingMachine.SelectProduct("1");
        assertEquals("THANK YOU", vendingMachine.readDisplay());
        assertTrue(vendingMachine.getDispenserTray().contains("Cola"));
    }

    @Test
    public void whenTwoQuartersInsertedAndProduct2SelectedAndDisplayReadReturnsThankYouAndProduct2AddedToDispenerTray() throws UnrecognizedCoinInserted {
        vendingMachine.insertCoin("Quarter");
        vendingMachine.insertCoin("Quarter");
        vendingMachine.SelectProduct("2");
        assertEquals("THANK YOU", vendingMachine.readDisplay());
        assertTrue(vendingMachine.getDispenserTray().contains("Chips"));
    }

    @Test
    public void whenTwoQuartersAndOneDimeAndOneNickelInsertedAndProduct3SelectedAndDisplayReadReturnsThankYouAndProduct3AddedToDispenerTray() throws UnrecognizedCoinInserted {
        vendingMachine.insertCoin("Quarter");
        vendingMachine.insertCoin("Quarter");
        vendingMachine.insertCoin("Dime");
        vendingMachine.insertCoin("Nickel");
        vendingMachine.SelectProduct("3");
        assertEquals("THANK YOU", vendingMachine.readDisplay());
        assertTrue(vendingMachine.getDispenserTray().contains("Candy"));
    }

    @Test
    public void whenProduct1SuccesfullyDispensedAndDisplayReadAgainReturnsInsertCoinsWithZeroBalance() throws UnrecognizedCoinInserted {
        //whenFourQuartersInsertedAndProduct1SelectedAndDisplayReadReturnsThankYouAndProduct1AddedToDispenerTray();
        vendingMachine.insertCoin("Quarter");
        vendingMachine.insertCoin("Quarter");
        vendingMachine.insertCoin("Quarter");
        vendingMachine.insertCoin("Quarter");
        vendingMachine.SelectProduct("1");
        assertEquals("THANK YOU", vendingMachine.readDisplay());
        assertTrue(vendingMachine.getDispenserTray().contains("Cola"));
        assertEquals("INSERT COIN", vendingMachine.readDisplay());
        assertEquals(0.0d, vendingMachine.getCurrentAmount().doubleValue(), 0d);
    }

    @Test
    public void whenTwoQuartersInsertedAndProductColaSelectedAndDisplayReadTwiceReturnsFiftyCentsAmountDisplay() throws UnrecognizedCoinInserted {
        vendingMachine.insertCoin("Quarter");
        vendingMachine.insertCoin("Quarter");
        vendingMachine.SelectProduct("1");
        assertEquals("PRICE: $1.00", vendingMachine.readDisplay());
        assertEquals("CURRENT: $0.50", vendingMachine.readDisplay());
        assertEquals(0.50d, vendingMachine.getCurrentAmount().doubleValue(), 0d);
    }

    @Test
    public void whenFiveQuartersInsertedAndColaSelectedAndDispensedOneQuarterMustBeInReturnTrayAndColaInDispenserTray() throws UnrecognizedCoinInserted {
        vendingMachine.insertCoin("Quarter");
        vendingMachine.insertCoin("Quarter");
        vendingMachine.insertCoin("Quarter");
        vendingMachine.insertCoin("Quarter");
        vendingMachine.insertCoin("Quarter");
        vendingMachine.SelectProduct("1");
        assertTrue("Cola not in dispenser tray", vendingMachine.getDispenserTray().contains("Cola"));
        assertTrue("Quarter not in return tray", vendingMachine.getReturnTray().contains(Coin.Quarter));
    }

    @Test
    public void whenTwoQuartersInsertedAndReturnMoneyPressedTwoQuartersMustBeInTheReturnTrayAndInsertCoinMustBeDisplayed() throws UnrecognizedCoinInserted {
        vendingMachine.insertCoin("Quarter");
        vendingMachine.insertCoin("Quarter");
        vendingMachine.returnMoney();
        List<Coin> twoQuarters = new ArrayList<>();
        twoQuarters.add(Coin.Quarter);
        twoQuarters.add(Coin.Quarter);
        assertTrue("Two quarters not in return tray", vendingMachine.getReturnTray().containsAll(twoQuarters));
        assertEquals("INSERT COIN", vendingMachine.readDisplay());
    }

    @Test
    public void whenTenColasDispensedAndFourQuartersInsertedAndColaSelectedMustDisplaySoldOutAndThenAmountOfOneDollar() throws UnrecognizedCoinInserted {

        for (int i = 0; i < 10; i++) {
            vendingMachine.insertCoin("Quarter");
            vendingMachine.insertCoin("Quarter");
            vendingMachine.insertCoin("Quarter");
            vendingMachine.insertCoin("Quarter");
            vendingMachine.SelectProduct("1");
        }

        vendingMachine.insertCoin("Quarter");
        vendingMachine.insertCoin("Quarter");
        vendingMachine.insertCoin("Quarter");
        vendingMachine.insertCoin("Quarter");
        vendingMachine.SelectProduct("1");
        assertEquals("SOLD OUT", vendingMachine.readDisplay());
        assertEquals(1.00d, vendingMachine.getCurrentAmount().doubleValue(), 0d);
        assertEquals("CURRENT: $1.00", vendingMachine.readDisplay());
    }
    
    @Test
    public void whenTenColasDispensed11thColaSelectedMustDisplaySoldOutAndInsertCoin() throws UnrecognizedCoinInserted {

        for (int i = 0; i < 10; i++) {
            vendingMachine.insertCoin("Quarter");
            vendingMachine.insertCoin("Quarter");
            vendingMachine.insertCoin("Quarter");
            vendingMachine.insertCoin("Quarter");
            vendingMachine.SelectProduct("1");
        }

        vendingMachine.SelectProduct("1");
        assertEquals("SOLD OUT", vendingMachine.readDisplay());
        assertEquals(0.00d, vendingMachine.getCurrentAmount().doubleValue(), 0d);
        assertEquals("INSERT COIN", vendingMachine.readDisplay());
    }
}
