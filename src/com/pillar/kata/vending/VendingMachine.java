package com.pillar.kata.vending;

import com.pillar.kata.vending.exceptions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The {@code VendingMachine} class emulates the actions of the vending machine
 * as per the kata description.
 *
 * @author Hariharan
 */
public class VendingMachine {

    private List<Coin> returnTray;
    private float currentAmountInserted;
    private HashMap<String, Product> mappedProducts;

    /**
     * Reads the Return Tray of coins for the vending machine
     *
     * @return the list of coins in the return tray of the vending machine
     */
    public List<Coin> getReturnTray() {
        return returnTray;
    }

    public VendingMachine() {
        resetVendingMachine();
        // load the default products with 10 of each
        loadProducts(createDefaultProducts());
    }
    
    private List<Product> createDefaultProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Cola", 1f, 10));
        products.add(new Product("Chips", 0.50f, 10));
        products.add(new Product("Candy", 0.65f, 10));        
        return products;
    }
    /**
     * Loads the vending machine with these products
     * @param products 
     */
    public void loadProducts(List<Product> products) {
        for (int productIndex = 0; productIndex < products.size(); productIndex++) {
            mappedProducts.put(""+(productIndex+1), products.get(productIndex));
        }
    }
    /**
     * Resets the vending machine to a pristine state
     */
    private void resetVendingMachine() {
        // Empty return tray initialized
        returnTray = new ArrayList<>();
        // initialize current amount
        currentAmountInserted = 0f;
        // initialize display
        setDisplay(Messages.INSERT_COIN);
        // initialize the product listing
        mappedProducts = new HashMap<>();
    }

    private String currentDisplay;

    /**
     * Formats and sets the current display for the vending machine
     *
     * @param messageFormat The formatting string of the message
     * @param args The arguments for substitution in the formatting
     */
    private void setDisplay(String messageFormat, Object... args) {
        currentDisplay = String.format(messageFormat, args);
    }

    /**
     * Adds a coin to the current amount held by the vending machine
     *
     * @param coin The coin to be added to the current amount
     */
    private void addCoinToAmount(Coin coin) {
        currentAmountInserted += coin.Value();
    }

    /**
     * Reads the current display of the vending machine
     *
     * @return The current message displayed by the vending machine
     */
    public String readDisplay() {
        return currentDisplay;
    }

    /**
     * Handles a coin name being inserted into the vending machine
     *
     * @param coinName The name of the coin being inserted
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
                break;
            case Dime:
                addCoinToAmount(insertedCoin);
                setDisplay(Messages.CURRENT_FORMAT, getCurrentAmount());
                break;

            case Quarter:
                addCoinToAmount(insertedCoin);
                setDisplay(Messages.CURRENT_FORMAT, getCurrentAmount());
                break;

            case Unknown: // the default specific case
                throw new UnrecognizedCoinInserted(coinName);

        }

    }
    
    /**
     * Select a product in the vending machine
     * @param productNumber The number of the product
     */
    public void SelectProduct(String productNumber) {
        // read the product from the listing 
        // show the price        
        setDisplay(Messages.PRICE_FORMAT, mappedProducts.get(productNumber).getUnitPrice());
    }

    /**
     * Reads the current amount inserted into the vending machine
     *
     * @return The current amount inserted in the vending machine
     */
    public float getCurrentAmount() {
        return currentAmountInserted;
    }

    /**
     * Converts a coin name into a {@link Coin}
     *
     * @param coinName The name of the coin to be converted
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

}
