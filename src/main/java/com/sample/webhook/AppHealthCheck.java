package com.sample.webhook;

import com.codahale.metrics.health.HealthCheck;

public class AppHealthCheck extends HealthCheck {

    public AppHealthCheck() {
        super();
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}