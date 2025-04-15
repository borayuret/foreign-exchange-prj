package com.borayuret.fxapi.controller;

import com.borayuret.fxapi.dto.ExchangeRateResponseDTO;
import com.borayuret.fxapi.service.ExchangeRateService;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that exposes an endpoint to retrieve exchange rates between two currencies.
 * Delegates the actual exchange rate logic to the ExchangeRateService.
 */
@RestController
@RequestMapping("/api/v1/rate")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    /**
     * Constructor-based dependency injection of the service layer.
     *
     * @param exchangeRateService service responsible for exchange rate logic
     */
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    /**
     * GET endpoint to retrieve the exchange rate between two currencies.
     *
     * Example:
     * GET /api/v1/rate?from=USD&to=EUR
     *
     * @param from the base/source currency (e.g., "USD")
     * @param to   the target currency (e.g., "EUR")
     * @return exchange rate DTO including from, to, and rate
     */
    @GetMapping
    public ExchangeRateResponseDTO getExchangeRate(
            @RequestParam String from,
            @RequestParam String to) {
        // Convert input to uppercase to ensure case-insensitive matching
        return exchangeRateService.getExchangeRate(from.toUpperCase(), to.toUpperCase());
    }
}
