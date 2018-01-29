package com.gamasoft.offers.model;

import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.List;

import static com.gamasoft.offers.model.OfferTest.createOffer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OffersRepositoryTest {

    private OffersRepository repository;

    @Before
    public void setUp(){
        repository = createExampleRepository();
    }

    @Test
    public void addAndRetrieveOffers() {

        Offer off1 = repository.get("OFF1");
        assertThat(off1).isNotNull();
        assertThat(off1.name).isEqualTo("OFF1");

        Offer off2 = repository.get("OFF3");
        assertThat(off2).isNotNull();
        assertThat(off2.name).isEqualTo("OFF3");
    }

    @Test
    public void dontAddOffersWithSameName() {

        Boolean res = repository.add(createOffer("OFF1"));
        assertThat(res).isFalse();

        Boolean res2 = repository.add(createOffer("SPECIAL"));
        assertThat(res2).isTrue();
    }

    @Test
    public void blockOffersWithoutName() {

        assertThatThrownBy(() -> { repository.add(createOffer("")); }).isInstanceOf(ModelException.class)
                .hasMessageContaining("It must have a name!");

    }

    @Test
    public void getAllOffers() {

        List<Offer> offerList = repository.getAll();
        assertThat(offerList).hasSize(5);
    }

    @Test
    public void getAllOffersNotExpiredOrCancelled() throws InterruptedException {

        repository.cancel("OFF2");
        repository.cancel("OFF4");
        repository.cancel("OFF5");
        repository.replace(createOffer("OFF3").withValidity(Duration.ofMillis(1)));

        Thread.sleep(10);
        List<Offer> offerList = repository.getAll();
        assertThat(offerList).hasSize(1);
        assertThat(offerList.get(0).name).isEqualTo("OFF1");

    }

    @Test
    public void queryOffersByDescription() {

        List<Offer> offerList = repository.queryByDescription("good");
        assertThat(offerList).hasSize(2);
        assertThat(offerList.get(0).description).isEqualTo("Good offer");
        assertThat(offerList.get(1).description).isEqualTo("Not so good offer");

    }

    public static OffersRepository createExampleRepository() {
        OffersRepository repository = new OffersRepository();
        repository.add(new Offer("OFF1", "Good offer", "USD", 123.4, Duration.ofDays(1)));
        repository.add(new Offer("OFF2", "Not so good offer", "USD", 234.5, Duration.ofDays(5)));
        repository.add(new Offer("OFF3", "Bad offer", "USD", 345.6, Duration.ofDays(10)));
        repository.add(new Offer("OFF4", "New offer", "USD", 10.0, Duration.ofDays(1)));
        repository.add(new Offer("OFF5", "Old offer but not bad", "USD", 12.2, Duration.ofDays(1)));
        return repository;
    }
}