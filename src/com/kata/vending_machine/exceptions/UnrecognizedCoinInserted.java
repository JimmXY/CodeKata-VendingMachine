package com.kata.vending_machine.exceptions;

/**
 *
 * @author Hariharan
 */
public class UnrecognizedCoinInserted extends Exception {

    private String coinName;
    public UnrecognizedCoinInserted(String coinName) {
        this.coinName = coinName;
    }

    @Override
    public String getMessage() {
        return String.format("The coin %s is not a recognized coin", coinName);
    }
    
    
    
}
