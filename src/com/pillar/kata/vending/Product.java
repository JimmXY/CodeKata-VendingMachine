package com.pillar.kata.vending;

import java.math.BigDecimal;

/**
 *
 * @author Hariharan
 */
public class Product {

    private String name;
    private BigDecimal unitPrice;
    private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = BigDecimal.valueOf(unitPrice);
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
