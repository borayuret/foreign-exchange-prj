package com.borayuret.fxapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Request DTO for currency conversion.
 * Contains the amount to convert, the source currency, and the target currency.
 */
@Schema(description = "Request object for currency conversion")
public class CurrencyConversionRequestDTO {

    @Schema(description = "Amount to convert", example = "100.0")
    private double amount;
    @Schema(description = "Source currency", example = "USD")
    private String from;
    @Schema(description = "Target currency", example = "EUR")
    private String to;

    public CurrencyConversionRequestDTO() {
    }

    public CurrencyConversionRequestDTO(double amount, String from, String to) {
        this.amount = amount;
        this.from = from;
        this.to = to;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
