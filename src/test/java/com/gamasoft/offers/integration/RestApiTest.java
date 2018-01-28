package com.gamasoft.offers.integration;

import com.gamasoft.offers.model.Offer;
import com.gamasoft.offers.model.OffersRepository;
import com.gamasoft.offers.rest.RestServer;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Duration;

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



}
