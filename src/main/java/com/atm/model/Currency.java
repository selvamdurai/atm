package com.atm.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Currency{
    @JsonProperty("Fifties")
    FIFTIES(1,50),
    @JsonProperty("Twenties")
    TWENTIES(2,20),
    @JsonProperty("Tens")
    TENS(3,10),
    @JsonProperty("Fives")
    FIVES(4,5);

    private final int id;
    private final int amount;


    Currency(int id, int amount) {
        this.amount=amount;
        this.id = id;
    }
    @JsonValue
    public static Currency getCurrency(int val){
        return Arrays.stream(Currency.values())
                .filter(c -> c.getId() == val).findFirst()
                .orElseThrow(() -> new IllegalStateException("Unsupported Currency: " + val));
    }


    public int getAmount(){return this.amount;}


    public int getId(){return this.id;}

}
