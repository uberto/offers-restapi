package com.gamasoft.offers.model;

import org.junit.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class OfferTest {


    @Test
    public void expireAfterValidityPassed() throws InterruptedException {

        Offer offer = createOffer("OFF1").withValidity(Duration.ofMillis(50));

        assertThat(offer.isExpired()).isFalse();

        Thread.sleep(100);

        assertThat(offer.isExpired()).isTrue();

    }



    public static Offer createOffer(String name) {
        return new Offer(name,"very good stuff", "GBP", 123.45, Duration.ofDays(7));
    }



}