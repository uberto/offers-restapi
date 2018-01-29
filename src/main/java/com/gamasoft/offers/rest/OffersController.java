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

        if (offers.add(offer)) {

            response.status(201);

            RespInfo res = new RespInfo("Successfully created", 201, Paths.replaceId(Paths.SINGLE_OFFER, offer.name));

            return Json.respInfoToJson(res);
        } else {
            response.status(400);

            RespInfo res = new RespInfo("Offer already present", 400, Paths.replaceId(Paths.SINGLE_OFFER, offer.name));

            return Json.respInfoToJson(res);

        }
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
        Offer offer = Json.offerFromJson(request.body());

        if (offers.replace(offer)) {

            response.status(202);

            RespInfo res = new RespInfo("Successfully modified", 202, Paths.replaceId(Paths.SINGLE_OFFER, offer.name));

            return Json.respInfoToJson(res);
        } else {
            response.status(400);

            RespInfo res = new RespInfo("Offer not present", 400, "");

            return Json.respInfoToJson(res);

        }
    }

    public String cancel(Request request, Response response) {
        String name = request.params("name");

        if (offers.cancel(name)){
            response.status(202);

            RespInfo res = new RespInfo("Successfully cancelled", 202, Paths.replaceId(Paths.SINGLE_OFFER, name));

            return Json.respInfoToJson(res);
        } else{
            return null; //404
        }


    }

    public String query(Request request, Response response) {
        String word = request.queryParams("description");
        return Json.offersToJson( offers.queryByDescription(word));
    }
}
