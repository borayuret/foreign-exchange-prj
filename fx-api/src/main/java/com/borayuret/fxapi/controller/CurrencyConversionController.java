package com.borayuret.fxapi.controller;

import com.borayuret.fxapi.dto.CurrencyConversionRequestDTO;
import com.borayuret.fxapi.dto.CurrencyConversionResponseDTO;
import com.borayuret.fxapi.service.CurrencyConversionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that handles currency conversion requests.
 * Exposes an endpoint to convert an amount from one currency to another.
 */
@RestController
@RequestMapping("/api/v1")
public class CurrencyConversionController {

    private final CurrencyConversionService conversionService;

    /**
     * Constructor-based injection of the conversion service.
     *
     * @param conversionService service handling the conversion logic
     */
    public CurrencyConversionController(CurrencyConversionService conversionService) {
        this.conversionService = conversionService;
    }

    /**
     * POST endpoint to convert a currency amount from one currency to another.
     * Accepts a JSON request body and returns a JSON response.
     *
     * Example Request:
     * POST /api/v1/convert
     * {
     *   "amount": 100,
     *   "from": "USD",
     *   "to": "EUR"
     * }
     *
     * @param requestDTO conversion request with amount, source and target currencies
     * @return response containing the converted amount and a unique transaction ID
     */
    @PostMapping(value = "/convert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CurrencyConversionResponseDTO convert(@RequestBody CurrencyConversionRequestDTO requestDTO) {
        return conversionService.convertCurrency(requestDTO);
    }
}
