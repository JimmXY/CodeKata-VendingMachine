package com.kata.vending_machine.exceptions;

/**
 *
 * @author Hariharan
 */
public class ExactChangeNotAvailableException extends Exception {

	private static final long serialVersionUID = -5582575050578181099L;

	public ExactChangeNotAvailableException(String message) {
        super(message);
    }
    
}
