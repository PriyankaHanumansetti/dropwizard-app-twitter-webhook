package com.sample.webhook.resource;

import com.sample.webhook.RequestAuthenticationUtil;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Path("/twitter")
public class WebhookResource {

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

    protected String getEncodedString(final byte[] signBytes) {
        return Base64.getEncoder().encodeToString(signBytes);
    }}
