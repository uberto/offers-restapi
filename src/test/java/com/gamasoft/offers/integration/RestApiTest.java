package com.gamasoft.offers.integration;

import com.gamasoft.offers.model.Offer;
import com.gamasoft.offers.model.OffersRepository;
import com.gamasoft.offers.rest.Json;
import com.gamasoft.offers.rest.RestServer;
import com.google.gson.Gson;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Duration;
import java.util.List;

import static com.gamasoft.offers.model.OfferTest.createOffer;
import static com.gamasoft.offers.model.OffersRepositoryTest.createExampleRepository;
import static org.assertj.core.api.Assertions.assertThat;

public class RestApiTest {

    private static RestServer restServer;
    private static HttpClient httpClient;

    @BeforeClass
    public static void startServer() throws Exception {

        OffersRepository offers = createExampleRepository();

        restServer = new RestServer(offers);
        restServer.start(8081);

        httpClient = new HttpClient();
        httpClient.start();

        Thread.sleep(500);
    }

    @AfterClass
    public static void stopServer() throws Exception {

        httpClient.stop();

        restServer.stopServer();
    }

    @Test
    public void getSingleOffer() throws Exception {

        String offerPage = httpClient.GET("http://localhost:8081/offer/OFF1").getContentAsString();
        assertThat(offerPage).contains("Good offer");

    }

    @Test
    public void addNewOffer() throws Exception {

        String json = Json.offerToJson(createOffer("SPECIAL"));
        ContentResponse response = httpClient.POST("http://localhost:8081/offers")
                .content(new StringContentProvider(json), "application/json")
                .send();

        assertThat(response.getStatus()).isEqualTo(201);
        String msg = response.getContentAsString();

        assertThat(msg).isEqualTo("{\"message\":\"Successfully created\",\"httpStatus\":201,\"resouceUri\":\"/offer/SPECIAL\"}");

    }

    @Test
    public void getAllOffers() throws Exception {

        String allOffersJson = httpClient.GET("http://localhost:8081/offers").getContentAsString();

        System.out.println(allOffersJson);
        assertThat(allOffersJson).contains("OFF1");
        assertThat(allOffersJson).contains("OFF2");
        assertThat(allOffersJson).contains("OFF3");
        Gson gson = new Gson();
        List list = gson.fromJson(allOffersJson, List.class);

        assertThat(list).hasSize(3);
//        offers.cancel("OFF2");
//        offers.replace(createOffer("OFF3").withValidity(Duration.ofMillis(1)));


    }





}
