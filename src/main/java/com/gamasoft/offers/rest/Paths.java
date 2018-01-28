package com.gamasoft.offers.rest;

public class Paths {
    public static final String HOME = "/";
    public static final String SINGLE_OFFER = "/offer/:name";
    public static final String ALL_OFFERS = "/offers";

    public static String replaceId(String path, String name) {
        return path.replaceAll(":name", name);
    }
}
