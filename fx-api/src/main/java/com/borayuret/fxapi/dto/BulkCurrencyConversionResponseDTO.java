package com.borayuret.fxapi.dto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO representing the response to a bulk currency conversion.
 * Contains a list of results for each row in the CSV file.
 */
public class BulkCurrencyConversionResponseDTO implements Serializable {

    /**
     * List of successful currency conversion results,
     * each including converted amount and transaction ID.
     */
    private List<CurrencyConversionResponseDTO> results;

    private static final long serialVersionUID = 1L;

    public BulkCurrencyConversionResponseDTO() {}

    public BulkCurrencyConversionResponseDTO(List<CurrencyConversionResponseDTO> results) {
        this.results = results;
    }

    public List<CurrencyConversionResponseDTO> getResults() {
        return results;
    }

    public void setResults(List<CurrencyConversionResponseDTO> results) {
        this.results = results;
    }
}
