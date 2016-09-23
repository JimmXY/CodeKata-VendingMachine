package com.kata.vending_machine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kata.vending_machine.exceptions.*;

/**
 * The {@code VendingMachine} class emulates the actions of the vending machine
 * as per the kata description.
 *
 * @author Hariharan
 */
public class VendingMachine {

	private List<Coin> returnTray;
	private BigDecimal currentAmountInserted;
	private HashMap<String, Product> mappedProducts;
	private List<String> dispenserTray;
	private HashMap<Coin, Integer> coinCounts;
	
	public String peekDisplayText() {
		return currentDisplay;
	}
	
 
	public HashMap<String, Product> getMappedProducts() {
		return mappedProducts;
	}


	/**
	 * Reads the Return Tray of coins for the vending machine
	 *
	 * @return the list of coins in the return tray of the vending machine
	 */
	public List<Coin> getReturnTray() {
		return returnTray;
	}

	public VendingMachine(List<Product> products, List<Coin> startingCoins) {
		resetVendingMachine();
		loadProducts(products);
		loadCoins(startingCoins);
	}

	/**
	 * Loads the vending machine with these products
	 *
	 * @param products
	 */
	private void loadProducts(List<Product> products) {
		for (int productIndex = 0; productIndex < products.size(); productIndex++) {
			mappedProducts.put("" + (productIndex + 1), products.get(productIndex));
		}
	}

	public List<String> getDispenserTray() {
		return dispenserTray;
	}

	/**
	 * Resets the vending machine to a pristine state
	 */
	private void resetVendingMachine() {
		// Empty return tray initialized
		returnTray = new ArrayList<>();
		// initialize current amount
		currentAmountInserted = BigDecimal.ZERO;
		currentAmountInserted.setScale(2);
		// initialize display
		setDisplay(Messages.INSERT_COIN);
		// initialize the product listing
		mappedProducts = new HashMap<>();
		// clear dispenser tray
		dispenserTray = new ArrayList<>();
		// clear coin counter
		coinCounts = new HashMap<>();
	}

	private String currentDisplay;

	/**
	 * Formats and sets the current display for the vending machine
	 *
	 * @param messageFormat
	 *            The formatting string of the message
	 * @param args
	 *            The arguments for substitution in the formatting
	 */
	private void setDisplay(String messageFormat, Object... args) {
		currentDisplay = String.format(messageFormat, args);
	}

	/**
	 * Adds a coin to the current amount held by the vending machine
	 *
	 * @param coin
	 *            The coin to be added to the current amount
	 */
	private void addCoinToAmount(Coin coin) {
		currentAmountInserted = currentAmountInserted.add(coin.Value());
	}

	/**
	 * Reads the current display of the vending machine
	 *
	 * @return The current message displayed by the vending machine
	 */
	public String readDisplay() {
		String currentMessage = currentDisplay;
		resetDisplay(); // resets display after each read
		return currentMessage;
	}

	private void resetDisplay() {
		// if coins in machine, display amount
		if (currentAmountInserted.doubleValue() > 0) {
			setDisplay(Messages.CURRENT_FORMAT, currentAmountInserted.doubleValue());
		} else if (coinCounts.get(Coin.Nickel) == 0 || coinCounts.get(Coin.Dime) == 0) {
			setDisplay(Messages.INSERT_COIN_EXACT);
		} else {
			// else display insert coin message
			setDisplay(Messages.INSERT_COIN);
		}
	}

	/**
	 * Handles a coin name being inserted into the vending machine
	 *
	 * @param coinName
	 *            The name of the coin being inserted
	 * @throws UnrecognizedCoinInserted
	 */
	public void insertCoin(String coinName) throws UnrecognizedCoinInserted {
		// Parse coin from the coin name
		Coin insertedCoin = readCoinFromName(coinName);
		switch (insertedCoin) {
		case Penny:
			// message changed to insert coin
			setDisplay(Messages.INSERT_COIN);
			// pennies are added to the return tray
			returnTray.add(insertedCoin);
			break;
		case Nickel:
			addCoinToAmount(insertedCoin);
			setDisplay(Messages.CURRENT_FORMAT, getCurrentAmount());
			coinCounts.replace(insertedCoin, coinCounts.get(insertedCoin) + 1);
			break;
		case Dime:
			addCoinToAmount(insertedCoin);
			setDisplay(Messages.CURRENT_FORMAT, getCurrentAmount());
			coinCounts.replace(insertedCoin, coinCounts.get(insertedCoin) + 1);
			break;

		case Quarter:
			addCoinToAmount(insertedCoin);
			setDisplay(Messages.CURRENT_FORMAT, getCurrentAmount());
			coinCounts.replace(insertedCoin, coinCounts.get(insertedCoin) + 1);
			break;

		case Unknown: // the default specific case
			throw new UnrecognizedCoinInserted(coinName);

		}

	}

	/**
	 * Select a product in the vending machine
	 *
	 * @param productNumber
	 *            The number of the product
	 */
	public void SelectProduct(String productNumber) {

		// read the product from the listing
		Product selectedProduct = mappedProducts.get(productNumber);

		// if item is sold out, show sold out message
		if (selectedProduct.getQuantity() == 0) {
			setDisplay(Messages.SOLD_OUT);
			return;
		}

		// check if enough money in machine
		if (currentAmountInserted.doubleValue() >= selectedProduct.getUnitPrice().doubleValue()) {
			// dispense the product
			dispenserTray.add(selectedProduct.getName());
			// display thanks
			setDisplay(Messages.THANK_YOU);
			// Dispense the change if needed
			BigDecimal changeAmount = currentAmountInserted.subtract(selectedProduct.getUnitPrice());
			// Convert the change to coins
			List<Coin> change = getChangeForAmount(changeAmount);
			// add coins to return tray
			returnTray.addAll(change);
			// remove coins from coins
			for (Coin coin : change) {
				coinCounts.replace(coin, coinCounts.get(coin) - 1);
			}

			// set amount in machine to 0
			currentAmountInserted = BigDecimal.ZERO;

			// remove item from inventory
			selectedProduct.setQuantity(selectedProduct.getQuantity() - 1);
		} else {
			// show the price
			setDisplay(Messages.PRICE_FORMAT, selectedProduct.getUnitPrice());
		}
	}

	/**
	 * Reads the current amount inserted into the vending machine
	 *
	 * @return The current amount inserted in the vending machine
	 */
	public BigDecimal getCurrentAmount() {
		return currentAmountInserted;
	}

	/**
	 * Converts a coin name into a {@link Coin}
	 *
	 * @param coinName
	 *            The name of the coin to be converted
	 * @return The particular {@link Coin} the coin name corresponds to
	 */
	private Coin readCoinFromName(String coinName) {
		// keeping all coin reading within vending machine logic
		if (coinName.equalsIgnoreCase("penny")) {
			return Coin.Penny;
		} else if (coinName.equalsIgnoreCase("dime")) {
			return Coin.Dime;
		} else if (coinName.equalsIgnoreCase("nickel")) {
			return Coin.Nickel;
		} else if (coinName.equalsIgnoreCase("quarter")) {
			return Coin.Quarter;
		}
		return Coin.Unknown;

	}

	public void returnMoney() {

		// change the current amount into coins and put into return tray
		List<Coin> returnCoins = getChangeForAmount(currentAmountInserted);
		returnTray.addAll(returnCoins);
		for (Coin returnCoin : returnCoins) {
			coinCounts.replace(returnCoin, coinCounts.get(returnCoin) - 1);
		}
		// reset the current amount and display
		currentAmountInserted = BigDecimal.ZERO;
		setDisplay(Messages.INSERT_COIN);
	}
	
	public void clearDispenserTray() {
		dispenserTray.clear();
	}
	
	public void clearReturnTray() {
		returnTray.clear();
	}

	/**
	 * Get the change in coins for the given amount
	 *
	 * @param changeAmount
	 * @return
	 */
	private List<Coin> getChangeForAmount(BigDecimal changeAmount) {
		List<Coin> change = new ArrayList<>();

		while (changeAmount.compareTo(BigDecimal.ZERO) > 0) {
			Coin coin = Coin.Unknown;
			if (changeAmount.doubleValue() >= 0.25 && coinCounts.get(Coin.Quarter) > 0) {
				coin = Coin.Quarter;
			} else if (changeAmount.doubleValue() >= 0.10 && coinCounts.get(Coin.Dime) > 0) {
				coin = Coin.Dime;
			} else if (changeAmount.doubleValue() >= 0.05 && coinCounts.get(Coin.Nickel) > 0) {
				coin = Coin.Nickel;
			}
			change.add(coin);
			changeAmount = changeAmount.subtract(coin.Value());
		}

		return change;
	}

	private void loadCoins(List<Coin> coins) {
		for (Coin coin : coins) {
			if (coinCounts.containsKey(coin)) {
				coinCounts.replace(coin, coinCounts.get(coin) + 1);
			} else {
				coinCounts.put(coin, 1);
			}
		}

	}

}
