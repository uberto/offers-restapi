package com.gamasoft.offers.rest;

import com.gamasoft.offers.model.Offer;


public class OfferHateoas extends Offer {

    public final Link viewLink;

    public final Link editLink;

    public OfferHateoas(Offer offer) {
        super(offer.name, offer.description, offer.currency, offer.price, offer.validity, offer.creationDate, offer.isCancelled);

        viewLink = new Link("view", Paths.SINGLE_OFFER.replace(":name", this.name));
        editLink = new Link("edit", Paths.SINGLE_OFFER_EDIT.replace(":name", this.name));

    }
}
