package com.gamasoft.offers.rest;

import com.gamasoft.offers.model.Offer;
import org.junit.Test;

import static com.gamasoft.offers.model.OfferTest.createOffer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class JsonTest {

    @Test
    public void serializeToAndFromJson(){
        Offer offer = createOffer("SPECIAL");
        String json = Json.offerToJson(offer);

//        System.out.println(json);

        Offer newOffer = Json.offerFromJson(json);
        assertThat(newOffer).isEqualTo(offer);
    }

}