package com.borayuret.fxapi.service;

import com.borayuret.fxapi.client.ExchangeRateClient;
import com.borayuret.fxapi.client.ExchangeRateClientFactory;
import com.borayuret.fxapi.client.FixerExchangeRateClient;
import com.borayuret.fxapi.dto.ExchangeRateResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Service layer responsible for handling currency exchange rate logic.
 * It acts as a bridge between the controller and the external API client.
 */
@Slf4j
@Service
public class ExchangeRateService {

    private final ExchangeRateClientFactory exchangeRateClientFactory;

    // Injecting ExchangeRateClientFactory through constructor
    public ExchangeRateService(ExchangeRateClientFactory exchangeRateClientFactory) {
        this.exchangeRateClientFactory = exchangeRateClientFactory;
    }

    @Value("${exchange.api.provider:fixer}")  // Get provider from application.properties
    private String provider;

    /**
     * Retrieves the exchange rate from one currency to another.
     *
     * @param from source currency (e.g., "USD")
     * @param to   target currency (e.g., "TRY")
     * @return a DTO containing from, to, and the exchange rate
     */
    @Cacheable(value = "exchangeRates", key = "'rate_' + #from + '_' + #to")
    public ExchangeRateResponseDTO getExchangeRate(String from, String to) {



        // Select the appropriate client based on the provider
        ExchangeRateClient exchangeRateClient = exchangeRateClientFactory.getClient(provider);
        double rate = exchangeRateClient.getRate(from, to);
        return new ExchangeRateResponseDTO(from, to, rate);
    }
}
