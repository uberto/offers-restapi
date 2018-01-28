package com.gamasoft.offers.model;

import org.junit.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class OfferTest {


    @Test
    public void expireAfterValidityPassed() throws InterruptedException {

        Offer offer = new Offer("very good stuff", "GBP", 123.45, Duration.ofMillis(100));

        assertThat(offer.isExpired()).isFalse();

        Thread.sleep(100);

        assertThat(offer.isExpired()).isTrue();

    }



}