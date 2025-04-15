package com.borayuret.fxapi.service;

import com.borayuret.fxapi.client.ExchangeRateClient;
import com.borayuret.fxapi.dto.CurrencyConversionRequestDTO;
import com.borayuret.fxapi.dto.CurrencyConversionResponseDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service layer responsible for handling currency conversion logic.
 * Calculates the converted amount using exchange rates and returns a response with a unique transaction ID.
 */
@Service
public class CurrencyConversionService {

    private final ExchangeRateClient exchangeRateClient;

    /**
     * Constructor-based dependency injection.
     *
     * @param exchangeRateClient client for fetching real-time exchange rates
     */
    public CurrencyConversionService(ExchangeRateClient exchangeRateClient) {
        this.exchangeRateClient = exchangeRateClient;
    }

    /**
     * Converts a given amount from one currency to another.
     * Generates a unique transaction ID for tracking the conversion.
     *
     * @param request the conversion request containing amount, source, and target currencies
     * @return a response DTO containing the converted amount and a transaction ID
     */
    public CurrencyConversionResponseDTO convertCurrency(CurrencyConversionRequestDTO request) {
        double rate = exchangeRateClient.getRate(request.getFrom(), request.getTo());
        double convertedAmount = request.getAmount() * rate;
        UUID transactionId = UUID.randomUUID();

        return new CurrencyConversionResponseDTO(convertedAmount, transactionId);
    }
}
