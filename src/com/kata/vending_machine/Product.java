package com.kata.vending_machine;

import java.math.BigDecimal;

/**
 *
 * @author Hariharan
 */
public class Product {
    private final String name;
    private final BigDecimal unitPrice;
    private int quantity;

    public String getName() {
        return name;
    }

   
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
 
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product(String name, double unitPrice, int quantity) {
        this.name = name;
        this.unitPrice = BigDecimal.valueOf(unitPrice);
        this.unitPrice.setScale(2);
        this.quantity = quantity;
    }
}
