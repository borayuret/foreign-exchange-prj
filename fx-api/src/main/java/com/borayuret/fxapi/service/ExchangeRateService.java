package com.borayuret.fxapi.service;

import com.borayuret.fxapi.client.ExchangeRateClient;
import com.borayuret.fxapi.dto.ExchangeRateResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Service layer responsible for handling currency exchange rate logic.
 * It acts as a bridge between the controller and the external API client.
 */
@Slf4j
@Service
public class ExchangeRateService {

    private final ExchangeRateClient exchangeRateClient;

    /**
     * Constructor-based dependency injection of ExchangeRateClient.
     *
     * @param exchangeRateClient client used to retrieve exchange rates from external API
     */
    public ExchangeRateService(ExchangeRateClient exchangeRateClient) {
        this.exchangeRateClient = exchangeRateClient;
    }

    /**
     * Retrieves the exchange rate from one currency to another.
     *
     * @param from source currency (e.g., "USD")
     * @param to   target currency (e.g., "TRY")
     * @return a DTO containing from, to, and the exchange rate
     */
    @Cacheable(value = "exchangeRates", key = "'rate_' + #from + '_' + #to")
    public ExchangeRateResponseDTO getExchangeRate(String from, String to) {
        double rate = exchangeRateClient.getRate(from, to);
        log.info("Calling Fixer API for {} → {}", from, to);
        return new ExchangeRateResponseDTO(from, to, rate);
    }
}
