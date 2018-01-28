package com.gamasoft.offers.rest;

import com.gamasoft.offers.model.Offer;
import com.gamasoft.offers.model.OffersRepository;
import com.gamasoft.offers.model.RespInfo;
import spark.Request;
import spark.Response;

public class OffersController {

    private OffersRepository offers;

    public OffersController(OffersRepository offers) {

        this.offers = offers;
    }

    public String createOffer(Request request, Response response) {

        Offer offer = Json.offerFromJson(request.body());

        offers.add(offer);

        response.status(201);

        RespInfo res = new RespInfo("Successfully created", 201, Paths.replaceId(Paths.SINGLE_OFFER, offer.name));

        return Json.respInfoToJson(res);
    }


    public String showAll(Request request, Response response) {
        return Json.offersToJson( offers.getAll());
    }

    public String showSingle(Request request, Response response) {
        String name = request.params("name");

        Offer offer = offers.get(name);

        if (offer == null)
            return null; //404

        return Json.offerToJson(offer);
    }


    public String edit(Request request, Response response) {
        return null;
    }

    public String cancel(Request request, Response response) {
        return null;
    }
}
