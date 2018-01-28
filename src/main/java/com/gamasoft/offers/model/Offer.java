package com.gamasoft.offers.model;

import com.google.gson.Gson;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

public class Offer{

    //Immutable entity that represent offers to customers
    public final String name;
    public final String description;
    public final String currency;
    public final Double price;
    public final Duration validity;
    public final Instant creationDate;
    public final Boolean isCancelled;

    private Offer(String name, String description, String currency, Double price, Duration validity,
                 Instant creationDate, Boolean isCancelled) {
        this.name = name;
        this.description = description;
        this.currency = currency;
        this.price = price;
        this.validity = validity;
        this.creationDate = creationDate;
        this.isCancelled = isCancelled;
    }

    public Offer(String name, String description, String currency, Double price, Duration validity) {
        this(name, description, currency, price, validity, Instant.now(), false);
    }

    public Boolean isExpired() {
        Instant expiry = creationDate.plus(validity);
        return expiry.isBefore(Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Objects.equals(name, offer.name) &&
                Objects.equals(description, offer.description) &&
                Objects.equals(currency, offer.currency) &&
                Objects.equals(price, offer.price) &&
                Objects.equals(validity, offer.validity) &&
                Objects.equals(creationDate, offer.creationDate) &&
                Objects.equals(isCancelled, offer.isCancelled);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, description, currency, price, validity, creationDate, isCancelled);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", currency='" + currency + '\'' +
                ", price=" + price +
                ", validity=" + validity +
                ", creationDate=" + creationDate +
                ", isCancelled=" + isCancelled +
                '}';
    }

    public Offer withValidity(Duration validity) {
        return new Offer(this.name, this.description, this.currency, this.price, validity, this.creationDate, this.isCancelled);
    }

    public Offer cancel() {
        return new Offer(this.name, this.description, this.currency, this.price, this.validity, this.creationDate, true);
    }

}