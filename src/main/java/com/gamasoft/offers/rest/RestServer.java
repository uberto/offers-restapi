package com.gamasoft.offers.rest;

import com.gamasoft.offers.model.OffersRepository;

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
//
//        put("/offer/:id", "application/json", offersController::edit);
//
//        delete("/offer/:id", offersController::cancel);

    }

    public void stopServer() {
        stop();
    }
}
