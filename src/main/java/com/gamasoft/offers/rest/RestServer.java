package com.gamasoft.offers.rest;

import com.gamasoft.offers.model.OffersRepository;
import spark.ResponseTransformer;

import static spark.Spark.*;

public class RestServer {
    private final OffersController offersController;

    public RestServer(OffersRepository offers) {
        offersController = new OffersController(offers);    }

    public void start(int port) {

        port(port);

        get(Paths.HOME, (request, response) -> "Greetings from your BestCompany!");

        post(Paths.ALL_OFFERS, "application/json", offersController::createOffer);

        get(Paths.ALL_OFFERS, offersController::showAll);

        get(Paths.SINGLE_OFFER, offersController::showSingle);

        put(Paths.SINGLE_OFFER_EDIT, "application/json", offersController::edit);

        delete(Paths.SINGLE_OFFER, offersController::cancel);

        get(Paths.OFFERS_QUERY, offersController::query);
    }

    public void stopServer() {
        stop();
    }
}
