package com.gamasoft.offers.model;

import java.time.Instant;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;

public class Offer {

    final long id;
    final String description;
    final String currency;
    final Double price;
    final int validityInDays;
    final Date creationDate;

    public Offer(String description, String currency, Double price, int validityInDays) {
        this.description = description;
        this.currency = currency;
        this.price = price;
        this.validityInDays = validityInDays;
        id = -1;
        creationDate = new Date();
    }

    public Boolean isExpired(){
        Instant expiry = creationDate.toInstant().plus(validityInDays, DAYS);
        return expiry.isBefore(Instant.now());
    }

}
