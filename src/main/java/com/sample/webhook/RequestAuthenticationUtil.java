package com.sample.webhook;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
public final class RequestAuthenticationUtil {
    public static final String HMAC_SHA1_ALG = "HmacSHA1";
    public static final String HMAC_SHA256_ALG = "HmacSHA256";

    /**
     * Creates digest using key,value and sha algorithm name provided
     * 
     * @param key
     *            auth consumer secret key
     * @param value
     *            message json string
     * @param shaMethod
     *            which sha algorithm has to be used to generate digest
     * @return bytearray calculated using above values
     * @throws NoSuchAlgorithmException
     *             If sha alogrithm is invalid throws this error
     * @throws InvalidKeyException
     *             if incoming data is not valid
     */
    public static byte[] getDigest(final String key, final String value, final String shaMethod)
            throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(shaMethod);

        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), shaMethod);
        mac.init(signingKey);
        log.debug("Generating digest for {}, {}", key, value);
        byte[] rawHmac = mac.doFinal(value.getBytes());

        return rawHmac;
    }
}
