package com.pillar.kata.vending;

import com.pillar.kata.vending.exceptions.*;
import java.util.ArrayList;
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
                setDisplay(Messages.CURRENT_FORMAT, insertedCoin.Value());
                addCoinToAmount(insertedCoin);
                break;
            case Dime:
                setDisplay(Messages.CURRENT_FORMAT, insertedCoin.Value());
                addCoinToAmount(insertedCoin);
                break;

            case Quarter:
                break;

            case Unknown: // the default specific case

                break;

        }

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
