package com.sample.webhook.resource;

import com.sample.webhook.App;
import com.sample.webhook.AppConfiguration;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.DropwizardTestSupport;
import io.dropwizard.testing.ResourceHelpers;
import org.glassfish.jersey.client.ClientProperties;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class WebhookResourceTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(WebhookResourceTest.class);
    static Client client;
    static Connection c = null;
    public static final DropwizardTestSupport<AppConfiguration> RULE = new DropwizardTestSupport<AppConfiguration>(
            App.class, ResourceHelpers.resourceFilePath("local.yml"));

    @BeforeClass
    public static void beforeClass() throws SQLException {
        RULE.before();
        client = new JerseyClientBuilder(RULE.getEnvironment()).build("test client");
        client.property(ClientProperties.CONNECT_TIMEOUT, 100000);
        client.property(ClientProperties.READ_TIMEOUT, 100000);
        client.property("jersey.config.client.connectTimeout", 500000);
        client.property("jersey.config.client.readTimeout", 100000);

    }

    @AfterClass
    public static void afterClass() {
        RULE.after();
    }

    @Test
    public void testProcessEvent() throws IOException {
        LOGGER.info("Running Test {}", new Object() {
        }.getClass().getEnclosingMethod().getName());

        Response response =
                client
                .target(String.format("http://localhost:%d/twitter", RULE.getLocalPort()))
                .request().post(Entity.json("{\"event\":\"I am sending an event\"}"));

        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}
