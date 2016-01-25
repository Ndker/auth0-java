/*
 * Auth0.java
 *
 * Copyright (c) 2015 Auth0 (http://auth0.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.auth0;

import com.auth0.authentication.AuthenticationAPIClient;
import com.auth0.util.BaseMetrics;
import com.auth0.util.Metrics;
import com.squareup.okhttp.HttpUrl;

/**
 * Represents your Auth0 account information (clientId & domain),
 * and it's used to obtain clients for Auth0's APIs.
 * <pre>{@code
 * Auth0 auth0 = new Auth0("YOUR_CLIENT_ID", "YOUR_DOMAIN");
 * }</pre>
 */
public class Auth0 {

    /**
     * Version of auth0-java
     */
    public static final String VERSION = "1.0.0";
    /**
     * Name of the library auth0-java
     */
    public static final String NAME = "auth0-java";

    private static final String AUTH0_US_CDN_URL = "https://cdn.auth0.com";
    private static final String DOT_AUTH0_DOT_COM = ".auth0.com";

    private final String clientId;
    private final String domainUrl;
    private final String configurationUrl;
    private Metrics metrics;

    /**
     * Creates a new object using clientId & domain
     * @param clientId of your Auth0 application
     * @param domain of your Auth0 account
     */
    public Auth0(String clientId, String domain) {
        this(clientId, domain, null);
    }

    /**
     * Creates a new object using clientId, domain and configuration domain.
     * Useful when using a on-premise auth0 server that is not in the public cloud,
     * otherwise we recommend using the constructor {@link #Auth0(String, String)}
     * @param clientId of your Auth0 application
     * @param domain of your Auth0 account
     * @param configurationDomain where Auth0's configuration will be fetched. By default is Auth0 public cloud
     */
    public Auth0(String clientId, String domain, String configurationDomain) {
        this.clientId = clientId;
        this.domainUrl = ensureUrlString(domain);
        this.configurationUrl = resolveConfiguration(configurationDomain, this.domainUrl);
        this.metrics = new BaseMetrics();
        this.metrics.usingLibrary(Auth0.NAME, Auth0.VERSION);
    }

    /**
     * @return your Auth0 application client identifier
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @return your Auth0 account domain url
     */
    public String getDomainUrl() {
        return domainUrl;
    }

    /**
     * @return your account configuration url
     */
    public String getConfigurationUrl() {
        return configurationUrl;
    }

    /**
     * @return a new Authentication API client using your account credentials
     */
    public AuthenticationAPIClient newAuthenticationAPIClient() {
        return new AuthenticationAPIClient(this);
    }

    /**
     * @return Url to perform the web flow of OAuth
     */
    public String getAuthorizeUrl() {
        return HttpUrl.parse(domainUrl).newBuilder()
                .addEncodedPathSegment("authorize")
                .build()
                .toString();
    }

    /**
     * @return Auth0 metrics sent in every requests
     */
    public Metrics getMetrics() {
        return metrics;
    }

    /**
     * Define what metrics are sent to Auth0 on every request.
     * @param metrics to send or nil to send nothing
     */
    public void setMetrics(Metrics metrics) {
        if (metrics == null) {
            this.metrics = new BaseMetrics();
        } else {
            this.metrics = metrics;
        }
    }

    private String resolveConfiguration(String configurationDomain, String domainUrl) {
        String url = ensureUrlString(configurationDomain);
        if (configurationDomain == null && domainUrl != null) {
            final HttpUrl domainUri = HttpUrl.parse(domainUrl);
            final String host = domainUri.host();
            if (host.endsWith(DOT_AUTH0_DOT_COM)) {
                String[] parts = host.split("\\.");
                if (parts.length > 3) {
                    url = "https://cdn." + parts[parts.length-3] + DOT_AUTH0_DOT_COM;
                } else {
                    url = AUTH0_US_CDN_URL;
                }
            } else {
                url = domainUrl;
            }
        }
        return url;
    }

    private String ensureUrlString(String url) {
        String safeUrl = null;
        if (url != null) {
            safeUrl = url.startsWith("http") ? url : "https://" + url;
        }
        return safeUrl;
    }

}
