package com.springboot.demo.controller.ehcache.config;

import java.util.concurrent.TimeUnit;

import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;

public class CustomEhCacheExpiry implements ExpiryPolicy {

    private static final Duration TWO_MINUTES = new Duration(TimeUnit.MINUTES, 2);

    @Override
    public Duration getExpiryForCreation() {
        return Duration.ONE_MINUTE;
    }

    @Override
    public Duration getExpiryForAccess() {
        return TWO_MINUTES;
    }

    @Override
    public Duration getExpiryForUpdate() {
        return TWO_MINUTES;
    }
}
