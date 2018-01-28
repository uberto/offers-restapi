package com.gamasoft.offers.model;

import com.google.gson.Gson;
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

    @Test
    public void fromJson(){
        Gson gson = new Gson();
        Offer offer = createOffer("SPECIAL");
        String json = gson.toJson(offer);

        System.out.println(json);

        Offer newOffer = gson.fromJson(json, Offer.class );
        assertThat(newOffer).isEqualTo(offer);
    }

    public static Offer createOffer(String name) {
        return new Offer(name,"very good stuff", "GBP", 123.45, Duration.ofDays(7));
    }



}