package com.sample.webhook;

import io.dropwizard.Application;

import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sample.webhook.resource.WebhookResource;


public class App extends Application<AppConfiguration> {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public String getName() {
        return "configApi";
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bs) {

    }

    @Override
    public void run(AppConfiguration appConfiguration, Environment e) throws Exception {

        logger.info("Starting Backfill Api..");

        logger.debug("Registering Resources");
        e.jersey().register(new WebhookResource());

        logger.info("Registering HealthCheck App");
        e.healthChecks().register("app", new AppHealthCheck());
    }
}