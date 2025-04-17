package com.borayuret.fxapi.controller;

import com.borayuret.fxapi.dto.BulkCurrencyConversionResponseDTO;
import com.borayuret.fxapi.dto.CurrencyConversionRequestDTO;
import com.borayuret.fxapi.dto.CurrencyConversionResponseDTO;
import com.borayuret.fxapi.model.CurrencyConversion;
import com.borayuret.fxapi.service.CurrencyConversionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.UUID;

/**
 * REST controller that handles currency conversion requests.
 * Exposes an endpoint to convert an amount from one currency to another.
 */
@Tag(name = "Currency Conversion API", description = "Endpoints for converting currency and viewing history")
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
    @Operation(summary = "Convert currency amount", description = "Converts an amount from one currency to another using exchange rates.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conversion successful"),
            @ApiResponse(responseCode = "400", description = "Malformed or invalid JSON (ERR_MALFORMED_JSON)"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error (ERR_INTERNAL_SERVER)")
    })
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
    @Operation(summary = "Get conversion history", description = "Returns a paginated list of conversion records filtered by transactionId or date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "History retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Missing or invalid parameters (ERR_INVALID_PARAMS / ERR_INVALID_TYPE)"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error (ERR_INTERNAL_SERVER)")
    })
    @GetMapping("/history")
    public Page<CurrencyConversion> getConversionHistory(
            @RequestParam(required = false) UUID transactionId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Pageable pageable) {

        return conversionService.getConversionHistory(transactionId, date, pageable);
    }


    /**
     * Accepts a CSV file upload and performs bulk currency conversion.
     *
     * @param file CSV file with rows like: amount,from,to
     * @return list of conversion results for each row
     */
    @Operation(
            summary = "Bulk currency conversion via CSV upload",
            description = "Processes a CSV file containing multiple currency conversion requests."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All conversions processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid CSV format or file upload error"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @PostMapping(value = "/convert/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BulkCurrencyConversionResponseDTO convertCsv(@RequestPart("file") MultipartFile file) {
        return conversionService.convertFromCsv(file);
    }

}

