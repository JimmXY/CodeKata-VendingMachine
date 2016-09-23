/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pillar.kata.vending;

import java.math.BigDecimal;

/**
 *
 * @author Hariharan
 */
public enum Coin {

    Penny(0.01d),
    Nickel(0.05d),
    Dime(0.10d),
    Quarter(0.25d),
    Unknown(0.00d);

    private final BigDecimal value;

    Coin(double value) {
        this.value = BigDecimal.valueOf(value);
        this.value.setScale(2);
    }

    public BigDecimal Value() {
        return value;
    }
}
