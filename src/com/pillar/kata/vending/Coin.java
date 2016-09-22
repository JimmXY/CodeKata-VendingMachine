/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pillar.kata.vending;

/**
 *
 * @author Hariharan
 */
public enum Coin {
    
    Penny(0.01),
    Nickel(0.05),
    Dime(0.10),
    Quarter(0.25), 
    Unknown(0.00);
    
    private final double value;  
    
    Coin(double value) {
        this.value = value;
    }
    public double Value() { return value; }
}
