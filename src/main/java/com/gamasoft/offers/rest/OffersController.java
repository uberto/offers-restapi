package com.gamasoft.offers.rest;

import com.gamasoft.offers.model.Offer;
import com.gamasoft.offers.model.OffersRepository;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class OffersController {

    private OffersRepository offers;

    public OffersController(OffersRepository offers) {

        this.offers = offers;
    }

    public String createOffer(Request request, Response response) {

        Gson gson = new Gson();
        Offer offer = gson.fromJson(request.body(), Offer.class );

        offers.add(offer);

        response.status(201);
        return "created! " + Paths.replaceId(Paths.SINGLE_OFFER, offer.name);
    }

    public String showAll(Request request, Response response) {
        return null;
    }

    public String showSingle(Request request, Response response) {
        String name = request.params("name");

        Offer offer = offers.get(name);

        return offer.toString();
    }


}
