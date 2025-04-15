package com.borayuret.fxapi.controller;

import com.borayuret.fxapi.dto.CurrencyConversionRequestDTO;
import com.borayuret.fxapi.dto.CurrencyConversionResponseDTO;
import com.borayuret.fxapi.model.CurrencyConversion;
import com.borayuret.fxapi.service.CurrencyConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

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


    /**
     * Retrieves conversion history based on transaction ID or date filter.
     * Pagination is supported through Pageable parameters.
     *
     * @param transactionId optional UUID to filter by transaction ID
     * @param date optional date to filter by conversion date (yyyy-MM-dd)
     * @param pageable pagination settings (page, size)
     * @return paginated list of conversion records
     */
    @GetMapping("/history")
    public Page<CurrencyConversion> getConversionHistory(
            @RequestParam(required = false) UUID transactionId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Pageable pageable) {

        return conversionService.getConversionHistory(transactionId, date, pageable);
    }

}
