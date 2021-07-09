package com.atm.model;

import java.util.Arrays;

public enum Currency{
    FIFTIES(1,50),
    TWENTIES(2,20),
    TENS(3,10),
    FIVES(4,5);

    private final int id;
    private final int amount;


    Currency(int id, int amount) {
        this.amount=amount;
        this.id = id;
    }

    public static Currency getCurrency(int val){
        return Arrays.stream(Currency.values())
                .filter(c -> c.getId() == val).findFirst()
                .orElseThrow(() -> new IllegalStateException("Unsupported Currency: " + val));
    }
    public int getAmount(){return this.amount;}
    public int getId(){return this.id;}

}
