package com.sample.webhook.resource;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.webhook.RequestAuthenticationUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.ws.rs.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Path("/twitter")
public class WebhookResource {


    class Event {
        @JsonRawValue
        private String body;
        private MultivaluedMap<String, String> headers;

        Event() {};

        Event(String body, MultivaluedMap<String, String> headers) {
            this.body = body;
            this.headers = headers;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public MultivaluedMap<String, String> getHeaders() {
            return headers;
        }

        public void setHeaders(MultivaluedMap<String, String> headers) {
            this.headers = headers;
        }
    }

    static Queue<Event> queue = new LinkedList<>();

    @GET
    @Path("/hi")
    public String hi() {
        return "Welcome to Webhooks FrontEnd Application";
    }

    /**
     * Authorizes incoming webhook events
     *
     * @param requestHeaders http request header
     * @param eventBody      message payload
     * @return Response if valid or bad request
     */
    @POST
    public Response hook(@Context HttpHeaders requestHeaders, String eventBody) {

        if (StringUtils.isEmpty(eventBody)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Response response;
        try {
            System.out.println("Event body : " + eventBody);
            queue.add(new Event(eventBody, requestHeaders.getRequestHeaders()));
            response = Response.ok().build();

        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }

    @GET
    public Response validateHeartBeat(@QueryParam("crc_token") String crcToken) {
        byte[] requestDigest;
        Response finalResponse;
        String consumerSecretStr = "O1BQ6DKMVhDNNBpTrGWlKLXDTeEyHWABAmGMYyq8U";
        try {
            requestDigest = RequestAuthenticationUtil.getDigest(consumerSecretStr, crcToken, "HmacSHA256");
            String response = "{\"response_token\":\"" + "sha256=" + getEncodedString(requestDigest) + "\"}";
            finalResponse = Response.ok().entity(response).build();

        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            finalResponse = Response.status(Response.Status.BAD_REQUEST).build();
        }
        return finalResponse;
    }

    @GET
    @Path("/events")
    @Produces("application/json")
    public Response getEvents() throws JsonProcessingException {
        Response finalResponse;
        Event event = queue.peek();
        if (event != null) {
            return Response.ok().entity(Entity.json(event)).build();
        } else {
            return Response.noContent().build();
        }
    }

    protected String getEncodedString(final byte[] signBytes) {
        return Base64.getEncoder().encodeToString(signBytes);
    }}
