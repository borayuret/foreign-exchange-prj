package com.borayuret.fxapi.service;

import com.borayuret.fxapi.client.ExchangeRateClient;
import com.borayuret.fxapi.dto.ExchangeRateResponseDTO;
import org.springframework.stereotype.Service;

/**
 * Service layer responsible for handling currency exchange rate logic.
 * It acts as a bridge between the controller and the external API client.
 */
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
    public ExchangeRateResponseDTO getExchangeRate(String from, String to) {
        double rate = exchangeRateClient.getRate(from, to);
        return new ExchangeRateResponseDTO(from, to, rate);
    }
}
