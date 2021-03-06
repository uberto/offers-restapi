package com.gamasoft.offers.rest;

import com.gamasoft.offers.model.Offer;
import com.gamasoft.offers.model.RespInfo;
import com.google.gson.Gson;

import java.util.List;
import java.util.stream.Collectors;

public class Json {

    private static Gson gson = new Gson();

    public static String respInfoToJson(RespInfo respInfo) {
        return gson.toJson(respInfo);
    }

    public static Offer offerFromJson(String json) {
        return gson.fromJson(json, Offer.class);
    }

    public static String offerToJson(Offer offer) {
        return gson.toJson(new OfferHateoas( offer));
    }

    public static String offersToJson(List<Offer> offers) {
        return gson.toJson(offers.stream().map(OfferHateoas::new).collect(Collectors.toList()));
    }
}
