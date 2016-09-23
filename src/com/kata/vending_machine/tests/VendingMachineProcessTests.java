package com.kata.vending_machine.tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.kata.vending_machine.Coin;
import com.kata.vending_machine.Product;
import com.kata.vending_machine.VendingMachine;
import com.kata.vending_machine.exceptions.ExactChangeNotAvailableException;
import com.kata.vending_machine.exceptions.UnrecognizedCoinInserted;

public class VendingMachineProcessTests {

	private VendingMachine vendingMachine;

	private List<Product> createDefaultProducts() {
		List<Product> products = new ArrayList<>();
		products.add(new Product("Cola", 1d, 10));
		products.add(new Product("Chips", 0.50d, 10));
		products.add(new Product("Candy", 0.65d, 10));
		return products;
	}

	private List<Coin> createStartingCoins() {
		List<Coin> coins = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			coins.add(Coin.Dime);
		}

		for (int i = 0; i < 10; i++) {
			coins.add(Coin.Nickel);
		}

		for (int i = 0; i < 10; i++) {
			coins.add(Coin.Quarter);
		}

		return coins;
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
		vendingMachine = new VendingMachine(createDefaultProducts(), createStartingCoins());
	}

	@After
	public void tearDown() {
	}

	@Test
	public void whenCandyChipsColaAreBoughtWithDifferentCoinCombosAndThenStateChecked()
			throws UnrecognizedCoinInserted, ExactChangeNotAvailableException {
		List<String> expectedDispenserTray = new ArrayList<>();
		List<Coin> expectedReturnTray = new ArrayList<>();
		// Dispenser tray and return tray ARE NOT cleared

		// Buy candy
		assertEquals("INSERT COIN", vendingMachine.readDisplay());
		vendingMachine.insertCoin("Quarter");
		vendingMachine.insertCoin("Quarter");
		vendingMachine.insertCoin("Dime");
		vendingMachine.insertCoin("Nickel");
		vendingMachine.SelectProduct("3");
		assertEquals("THANK YOU", vendingMachine.readDisplay());
		assertEquals(0.0d, vendingMachine.getCurrentAmount().doubleValue(), 0.0d);
		expectedDispenserTray.add("Candy");
		assertTrue("Dispenser tray contains candy",
				vendingMachine.getDispenserTray().containsAll(expectedDispenserTray));
		assertTrue("Return tray contains nothing", vendingMachine.getReturnTray().containsAll(expectedReturnTray));

		// Buy chips
		assertEquals("INSERT COIN", vendingMachine.readDisplay());
		vendingMachine.insertCoin("Quarter");
		vendingMachine.insertCoin("Dime");
		vendingMachine.insertCoin("Dime");
		vendingMachine.insertCoin("Nickel");
		vendingMachine.SelectProduct("2");
		assertEquals("THANK YOU", vendingMachine.readDisplay());
		assertEquals(0.0d, vendingMachine.getCurrentAmount().doubleValue(), 0.0d);
		expectedDispenserTray.add("Chips");
		assertTrue("Dispenser tray contains candy, chips",
				vendingMachine.getDispenserTray().containsAll(expectedDispenserTray));
		assertTrue("Return tray contains nothing", vendingMachine.getReturnTray().containsAll(expectedReturnTray));

		// Buy Cola
		assertEquals("INSERT COIN", vendingMachine.readDisplay());
		for (int i = 0; i < 10; i++) {
			vendingMachine.insertCoin("Dime");
		}
		vendingMachine.SelectProduct("1");
		assertEquals("THANK YOU", vendingMachine.readDisplay());
		assertEquals(0.0d, vendingMachine.getCurrentAmount().doubleValue(), 0.0d);
		expectedDispenserTray.add("Cola");
		assertTrue("Dispenser tray contains candy, chips, cola",
				vendingMachine.getDispenserTray().containsAll(expectedDispenserTray));
		assertTrue("Return tray contains nothing", vendingMachine.getReturnTray().containsAll(expectedReturnTray));
	}

	@Test
	public void whenChipsCandyColaBoughtWithExtraChangeAndStateChecked()
			throws UnrecognizedCoinInserted, ExactChangeNotAvailableException {
		List<String> dispenserTray = new ArrayList<>();
		List<Coin> returnTray = new ArrayList<>();

		assertEquals("INSERT COIN", vendingMachine.readDisplay());
		vendingMachine.insertCoin("Quarter");
		vendingMachine.insertCoin("Dime");
		vendingMachine.insertCoin("Dime");
		vendingMachine.insertCoin("Dime");
		vendingMachine.SelectProduct("2");
		assertEquals("THANK YOU", vendingMachine.readDisplay());
		dispenserTray.add("Chips");
		assertTrue("Dispenser tray contains chips", vendingMachine.getDispenserTray().containsAll(dispenserTray));
		returnTray.add(Coin.Nickel);
		assertTrue("Return tray contains nickel", vendingMachine.getReturnTray().containsAll(returnTray));

		assertEquals("INSERT COIN", vendingMachine.readDisplay());
		vendingMachine.insertCoin("Quarter");
		vendingMachine.insertCoin("Dime");
		vendingMachine.insertCoin("Dime");
		vendingMachine.insertCoin("Quarter");
		vendingMachine.SelectProduct("3");
		assertEquals("THANK YOU", vendingMachine.readDisplay());
		dispenserTray.add("Candy");
		assertTrue("Dispenser tray contains chips, candy",
				vendingMachine.getDispenserTray().containsAll(dispenserTray));
		returnTray.add(Coin.Nickel);
		assertTrue("Return tray contains 2 nickels", vendingMachine.getReturnTray().containsAll(returnTray));

		assertEquals("INSERT COIN", vendingMachine.readDisplay());
		vendingMachine.insertCoin("Quarter");
		vendingMachine.insertCoin("Quarter");
		vendingMachine.insertCoin("Quarter");
		vendingMachine.insertCoin("Dime");
		vendingMachine.insertCoin("Dime");
		vendingMachine.insertCoin("Dime");
		vendingMachine.SelectProduct("1");
		assertEquals("THANK YOU", vendingMachine.readDisplay());
		dispenserTray.add("Cola");
		assertTrue("Dispenser tray contains chips, candy, cola",
				vendingMachine.getDispenserTray().containsAll(dispenserTray));
		returnTray.add(Coin.Nickel);
		assertTrue("Return tray contains 3 nickels", vendingMachine.getReturnTray().containsAll(returnTray));

	}
	
	@Test
	public void whenPennyIsInsertedItIsReturned() throws UnrecognizedCoinInserted {
		List<Coin> returnTrayCoins = new ArrayList<>();
		// Insert a penny
		assertEquals("INSERT COIN", vendingMachine.readDisplay());
		vendingMachine.insertCoin("penny");
		returnTrayCoins.add(Coin.Penny);
		assertTrue("Return tray contains a penny", vendingMachine.getReturnTray().containsAll(returnTrayCoins));
	}

	@Test
	public void whenMultipleDisplayChecksWhileDispensingColaThenReturnsAndThenInsertRial()
			throws UnrecognizedCoinInserted, ExactChangeNotAvailableException {
		List<String> dispenserItems = new ArrayList<>();
		List<Coin> returnTrayCoins = new ArrayList<>();

		// Get Cola
		assertEquals("INSERT COIN", vendingMachine.readDisplay());
		vendingMachine.insertCoin("Dime");
		assertEquals("CURRENT: $0.10", vendingMachine.readDisplay());
		vendingMachine.insertCoin("Nickel");
		assertEquals("CURRENT: $0.15", vendingMachine.readDisplay());
		vendingMachine.insertCoin("Quarter");
		assertEquals("CURRENT: $0.40", vendingMachine.readDisplay());
		vendingMachine.insertCoin("Quarter");
		assertEquals("CURRENT: $0.65", vendingMachine.readDisplay());
		vendingMachine.insertCoin("Quarter");
		assertEquals("CURRENT: $0.90", vendingMachine.readDisplay());
		vendingMachine.insertCoin("Nickel");
		vendingMachine.SelectProduct("1");

		assertTrue("Dispenser contains nothing", vendingMachine.getDispenserTray().containsAll(dispenserItems));
		assertEquals("PRICE: $1.00", vendingMachine.readDisplay());
		assertEquals("CURRENT: $0.95", vendingMachine.readDisplay());
		vendingMachine.insertCoin("Nickel");
		assertEquals("CURRENT: $1.00", vendingMachine.readDisplay());
		vendingMachine.SelectProduct("1");
		dispenserItems.add("Cola");
		assertTrue("Dispenser contains cola", vendingMachine.getDispenserTray().containsAll(dispenserItems));
		assertEquals("THANK YOU", vendingMachine.readDisplay());

		// Insert some coins and then return
		assertEquals("INSERT COIN", vendingMachine.readDisplay());
		vendingMachine.insertCoin("Dime");
		vendingMachine.insertCoin("Nickel");
		vendingMachine.returnMoney();
		returnTrayCoins.add(Coin.Dime);
		returnTrayCoins.add(Coin.Nickel);
		assertTrue("Return tray contains dime and nickel", vendingMachine.getReturnTray().containsAll(returnTrayCoins));
		assertEquals("INSERT COIN", vendingMachine.readDisplay());

		// Insert a rial
		assertEquals("INSERT COIN", vendingMachine.readDisplay());
		thrown.expect(UnrecognizedCoinInserted.class);
		thrown.expectMessage("The coin Rial is not a recognized coin");
		vendingMachine.insertCoin("Rial");
	}

	@Test
	public void whenExactChangeRequiredAndSoldOut() throws UnrecognizedCoinInserted{
		assertEquals("INSERT COIN", vendingMachine.readDisplay());
		List<String> dispenserItems = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			vendingMachine.insertCoin("Quarter");
			vendingMachine.insertCoin("Quarter");
			vendingMachine.insertCoin("Quarter");
			vendingMachine.insertCoin("Quarter");
			vendingMachine.SelectProduct("3");
			dispenserItems.add("Candy");
			assertTrue("Dispenser contains " + (i+1) + " candies", vendingMachine.getDispenserTray().containsAll(dispenserItems));
			assertEquals("THANK YOU", vendingMachine.readDisplay());
		}
		
		assertEquals("EXACT CHANGE ONLY", vendingMachine.readDisplay());
		vendingMachine.SelectProduct("3");
		assertEquals("SOLD OUT", vendingMachine.readDisplay());
		
	}
	

}
