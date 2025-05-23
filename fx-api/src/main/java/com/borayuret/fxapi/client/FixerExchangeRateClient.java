package com.borayuret.fxapi.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Map;

/**
 * Client class responsible for communicating with the external exchange rate API (Fixer.io).
 * Fetches currency exchange rates for two given currencies.
 */
@Slf4j
@Component
public class FixerExchangeRateClient implements ExchangeRateClient {

    private static FixerExchangeRateClient instance;

    // Base URL of the Fixer.io API, injected from application properties
    @Value("${fx.api.base-url}")
    private String baseUrl;

    // API access key (Fixer.io API key), injected from application properties
    @Value("${fx.api.key}")
    private String apiKey;

    // Simple HTTP client to perform REST requests
    private final RestTemplate restTemplate = new RestTemplate();

    // Private constructor to prevent instantiation from outside
    private FixerExchangeRateClient(@Value("${fx.api.base-url}") String baseUrl,
                                    @Value("${fx.api.key}") String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    // Public method to provide access to the instance
    public static synchronized FixerExchangeRateClient getInstance(@Value("${fx.api.base-url}") String baseUrl,
                                                                   @Value("${fx.api.key}") String apiKey) {
        if (instance == null) {
            instance = new FixerExchangeRateClient(baseUrl, apiKey);
        }
        return instance;
    }


    /**
     * Fetches the exchange rate between two currencies using Fixer.io API.
     *
     * @param from the source currency (e.g., "USD")
     * @param to   the target currency (e.g., "TRY")
     * @return exchange rate (how much 1 unit of 'from' is in terms of 'to')
     */
    public Double getRate(String from, String to) {
        // Construct the API URL with both currencies
        String url = String.format("%s/latest?access_key=%s&symbols=%s,%s", baseUrl, apiKey, from, to);

        // Send a GET request and parse the response as a Map
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, null, Map.class);
        Map<String, Object> body = response.getBody();

        log.info("Calling Fixer API for {} → {}", from, to);


        // Check if the API call was successful
        Boolean success = (Boolean) body.get("success");
        if (success == null || !success) {
            System.out.println("Fixer API error: " + body.get("error"));
            throw new RuntimeException("Fixer API error: " + body.get("error"));
        }

        // Extract the "rates" object from the response
        Map<String, Object> rates = (Map<String, Object>) body.get("rates");

        if (rates == null) {
            throw new RuntimeException("Rates are null");
        }

        // Retrieve rates for the requested currencies
        Double fromRate = ((Number) rates.get(from)).doubleValue();
        Double toRate = ((Number) rates.get(to)).doubleValue();

        if (fromRate == null || toRate == null) {
            throw new RuntimeException("One of the currencies is missing in the response.");
        }

        // Since all Fixer.io rates are based on EUR,
        // the exchange rate from currency A to B = rate(B) / rate(A)
        return toRate / fromRate;
    }
}
