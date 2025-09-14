package io.bitvelocity.infra.util;

/**
 * Central naming helper. Future: include env prefix, truncation, hashing, etc.
 */
public final class Naming {

    private static final String BASE = "bitvelocity";

    private Naming() {}

    public static String resource(String domain, String component) {
        return (BASE + "-" + domain + "-" + component).toLowerCase();
    }
}