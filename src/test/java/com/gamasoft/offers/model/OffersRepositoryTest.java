package com.gamasoft.offers.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.Duration;
import java.util.List;

import static com.gamasoft.offers.model.OfferTest.createOffer;
import static org.assertj.core.api.Assertions.assertThat;

public class OffersRepositoryTest {


    @Test
    public void addAndRetrieveOffers() {
        OffersRepository repository = createExampleRepository();


        Offer off1 = repository.get("OFF1");
        assertThat(off1).isNotNull();
        assertThat(off1.name).isEqualTo("OFF1");

        Offer off2 = repository.get("OFF3");
        assertThat(off2).isNotNull();
        assertThat(off2.name).isEqualTo("OFF3");
    }

    @Test
    public void dontAddOffersWithSameName() {
        OffersRepository repository = createExampleRepository();

        Boolean res = repository.add(createOffer("OFF1"));
        assertThat(res).isFalse();

        Boolean res2 = repository.add(createOffer("SPECIAL"));
        assertThat(res2).isTrue();
    }

    @Test
    public void getAllOffers() {
        OffersRepository repository = createExampleRepository();

        List<Offer> offerList = repository.getAll();
        assertThat(offerList).hasSize(3);
    }

    @Test
    public void getAllOffersNotExpiredOrCancelled() {
        OffersRepository repository = createExampleRepository();

        repository.cancel("OFF2");
        repository.replace(createOffer("OFF3").withValidity(Duration.ofMillis(1)));

        List<Offer> offerList = repository.getAll();
        assertThat(offerList).hasSize(1);
        assertThat(offerList.get(0).name).isEqualTo("OFF1");

    }

    public static OffersRepository createExampleRepository() {
        OffersRepository repository = new OffersRepository();
        repository.add(new Offer("OFF1", "Good offer", "USD", 123.4, Duration.ofDays(1)));
        repository.add(new Offer("OFF2", "Not so good offer", "USD", 234.5, Duration.ofDays(5)));
        repository.add(new Offer("OFF3", "Bad offer", "USD", 345.6, Duration.ofDays(10)));
        return repository;
    }
}