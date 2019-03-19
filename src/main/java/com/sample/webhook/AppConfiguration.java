package com.sample.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.cache.CacheBuilderSpec;
import io.dropwizard.Configuration;

public class AppConfiguration extends Configuration {

    @JsonProperty
    private CacheBuilderSpec authenticationCachePolicy;

    public CacheBuilderSpec getAuthenticationCachePolicy() {
        return this.authenticationCachePolicy;
    }

    public void setAuthenticationCachePolicy(CacheBuilderSpec authenticationCachePolicy) {
        this.authenticationCachePolicy = authenticationCachePolicy;
    }

}
