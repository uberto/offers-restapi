package com.gamasoft.offers;

import com.gamasoft.offers.model.OffersRepository;
import com.gamasoft.offers.rest.RestServer;

public class Application {

    public static void main(String[] args) {

        OffersRepository offers = new OffersRepository();

        RestServer ws = new RestServer(offers);

        ws.start(8080);
    }


}
