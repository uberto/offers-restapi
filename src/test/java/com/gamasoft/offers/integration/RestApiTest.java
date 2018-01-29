package com.gamasoft.offers.integration;

import com.gamasoft.offers.model.Offer;
import com.gamasoft.offers.model.OffersRepository;
import com.gamasoft.offers.rest.Json;
import com.gamasoft.offers.rest.RestServer;
import com.google.gson.Gson;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

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
        assertThat(offerPage).contains("OFF1");

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
    public void rejectNewOfferWIthSameName() throws Exception {

        String json = Json.offerToJson(createOffer("OFF1"));
        ContentResponse response = httpClient.POST("http://localhost:8081/offers")
                .content(new StringContentProvider(json), "application/json")
                .send();

        assertThat(response.getStatus()).isEqualTo(400);
        String msg = response.getContentAsString();

        assertThat(msg).contains("Offer already present");
    }


    @Test
    public void getAllOffers() throws Exception {

        String allOffersJson = httpClient.GET("http://localhost:8081/offers").getContentAsString();

//        System.out.println(allOffersJson);
        assertThat(allOffersJson).contains("OFF1");
        assertThat(allOffersJson).contains("OFF2");
        assertThat(allOffersJson).contains("OFF3");
        assertThat(allOffersJson).contains("OFF4");
        assertThat(allOffersJson).contains("OFF5");
        Gson gson = new Gson();
        List list = gson.fromJson(allOffersJson, List.class);

        assertThat(list).hasSize(5);

    }

    @Test
    public void cancelAnOffer() throws Exception {
        ContentResponse response = httpClient.newRequest("http://localhost:8081/offer/OFF2")
                .method(HttpMethod.DELETE)
                .send();

        assertThat(response.getStatus()).isEqualTo(202);
        String msg = response.getContentAsString();

        assertThat(msg).isEqualTo("{\"message\":\"Successfully cancelled\",\"httpStatus\":202,\"resouceUri\":\"/offer/OFF2\"}");

    }

    @Test
    public void modifyAnOffer() throws Exception {

        String json = Json.offerToJson(createOffer("OFF1").withDescription("Better description"));

        ContentResponse response = httpClient.newRequest("http://localhost:8081/offer/OFF1/edit")
                .method(HttpMethod.PUT)
                .content(new StringContentProvider(json), "application/json")
                .send();
        assertThat(response.getStatus()).isEqualTo(202);
        String msg = response.getContentAsString();

        assertThat(msg).isEqualTo("{\"message\":\"Successfully modified\",\"httpStatus\":202,\"resouceUri\":\"/offer/OFF1\"}");

    }


    @Test
    public void queryByDescription() throws Exception {

        String allOffersJson = httpClient.GET("http://localhost:8081/offers/search?description=bad").getContentAsString();

        assertThat(allOffersJson).contains("OFF3");
        assertThat(allOffersJson).contains("OFF5");
        Gson gson = new Gson();
        List list = gson.fromJson(allOffersJson, List.class);

        assertThat(list).hasSize(2);

    }


}
