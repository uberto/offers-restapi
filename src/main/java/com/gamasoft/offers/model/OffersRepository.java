package com.gamasoft.offers.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class OffersRepository {

    //this class has the Reponsability to store and retrive Offers from the persistence.
    //for the example it's just using memory

    Map<String, Offer> memCache = new ConcurrentHashMap<>();

    public Boolean add(Offer offer) {

        if (offer == null || offer.name == null || offer.name.isEmpty())
            throw new ModelException("The offer is not valid! It must have a name!");

        if (memCache.containsKey(offer.name)) //already present
            return false;

        memCache.put(offer.name, offer);
        return true;
    }

    public Offer get(String name) {
        return memCache.get(name);
    }

    public List<Offer> getAll() {
        return memCache.values().stream()
                .filter(o -> !o.isCancelled && !o.isExpired())
                .collect(Collectors.toList());
    }

    public Boolean cancel(String name) {

        if (!memCache.containsKey(name))
            return false;

        memCache.put(name, memCache.get(name).cancel());

        return true;
    }

    public Boolean replace(Offer offer) {
        if (!memCache.containsKey(offer.name))
            return false;

        memCache.put(offer.name, offer);

        return true;
    }

    public List<Offer> queryByDescription(String word) {
        //return all Offers that contain the word (case insensitive) in description
        String search = word.toLowerCase();
        return getAll().stream()
                .filter(o -> o.description.toLowerCase().contains(search))
                .collect(Collectors.toList());
    }
}
