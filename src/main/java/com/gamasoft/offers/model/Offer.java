package com.gamasoft.offers.model;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;

public class Offer {

    final long id;
    final String description;
    final String currency;
    final Double price;
    final Duration validity;
    final Date creationDate;
    final Boolean isCancelled;

    public Offer(String description, String currency, Double price, Duration validity) {
        this.description = description;
        this.currency = currency;
        this.price = price;
        this.validity = validity;
        id = -1;
        creationDate = new Date();
        isCancelled = false;
    }

    public Boolean isExpired(){
        Instant expiry = creationDate.toInstant().plus(validity);
        return expiry.isBefore(Instant.now());
    }

}
