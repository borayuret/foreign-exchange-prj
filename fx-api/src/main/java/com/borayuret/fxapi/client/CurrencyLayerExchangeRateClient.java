package com.borayuret.fxapi.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@Slf4j
@Component
public class CurrencyLayerExchangeRateClient implements ExchangeRateClient {

    @Value("${currencylayer.api.base-url}")
    private String baseUrl;

    @Value("${currencylayer.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    private static CurrencyLayerExchangeRateClient instance; // Singleton instance

    // Private constructor to prevent instantiation
    private CurrencyLayerExchangeRateClient() {
    }

    // Public method to get the Singleton instance
    public static synchronized CurrencyLayerExchangeRateClient getInstance() {
        if (instance == null) {
            instance = new CurrencyLayerExchangeRateClient();
        }
        return instance;
    }

    @Override
    public Double getRate(String from, String to) {
        String url = String.format("%s?access_key=%s&currencies=%s&source=%s&format=1", baseUrl, apiKey, to, from);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, null, Map.class);
        Map<String, Object> body = response.getBody();

        log.info("Calling CurrencyLayer API for {} → {}", from, to);

        Boolean success = (Boolean) body.get("success");
        if (success == null || !success) {
            throw new RuntimeException("CurrencyLayer API error: " + body.get("error"));
        }

        Map<String, Object> rates = (Map<String, Object>) body.get("quotes");
        Double rate = ((Number) rates.get("USD" + to)).doubleValue();

        return rate;
    }
}
