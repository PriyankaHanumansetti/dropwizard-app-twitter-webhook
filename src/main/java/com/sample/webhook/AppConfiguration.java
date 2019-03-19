package com.sample.webhook;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.cache.CacheBuilderSpec;
import com.oracle.ci.api.authentication.dropwizard.ApiAuthenticatorConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.setup.Environment;

import javax.inject.Provider;
import javax.sql.DataSource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

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
